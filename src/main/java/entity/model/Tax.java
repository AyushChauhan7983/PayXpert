package entity.model;
import java.util.*;
public class Tax {
    private int taxID;
    private int employeeID;
    private int taxYear;
    private double taxableIncome;
    private double taxAmount;
    
    public Tax() {
        // Default constructor
    }

	public Tax(int taxID, int employeeID, int taxYear, double taxableIncome, double taxAmount) {
        this.taxID = taxID;
        this.employeeID = employeeID;
        this.taxYear = taxYear;
        this.taxableIncome = taxableIncome;
        this.taxAmount = taxAmount;
    }


    public int getTaxID() {
		return taxID;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public int getTaxYear() {
		return taxYear;
	}

	public double getTaxableIncome() {
		return taxableIncome;
	}

	public double getTaxAmount() {
		return taxAmount;
	}
    @Override
	public String toString() {
		return "Tax [taxID=" + taxID + ", employeeID=" + employeeID + ", taxYear=" + taxYear + ", taxableIncome="
				+ taxableIncome + ", taxAmount=" + taxAmount + "]";
	}

	public void setTaxID(int taxID) {
		this.taxID = taxID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public void setTaxYear(int taxYear) {
		this.taxYear = taxYear;
	}

	public void setTaxableIncome(double taxableIncome) {
		this.taxableIncome = taxableIncome;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}



}
