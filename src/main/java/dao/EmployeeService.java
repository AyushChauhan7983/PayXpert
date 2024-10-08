package dao;

import entity.model.Employee;
import exception.EmployeeNotFoundException;
import exception.DatabaseConnectionException;
import exception.InvalidInputException;
import java.util.List;

public interface EmployeeService {
    Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException, DatabaseConnectionException;
    List<Employee> getAllEmployees() throws DatabaseConnectionException;
    void addEmployee(Employee employee) throws InvalidInputException, DatabaseConnectionException;
    void updateEmployee(Employee employee) throws EmployeeNotFoundException, InvalidInputException, DatabaseConnectionException;
    void removeEmployee(int employeeId) throws EmployeeNotFoundException, DatabaseConnectionException;
}
