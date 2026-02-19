# Web Application with JMX Monitoring

This is a Java web application that includes custom MBeans for monitoring user interactions with the coordinate checking system.

## Features

1. **PointsCounter MBean**: Tracks total points set by users and number of misses. Sends notifications when total points reach multiples of 15.
2. **MissPercentage MBean**: Calculates the percentage of misses relative to total clicks on the coordinate plane.

## Running the Application

### Prerequisites
- Docker and Docker Compose
- Java 8+
- Maven 3+

### Build and Run
1. Execute the run script:
   ```
   run_app.bat
   ```

Or manually:
1. Build the application: `mvn clean package -DskipTests`
2. Start the services: `docker-compose up -d`

The application will be available at `http://localhost:8080/Lab3`

## JMX Monitoring

The application exposes MBeans for monitoring via JMX on port 9999.

### Using JConsole
1. Launch JConsole: `jconsole`
2. Connect to: `service:jmx:rmi:///jndi/rmi://localhost:9999/jmxrmi`
3. Navigate to the MBeans tab to view your custom MBeans:
   - `com.bordomey:type=PointsCounter,name=PointsCounter`
   - `com.bordomey:type=MissPercentage,name=MissPercentage`

### Using VisualVM
1. Launch VisualVM
2. Add Remote Host: `localhost:9999`
3. Connect and monitor the application's performance and MBean metrics

## MBean Attributes

### PointsCounter MBean
- `TotalPoints`: Total number of points set by users
- `MissedPoints`: Number of points that did not hit the target area
- `NotificationSent`: Boolean indicating if a notification was sent for multiple of 15

### MissPercentage MBean
- `MissPercentage`: Percentage of misses relative to total clicks
- `TotalClicks`: Total number of clicks recorded

## Testing the MBeans

1. Access the web application
2. Submit multiple coordinate points
3. Monitor the MBeans in JConsole or VisualVM to see the counters update
4. When you reach multiples of 15 total points, you should see notifications