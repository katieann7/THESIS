package com.two.factor.authentication;

import com.two.factor.authentication.appl.facade.timerecord.TimeRecordFacade;
import com.two.factor.authentication.appl.facade.timerecord.impl.TimeRecordFacadeImpl;
import com.two.factor.authentication.appl.model.time.TimeRecord;
import com.two.factor.authentication.data.dao.timerecord.TimeRecordDao;
import com.two.factor.authentication.data.dao.timerecord.impl.TimeRecordDaoImpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class TimeRecordApplication {

    private TimeRecordFacade timeRecordFacade;

    public TimeRecordApplication() {
        TimeRecordDao timeRecordDao = new TimeRecordDaoImpl();
        this.timeRecordFacade = new TimeRecordFacadeImpl(timeRecordDao);
    }

    public TimeRecordFacade getTimeRecordFacade() {
        return timeRecordFacade;
    }

    public void run() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Time In");
            System.out.println("2. Time Out");
            System.out.println("3. Exit");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 3) {
                System.out.println("Exiting the system.");
                break;
            }

            System.out.println("Enter Employee Number: ");
            String employeeNumber = scanner.nextLine();

            processTimeInOut(choice, employeeNumber, dateFormat);
        }
    }

    private void processTimeInOut(int choice, String employeeNumber, SimpleDateFormat dateFormat) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        switch (choice) {
            case 1:
                // Time In
                System.out.println("You have chosen Time In.");
                timeIn(employeeNumber, currentTime);
                break;
            case 2:
                // Time Out
                System.out.println("You have chosen Time Out.");
                timeOut(employeeNumber, currentTime);
                break;
            default:
                System.out.println("Invalid choice. Please select 1 for Time In or 2 for Time Out.");
        }
    }

    private void timeIn(String employeeNo, Timestamp timeIn) {
        TimeRecord existingRecord = timeRecordFacade.getTimeRecordByEmployeeNo(employeeNo);
        if (existingRecord != null && existingRecord.hasTimedIn() && isSameDay(existingRecord.getTimeIn(), timeIn)) {
            System.out.println("Employee " + employeeNo + " has already timed in today.");
            return;
        }

        TimeRecord timeRecord = timeRecordFacade.addTimeRecord(employeeNo, timeIn, null, 0.0);
        if (timeRecord != null) {
            System.out.println("Employee " + employeeNo + " has timed in at " +
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timeRecord.getTimeIn()));
        } else {
            System.out.println("Failed to add time-in for employee " + employeeNo);
        }
    }

    private void timeOut(String employeeNo, Timestamp timeOut) {
        TimeRecord timeRecord = timeRecordFacade.getTimeRecordByEmployeeNo(employeeNo);
        if (timeRecord != null) {
            if (timeRecord.hasTimedIn()) {
                if (timeRecord.getTimeOut() != null) {
                    System.out.println("Employee " + employeeNo + " has already timed out.");
                } else if (isSameDay(timeRecord.getTimeIn(), timeOut)) {
                    timeRecord.setTimeOut(timeOut);

                    long durationMillis = timeOut.getTime() - timeRecord.getTimeIn().getTime();
                    double totalHours = durationMillis / 3600000.0;
                    timeRecord.setTotalHours(totalHours);

                    timeRecordFacade.updateTimeRecord(timeRecord);
                    System.out.println("Employee " + employeeNo + " has timed out at " +
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timeRecord.getTimeOut()));
                    System.out.println("Total hours worked: " + totalHours);
                } else {
                    System.out.println("Failed to time out. Time out must be on the same day as time in.");
                }
            } else {
                System.out.println("No time-in record found for employee " + employeeNo);
            }
        } else {
            System.out.println("No time record found for employee " + employeeNo);
        }
    }

    private boolean isSameDay(Timestamp timeIn, Timestamp timeOut) {
        java.util.Calendar cal1 = java.util.Calendar.getInstance();
        java.util.Calendar cal2 = java.util.Calendar.getInstance();

        cal1.setTimeInMillis(timeIn.getTime());
        cal2.setTimeInMillis(timeOut.getTime());

        return cal1.get(java.util.Calendar.YEAR) == cal2.get(java.util.Calendar.YEAR) &&
                cal1.get(java.util.Calendar.MONTH) == cal2.get(java.util.Calendar.MONTH) &&
                cal1.get(java.util.Calendar.DAY_OF_MONTH) == cal2.get(java.util.Calendar.DAY_OF_MONTH);
    }
}
