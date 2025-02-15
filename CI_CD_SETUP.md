# Setting up CI/CD with GitHub Actions

This guide explains how to set up the CI/CD pipeline for the ServiceNow Form Filler.

## Prerequisites

1. GitHub repository with your code
2. ServiceNow instance credentials
3. GitHub account with repository access

## Setting up GitHub Secrets

Add the following secrets to your GitHub repository:

1. Go to your repository settings
2. Navigate to "Secrets and variables" > "Actions"
3. Add the following secrets:
   - `SERVICENOW_USERNAME`: Your ServiceNow username
   - `SERVICENOW_PASSWORD`: Your ServiceNow password
   - `SERVICENOW_INSTANCE`: Your ServiceNow instance (e.g., "dev12345.service-now.com")

## Pipeline Features

The CI/CD pipeline includes:

1. **Build and Test**:
   - Builds the project
   - Installs dependencies
   - Runs example tests
   - Captures screenshots and videos
   - Saves test artifacts

2. **Deploy**:
   - Creates a new release
   - Packages the application
   - Uploads artifacts to GitHub releases

## Workflow Files

1. Java Version (`.github/workflows/java-ci.yml`):
   - Builds with Maven
   - Installs Playwright dependencies
   - Runs example tests
   - Creates releases with JAR files

2. JavaScript Version (`.github/workflows/node-ci.yml`):
   - Installs Node.js dependencies
   - Runs example tests
   - Creates releases with npm packages

## Artifacts

The pipeline saves the following artifacts:
- Screenshots from test runs
- Video recordings (if enabled)
- Playwright traces (Java version)
- Log files

## Customizing the Pipeline

1. **Trigger Events**:
   ```yaml
   on:
     push:
       branches: [ main, master ]
     pull_request:
       branches: [ main, master ]
     workflow_dispatch:  # Manual trigger
   ```

2. **Test Configuration**:
   ```json
   {
     "headless": true,
     "slowMo": 0,
     "screenshot": true,
     "submit": false
   }
   ```

3. **Environment Variables**:
   ```yaml
   env:
     SERVICENOW_USERNAME: ${{ secrets.SERVICENOW_USERNAME }}
     SERVICENOW_PASSWORD: ${{ secrets.SERVICENOW_PASSWORD }}
     SERVICENOW_INSTANCE: ${{ secrets.SERVICENOW_INSTANCE }}
   ```

## Running the Pipeline

The pipeline runs automatically on:
1. Push to main/master branch
2. Pull request to main/master branch
3. Manual trigger from GitHub Actions tab

## Viewing Results

1. Go to your repository's "Actions" tab
2. Click on a workflow run
3. View:
   - Build logs
   - Test results
   - Artifacts (screenshots, videos, logs)
   - Created releases

## Troubleshooting

1. **Build Failures**:
   - Check build logs in GitHub Actions
   - Verify all dependencies are properly specified
   - Ensure secrets are correctly set

2. **Test Failures**:
   - Check test logs and screenshots
   - Verify ServiceNow instance is accessible
   - Check form field IDs match your instance

3. **Release Issues**:
   - Ensure GitHub token has required permissions
   - Check version numbers and tags
   - Verify artifact paths

## Best Practices

1. **Security**:
   - Never commit credentials
   - Use GitHub secrets for sensitive data
   - Regularly rotate credentials

2. **Testing**:
   - Run tests in headless mode
   - Keep tests focused and independent
   - Save evidence for debugging

3. **Maintenance**:
   - Regularly update dependencies
   - Monitor pipeline performance
   - Clean up old artifacts and releases
