# Build Instructions for WebLab3

This project includes an Apache Ant build script (build.xml) that implements the requirements from My_task.txt.

## Prerequisites

To use the Ant build system, you need to have Apache Ant installed on your system:

1. Download Apache Ant from https://ant.apache.org/bindownload.cgi
2. Extract it to a directory of your choice
3. Add the `bin` directory to your system PATH
4. Ensure you have Java JDK installed and JAVA_HOME configured

## Required Libraries

The build script expects the ant-contrib library in the `lib` directory:
- `lib/ant-contrib-1.0b3.jar` - Provides additional Ant tasks like `<if>` and `<trycatch>`

## Build Properties

The `build.properties` file contains all configurable parameters for the build process:
- Source and destination directories
- Main class for the JAR manifest
- JAR file name and location
- JVM parameters
- Music player settings (for the music target)

## Available Targets

Run these targets using: `ant [target-name]`

- `compile` - Compiles source code to class files
- `build` - Compiles and packages the application into a JAR file
- `clean` - Removes all compiled classes and temporary files
- `test` - Runs JUnit tests after building
- `native2ascii` - Converts native resource files to ASCII
- `music` - Plays a sound after successful build
- `team` - Gets previous revisions and builds them (simulated)
- `diff` - Checks Git status and commits changes (if appropriate)

## Running the Build

1. Install Apache Ant as described above
2. Place ant-contrib-1.0b3.jar in the lib directory
3. Execute any of the targets using the `ant` command

Example:
```
ant clean      # Clean previous build artifacts
ant compile    # Compile the source code
ant build      # Create the JAR file
ant test       # Run unit tests
ant music      # Build and play music
```

Alternatively, you can run the batch script `run_build.bat` which will execute all targets in sequence if Ant is installed.