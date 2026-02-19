@echo off
REM Batch script to run Ant build for WebLab3 project
REM Make sure Apache Ant is installed and in your PATH

echo Checking if Apache Ant is installed...
ant -version >nul 2>&1
if errorlevel 1 (
    echo Apache Ant is not installed or not in PATH.
    echo Please install Apache Ant and make sure it's in your PATH.
    echo You can download it from: https://ant.apache.org/bindownload.cgi
    goto :END
)

echo Apache Ant is installed.
echo Starting build process...

REM Run the different targets as specified in the build.xml
echo.
echo Running 'compile' target...
ant compile
if errorlevel 1 (
    echo Compilation failed.
    goto :END
)

echo.
echo Running 'build' target...
ant build
if errorlevel 1 (
    echo Build failed.
    goto :END
)

echo.
echo Running 'test' target...
ant test
if errorlevel 1 (
    echo Tests failed.
    goto :END
)

echo.
echo Running 'music' target...
ant music

echo.
echo Running 'team' target...
ant team

echo.
echo Running 'diff' target...
ant diff

echo.
echo Build process completed successfully!

:END
pause