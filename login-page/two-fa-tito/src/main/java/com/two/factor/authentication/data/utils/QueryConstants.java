package com.two.factor.authentication.data.utils;

public class QueryConstants {
    public static final String INSERT_TIME_RECORD_STATEMENT =
            "INSERT INTO timerecord (employee_no, time_in, time_out, total_hours, created_at) VALUES (?, ?, ?, ?, ?)";

    public static final String SELECT_TIME_RECORD_STATEMENT =
            "SELECT TIME_IN, TIME_OUT, TOTAL_HOURS, CREATED_AT FROM timerecord WHERE employee_no = ? AND TRUNC(TIME_IN) = TRUNC(SYSDATE)";

    public static final String UPDATE_TIME_RECORD_STATEMENT =
            "UPDATE timerecord SET TIME_IN = ?, TIME_OUT = ?, TOTAL_HOURS = ? WHERE employee_no = ? AND TRUNC(TIME_IN) = TRUNC(SYSDATE)";

    public static final String CHECK_EMPLOYEE_EXISTS_STATEMENT =
            "SELECT COUNT(*) FROM timerecord WHERE employee_no = ? AND TRUNC(TIME_IN) = TRUNC(SYSDATE)";
}
