package com.servicenow.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FormFillerConfig {
    private boolean headless;
    private int slowMo;
    private int delay;
    private boolean screenshot;
    private boolean submit;
    private String submitButtonId;
    private int waitAfterSubmit;
    private Credentials credentials;
    private boolean trace;
    private boolean recordVideo;
    private boolean evidence;
    private boolean consoleLogs;
    private Viewport viewport;
    private RetryConfig retryOnError;
    private TimeoutConfig timeouts;
    private NotificationConfig notifications;

    // Getters and Setters
    public boolean isHeadless() { return headless; }
    public void setHeadless(boolean headless) { this.headless = headless; }
    
    public int getSlowMo() { return slowMo; }
    public void setSlowMo(int slowMo) { this.slowMo = slowMo; }
    
    public int getDelay() { return delay; }
    public void setDelay(int delay) { this.delay = delay; }
    
    public boolean isScreenshot() { return screenshot; }
    public void setScreenshot(boolean screenshot) { this.screenshot = screenshot; }
    
    public boolean isSubmit() { return submit; }
    public void setSubmit(boolean submit) { this.submit = submit; }
    
    public String getSubmitButtonId() { return submitButtonId; }
    public void setSubmitButtonId(String submitButtonId) { this.submitButtonId = submitButtonId; }
    
    public int getWaitAfterSubmit() { return waitAfterSubmit; }
    public void setWaitAfterSubmit(int waitAfterSubmit) { this.waitAfterSubmit = waitAfterSubmit; }
    
    public Credentials getCredentials() { return credentials; }
    public void setCredentials(Credentials credentials) { this.credentials = credentials; }
    
    public boolean isTrace() { return trace; }
    public void setTrace(boolean trace) { this.trace = trace; }
    
    public boolean isRecordVideo() { return recordVideo; }
    public void setRecordVideo(boolean recordVideo) { this.recordVideo = recordVideo; }
    
    public boolean isEvidence() { return evidence; }
    public void setEvidence(boolean evidence) { this.evidence = evidence; }
    
    public boolean isConsoleLogs() { return consoleLogs; }
    public void setConsoleLogs(boolean consoleLogs) { this.consoleLogs = consoleLogs; }
    
    public Viewport getViewport() { return viewport; }
    public void setViewport(Viewport viewport) { this.viewport = viewport; }
    
    public RetryConfig getRetryOnError() { return retryOnError; }
    public void setRetryOnError(RetryConfig retryOnError) { this.retryOnError = retryOnError; }
    
    public TimeoutConfig getTimeouts() { return timeouts; }
    public void setTimeouts(TimeoutConfig timeouts) { this.timeouts = timeouts; }
    
    public NotificationConfig getNotifications() { return notifications; }
    public void setNotifications(NotificationConfig notifications) { this.notifications = notifications; }
}
