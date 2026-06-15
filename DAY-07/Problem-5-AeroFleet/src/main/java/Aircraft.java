package com.aerofleet;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "aircraft")
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aircraftId;
    
    private String modelName;
    private String registrationNumber;
    private boolean isGrounded;
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "aircraft_maintenance_logs", 
                     joinColumns = @JoinColumn(name = "aircraft_id"))
    private List<MaintenanceLog> maintenanceLogs;
    
    public Aircraft() {}
    public Aircraft(String modelName, String registrationNumber, boolean isGrounded) {
        this.modelName = modelName;
        this.registrationNumber = registrationNumber;
        this.isGrounded = isGrounded;
    }
    
    public Long getAircraftId() { return aircraftId; }
    public String getModelName() { return modelName; }
    public String getRegistrationNumber() { return registrationNumber; }
    public boolean isGrounded() { return isGrounded; }
    public List<MaintenanceLog> getMaintenanceLogs() { return maintenanceLogs; }
    public void setMaintenanceLogs(List<MaintenanceLog> maintenanceLogs) { this.maintenanceLogs = maintenanceLogs; }
}
