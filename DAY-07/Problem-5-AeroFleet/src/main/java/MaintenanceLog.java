package com.aerofleet;

import jakarta.persistence.*;

@Embeddable
public class MaintenanceLog {
    private String actionType;
    private String description;
    private String date;
    private int hoursSpent;
    
    public MaintenanceLog() {}
    public MaintenanceLog(String actionType, String description, String date, int hoursSpent) {
        this.actionType = actionType;
        this.description = description;
        this.date = date;
        this.hoursSpent = hoursSpent;
    }
    
    public String getActionType() { return actionType; }
    public void setActionType(String actionType) { this.actionType = actionType; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    
    public int getHoursSpent() { return hoursSpent; }
    public void setHoursSpent(int hoursSpent) { this.hoursSpent = hoursSpent; }
    
    @Override
    public String toString() {
        return String.format("MaintenanceLog{actionType='%s', date='%s', hoursSpent=%d}", actionType, date, hoursSpent);
    }
}
