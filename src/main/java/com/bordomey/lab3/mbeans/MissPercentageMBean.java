package com.bordomey.lab3.mbeans;

public interface MissPercentageMBean {
    double getMissPercentage();
    long getTotalClicks();
    void recordClick(boolean isHit);
    void resetStats();
}