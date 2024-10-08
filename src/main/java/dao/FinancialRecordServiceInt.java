package dao;

import entity.model.FinancialRecord;
import exception.DatabaseConnectionException;
import exception.FinancialRecordException;
import java.util.*;

public interface FinancialRecordServiceInt {
    FinancialRecord getFinancialRecordById(int recordId) throws DatabaseConnectionException, FinancialRecordException;

    List<FinancialRecord> getFinancialRecordsForEmployee(int employeeId) throws DatabaseConnectionException;

    List<FinancialRecord> getFinancialRecordsForDate(String recordDate) throws DatabaseConnectionException;

    void addFinancialRecord(int employeeId, String description, double amount, String recordType) throws DatabaseConnectionException;
}
