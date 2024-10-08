package dao;

import java.sql.Connection;
import java.sql.*;
import java.util.*;
import entity.model.Tax;
import exception.FinancialRecordException;
import exception.InvalidInputException;
import exception.TaxCalculationException;

public class TaxServiceImp implements TaxServiceInt {
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public double CalculateTax(int employeeId, int taxYear) throws SQLException, InvalidInputException, TaxCalculationException {
        if (employeeId <= 0 || taxYear <= 0) {
            throw new InvalidInputException("Employee ID and Tax Year must be positive integers.");
        }

        double totalTax = 0.0; // Initialize total tax amount
        String sql = "SELECT SUM(TaxAmount) AS TotalTax FROM Tax WHERE EmployeeID = ? AND TaxYear = ?";

        // Use try-with-resources to ensure resources are closed
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, employeeId);
            preparedStatement.setInt(2, taxYear);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Check if there is a result
                if (resultSet.next()) {
                    // Retrieve the total tax amount, defaulting to 0.0 if NULL
                    totalTax = resultSet.getDouble("TotalTax");
                    if (resultSet.wasNull()) {
                        totalTax = 0.0; // Explicitly set to 0 if the result is NULL
                    }
                } else {
                    throw new TaxCalculationException("No tax records found for Employee ID: " + employeeId + " in Tax Year: " + taxYear);
                }
            }
        } catch (SQLException e) {
            // Propagate the exception to the caller
            throw new SQLException("Error calculating tax: " + e.getMessage(), e);
        }

        // Return the calculated total tax amount
        return totalTax;
    }

    @Override
    public Tax getTaxById(int taxId) throws SQLException, FinancialRecordException, InvalidInputException {
        if (taxId <= 0) {
            throw new InvalidInputException("Tax ID must be a positive integer.");
        }

        Tax tax = null;
        String sql = "SELECT * FROM Tax WHERE TaxID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, taxId);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Check if the tax record exists
                if (resultSet.next()) {
                    // Retrieve values from the result set
                    int retrievedTaxId = resultSet.getInt("TaxID");
                    int employeeId = resultSet.getInt("EmployeeID");
                    int taxYear = resultSet.getInt("TaxYear");
                    double taxableIncome = resultSet.getDouble("TaxableIncome");
                    double taxAmount = resultSet.getDouble("TaxAmount");

                    // Create a Tax object with the retrieved values
                    tax = new Tax(retrievedTaxId, employeeId, taxYear, taxableIncome, taxAmount);
                } else {
                    // Tax record not found
                    throw new FinancialRecordException("Tax record with ID " + taxId + " not found.");
                }
            }
        } catch (SQLException e) {
            // Propagate the exception to the caller
            throw new SQLException("Error fetching tax record by ID: " + e.getMessage(), e);
        }

        return tax; // Return the Tax object or null if not found
    }

    @Override
    public List<Tax> getTaxesForEmployee(int employeeId) throws SQLException, FinancialRecordException, InvalidInputException {
        if (employeeId <= 0) {
            throw new InvalidInputException("Employee ID must be a positive integer.");
        }

        List<Tax> taxes = new ArrayList<>();
        String sql = "SELECT * FROM Tax WHERE EmployeeID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, employeeId);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Process the result set
                while (resultSet.next()) {
                    // Retrieve values from the result set
                    int taxId = resultSet.getInt("TaxID");
                    int retrievedEmployeeId = resultSet.getInt("EmployeeID");
                    int taxYear = resultSet.getInt("TaxYear");
                    double taxableIncome = resultSet.getDouble("TaxableIncome");
                    double taxAmount = resultSet.getDouble("TaxAmount");

                    // Create a Tax object with the retrieved values
                    Tax tax = new Tax(taxId, retrievedEmployeeId, taxYear, taxableIncome, taxAmount);

                    // Add the Tax object to the list
                    taxes.add(tax);
                }
            }
        } catch (SQLException e) {
            // Propagate the exception to the caller
            throw new SQLException("Error fetching taxes for employee: " + e.getMessage(), e);
        }

        if (taxes.isEmpty()) {
            throw new FinancialRecordException("No tax records found for Employee ID: " + employeeId);
        }

        return taxes; // Return the list of taxes for the employee
    }

    @Override
    public List<Tax> getTaxesForYear(int taxYear) throws SQLException, FinancialRecordException, InvalidInputException {
        if (taxYear <= 0) {
            throw new InvalidInputException("Tax Year must be a positive integer.");
        }

        List<Tax> taxes = new ArrayList<>();
        String sql = "SELECT * FROM Tax WHERE TaxYear = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, taxYear);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Process the result set
                while (resultSet.next()) {
                    // Retrieve values from the result set
                    int taxId = resultSet.getInt("TaxID");
                    int employeeId = resultSet.getInt("EmployeeID");
                    double taxableIncome = resultSet.getDouble("TaxableIncome");
                    double taxAmount = resultSet.getDouble("TaxAmount");

                    // Create a Tax object with the retrieved values
                    Tax tax = new Tax(taxId, employeeId, taxYear, taxableIncome, taxAmount);

                    // Add the Tax object to the list
                    taxes.add(tax);
                }
            }
        } catch (SQLException e) {
            // Propagate the exception to the caller
            throw new SQLException("Error fetching taxes for year: " + e.getMessage(), e);
        }

        if (taxes.isEmpty()) {
            throw new FinancialRecordException("No tax records found for Tax Year: " + taxYear);
        }

        return taxes; // Return the list of taxes for the specified tax year
    }
}
