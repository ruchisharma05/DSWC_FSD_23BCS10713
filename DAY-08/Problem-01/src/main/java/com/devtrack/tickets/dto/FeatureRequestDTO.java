package com.devtrack.tickets.dto;

import jakarta.validation.constraints.Positive;

public class FeatureRequestDTO extends TicketRequestDTO {

    @Positive(message = "Business value points must be greater than 0")
    private int businessValuePoints;

    private String targetAudience;

    public int getBusinessValuePoints() {
        return businessValuePoints;
    }

    public void setBusinessValuePoints(int businessValuePoints) {
        this.businessValuePoints = businessValuePoints;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }
}
