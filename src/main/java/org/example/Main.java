package org.example;
import java.util.*;
import entity.model.Employee;
import entity.model.FinancialRecord;
import entity.model.Payroll;
import entity.model.Tax;
import exception.*;
import util.*;
import java.sql.*;
import java.time.LocalDate;

import dao.*;
public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        EmployeeService employeeService = new EmployeeServiceImp();
        PayrollServiceInt payrollService = new PayrollServiceImp();
        TaxServiceInt taxService = new TaxServiceImp();
        FinancialRecordServiceInt financialRecordService = new FinancialRecordServiceImp();
        try (Connection connection = DatabaseContext.getConnection()) {
            ((EmployeeServiceImp) employeeService).setConnection(connection);
            ((PayrollServiceImp) payrollService).setConnection(connection);
            ((TaxServiceImp) taxService).setConnection(connection);
            ((FinancialRecordServiceImp) financialRecordService).setConnection(connection);

            int choice;
            do {
                System.out.println("===============================================");
                System.out.println("      WELCOME TO THE PAYROLL MANAGEMENT SYSTEM  ");
                System.out.println("===============================================");
                System.out.println("            Please choose an option:          ");
                System.out.println("                                              ");
                System.out.println("               1. Get Employee by ID          ");
                System.out.println("               2. Get All Employees            ");
                System.out.println("               3. Add Employee                 ");
                System.out.println("               4. Update Employee                 ");
                System.out.println("               5. Remove Employee              ");
                System.out.println("               6. Get Financial Record by ID   ");
                System.out.println("               7. Get Financial Records for Employee");
                System.out.println("               8. Get Financial Record by Date  ");
                System.out.println("               9. Add Financial Record          ");
                System.out.println("               10. Get Payroll by Payroll ID    ");
                System.out.println("               11. Get Payroll for Employee     ");
                System.out.println("               12. Get Payroll for Period       ");
                System.out.println("               13. Generate Payroll              ");
                System.out.println("               14. Get Tax by ID                ");
                System.out.println("               15. Get Tax by Employee ID       ");
                System.out.println("               16. Get Tax for Year             ");
                System.out.println("               17. Calculate Tax             ");
                System.out.println("                                              ");
                System.out.println("               0. Exit                         ");
                System.out.println("===============================================");
                System.out.print("               Enter your choice: ");


                choice = scanner.nextInt();

                switch (choice) {
                    case 0 : System.out.println("ThankYou for visiting!!!");
                        System.exit(0);
                        break;

                    case 1:
                        try {
                            System.out.print("Enter Employee ID: ");
                            int employeeId = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            if (employeeId != 0) {
                                // Retrieve and output employee details
                                Employee employee = employeeService.getEmployeeById(employeeId);
                                System.out.println("Employee ID: " + employee.getEmployeeID() +
                                        ", Name: " + employee.getFirstName() + " " + employee.getLastName());
                            } else {
                                System.out.println("Enter a valid Employee ID.");
                            }

                        } catch (EmployeeNotFoundException | DatabaseConnectionException e) {
                            // Handle exceptions and display error message
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    case 2:
                        try {
                            // Retrieve and print all employees
                            List<Employee> employees = employeeService.getAllEmployees();

                        } catch (DatabaseConnectionException e) {
                            // Handle exceptions and display error message
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;


                    case 3:
                        try {
                            // Remove the prompt for employee ID as it will be auto-generated
                            System.out.println("Enter first name: ");
                            String firstName = scanner.next();
                            System.out.println("Enter last name: ");
                            String lastName = scanner.next();
                            System.out.println("Enter date of birth (yyyy-MM-dd): ");
                            String dateOfBirth = scanner.next();
                            System.out.println("Enter gender (M/F): ");
                            String gender = scanner.next();
                            System.out.println("Enter email: ");
                            String email = scanner.next();
                            System.out.println("Enter phone number: ");
                            String phoneNumber = scanner.next();
                            System.out.println("Enter address: ");
                            String address = scanner.next();
                            System.out.println("Enter position: ");
                            String position = scanner.next();
                            System.out.println("Enter joining date (yyyy-MM-dd): ");
                            String joiningDate = scanner.next();
                            System.out.println("Enter termination date (yyyy-MM-dd, or press Enter if none): ");
                            scanner.nextLine(); // Consume leftover newline
                            String terminationDate = scanner.nextLine();
                            if (terminationDate.trim().isEmpty()) {
                                terminationDate = null; // Handle case where there is no termination date
                            }

                            Employee employee = new Employee(0, firstName, lastName, dateOfBirth, gender, email, phoneNumber, address, position, joiningDate, terminationDate, 50000);
                            employeeService.addEmployee(employee);
                            System.out.println("Employee added successfully.");
                        } catch (InvalidInputException | DatabaseConnectionException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    case 4:
                        try {
                            System.out.print("Enter Employee ID to update: ");
                            int employeeIdToUpdate = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            System.out.print("Enter new first name: ");
                            String newFirstName = scanner.nextLine();

                            System.out.print("Enter new last name: ");
                            String newLastName = scanner.nextLine();

                            System.out.print("Enter new date of birth (yyyy-MM-dd): ");
                            String newDateOfBirth = scanner.nextLine();

                            System.out.print("Enter new gender (M/F): ");
                            String newGender = scanner.nextLine();

                            System.out.print("Enter new email: ");
                            String newEmail = scanner.nextLine();

                            System.out.print("Enter new phone number: ");
                            String newPhoneNumber = scanner.nextLine();

                            System.out.print("Enter new address: ");
                            String newAddress = scanner.nextLine();

                            System.out.print("Enter new position: ");
                            String newPosition = scanner.nextLine();

                            System.out.print("Enter new joining date (yyyy-MM-dd): ");
                            String newJoiningDate = scanner.nextLine();

                            System.out.print("Enter new termination date (yyyy-MM-dd): ");
                            String newTerminationDate = scanner.nextLine();

                            // Create an Employee object with updated details
                            Employee updatedEmployee = new Employee(employeeIdToUpdate, newFirstName, newLastName, newDateOfBirth, newGender, newEmail, newPhoneNumber, newAddress, newPosition, newJoiningDate, newTerminationDate, 50000);

                            // Call the update method
                            employeeService.updateEmployee(updatedEmployee);
                            System.out.println("Employee updated successfully.");
                        } catch (EmployeeNotFoundException | InvalidInputException | DatabaseConnectionException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    case 5:
                        try {
                            System.out.print("Enter Employee ID to remove: ");
                            int id = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            if (id != 0) {
                                employeeService.removeEmployee(id);
                                System.out.println("Employee removed successfully.");
                            } else {
                                System.out.println("Invalid Employee ID provided.");
                            }
                        } catch (EmployeeNotFoundException | DatabaseConnectionException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case 6:
                        System.out.print("Enter recordID: ");
                        int idToRemove = scanner.nextInt();

                        try {
                            // Retrieve the financial record by ID
                            FinancialRecord financialRecord = financialRecordService.getFinancialRecordById(idToRemove);

                            if (financialRecord != null) {
                                System.out.println("Financial Record Details: ");
                                System.out.println(financialRecord); // This will print the details of the financial record
                                System.out.println("---------------------------------"); // Optional separator for better readability
                            } else {
                                System.out.println("Financial Record not found for Record ID: " + idToRemove);
                            }
                        } catch (DatabaseConnectionException | FinancialRecordException e) {
                            // Handle the exception (log it, display a message, etc.)
                            System.err.println("Error: " + e.getMessage());
                        }
                        break;

                    case 7:
                        System.out.print("Enter employee id: ");
                        int eeeid = scanner.nextInt();

                        try {
                            // Retrieve the financial records for the specified employee
                            List<FinancialRecord> financialRecords = financialRecordService.getFinancialRecordsForEmployee(eeeid);

                            if (!financialRecords.isEmpty()) {
                                System.out.println("Financial Record Details: ");
                                for (FinancialRecord record : financialRecords) {
                                    System.out.println(record); // This will print each financial record using its toString method
                                    System.out.println("---------------------------------"); // Optional separator for better readability
                                }
                            } else {
                                System.out.println("Financial Record not found for Employee ID: " + eeeid);
                            }
                        } catch (DatabaseConnectionException e) {
                            // Handle the exception (log it, display a message, etc.)
                            System.err.println("Error: " + e.getMessage());
                        }
                        break;

                    case 8:
                        System.out.print("Enter date to get records (yyyy-MM-dd): ");
                        String recordDate = scanner.next();

                        try {
                            // Retrieve the financial records for the specified date
                            List<FinancialRecord> financialRecordsByDate = financialRecordService.getFinancialRecordsForDate(recordDate);

                            if (!financialRecordsByDate.isEmpty()) {
                                System.out.println("Financial Record Details: ");
                                for (FinancialRecord record : financialRecordsByDate) {
                                    System.out.println(record); // This will print each financial record using its toString method
                                    System.out.println("---------------------------------"); // Optional separator for better readability
                                }
                            } else {
                                System.out.println("Financial Record not found for date: " + recordDate);
                            }
                        } catch (DatabaseConnectionException e) {
                            // Handle the exception (log it, display a message, etc.)
                            System.err.println("Error: " + e.getMessage());
                        }
                        break;

                    case 9:
                        System.out.print("Enter employee ID: ");
                        int eid = scanner.nextInt();

                        System.out.print("Enter description: ");
                        String description = scanner.next();

                        System.out.print("Enter amount: ");
                        double amount = scanner.nextDouble();

                        System.out.print("Enter record type: ");
                        String recordType = scanner.next();

                        try {
                            financialRecordService.addFinancialRecord(eid, description, amount, recordType);
                        } catch (DatabaseConnectionException e) {
                            // Handle the exception (log it, display a message, etc.)
                            System.err.println("Error: " + e.getMessage());
                        }
                        break;


                    case 10:
                        System.out.print("Enter payroll ID: ");
                        int payrollId = scanner.nextInt();
                        try {
                            // Retrieve the payroll record by ID
                            Payroll finalPayroll = payrollService.getPayrollById(payrollId);

                            if (finalPayroll != null) {
                                System.out.println("Payroll Record Details: ");
                                System.out.println("Payroll ID: " + finalPayroll.getPayrollID());
                                System.out.println("Employee ID: " + finalPayroll.getEmployeeID());
                                System.out.println("Pay Period Start Date: " + finalPayroll.getPayPeriodStartDate());
                                System.out.println("Pay Period End Date: " + finalPayroll.getPayPeriodEndDate());
                                System.out.println("Basic Salary: " + finalPayroll.getBasicSalary());
                                System.out.println("Overtime Pay: " + finalPayroll.getOvertimePay());
                                System.out.println("Deductions: " + finalPayroll.getDeductions());
                                System.out.println("Net Salary: " + finalPayroll.getNetSalary());
                            } else {
                                System.out.println("Payroll Record not found.");
                            }
                        } catch (PayrollGenerationException e) {
                            System.out.println("Error: " + e.getMessage());
                        } catch (DatabaseConnectionException e) {
                            System.out.println("Database error: " + e.getMessage());
                        }
                        break;

                    case 11:
                        System.out.print("Enter employee ID: ");
                        int eeid = scanner.nextInt();
                        try {
                            // Retrieve all payroll records for the specified employee ID
                            List<Payroll> finalPayrolls = payrollService.getPayrollsForEmployee(eeid);

                            if (finalPayrolls != null && !finalPayrolls.isEmpty()) {
                                System.out.println("Payroll Record Details:");
                                for (Payroll payroll : finalPayrolls) {
                                    System.out.println(payroll); // This will print each payroll detail on a new line
                                    System.out.println("---------------------------------"); // Optional separator for better readability
                                }
                            } else {
                                System.out.println("Payroll Records not found for Employee ID: " + eeid);
                            }
                        } catch (DatabaseConnectionException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case 12:
                        System.out.println("Enter start date year: ");
                        int startdateyear = scanner.nextInt();
                        System.out.println("Enter start date month: ");
                        int startdatemonth = scanner.nextInt();
                        System.out.println("Enter start date day: ");
                        int startdateday = scanner.nextInt();
                        System.out.println("Enter end date year: ");
                        int enddateyear = scanner.nextInt();
                        System.out.println("Enter end date month: ");
                        int enddatemonth = scanner.nextInt();
                        System.out.println("Enter end date day: ");
                        int enddateday = scanner.nextInt();

                        try {
                            List<Payroll> finalpayroll3 = payrollService.getPayrollsForPeriod(
                                    LocalDate.of(startdateyear, startdatemonth, startdateday),
                                    LocalDate.of(enddateyear, enddatemonth, enddateday)
                            );

                            if (finalpayroll3 != null && !finalpayroll3.isEmpty()) {
                                System.out.println("Payroll Record Details:");
                                for (Payroll payroll : finalpayroll3) {
                                    System.out.println(payroll);
                                }
                            } else {
                                System.out.println("Payroll Record not found.");
                            }
                        } catch (DatabaseConnectionException e) {
                            System.out.println("Database error: " + e.getMessage());
                        }
                        break;

                    case 13:
                        System.out.print("Enter Employee ID to generate payroll: ");
                        int eidd = scanner.nextInt();
                        System.out.println("Enter start date year: ");
                        int startdateyear1 = scanner.nextInt();
                        System.out.println("Enter start date month: ");
                        int startdatemonth1 = scanner.nextInt();
                        System.out.println("Enter start date day: ");
                        int startdateday1 = scanner.nextInt();
                        System.out.println("Enter end date year: ");
                        int enddateyear1 = scanner.nextInt();
                        System.out.println("Enter end date month: ");
                        int enddatemonth1 = scanner.nextInt();
                        System.out.println("Enter end date day: ");
                        int enddateday1 = scanner.nextInt();

                        try {
                            boolean isGenerated = payrollService.generatePayroll(
                                    eidd,
                                    LocalDate.of(startdateyear1, startdatemonth1, startdateday1),
                                    LocalDate.of(enddateyear1, enddatemonth1, enddateday1)
                            );

                            if (isGenerated) {
                                System.out.println("Payroll Generated Successfully.");
                            } else {
                                System.out.println("Failed to generate payroll.");
                            }
                        } catch (DatabaseConnectionException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case 14:
                        System.out.print("Enter tax ID: ");
                        int taxId = scanner.nextInt();

                        try {
                            Tax taxRecord = taxService.getTaxById(taxId);
                            if (taxRecord != null) {
                                System.out.println("Tax Record Details:");
                                System.out.println("Tax ID: " + taxRecord.getTaxID());
                                System.out.println("Employee ID: " + taxRecord.getEmployeeID());
                                System.out.println("Tax Year: " + taxRecord.getTaxYear());
                                System.out.println("Tax Amount: " + taxRecord.getTaxAmount());
                            } else {
                                System.out.println("Tax Record not found.");
                            }
                        } catch (SQLException e) {
                            System.err.println("Database error occurred: " + e.getMessage());
                        } catch (InvalidInputException e) {
                            System.err.println("Invalid input: " + e.getMessage());
                        } catch (FinancialRecordException e) {
                            System.err.println("Financial record error: " + e.getMessage());
                        }
                        break;

                    case 15:
                        System.out.print("Enter employee ID: ");
                        int employeeIdForTax = scanner.nextInt();

                        try {
                            List<Tax> taxRecordsForEmployee = taxService.getTaxesForEmployee(employeeIdForTax);
                            if (!taxRecordsForEmployee.isEmpty()) {
                                System.out.println("Tax Record Details:");
                                for (Tax t : taxRecordsForEmployee) {
                                    System.out.println("Tax ID: " + t.getTaxID());
                                    System.out.println("Employee ID: " + t.getEmployeeID());
                                    System.out.println("Tax Year: " + t.getTaxYear());
                                    System.out.println("Tax Amount: " + t.getTaxAmount());
                                    System.out.println("-----------------------------"); // Separator for clarity
                                }
                            } else {
                                System.out.println("Tax Records not found for Employee ID: " + employeeIdForTax);
                            }
                        } catch (SQLException e) {
                            System.err.println("Database error occurred: " + e.getMessage());
                        } catch (InvalidInputException e) {
                            System.err.println("Invalid input: " + e.getMessage());
                        } catch (FinancialRecordException e) {
                            System.err.println("Financial record error: " + e.getMessage());
                        }
                        break;

                    case 16:
                        System.out.print("Enter year: ");
                        int taxYear = scanner.nextInt();

                        try {
                            List<Tax> taxRecordsForYear = taxService.getTaxesForYear(taxYear);
                            if (!taxRecordsForYear.isEmpty()) {
                                System.out.println("Tax Record Details:");
                                for (Tax t : taxRecordsForYear) {
                                    System.out.println("Tax ID: " + t.getTaxID());
                                    System.out.println("Employee ID: " + t.getEmployeeID());
                                    System.out.println("Tax Year: " + t.getTaxYear());
                                    System.out.println("Tax Amount: " + t.getTaxAmount());
                                    System.out.println("-----------------------------"); // Separator for clarity
                                }
                            } else {
                                System.out.println("Tax Records not found for Year: " + taxYear);
                            }
                        } catch (SQLException e) {
                            System.err.println("Database error occurred: " + e.getMessage());
                        } catch (InvalidInputException e) {
                            System.err.println("Invalid input: " + e.getMessage());
                        } catch (FinancialRecordException e) {
                            System.err.println("Financial record error: " + e.getMessage());
                        }
                        break;

                    case 17: // Example case for calculating tax
                        System.out.print("Enter employee ID: ");
                        int empId = scanner.nextInt();

                        System.out.print("Enter tax year: ");
                        int year = scanner.nextInt();

                        try {
                            double taxAmount = taxService.CalculateTax(empId, year);
                            System.out.println("Total tax for employee ID " + empId + " in year " + year + " is: " + taxAmount);
                        } catch (SQLException e) {
                            System.err.println("Database error occurred: " + e.getMessage());
                        } catch (InvalidInputException e) {
                            System.err.println("Invalid input: " + e.getMessage());
                        } catch (TaxCalculationException e) {
                            System.err.println("Tax calculation error: " + e.getMessage());
                        }
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 0);{

                scanner.close();
            }

        }
    }

}
