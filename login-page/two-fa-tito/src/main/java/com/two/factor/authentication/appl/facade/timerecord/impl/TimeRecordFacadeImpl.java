package com.two.factor.authentication.appl.facade.timerecord.impl;

import com.two.factor.authentication.appl.facade.timerecord.TimeRecordFacade;
import com.two.factor.authentication.appl.model.time.TimeRecord;
import com.two.factor.authentication.data.dao.timerecord.TimeRecordDao;

import java.sql.Timestamp;

public class TimeRecordFacadeImpl implements TimeRecordFacade {

    private TimeRecordDao timeRecordDao;

    public TimeRecordFacadeImpl(TimeRecordDao timeRecordDao) {
        this.timeRecordDao = timeRecordDao;
    }
    @Override
    public TimeRecord addTimeRecord(String employeeNo) {
        return null;
    }
    @Override
    public TimeRecord addTimeRecord(String employeeNo, Timestamp timeIn, Timestamp timeOut, double totalHours) {
        return timeRecordDao.addTimeRecord(employeeNo, timeIn, timeOut, totalHours);
    }

    @Override
    public TimeRecord getTimeRecordByEmployeeNo(String employeeNo) {
        return timeRecordDao.getTimeRecordByEmployeeNo(employeeNo);
    }

    @Override
    public void updateTimeRecord(TimeRecord timeRecord) {
        if (timeRecordDao.checkEmployeeExists(timeRecord.getEmployeeNo(), timeRecord.getTimeIn())) {
            timeRecordDao.updateTimeRecord(timeRecord);
        } else {
            System.out.println("Employee does not exist or time out is not valid.");
        }
    }

}
