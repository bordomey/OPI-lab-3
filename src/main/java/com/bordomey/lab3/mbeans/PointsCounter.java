package com.bordomey.lab3.mbeans;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.openmbean.OpenDataException;
import java.util.concurrent.atomic.AtomicLong;

public class PointsCounter extends NotificationBroadcasterSupport implements PointsCounterMBean {
    private final AtomicLong totalPoints = new AtomicLong(0);
    private final AtomicLong missedPoints = new AtomicLong(0);
    private volatile boolean notificationSent = false;
    private long sequenceNumber = 0;
    
    private static final String NOTIFICATION_TYPE = "points.multiple.of.15";

    @Override
    public long getTotalPoints() {
        return totalPoints.get();
    }

    @Override
    public long getMissedPoints() {
        return missedPoints.get();
    }

    @Override
    public void incrementPoint(boolean isHit) {
        long currentTotal = totalPoints.incrementAndGet();
        
        if (!isHit) {
            missedPoints.incrementAndGet();
        }
        
        // Check if total points is a multiple of 15
        if (currentTotal % 15 == 0) {
            sendNotification();
            notificationSent = true;
        } else {
            notificationSent = false;
        }
    }

    @Override
    public boolean isNotificationSent() {
        return notificationSent;
    }

    @Override
    public void resetCounters() {
        totalPoints.set(0);
        missedPoints.set(0);
        notificationSent = false;
    }

    private void sendNotification() {
        Notification notification = new Notification(
                NOTIFICATION_TYPE,
                this,
                sequenceNumber++,
                System.currentTimeMillis(),
                "Total points reached " + totalPoints.get() + ", which is a multiple of 15"
        );
        sendNotification(notification);
    }
}