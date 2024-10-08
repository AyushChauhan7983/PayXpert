package dao;

import entity.model.FinancialRecord;
import exception.DatabaseConnectionException;
import exception.FinancialRecordException;

import java.util.*;
import java.sql.*;
import java.time.LocalDate;

public class FinancialRecordServiceImp implements FinancialRecordServiceInt {
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public FinancialRecord getFinancialRecordById(int recordId) throws DatabaseConnectionException, FinancialRecordException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        FinancialRecord financialRecord = null;

        String sql = "SELECT * FROM FinancialRecord WHERE RecordID = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, recordId);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                financialRecord = new FinancialRecord();
                financialRecord.setRecordID(resultSet.getInt("RecordID"));
                financialRecord.setEmployeeID(resultSet.getInt("EmployeeID"));
                financialRecord.setRecordDate(resultSet.getString("RecordDate"));
                financialRecord.setDescription(resultSet.getString("Description"));
                financialRecord.setAmount(resultSet.getDouble("Amount"));
                financialRecord.setRecordType(resultSet.getString("RecordType"));
            } else {
                throw new FinancialRecordException("Financial record not found for ID: " + recordId);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error while retrieving financial record.");
        } finally {
            closeResources(preparedStatement, resultSet);
        }
        return financialRecord;
    }

    @Override
    public List<FinancialRecord> getFinancialRecordsForEmployee(int employeeId) throws DatabaseConnectionException {
        List<FinancialRecord> financialRecords = new ArrayList<>();
        String sql = "SELECT * FROM FinancialRecord WHERE EmployeeID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, employeeId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                FinancialRecord financialRecord = new FinancialRecord();
                financialRecord.setRecordID(resultSet.getInt("RecordID"));
                financialRecord.setEmployeeID(resultSet.getInt("EmployeeID"));
                financialRecord.setRecordDate(resultSet.getString("RecordDate"));
                financialRecord.setDescription(resultSet.getString("Description"));
                financialRecord.setAmount(resultSet.getDouble("Amount"));
                financialRecord.setRecordType(resultSet.getString("RecordType"));

                financialRecords.add(financialRecord);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error while retrieving financial records for employee.");
        }
        return financialRecords;
    }

    public List<FinancialRecord> getFinancialRecordsForDate(String recordDate) throws DatabaseConnectionException {
        List<FinancialRecord> financialRecords = new ArrayList<>();
        String sql = "SELECT * FROM FinancialRecord WHERE RecordDate = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, recordDate);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                FinancialRecord financialRecord = new FinancialRecord();
                financialRecord.setRecordID(resultSet.getInt("RecordID"));
                financialRecord.setEmployeeID(resultSet.getInt("EmployeeID"));
                financialRecord.setRecordDate(resultSet.getString("RecordDate"));
                financialRecord.setDescription(resultSet.getString("Description"));
                financialRecord.setAmount(resultSet.getDouble("Amount"));
                financialRecord.setRecordType(resultSet.getString("RecordType"));

                financialRecords.add(financialRecord);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error while retrieving financial records for the date.");
        }
        return financialRecords;
    }

    @Override
    public void addFinancialRecord(int employeeId, String description, double amount, String recordType) throws DatabaseConnectionException {
        String sql = "INSERT INTO FinancialRecord (EmployeeID, RecordDate, Description, Amount, RecordType) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, employeeId);
            preparedStatement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            preparedStatement.setString(3, description);
            preparedStatement.setDouble(4, amount);
            preparedStatement.setString(5, recordType);

            preparedStatement.executeUpdate();
            System.out.println("Financial record added successfully.");

        } catch (SQLException e) {
            // Include the original exception message for better debugging
            throw new DatabaseConnectionException("Error while adding financial record: " + e.getMessage());
        }
    }


    private void closeResources(PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Could also be logged
        }
    }
}
