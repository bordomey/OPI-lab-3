@echo off
echo Starting the Web Application with Local Tomcat...

echo.
echo Prerequisites:
echo 1. Make sure Apache Tomcat 9 is installed on your system
echo 2. Set the CATALINA_HOME environment variable to your Tomcat installation directory
echo 3. Make sure the database container is running: docker start web_lab3_db
echo.

REM Check if CATALINA_HOME is set
if "%CATALINA_HOME%"=="" (
    echo Error: CATALINA_HOME environment variable is not set.
    echo Please install Tomcat and set the CATALINA_HOME variable.
    echo For example: set CATALINA_HOME=C:\apache-tomcat-9.0.xx
    pause
    exit /b 1
)

REM Check if Tomcat directory exists
if not exist "%CATALINA_HOME%\bin\catalina.bat" (
    echo Error: Tomcat installation not found at %CATALINA_HOME%
    echo Please verify your Tomcat installation.
    pause
    exit /b 1
)

REM Copy the WAR file to Tomcat's webapps folder
echo Copying application to Tomcat webapps folder...
copy /Y "target\Lab3.war" "%CATALINA_HOME%\webapps\"

echo.
echo Starting Tomcat server...
call "%CATALINA_HOME%\bin\catalina.bat" start

echo.
echo The application should be available at http://localhost:8080/Lab3
echo.
echo To stop the server later, run: %CATALINA_HOME%\bin\catalina.bat stop
pause