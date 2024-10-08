package entity.model;
import java.util.*;
public class Payroll {
    private int payrollID;
    private int employeeID;
    private Date payPeriodStartDate;
    private Date payPeriodEndDate;
    private double basicSalary;
    private double overtimePay;
    private double deductions;
    private double netSalary;
    
    public Payroll() {
        // Default constructor
    }


    public Payroll(int payrollID, int employeeID, Date payPeriodStartDate, Date payPeriodEndDate,
                   double basicSalary, double overtimePay, double deductions, double netSalary) {
        this.payrollID = payrollID;
        this.employeeID = employeeID;
        this.payPeriodStartDate = payPeriodStartDate;
        this.payPeriodEndDate = payPeriodEndDate;
        this.basicSalary = basicSalary;
        this.overtimePay = overtimePay;
        this.deductions = deductions;
        this.netSalary = netSalary;
    }

	public int getPayrollID() {
		return payrollID;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public Date getPayPeriodStartDate() {
		return payPeriodStartDate;
	}

	public Date getPayPeriodEndDate() {
		return payPeriodEndDate;
	}

	public double getBasicSalary() {
		return basicSalary;
	}

	public double getOvertimePay() {
		return overtimePay;
	}

	public double getDeductions() {
		return deductions;
	}

	public double getNetSalary() {
		return netSalary;
	}
    @Override
    public String toString() {
        return "Payroll{" +
                "payrollID=" + payrollID +
                ", employeeID=" + employeeID +
                ", payPeriodStartDate=" + payPeriodStartDate +
                ", payPeriodEndDate=" + payPeriodEndDate +
                ", basicSalary=" + basicSalary +
                ", overtimePay=" + overtimePay +
                ", deductions=" + deductions +
                ", netSalary=" + netSalary +
                '}';
    }


	public void setPayrollID(int payrollID) {
		this.payrollID = payrollID;
	}


	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}


	public void setPayPeriodStartDate(Date payPeriodStartDate) {
		this.payPeriodStartDate = payPeriodStartDate;
	}


	public void setPayPeriodEndDate(Date payPeriodEndDate) {
		this.payPeriodEndDate = payPeriodEndDate;
	}


	public void setBasicSalary(double basicSalary) {
		this.basicSalary = basicSalary;
	}


	public void setOvertimePay(double overtimePay) {
		this.overtimePay = overtimePay;
	}


	public void setDeductions(double deductions) {
		this.deductions = deductions;
	}


	public void setNetSalary(double netSalary) {
		this.netSalary = netSalary;
	}



}
