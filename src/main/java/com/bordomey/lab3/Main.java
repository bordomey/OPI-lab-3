package com.bordomey.lab3;

import com.bordomey.lab3.beans.Coordinates;
import com.bordomey.lab3.utils.AreaCheck;

/**
 * Main class for WebLab3 application
 * Used as entry point when running the JAR file
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("WebLab3 Application Started");
        
        // Example usage of the AreaCheck functionality
        AreaCheck areaCheck = new AreaCheck();
        Coordinates coords = new Coordinates();
        
        // Test a sample point
        coords.setX(1.0);
        coords.setY(-1.0);
        coords.setR(2.0);
        
        boolean result = areaCheck.isHit(coords);
        System.out.println("Point (" + coords.getX() + ", " + coords.getY() + 
                          ") with radius " + coords.getR() + " is " + 
                          (result ? "inside" : "outside") + " the target area.");
        
        System.out.println("WebLab3 Application Completed");
    }
}