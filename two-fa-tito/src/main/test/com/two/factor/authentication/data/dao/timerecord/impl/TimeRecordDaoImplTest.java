package com.two.factor.authentication.data.dao.timerecord.impl;

import com.two.factor.authentication.appl.model.time.TimeRecord;
import com.two.factor.authentication.data.dao.timerecord.TimeRecordDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TimeRecordDaoImplTest {

   /* private TimeRecordDao timeRecordDao;
    private List<TimeRecord> timeRecordList;

    @BeforeEach
    public void setup() {
        timeRecordList = new ArrayList<>();
        TimeRecord timeRecord1 = new TimeRecord("EMP21-0073");
        TimeRecord timeRecord2 = new TimeRecord("EMP21-0143");
        timeRecordList.add(timeRecord1);
        timeRecordList.add(timeRecord2);

        timeRecordDao = mock(TimeRecordDao.class);
    }

    @Test
    void testAddTimeRecord() {
        TimeRecord timeRecord = new TimeRecord("EMP21-0073");

        // Mocking the addTimeRecord method
        when(timeRecordDao.addTimeRecord(employeeNo, timeIn, timeOut, totalHours)).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                if (arguments != null && arguments.length > 0 && arguments[0] instanceof TimeRecord) {
                    timeRecordList.add((TimeRecord) arguments[0]); // Add to the list
                    return true;
                }
                return false;
            }
        });

        // Act
        Boolean result = timeRecordDao.addTimeRecord(employeeNo, timeIn, timeOut, totalHours).hasTimedIn();

        // Verify the size of the list
        assertEquals(3, timeRecordList.size());
        assertTrue(result);  // The addition was successful
    }

    @Test
    public void testUpdateTimeRecord() {
        TimeRecord timeRecordToUpdate = new TimeRecord("EMP21-0073");

        // Mocking the updateTimeRecord method
        when(timeRecordDao.updateTimeRecord()).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                if (arguments != null && arguments.length > 0 && arguments[0] instanceof TimeRecord) {
                    TimeRecord updatedRecord = (TimeRecord) arguments[0];

                    // Find the existing record by employeeNo and update it
                    Optional<TimeRecord> existingRecord = timeRecordList.stream()
                            .filter(c -> c.getEmployeeNo().equals(updatedRecord.getEmployeeNo()))
                            .findFirst();

                    if (existingRecord.isPresent()) {
                        timeRecordList.remove(existingRecord.get());
                        timeRecordList.add(updatedRecord);
                        return true;
                    }
                }
                return false;
            }
        });

        // Act
        Boolean updateResult = timeRecordDao.updateTimeRecord().hasTimedIn();

        // Verify that the record was updated
        Optional<TimeRecord> updatedRecord = timeRecordList.stream()
                .filter(c -> c.getEmployeeNo().equals("EMP21-0073"))
                .findFirst();

        // Assertions
        assertTrue(updateResult);  // The update was successful
        assertTrue(updatedRecord.isPresent());
        assertEquals("EMP21-0073", updatedRecord.get().getEmployeeNo());
    }*/
}
