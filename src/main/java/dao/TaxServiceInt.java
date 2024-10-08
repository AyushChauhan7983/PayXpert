package dao;

import java.sql.SQLException;
import java.util.*;
import entity.model.Tax;
import exception.FinancialRecordException;
import exception.InvalidInputException;
import exception.TaxCalculationException;

public interface TaxServiceInt {
    double CalculateTax(int employeeId, int taxYear) throws SQLException, InvalidInputException, TaxCalculationException; // Added custom exceptions
    Tax getTaxById(int taxId) throws SQLException, InvalidInputException, FinancialRecordException; // Added custom exceptions
    List<Tax> getTaxesForEmployee(int employeeId) throws SQLException, InvalidInputException, FinancialRecordException; // Added custom exceptions
    List<Tax> getTaxesForYear(int taxYear) throws SQLException, InvalidInputException, FinancialRecordException; // Added custom exceptions
}
