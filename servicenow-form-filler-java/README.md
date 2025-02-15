# ServiceNow Form Filler (Java)

A Java-based tool for automating form filling in ServiceNow using Playwright.

## Features

- Automated form filling for ServiceNow
- Support for various field types:
  - Text fields
  - Choice/Select fields
  - Reference/Lookup fields
  - Date/Time fields
  - Journal/Rich text fields
  - File attachments
- Configurable options:
  - Headless mode
  - Slow motion mode
  - Screenshots
  - Video recording
  - Tracing
  - Custom timeouts
  - Viewport size
  - Error retry
  - Notifications (Email/Slack)

## Prerequisites

- Java 11 or higher
- Maven

## Installation

1. Clone the repository
2. Install dependencies:
   ```bash
   mvn install
   ```

## Usage

1. Create a form data JSON file (see examples folder)
2. Create a configuration JSON file (see examples folder)
3. Run the form filler:
   ```bash
   java -jar target/servicenow-form-filler-1.0-SNAPSHOT.jar \
     --url "https://your-instance.service-now.com/incident.do" \
     --data "examples/incident-form.json" \
     --config "examples/config-basic.json"
   ```

## Configuration Options

See the example configuration files in the `examples` directory for all available options:
- `config-basic.json`: Basic configuration
- `config-advanced.json`: Advanced configuration with debugging features

## Form Data Format

Form data is specified in JSON format. See the example form data files in the `examples` directory:
- `incident-form.json`: Example for incident creation
- `change-request-form.json`: Example for change requests
- `service-catalog-form.json`: Example for service catalog items

## Project Structure

```
servicenow-form-filler/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── servicenow/
│   │   │           ├── config/
│   │   │           │   ├── FormFillerConfig.java
│   │   │           │   ├── Credentials.java
│   │   │           │   ├── NotificationConfig.java
│   │   │           │   ├── RetryConfig.java
│   │   │           │   ├── TimeoutConfig.java
│   │   │           │   └── Viewport.java
│   │   │           ├── model/
│   │   │           │   ├── FormData.java
│   │   │           │   └── FormField.java
│   │   │           └── FormFiller.java
│   │   └── resources/
│   │       └── logback.xml
├── examples/
│   ├── incident-form.json
│   ├── change-request-form.json
│   ├── service-catalog-form.json
│   ├── config-basic.json
│   └── config-advanced.json
├── pom.xml
└── README.md
```

## Output Directories

The following directories will be created as needed:
- `screenshots/`: Screenshot images
- `videos/`: Video recordings
- `traces/`: Playwright trace files
- `logs/`: Log files

## Error Handling

- Screenshots are automatically taken on errors if enabled
- Detailed logs are written to both console and file
- Optional retry mechanism for failed operations
- Optional email/Slack notifications on errors

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License.
