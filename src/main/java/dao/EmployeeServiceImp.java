package dao;

import entity.model.Employee;
import entity.model.ValidationService;
import exception.DatabaseConnectionException;
import exception.EmployeeNotFoundException;
import exception.InvalidInputException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeServiceImp implements EmployeeService {
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException, DatabaseConnectionException {
        Employee employee = null;
        try {
            if (connection == null) {
                throw new DatabaseConnectionException("Database connection is not established.");
            }

            String sqlQuery = "SELECT * FROM Employee WHERE EmployeeID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setInt(1, employeeId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        employee = new Employee();
                        employee.setEmployeeID(resultSet.getInt("EmployeeID"));
                        employee.setFirstName(resultSet.getString("FirstName"));
                        employee.setLastName(resultSet.getString("LastName"));
                    } else {
                        throw new EmployeeNotFoundException("Employee with ID " + employeeId + " does not exist.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to retrieve employee: " + e.getMessage());
        }
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() throws DatabaseConnectionException {
        List<Employee> employees = new ArrayList<>();
        try {
            if (connection == null) {
                throw new DatabaseConnectionException("Database connection is not established.");
            }

            String sqlQuery = "SELECT * FROM Employee";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Employee employee = new Employee();
                    employee.setEmployeeID(resultSet.getInt("EmployeeID"));
                    employee.setFirstName(resultSet.getString("FirstName"));
                    employee.setLastName(resultSet.getString("LastName"));
                    employees.add(employee);
                    System.out.println(employee.getEmployeeID() + " " + employee.getFirstName() + " " + employee.getLastName());
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to retrieve employees: " + e.getMessage());
        }
        return employees;
    }

    @Override
    public void addEmployee(Employee employee) throws InvalidInputException, DatabaseConnectionException {
        if (!ValidationService.isValidEmployeeData(employee)) {
            throw new InvalidInputException("Employee details are incomplete or invalid.");
        }

        try {
            if (connection == null) {
                throw new DatabaseConnectionException("Database connection is not established.");
            }

            String sqlQuery = "INSERT INTO Employee (FirstName, LastName, DateOfBirth, Gender, Email, PhoneNumber, Address, Position, JoiningDate, TerminationDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, employee.getFirstName());
                preparedStatement.setString(2, employee.getLastName());
                preparedStatement.setString(3, employee.getDateOfBirth());
                preparedStatement.setString(4, employee.getGender());
                preparedStatement.setString(5, employee.getEmail());
                preparedStatement.setString(6, employee.getPhoneNumber());
                preparedStatement.setString(7, employee.getAddress());
                preparedStatement.setString(8, employee.getPosition());
                preparedStatement.setString(9, employee.getJoiningDate());
                preparedStatement.setString(10, employee.getTerminationDate());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Employee added successfully.");
                } else {
                    throw new InvalidInputException("Failed to add employee. Please check your inputs and try again.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to add employee: " + e.getMessage());
        }
    }


    @Override
    public void updateEmployee(Employee employee) throws EmployeeNotFoundException, InvalidInputException, DatabaseConnectionException {
        if (!ValidationService.isValidEmployeeData(employee)) {
            throw new InvalidInputException("Invalid or incomplete employee details provided.");
        }

        try {
            if (connection == null) {
                throw new DatabaseConnectionException("Database connection is not established.");
            }

            String updateQuery = "UPDATE Employee SET FirstName = ?, LastName = ?, DateOfBirth = ?, Gender = ?, Email = ?, PhoneNumber = ?, Address = ?, Position = ?, JoiningDate = ?, TerminationDate = ? WHERE EmployeeID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, employee.getFirstName());
                preparedStatement.setString(2, employee.getLastName());
                preparedStatement.setString(3, employee.getDateOfBirth());
                preparedStatement.setString(4, employee.getGender());
                preparedStatement.setString(5, employee.getEmail());
                preparedStatement.setString(6, employee.getPhoneNumber());
                preparedStatement.setString(7, employee.getAddress());
                preparedStatement.setString(8, employee.getPosition());
                preparedStatement.setString(9, employee.getJoiningDate());
                preparedStatement.setString(10, employee.getTerminationDate());
                preparedStatement.setInt(11, employee.getEmployeeID());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Employee updated successfully.");
                } else {
                    throw new EmployeeNotFoundException("Employee with ID " + employee.getEmployeeID() + " not found.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to update employee: " + e.getMessage());
        }
    }


    @Override
    public void removeEmployee(int employeeId) throws EmployeeNotFoundException, DatabaseConnectionException {
        try {
            if (connection == null) {
                throw new DatabaseConnectionException("Database connection is not established.");
            }

            // Check if the employee exists
            String checkEmployeeSql = "SELECT COUNT(*) FROM Employee WHERE EmployeeID = ?";
            try (PreparedStatement checkPs = connection.prepareStatement(checkEmployeeSql)) {
                checkPs.setInt(1, employeeId);
                ResultSet rs = checkPs.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");
                }
            }

            // Delete dependent records in financialrecord first
            String deleteDependentRecordsSql = "DELETE FROM financialrecord WHERE EmployeeID = ?";
            try (PreparedStatement deleteDepPs = connection.prepareStatement(deleteDependentRecordsSql)) {
                deleteDepPs.setInt(1, employeeId);
                int deletedRecords = deleteDepPs.executeUpdate();
                System.out.println(deletedRecords + " financial records deleted for employee ID " + employeeId + ".");
            }

            // Delete records from payroll table
            String deletePayrollRecordsSql = "DELETE FROM payroll WHERE EmployeeID = ?";
            try (PreparedStatement deletePayrollPs = connection.prepareStatement(deletePayrollRecordsSql)) {
                deletePayrollPs.setInt(1, employeeId);
                int deletedPayrollRecords = deletePayrollPs.executeUpdate();
                System.out.println(deletedPayrollRecords + " payroll records deleted for employee ID " + employeeId + ".");
            }

            // Delete records from tax table
            String deleteTaxRecordsSql = "DELETE FROM tax WHERE EmployeeID = ?";
            try (PreparedStatement deleteTaxPs = connection.prepareStatement(deleteTaxRecordsSql)) {
                deleteTaxPs.setInt(1, employeeId);
                int deletedTaxRecords = deleteTaxPs.executeUpdate();
                System.out.println(deletedTaxRecords + " tax records deleted for employee ID " + employeeId + ".");
            }

            // Now delete the employee
            String deleteEmployeeSql = "DELETE FROM Employee WHERE EmployeeID = ?";
            try (PreparedStatement ps = connection.prepareStatement(deleteEmployeeSql)) {
                ps.setInt(1, employeeId);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Employee with ID " + employeeId + " has been successfully removed.");
                } else {
                    throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to remove employee: " + e.getMessage());
        }
    }



}
