package com.corpmatrix.hr.dto;

public class ManagerSpanDTO {

    private final String managerName;
    private final long directReportCount;

    public ManagerSpanDTO(String managerName, long directReportCount) {
        this.managerName = managerName;
        this.directReportCount = directReportCount;
    }

    public String getManagerName() {
        return managerName;
    }

    public long getDirectReportCount() {
        return directReportCount;
    }
}
