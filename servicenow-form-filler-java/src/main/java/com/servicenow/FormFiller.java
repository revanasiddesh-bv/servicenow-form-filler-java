package com.servicenow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
import com.servicenow.config.FormFillerConfig;
import com.servicenow.model.FormData;
import com.servicenow.model.FormField;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class FormFiller {
    private static final Logger logger = LoggerFactory.getLogger(FormFiller.class);
    private final Playwright playwright;
    private final Browser browser;
    private final BrowserContext context;
    private final Page page;
    private final FormFillerConfig config;
    private final String url;
    private final FormData formData;

    public FormFiller(String url, FormData formData, FormFillerConfig config) {
        this.url = url;
        this.formData = formData;
        this.config = config;

        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
            .setHeadless(config.isHeadless())
            .setSlowMo(config.getSlowMo());

        browser = playwright.chromium().launch(launchOptions);
        
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();
        if (config.getViewport() != null) {
            contextOptions.setViewportSize(
                config.getViewport().getWidth(), 
                config.getViewport().getHeight()
            );
        }
        if (config.isRecordVideo()) {
            contextOptions.setRecordVideoDir(Paths.get("videos"));
        }
        
        context = browser.newContext(contextOptions);
        if (config.isTrace()) {
            context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true));
        }
        
        page = context.newPage();
        configureTimeouts();
    }

    private void configureTimeouts() {
        if (config.getTimeouts() != null) {
            page.setDefaultNavigationTimeout(config.getTimeouts().getNavigation());
            page.setDefaultTimeout(config.getTimeouts().getElement());
        }
    }

    public void fillForm() {
        try {
            // Navigate to the form
            logger.info("Navigating to {}", url);
            page.navigate(url);

            // Handle login if credentials are provided
            if (config.getCredentials() != null) {
                handleLogin();
            }

            // Fill form fields
            for (Map.Entry<String, FormField> entry : formData.getFields().entrySet()) {
                String fieldId = entry.getKey();
                FormField field = entry.getValue();
                
                fillField(fieldId, field);
                
                if (config.getDelay() > 0) {
                    page.waitForTimeout(config.getDelay());
                }
            }

            // Handle form submission
            if (config.isSubmit()) {
                submitForm();
            }

            // Take screenshot if enabled
            if (config.isScreenshot()) {
                takeScreenshot();
            }

            // Save trace if enabled
            if (config.isTrace()) {
                saveTrace();
            }

        } catch (Exception e) {
            logger.error("Error filling form", e);
            if (config.isScreenshot()) {
                takeScreenshot();
            }
            throw new RuntimeException("Form filling failed", e);
        } finally {
            cleanup();
        }
    }

    private void handleLogin() {
        logger.info("Handling login");
        page.fill("#user_name", config.getCredentials().getUsername());
        page.fill("#user_password", config.getCredentials().getPassword());
        page.click("#sysverb_login");
        page.waitForLoadState();
    }

    private void fillField(String fieldId, FormField field) {
        logger.info("Filling field: {}", fieldId);
        
        switch (field.getType().toLowerCase()) {
            case "text":
                page.fill("#" + fieldId, field.getValue());
                break;
                
            case "choice":
                page.selectOption("#" + fieldId, field.getValue());
                break;
                
            case "reference":
                handleReferenceField(fieldId, field);
                break;
                
            case "datetime":
                page.fill("#" + fieldId, field.getValue());
                break;
                
            case "journal":
                page.fill("#" + fieldId, field.getValue());
                break;
                
            case "attachment":
                handleAttachment(fieldId, field);
                break;
                
            default:
                logger.warn("Unsupported field type: {}", field.getType());
        }
    }

    private void handleReferenceField(String fieldId, FormField field) {
        page.click("#" + fieldId + "_lookup");
        page.fill("#" + fieldId + "_searchbox", field.getValue());
        // Wait for and click the lookup result
        page.click("text=" + field.getLookup());
    }

    private void handleAttachment(String fieldId, FormField field) {
        page.setInputFiles("#" + fieldId, Paths.get(field.getValue()));
    }

    private void submitForm() {
        logger.info("Submitting form");
        page.click("#" + config.getSubmitButtonId());
        if (config.getWaitAfterSubmit() > 0) {
            page.waitForTimeout(config.getWaitAfterSubmit());
        }
    }

    private void takeScreenshot() {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Path screenshotPath = Paths.get("screenshots", "screenshot_" + timestamp + ".png");
            page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath));
            logger.info("Screenshot saved: {}", screenshotPath);
        } catch (Exception e) {
            logger.error("Failed to take screenshot", e);
        }
    }

    private void saveTrace() {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Path tracePath = Paths.get("traces", "trace_" + timestamp + ".zip");
            context.tracing().stop(new Tracing.StopOptions().setPath(tracePath));
            logger.info("Trace saved: {}", tracePath);
        } catch (Exception e) {
            logger.error("Failed to save trace", e);
        }
    }

    private void cleanup() {
        try {
            context.close();
            browser.close();
            playwright.close();
        } catch (Exception e) {
            logger.error("Error during cleanup", e);
        }
    }

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder("u")
                .longOpt("url")
                .hasArg()
                .required()
                .desc("ServiceNow form URL")
                .build());
        options.addOption(Option.builder("d")
                .longOpt("data")
                .hasArg()
                .required()
                .desc("Path to form data JSON file")
                .build());
        options.addOption(Option.builder("c")
                .longOpt("config")
                .hasArg()
                .required()
                .desc("Path to configuration JSON file")
                .build());

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            ObjectMapper mapper = new ObjectMapper();
            FormData formData = mapper.readValue(Paths.get(cmd.getOptionValue("data")).toFile(), FormData.class);
            FormFillerConfig config = mapper.readValue(Paths.get(cmd.getOptionValue("config")).toFile(), FormFillerConfig.class);

            FormFiller formFiller = new FormFiller(cmd.getOptionValue("url"), formData, config);
            formFiller.fillForm();
            
        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("FormFiller", options);
            System.exit(1);
        } catch (Exception e) {
            logger.error("Error running form filler", e);
            System.exit(1);
        }
    }
}
