@echo off
echo Building the Web Application...

:: Build the application
mvn clean package -DskipTests

if %ERRORLEVEL% neq 0 (
    echo Build failed!
    pause
    exit /b %ERRORLEVEL%
)

echo Build completed successfully!

echo Starting Docker containers...
docker-compose up -d

echo Wait a moment for the services to start...
timeout /t 10

echo Application is now running!
echo - Web interface: http://localhost:8080/Lab3
echo - JMX monitoring available on port 9999
echo.
echo You can now connect JConsole or VisualVM to monitor the application:
echo - JMX Service URL: service:jmx:rmi:///jndi/rmi://localhost:9999/jmxrmi
echo.

pause