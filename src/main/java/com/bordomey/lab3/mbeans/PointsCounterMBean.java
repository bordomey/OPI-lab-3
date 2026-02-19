package com.bordomey.lab3.mbeans;

public interface PointsCounterMBean {
    long getTotalPoints();
    long getMissedPoints();
    void incrementPoint(boolean isHit);
    boolean isNotificationSent();
    void resetCounters();
}