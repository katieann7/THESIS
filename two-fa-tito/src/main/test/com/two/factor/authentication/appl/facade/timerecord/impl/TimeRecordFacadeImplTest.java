package com.two.factor.authentication.app.facade.timerecord.impl;

import com.two.factor.authentication.appl.facade.timerecord.impl.TimeRecordFacadeImpl;
import com.two.factor.authentication.appl.model.time.TimeRecord;
import com.two.factor.authentication.data.dao.timerecord.TimeRecordDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TimeRecordFacadeImplTest {

    private TimeRecordFacadeImpl timeRecordFacade;
    private TimeRecordDao mockTimeRecordDao;

    @BeforeEach
    void setUp() {
        // Mocking the TimeRecordDao
        mockTimeRecordDao = mock(TimeRecordDao.class);
        // Injecting the mock DAO into the facade
        timeRecordFacade = new TimeRecordFacadeImpl(mockTimeRecordDao);
    }

    @Test
    void addTimeRecord_ValidData_ReturnsTimeRecord() {
        String employeeNo = "EMP21-0073";
        Timestamp timeIn = new Timestamp(System.currentTimeMillis());
        Timestamp timeOut = new Timestamp(System.currentTimeMillis() + 3600000);  // 1 hour later
        double totalHours = 1.0;

        // Create a new TimeRecord that the mock DAO should return when addTimeRecord is called
        TimeRecord expectedTimeRecord = new TimeRecord(employeeNo);
        expectedTimeRecord.setTimeIn(timeIn);
        expectedTimeRecord.setTimeOut(timeOut);
        expectedTimeRecord.setTotalHours(totalHours);

        // Mocking the DAO method to return the created record
        when(mockTimeRecordDao.addTimeRecord()).thenReturn(true);

        // Act: call the facade's addTimeRecord method
        TimeRecord result = timeRecordFacade.addTimeRecord(employeeNo, timeIn, timeOut, totalHours);

        // Assert
        assertNotNull(result);
        assertEquals(employeeNo, result.getEmployeeNo());
        assertEquals(timeIn, result.getTimeIn());
        assertEquals(timeOut, result.getTimeOut());
        assertEquals(totalHours, result.getTotalHours(), 0.001);

        // Verify the DAO method was called with the expected TimeRecord
        verify(mockTimeRecordDao, times(1)).addTimeRecord();
    }

}
