package com.luidmyla.zahumna.tea.catalog.dao.impl.db;

import com.luidmyla.zahumna.tea.catalog.dao.TeaDAO;
import com.luidmyla.zahumna.tea.catalog.dao.impl.db.constants.DBConnectionDetails;

import java.sql.*;
import java.util.HashSet;

import static com.luidmyla.zahumna.tea.catalog.dao.impl.db.constants.DBQueries.*;

/**
 * TeaDAODBImpl
 *
 * @author lzahumna
 * since 20/05/2018.
 */
public class TeaDAODBImpl implements TeaDAO {

    public TeaDAODBImpl() {
        DBInitializer.initialize(getConnection());
    }

    private Connection getConnection() {
        try {
            Class.forName(DBConnectionDetails.driver);

            return DriverManager.getConnection(
                    DBConnectionDetails.url,
                    DBConnectionDetails.user,
                    DBConnectionDetails.password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("cannot create connection", e);
        }
    }

    @Override
    public HashSet<String> getTeaTypesByCountry(String country) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(getTeaTypesByCountry);
            pstmt.setString(1, country);
            ResultSet rs = pstmt.executeQuery();
            HashSet<String> values = new HashSet<>();

            while (rs.next()) {
                values.add(rs.getString("name"));
            }

            return values;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getPopularTeaByCountry(String country, Condition condition) {
        String sql;

        switch (condition) {
            default:
            case MAX:
                sql = getMostPopularTeaByCountry;
                break;
            case MIN:
                sql = getLessPopularTeaByCountry;
                break;
        }

        String result = null;

        try {
            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, country);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public String getFamousTeaInWorldByCondition(Condition condition) {
        String sql;

        switch (condition) {
            default:
            case MAX:
                sql = getMostFamousTeaInWorldByCondition;
                break;
            case MIN:
                sql = getLessFamousTeaInWorldByCondition;
                break;
        }

        try {
            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Double getNumberOfPeopleEnjoyTea(String teaType) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(getNumberOfPeopleEnjoyTea);
            pstmt.setString(1, teaType);

            double result = 0.0d;

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getDouble("peopleEnjoyIt");
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getCountryWithUniqueTeaByCondition(Condition condition) {
        String sql;

        switch (condition) {
            default:
            case MAX:
                sql = getCountryWithMostDifferentTea;
                break;
            case MIN:
                sql = getCountryWithLessDifferentTea;
                break;
        }

        String result = null;

        try {
            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public Integer countUniqueTeaTypes() {
        int count = 0;

        try {
            PreparedStatement pstmt = getConnection().prepareStatement(countUniqueTeaTypes);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("countName");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return count;
    }

    @Override
    public Double getNumberOfPeopleByTeaAndCountry(String teaType, String country) {
        double result = 0.0d;

        try {
            PreparedStatement pstmt = getConnection().prepareStatement(getNumberOfPeopleByTeaAndCountry);
            pstmt.setString(1, country);
            pstmt.setString(2, teaType);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getDouble("peopleEnjoyIt");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static void main(String[] args) {
        TeaDAODBImpl dao = new TeaDAODBImpl();

        System.out.println("getTeaTypesByCountry" + dao.getTeaTypesByCountry("Ukraine"));
        System.out.println();


        System.out.println("Max in country " + dao.getPopularTeaByCountry("Northern Mariana Islands", Condition.MAX));
        System.out.println();
        System.out.println("Min in country " + dao.getPopularTeaByCountry("Northern Mariana Islands", Condition.MIN));
        System.out.println();


        System.out.println("Max in the world " + dao.getFamousTeaInWorldByCondition(Condition.MAX));
        System.out.println();
        System.out.println("Min in the world " + dao.getFamousTeaInWorldByCondition(Condition.MIN));
        System.out.println();


        System.out.println("Number of people drink particular type of tea around the world " + dao.getNumberOfPeopleEnjoyTea("GREEN"));
        System.out.println();

        System.out.println("Country drinks the most different types of tea " + dao.getCountryWithUniqueTeaByCondition(Condition.MAX));
        System.out.println();
        System.out.println("Country drinks the less different types of tea " + dao.getCountryWithUniqueTeaByCondition(Condition.MIN));
        System.out.println();

        System.out.println("Number of unique types of tea existing in the catalog " + dao.countUniqueTeaTypes());
        System.out.println();

        System.out.println("Number of people drink particular type of tea per country " + dao.getNumberOfPeopleByTeaAndCountry("GREEN", "Italy"));
    }
}
