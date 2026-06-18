package com.devtrack.tickets.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class BugReportDTO extends TicketRequestDTO {

    @Min(value = 1, message = "Severity must be between 1 and 5")
    @Max(value = 5, message = "Severity must be between 1 and 5")
    private int severity;

    private String stepsToReproduce;

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public String getStepsToReproduce() {
        return stepsToReproduce;
    }

    public void setStepsToReproduce(String stepsToReproduce) {
        this.stepsToReproduce = stepsToReproduce;
    }
}
