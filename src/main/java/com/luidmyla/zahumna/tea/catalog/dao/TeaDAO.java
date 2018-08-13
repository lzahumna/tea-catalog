package com.luidmyla.zahumna.tea.catalog.dao;

import com.luidmyla.zahumna.tea.catalog.service.TeaCatalogService;

import java.util.HashSet;

/**
 * DAO
 *
 * @author lzahumna
 * since 20/05/2018.
 */
public interface TeaDAO {

    enum Condition {
        MIN,
        MAX,
        LENGTH
    }

    HashSet<String> getTeaTypesByCountry(String country);

    String getPopularTeaByCountry(String country, Condition condition);

    String getFamousTeaInWorldByCondition(Condition condition);

    Double getNumberOfPeopleEnjoyTea(String teaType);

    String getCountryWithUniqueTeaByCondition(Condition condition);

    Integer countUniqueTeaTypes();

    Double getNumberOfPeopleByTeaAndCountry(String teaType, String country);
}
