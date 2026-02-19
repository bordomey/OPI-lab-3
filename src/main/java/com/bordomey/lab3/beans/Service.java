package com.bordomey.lab3.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.bordomey.lab3.db.DataBaseController;
import com.bordomey.lab3.db.HitResult;
import com.bordomey.lab3.utils.AreaCheck;
import com.bordomey.lab3.utils.CoordinatesValidation;
import com.bordomey.lab3.utils.MBeanRegistrationUtil;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
@SessionScoped
@Named("service")
public class Service implements Serializable {
    private final AreaCheck areaCheck;
    private DataBaseController dbController;
    
    @Inject
    private MBeanRegistrationUtil mBeanUtil;

    public Service() {
        areaCheck = new AreaCheck();
        try {
            dbController = new DataBaseController();
            System.out.println("Database controller initialized successfully");
        } catch (Exception e) {
            System.err.println("Failed to initialize database controller: " + e.getMessage());
            e.printStackTrace();
            // Create a fallback controller or handle the error appropriately
            dbController = null;
        }
    }

    public LinkedList<HitResult> getUserHits(String sessionId) {
        if (sessionId == null) return new LinkedList<>();

        List<HitResult> hits = null;
        if (dbController != null) {
            hits = dbController.getUserHits(sessionId);
        } else {
            System.out.println("Database not available, returning empty list");
            hits = new LinkedList<>();
        }

        System.out.println("Return user hits: " + hits);

        return hits != null ? new LinkedList<>(hits) : new LinkedList<>();
    }

    public HitResult processRequest(String sessionId, Coordinates coordinates) {
        if (!CoordinatesValidation.validate(coordinates)) {
            System.out.println("Coordinates not valid");
            return null;
        }

        boolean isHit = areaCheck.isHit(coordinates);
        HitResult hitResult = new HitResult(sessionId, coordinates, getCurrentDate(), isHit);

        System.out.println("Get result:" + hitResult);

        // Update MBeans with the new point information
        if (mBeanUtil != null) {
            mBeanUtil.getPointsCounter().incrementPoint(isHit);
            mBeanUtil.getMissPercentage().recordClick(isHit);
        }

        // Save to database if available
        if (dbController != null) {
            dbController.addHitResult(hitResult);
        } else {
            System.out.println("Database not available, skipping save");
        }

        System.out.println("WTFFF");

        return hitResult;
    }

    public void clearUserHits(String sessionId) {
        if (dbController != null) {
            dbController.markUserHitsRemoved(sessionId);
        } else {
            System.out.println("Database not available, cannot clear hits");
        }
    }

    private String getCurrentDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
}
