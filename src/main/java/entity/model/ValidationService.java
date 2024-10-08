package entity.model;

import entity.model.Employee;

public class ValidationService {
    // Method for validating employee data
    public static boolean isValidEmployeeData(Employee employee) {
        if (employee == null) {
            return false; // Employee object is null, invalid
        }

        if (employee.getFirstName() == null || employee.getLastName() == null || employee.getDateOfBirth() == null
                || employee.getGender() == "\u0000" || employee.getEmail() == null || employee.getPhoneNumber() == null
                || employee.getAddress() == null || employee.getPosition() == null || employee.getJoiningDate() == null) {
            return false;
        }

        // If all validations pass, consider the employee data as valid
        return true;
    }

}
