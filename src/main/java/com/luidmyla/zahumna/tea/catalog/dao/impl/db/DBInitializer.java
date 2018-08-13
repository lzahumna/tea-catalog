package com.luidmyla.zahumna.tea.catalog.dao.impl.db;

import com.luidmyla.zahumna.tea.catalog.dao.impl.db.constants.DBConnectionDetails;
import com.luidmyla.zahumna.tea.catalog.domain.Tea;

import java.sql.*;
import java.util.Set;

import static com.luidmyla.zahumna.tea.catalog.utils.JSONTeaLoader.*;
import static com.luidmyla.zahumna.tea.catalog.dao.impl.db.constants.DBQueries.*;

class DBInitializer {

    static void initialize(Connection connection) {
        try {
            fillTablesIfTheyAreEmpty(connection);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void fillTablesIfTheyAreEmpty(Connection connection) throws SQLException {
        if (countRawsInTable(connection, countRowsInType) == 0) {
            int insertedTeaTypeCount = insertRecords(connection, uniqueTypesOfTea, insertValuesIntoType);
            System.out.println("Tea type table was successfully filled with " + insertedTeaTypeCount + " records");
        } else {
            System.out.println("Tea type table was not affected");
        }

        if (countRawsInTable(connection, countRowsInCounry) == 0) {
            int insertedCountryCount = insertRecords(connection, uniqueCountries, insertValuesIntoCountry);
            System.out.println("Country table was successfully filled with " + insertedCountryCount + " records");
        } else {
            System.out.println("Country table was not affected");
        }

        if (countRawsInTable(connection, countRawsInTea) == 0) {
            int insertedPeopleEnjoyItCount = insertPeopleEnjoyIt(connection);
            System.out.println("Country table was successfully filled with " + insertedPeopleEnjoyItCount + " records");
        } else {
            System.out.println("Tea table was not affected");
        }
    }

    private static int insertPeopleEnjoyIt(Connection connection) throws SQLException {
        double time = System.currentTimeMillis();
        int count = 0;
        PreparedStatement pstmt = connection.prepareStatement(insertValuesIntoTea);
        for (Tea tea : teaList) {
            pstmt.setDouble(1, tea.getPeopleEnjoyIt());
            pstmt.setString(2, tea.getType());
            pstmt.setString(3, tea.getCountry());
            pstmt.addBatch();

            if (++count % DBConnectionDetails.batchSize == 0) {
                pstmt.executeBatch();
            }
        }
        pstmt.executeBatch();
        System.out.println("Time of all queries execution is " + (System.currentTimeMillis() - time) / 1000 + " seconds");
        return count;
    }

    private static int insertRecords(Connection connection, Set<String> set, String sql) throws SQLException {
        int insertedCount = 0;
        for (String item : set) {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, item);
            insertedCount += pstmt.executeUpdate();
        }
        return insertedCount;
    }

    private static int countRawsInTable(Connection connection, String sql) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("TOTAL");
        } else {
            throw new RuntimeException("check your count queries");
        }
    }
}
