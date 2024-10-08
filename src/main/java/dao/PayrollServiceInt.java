package dao;

import java.util.*;
import java.time.LocalDate;
import entity.model.Payroll;
import exception.DatabaseConnectionException;  // Custom exception for database errors
import exception.PayrollGenerationException;     // Custom exception for payroll not found

public interface PayrollServiceInt {
    Payroll getPayrollById(int payrollId) throws PayrollGenerationException, DatabaseConnectionException; // Custom exceptions
    List<Payroll> getPayrollsForEmployee(int employeeId) throws DatabaseConnectionException; // Custom exception
    List<Payroll> getPayrollsForPeriod(LocalDate startDate, LocalDate endDate) throws DatabaseConnectionException; // Custom exception
    boolean generatePayroll(int employeeId, LocalDate startDate, LocalDate endDate) throws DatabaseConnectionException; // Custom exception
}
