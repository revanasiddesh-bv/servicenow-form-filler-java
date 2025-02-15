@echo off
echo ServiceNow Form Filler

REM Check Java installation
java -version
if %ERRORLEVEL% NEQ 0 (
    echo Java is not installed. Please install Java 11 or higher.
    exit /b 1
)

REM Check if JAR exists
if not exist "target\servicenow-form-filler-1.0-SNAPSHOT.jar" (
    echo Building project...
    call mvn clean install
    if %ERRORLEVEL% NEQ 0 (
        echo Build failed.
        exit /b 1
    )
)

REM Create required directories
mkdir screenshots 2>nul
mkdir videos 2>nul
mkdir traces 2>nul
mkdir logs 2>nul

REM Run the form filler
echo Running form filler...
java -jar target/servicenow-form-filler-1.0-SNAPSHOT.jar %*
