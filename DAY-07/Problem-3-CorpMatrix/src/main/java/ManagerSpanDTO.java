package com.corpmatrix;

public class ManagerSpanDTO {
    private String managerName;
    private Long directReportCount;
    
    public ManagerSpanDTO(String managerName, Long directReportCount) {
        this.managerName = managerName;
        this.directReportCount = directReportCount;
    }
    
    public String getManagerName() { return managerName; }
    public Long getDirectReportCount() { return directReportCount; }
    
    @Override
    public String toString() {
        return String.format("ManagerSpanDTO{managerName='%s', directReportCount=%d}", managerName, directReportCount);
    }
}
