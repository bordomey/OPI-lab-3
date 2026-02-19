package com.bordomey.lab3.utils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.bordomey.lab3.mbeans.MissPercentage;
import com.bordomey.lab3.mbeans.PointsCounter;

import java.lang.management.ManagementFactory;

@Named("mbeanUtil")
@ApplicationScoped
public class MBeanRegistrationUtil {

    private PointsCounter pointsCounter;
    private MissPercentage missPercentage;

    @PostConstruct
    public void initializeMBeans() {
        try {
            pointsCounter = new PointsCounter();
            missPercentage = new MissPercentage();

            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            
            ObjectName pointsCounterName = new ObjectName("com.bordomey:type=PointsCounter,name=PointsCounter");
            ObjectName missPercentageName = new ObjectName("com.bordomey:type=MissPercentage,name=MissPercentage");

            if (!server.isRegistered(pointsCounterName)) {
                server.registerMBean(pointsCounter, pointsCounterName);
            }
            
            if (!server.isRegistered(missPercentageName)) {
                server.registerMBean(missPercentage, missPercentageName);
            }

            System.out.println("MBeans registered successfully");

        } catch (Exception e) {
            System.err.println("Error registering MBeans: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void cleanupMBeans() {
        try {
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            
            ObjectName pointsCounterName = new ObjectName("com.bordomey:type=PointsCounter,name=PointsCounter");
            ObjectName missPercentageName = new ObjectName("com.bordomey:type=MissPercentage,name=MissPercentage");

            if (server.isRegistered(pointsCounterName)) {
                server.unregisterMBean(pointsCounterName);
            }
            
            if (server.isRegistered(missPercentageName)) {
                server.unregisterMBean(missPercentageName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PointsCounter getPointsCounter() {
        return pointsCounter;
    }

    public MissPercentage getMissPercentage() {
        return missPercentage;
    }
}