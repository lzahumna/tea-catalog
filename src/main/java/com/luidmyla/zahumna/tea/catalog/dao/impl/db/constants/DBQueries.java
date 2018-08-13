package com.luidmyla.zahumna.tea.catalog.dao.impl.db.constants;

/**
 * DBQueries
 *
 * @author lzahumna
 * since 20/05/2018.
 */
public interface DBQueries {

    // init scripts to fill DB
    String insertValuesIntoType = "INSERT INTO TYPE (name) VALUES (?)";

    String insertValuesIntoCountry = "INSERT INTO COUNTRY (name) VALUES (?)";

    String insertValuesIntoTea = "INSERT INTO TEA (peopleEnjoyit, type_id, country_id) " +
            "VALUES (?, (SELECT id FROM TYPE WHERE name = ?), " +
            "(SELECT id FROM COUNTRY WHERE name = ?))";

    // count rows in each table
    String countRowsInType = "SELECT COUNT(1) AS TOTAL FROM TYPE";
    String countRowsInCounry = "SELECT COUNT(1) AS TOTAL FROM COUNTRY";
    String countRawsInTea = "SELECT COUNT(1) AS TOTAL FROM TEA";


    String getTeaTypesByCountry = "SELECT" +
            "  TEA.COUNTRY_ID," +
            "  TYPE.NAME " +
            " FROM TEA, TYPE, COUNTRY" +
            " WHERE TEA.TYPE_ID = TYPE.ID AND TEA.COUNTRY_ID = COUNTRY.ID AND COUNTRY.NAME = ?" +
            " GROUP BY TEA.COUNTRY_ID, TYPE.NAME";

    String getMostPopularTeaByCountry = "SELECT name FROM TYPE WHERE id = " +
            "(SELECT type_id FROM (SELECT type_id," +
            " sum(peopleEnjoyIt) AS peopleEnjoyIt," +
            " country_id" +
            " FROM TEA, TYPE" +
            " WHERE country_id = (SELECT id FROM COUNTRY WHERE name = ?)" +
            " GROUP BY type_id, country_id" +
            " ORDER BY peopleEnjoyIt DESC" +
            " LIMIT 1) as tab)";

    String getLessPopularTeaByCountry = "SELECT name FROM TYPE WHERE id = " +
            "(SELECT type_id FROM (SELECT type_id," +
            " sum(peopleEnjoyIt) AS peopleEnjoyIt," +
            " country_id" +
            " FROM TEA, TYPE" +
            " WHERE country_id = (SELECT id FROM COUNTRY WHERE name = ?)" +
            " GROUP BY type_id, country_id" +
            " ORDER BY peopleEnjoyIt ASC" +
            " LIMIT 1) as tab)";

    // todo refactor with joins
    String getMostFamousTeaInWorldByCondition = "SELECT name" +
            " FROM TYPE" +
            " WHERE id = (SELECT type_id" +
            " FROM (SELECT" +
            " type_id," +
            " sum(peopleEnjoyIt) AS peopleEnjoyIt" +
            " FROM TEA, TYPE" +
            " GROUP BY type_id" +
            " ORDER BY peopleEnjoyIt DESC" +
            " LIMIT 1) AS tab)";

    // todo refactor with joins
    String getLessFamousTeaInWorldByCondition = "SELECT name" +
            " FROM TYPE" +
            " WHERE id = (SELECT type_id" +
            " FROM (SELECT" +
            " type_id," +
            " sum(peopleEnjoyIt) AS peopleEnjoyIt" +
            " FROM TEA, TYPE" +
            " GROUP BY type_id" +
            " ORDER BY peopleEnjoyIt ASC" +
            " LIMIT 1) AS tab)";

    String getNumberOfPeopleEnjoyTea = "SELECT sum(peopleEnjoyIt) AS peopleEnjoyIt" +
            " FROM TEA, TYPE" +
            " WHERE type_id = (SELECT id" +
            " FROM TYPE" +
            " WHERE name = ?)";

    String getCountryWithMostDifferentTea = "SELECT" +
            " COUNTRY.name," +
            " COUNT(typeId) AS count" +
            " FROM (SELECT" +
            " DISTINCT" +
            " (type_id) AS typeId," +
            " country_id" +
            " FROM TEA" +
            " GROUP BY country_id, type_id) AS agg, COUNTRY" +
            " WHERE agg.country_id = COUNTRY.id" +
            " GROUP BY country_id" +
            " ORDER BY count DESC" +
            " LIMIT 1";

    String getCountryWithLessDifferentTea = "SELECT" +
            " COUNTRY.name," +
            " COUNT(typeId) AS count" +
            " FROM (SELECT" +
            " DISTINCT" +
            " (type_id) AS typeId," +
            " country_id" +
            " FROM TEA" +
            " GROUP BY country_id, type_id) AS agg, COUNTRY" +
            " WHERE agg.country_id = COUNTRY.id" +
            " GROUP BY country_id" +
            " ORDER BY count ASC" +
            " LIMIT 1";

    String countUniqueTeaTypes = "SELECT COUNT(DISTINCT name) AS countName FROM TYPE";

    String getNumberOfPeopleByTeaAndCountry = "SELECT" +
            " type_id," +
            " sum(peopleEnjoyIt) AS peopleEnjoyIt," +
            " country_id" +
            " FROM TEA, TYPE" +
            " WHERE country_id = (SELECT id" +
            " FROM COUNTRY" +
            " WHERE name = ?) AND type_id = (SELECT id" +
            " FROM TYPE" +
            " WHERE name = ?)";
}
