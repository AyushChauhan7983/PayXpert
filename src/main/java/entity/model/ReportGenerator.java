package entity.model;

import java.util.*;

public class ReportGenerator {
    public static String generatePayrollReport(List<Payroll> payrolls) {
        StringBuilder report = new StringBuilder();
        if (payrolls.isEmpty()) {
            report.append("No payroll data available.");
        } else {
            for (Payroll payroll : payrolls) {
                report.append("Payroll Report for Payroll ID: ").append(payroll.getPayrollID()).append("\n");
                report.append("Pay Period: ").append(payroll.getPayPeriodStartDate()).append(" to ").append(payroll.getPayPeriodEndDate()).append("\n");
                report.append("Basic Salary: ").append(payroll.getBasicSalary()).append("\n");
                report.append("Overtime Pay: ").append(payroll.getOvertimePay()).append("\n");
                report.append("Deductions: ").append(payroll.getDeductions()).append("\n");
                report.append("Net Salary: ").append(payroll.getNetSalary()).append("\n");
                report.append("----------------------------------------\n");
            }
        }
        return report.toString();
    }

    public static String generateTaxReport(List<Tax> taxes) {
        StringBuilder report = new StringBuilder();
        if (taxes.isEmpty()) {
            report.append("No tax data available.");
        } else {
            for (Tax tax : taxes) {
                report.append("Tax Report for Tax ID: ").append(tax.getTaxID()).append("\n");
                report.append("Tax Year: ").append(tax.getTaxYear()).append("\n");
                report.append("Taxable Income: ").append(tax.getTaxableIncome()).append("\n");
                report.append("Tax Amount: ").append(tax.getTaxAmount()).append("\n");
                report.append("----------------------------------------\n");
            }
        }
        return report.toString();
    }

    public static String generateFinancialRecordReport(List<FinancialRecord> financialRecords) {
        StringBuilder report = new StringBuilder();
        if (financialRecords.isEmpty()) {
            report.append("No financial record data available.");
        } else {
            for (FinancialRecord record : financialRecords) {
                report.append("Financial Record Report for Record ID: ").append(record.getRecordID()).append("\n");
                report.append("Record Date: ").append(record.getRecordDate()).append("\n");
                report.append("Description: ").append(record.getDescription()).append("\n");
                report.append("Amount: ").append(record.getAmount()).append("\n");
                report.append("Record Type: ").append(record.getRecordType()).append("\n");
                report.append("----------------------------------------\n");
            }
        }
        return report.toString();
    }
}
