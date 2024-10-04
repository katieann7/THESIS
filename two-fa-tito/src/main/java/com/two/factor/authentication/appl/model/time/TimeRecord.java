package com.two.factor.authentication.appl.model.time;

import java.sql.Timestamp;

public class TimeRecord {

    private String employeeNo;
    private Timestamp timeIn;
    private Timestamp timeOut;
    private double totalHours;
    private Timestamp createdAt;

    public TimeRecord(String employeeNo) {
        this.employeeNo = employeeNo;
        this.timeIn = null;
        this.timeOut = null;
        this.totalHours = 0;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    // Getters and Setters
    public String getEmployeeNo() {
        return employeeNo;
    }

    public Timestamp getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Timestamp timeIn) {
        this.timeIn = timeIn;
        updateTotalHours(); // Update total hours when timeIn is set
    }

    public Timestamp getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Timestamp timeOut) {
        this.timeOut = timeOut;
        updateTotalHours(); // Update total hours when timeOut is set
    }

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours; // Allow manual setting of total hours
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    private void updateTotalHours() {
        if (timeIn != null && timeOut != null) {
            long durationMillis = timeOut.getTime() - timeIn.getTime();
            this.totalHours = durationMillis / 3600000.0; // Convert milliseconds to hours
        }
    }

    public boolean hasTimedIn() {
        return timeIn != null;
    }
}
