package entity.model;
import java.util.Date;

public class Employee {
	private int employeeID;
	private String firstName;
	private String lastName;
	private String dateOfBirth;
	private String gender;
	private String email;
	private String phoneNumber;
	private String address;
	private String position;
	private String joiningDate;
	private String terminationDate;

	private double basicSalary;

	// Constructors (default and parametrized)
	public Employee() {
		// Default constructor
	}

	public Employee(int employeeID, String firstName, String lastName, String dateOfBirth,
					String gender, String email, String phoneNumber, String address,
					String position, String joiningDate, String terminationDate, double basicSalary) {
		// Parametrized constructor
		this.employeeID = employeeID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.position = position;
		this.joiningDate = joiningDate;
		this.terminationDate = terminationDate;
		this.basicSalary = basicSalary;
	}

	public Employee(int i, String name, int i1) {
		if (i <= 0) {
			throw new IllegalArgumentException("Employee ID must be positive.");
		}
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("First name cannot be null or empty.");
		}
		if (i1 < 0) {
			throw new IllegalArgumentException("Basic salary cannot be negative.");
		}
	}

	// Getters and setters
	// ... (Getters and setters for all properties)

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public void setTerminationDate(String terminationDate) {
		this.terminationDate = terminationDate;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public String getPosition() {
		return position;
	}

	public String getJoiningDate() {
		return joiningDate;
	}

	public String getTerminationDate() {
		return terminationDate;
	}

	public double getBasicSalary() {
		return basicSalary; // Return the actual basic salary
	}

	public void setBasicSalary(double basicSalary) {
		this.basicSalary = basicSalary;
	}


	// Method
	public int calculateAge() {
		Date currentDate = new Date();
		int currentYear = currentDate.getYear();
		int birthYear = Integer.parseInt(dateOfBirth);
		return currentYear - birthYear;
	}

	@Override
	public String toString() {
		return "Employee{" +
				"employeeID=" + getEmployeeID() +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", dateOfBirth=" + dateOfBirth +
				", gender='" + gender + '\'' +
				", email='" + email + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", address='" + address + '\'' +
				", position='" + position + '\'' +
				", joiningDate=" + joiningDate +
				", terminationDate=" + terminationDate +
				'}';
	}

}


