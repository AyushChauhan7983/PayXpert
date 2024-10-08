package org.example;

import dao.PayrollServiceImp;
import entity.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class Test {

    private final PayrollServiceImp payrollService = new PayrollServiceImp();

    @org.junit.jupiter.api.Test
    public void testCalculateGrossSalaryForEmployee() {
        try{
            Employee employee = new Employee(1, "John Doe", 50000);
            PayrollServiceImp payrollService = new PayrollServiceImp();

            double grossSalary = payrollService.calculateGrossSalary(employee);

            // Assuming gross salary includes bonuses and allowances.
            assertEquals(50000, grossSalary, "Gross salary should be 5,000 including bonuses");
            System.out.println("testVerifytestCalculateGrossSalaryForEmployee passed.");
        }
        catch (Exception e)
        {
            System.out.println("testVerifyTaxCalculationForHighIncomeEmployee failed: " + e.getMessage());
        }

    }

    @org.junit.jupiter.api.Test
    public void testVerifyTaxCalculationForHighIncomeEmployee() {
        try {
            Employee highIncomeEmployee = new Employee(1, "Alice Johnson", 200000); // Basic salary: ₹200,000
            double grossSalary = payrollService.calculateGrossSalary(highIncomeEmployee);
            double expectedTax = grossSalary * 0.30; // 30% tax
            double calculatedTax = payrollService.calculateTax(highIncomeEmployee);
            assertEquals(expectedTax, calculatedTax, "Tax for high-income employees should be calculated correctly");
            System.out.println("testVerifyTaxCalculationForHighIncomeEmployee passed.");
        } catch (Exception e) {
            System.out.println("testVerifyTaxCalculationForHighIncomeEmployee failed: " + e.getMessage());
        }
    }

//    @org.junit.jupiter.api.Test
//    public void testCalculateNetSalaryAfterDeductions() {
//        try {
//            Employee employee = new Employee(2, "Jane Smith", 60000); // Basic salary: 60,000
//            double netSalary = payrollService.calculateNetSalary(employee);
//            assertEquals(54000, netSalary, "Net salary should be 54,000 after deductions");
//            System.out.println("testCalculateNetSalaryAfterDeductions passed.");
//        } catch (Exception e) {
//            System.out.println("testCalculateNetSalaryAfterDeductions failed: " + e.getMessage());
//        }
//    }

    @org.junit.jupiter.api.Test
    public void testProcessPayrollForMultipleEmployees() {
        try {
            List<Employee> employees = new ArrayList<>();
            employees.add(new Employee(1, "Alice Johnson", 50000)); // Basic Salary: 50,000
            employees.add(new Employee(2, "Bob Smith", 70000));    // Basic Salary: 70,000
            employees.add(new Employee(3, "Charlie Brown", 100000)); // Basic Salary: 1,00,000

            for (Employee employee : employees) {
                double netSalary = payrollService.calculateNetSalary(employee);
                assertNotNull(netSalary, "Net salary should not be null");
                assertTrue(netSalary > 0, "Net salary should be greater than zero");
            }
            System.out.println("testProcessPayrollForMultipleEmployees passed.");
        } catch (Exception e) {
            System.out.println("testProcessPayrollForMultipleEmployees failed: " + e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    public void testVerifyErrorHandlingForInvalidEmployeeData() {
        try {
            Employee invalidEmployee = new Employee(1, "Ayush Chauhan", 50000);

            System.out.println("testVerifyErrorHandlingForInvalidEmployeeData passed.");
        } catch (Exception e) {
            System.out.println("testVerifyErrorHandlingForInvalidEmployeeData failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===============================================");
            System.out.println("               Select test case to run:");
            System.out.println("               1: Calculate Gross Salary for Employee");
            System.out.println("               2: Verify Tax Calculation for High-Income Employee");
            System.out.println("               3: Process Payroll for Multiple Employees");
            System.out.println("               4: Verify Error Handling for Invalid Employee Data");
            System.out.println("               5: Exit");
            System.out.println("===============================================");
            System.out.print("               Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> test.testCalculateGrossSalaryForEmployee();
                case 2 -> test.testVerifyTaxCalculationForHighIncomeEmployee();
                case 3 -> test.testProcessPayrollForMultipleEmployees();
                case 4 -> test.testVerifyErrorHandlingForInvalidEmployeeData();
                case 5 -> {
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                }
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                    continue; // Continue to the next iteration for invalid input
                }
            }

            // Ask if the user wants to run another test
            System.out.print("Do you want to run another test case? (yes/no): ");
            String response = scanner.next();
            if (!response.equalsIgnoreCase("yes")) {
                System.out.println("Exiting...");
                scanner.close();
                return;
            }
        }
    }
}
