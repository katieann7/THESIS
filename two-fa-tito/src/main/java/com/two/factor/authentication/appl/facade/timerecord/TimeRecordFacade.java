package com.two.factor.authentication.appl.facade.timerecord;

import com.two.factor.authentication.appl.model.time.TimeRecord;

import java.sql.Timestamp;

public interface TimeRecordFacade {
    TimeRecord addTimeRecord(String employeeNo);

    TimeRecord addTimeRecord(String employeeNo, Timestamp timeIn, Timestamp timeOut, double totalHours);

    TimeRecord getTimeRecordByEmployeeNo(String employeeNo);

    void updateTimeRecord(TimeRecord timeRecord);
}
