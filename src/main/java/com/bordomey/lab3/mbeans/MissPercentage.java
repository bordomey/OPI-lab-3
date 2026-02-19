package com.bordomey.lab3.mbeans;

import java.util.concurrent.atomic.AtomicLong;

public class MissPercentage implements MissPercentageMBean {
    private final AtomicLong totalClicks = new AtomicLong(0);
    private final AtomicLong missedClicks = new AtomicLong(0);

    @Override
    public double getMissPercentage() {
        long total = totalClicks.get();
        if (total == 0) {
            return 0.0;
        }
        return (double) missedClicks.get() / total * 100;
    }

    @Override
    public long getTotalClicks() {
        return totalClicks.get();
    }

    @Override
    public void recordClick(boolean isHit) {
        totalClicks.incrementAndGet();
        if (!isHit) {
            missedClicks.incrementAndGet();
        }
    }

    @Override
    public void resetStats() {
        totalClicks.set(0);
        missedClicks.set(0);
    }
}