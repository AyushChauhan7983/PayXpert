package dao;

import java.util.*;
import java.sql.*;

import entity.model.Employee;
import entity.model.Payroll;
import java.time.LocalDate;
import exception.DatabaseConnectionException;
import exception.PayrollGenerationException;

public class PayrollServiceImp implements PayrollServiceInt {

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Payroll getPayrollById(int payrollId) throws PayrollGenerationException, DatabaseConnectionException {
        Payroll payroll = null; // Initialize payroll to null
        String sql = "SELECT * FROM Payroll WHERE PayrollID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Set the parameter for the query
            preparedStatement.setInt(1, payrollId);

            // Execute the query and get the result set
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if a payroll record was found
            if (resultSet.next()) {
                // Create a Payroll object and populate its attributes from the result set
                payroll = new Payroll();
                payroll.setPayrollID(resultSet.getInt("PayrollID"));
                payroll.setEmployeeID(resultSet.getInt("EmployeeID"));
                payroll.setPayPeriodStartDate(resultSet.getDate("PayPeriodStartDate"));
                payroll.setPayPeriodEndDate(resultSet.getDate("PayPeriodEndDate"));
                payroll.setBasicSalary(resultSet.getDouble("BasicSalary"));
                payroll.setOvertimePay(resultSet.getDouble("OvertimePay"));
                payroll.setDeductions(resultSet.getDouble("Deductions"));
                payroll.setNetSalary(resultSet.getDouble("NetSalary"));
            } else {
                throw new PayrollGenerationException("No payroll record found with ID: " + payrollId);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error fetching payroll record: " + e.getMessage());
        }

        return payroll; // Return the Payroll object or null if not found
    }

    @Override
    public List<Payroll> getPayrollsForEmployee(int employeeId) throws DatabaseConnectionException {
        List<Payroll> payrolls = new ArrayList<>();
        String sql = "SELECT * FROM Payroll WHERE EmployeeID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Set the parameter for the query
            preparedStatement.setInt(1, employeeId);

            // Execute the query and get the result set
            ResultSet resultSet = preparedStatement.executeQuery();

            // Iterate through the result set and create Payroll objects
            while (resultSet.next()) {
                Payroll payroll = new Payroll();
                payroll.setPayrollID(resultSet.getInt("PayrollID"));
                payroll.setEmployeeID(resultSet.getInt("EmployeeID"));
                payroll.setPayPeriodStartDate(resultSet.getDate("PayPeriodStartDate"));
                payroll.setPayPeriodEndDate(resultSet.getDate("PayPeriodEndDate"));
                payroll.setBasicSalary(resultSet.getDouble("BasicSalary"));
                payroll.setOvertimePay(resultSet.getDouble("OvertimePay"));
                payroll.setDeductions(resultSet.getDouble("Deductions"));
                payroll.setNetSalary(resultSet.getDouble("NetSalary"));

                // Add the payroll record to the list
                payrolls.add(payroll);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error fetching payrolls for employee ID " + employeeId + ": " + e.getMessage());
        }

        return payrolls.isEmpty() ? Collections.emptyList() : payrolls;
    }

    @Override
    public List<Payroll> getPayrollsForPeriod(LocalDate startDate, LocalDate endDate) throws DatabaseConnectionException {
        List<Payroll> payrolls = new ArrayList<>();
        String sql = "SELECT * FROM Payroll WHERE PayPeriodStartDate >= ? AND PayPeriodEndDate <= ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Set the parameters for the query
            preparedStatement.setDate(1, java.sql.Date.valueOf(startDate));
            preparedStatement.setDate(2, java.sql.Date.valueOf(endDate));

            // Execute the query and get the result set
            ResultSet resultSet = preparedStatement.executeQuery();

            // Iterate through the result set and create Payroll objects
            while (resultSet.next()) {
                Payroll payroll = new Payroll();
                payroll.setPayrollID(resultSet.getInt("PayrollID"));
                payroll.setEmployeeID(resultSet.getInt("EmployeeID"));
                payroll.setPayPeriodStartDate(resultSet.getDate("PayPeriodStartDate"));
                payroll.setPayPeriodEndDate(resultSet.getDate("PayPeriodEndDate"));
                payroll.setBasicSalary(resultSet.getDouble("BasicSalary"));
                payroll.setOvertimePay(resultSet.getDouble("OvertimePay"));
                payroll.setDeductions(resultSet.getDouble("Deductions"));
                payroll.setNetSalary(resultSet.getDouble("NetSalary"));

                // Add the payroll record to the list
                payrolls.add(payroll);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error fetching payrolls for period from " + startDate + " to " + endDate + ": " + e.getMessage());
        }

        return payrolls.isEmpty() ? Collections.emptyList() : payrolls;
    }

    @Override
    public boolean generatePayroll(int employeeId, LocalDate startDate, LocalDate endDate) throws DatabaseConnectionException {
        Double basicSalary = 5000.00;  // Replace with your calculation
        Double overtimePay = 200.00;    // Replace with your calculation
        Double deductions = 100.00;      // Replace with your calculation
        Double netSalary = 25500.00;

        String sql = "INSERT INTO Payroll (EmployeeID, PayPeriodStartDate, PayPeriodEndDate, BasicSalary, OvertimePay, Deductions, NetSalary) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Set parameters for the query based on your calculated values
            preparedStatement.setInt(1, employeeId);
            preparedStatement.setDate(2, java.sql.Date.valueOf(startDate));
            preparedStatement.setDate(3, java.sql.Date.valueOf(endDate));
            preparedStatement.setDouble(4, basicSalary);
            preparedStatement.setDouble(5, overtimePay);
            preparedStatement.setDouble(6, deductions);
            preparedStatement.setDouble(7, netSalary);

            // Execute the query to insert the payroll record
            int rowsAffected = preparedStatement.executeUpdate();

            // Return true if at least one row was affected (record inserted), false otherwise
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error generating payroll for employee ID " + employeeId + ": " + e.getMessage());
        }
    }

    public double calculateGrossSalary(Employee employee) {
        double basicSalary = employee.getBasicSalary();
        double hra = 0.20 * basicSalary; // 20% of basic salary
        double da = 0.10 * basicSalary;  // 10% of basic salary
        double otherAllowances = 50000;    // Fixed other allowances

        double grossSalary = basicSalary + hra + da + otherAllowances;
        return grossSalary;
    }



    public double calculateNetSalary(Employee employee) {
        // Calculate the gross salary using the already defined method
        double grossSalary = calculateGrossSalary(employee);

        // Define tax rate and insurance deduction
        double taxRate = 0.15; // 15% tax rate
        double insurance = 2000; // Fixed insurance amount

        // Calculate tax amount
        double taxAmount = grossSalary * taxRate;

        // Calculate net salary
        double netSalary = grossSalary - taxAmount - insurance;

        return netSalary;
    }

    public double calculateTax(Employee employee) {
        // Calculate the gross salary
        double grossSalary = calculateGrossSalary(employee);

        // Assuming the tax rate is 30% for high-income employees
        double taxRate = 0.30; // 30% tax rate

        // Calculate the tax amount
        return grossSalary * taxRate;
    }
}
