package com.luidmyla.zahumna.tea.catalog.dao.impl.map;

import com.luidmyla.zahumna.tea.catalog.dao.TeaDAO;
import com.luidmyla.zahumna.tea.catalog.service.TeaCatalogService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

/**
 * MapDAO
 *
 * @author lzahumna
 * since 20/05/2018.
 */
public class TeaInMemoryDAO implements TeaDAO {
    @Override
    public HashSet<String> getTeaTypesByCountry(String country) {
        HashSet<String> value = MapLoader.sortedCountryToTeaTypes.get(country);
        if (value == null) {
            throw new RuntimeException("Country does not exist!");
        }
        return value;
    }

    @Override
    public String getPopularTeaByCountry(String country, Condition condition) {
        TreeMap<String, Double> map = MapLoader.countryToTeaTypeAndPeopleEnjoyIt.get(country);
        return valueByCondition(condition, map);
    }

    @Override
    public String getFamousTeaInWorldByCondition(Condition condition) {
        return valueByCondition(condition, MapLoader.sortedTeaType2peopleEnjoyIt);
    }

    @Override
    public Double getNumberOfPeopleEnjoyTea(String teaType) {
        Double value = MapLoader.sortedTeaType2peopleEnjoyIt.get(teaType);
        if (value == null) {
            throw new RuntimeException("Tea type does not exist!");
        }
        return value;
    }

    @Override
    public String getCountryWithUniqueTeaByCondition(Condition condition) {
        switch (condition) {
            case MAX:
                return MapLoader.sortedCountryToTeaTypes.firstEntry().getKey();
            case MIN:
                return MapLoader.sortedCountryToTeaTypes.lastEntry().getKey();
            default:
                throw new RuntimeException("Condition should be MAX or MIN");
        }
    }

    @Override
    public Integer countUniqueTeaTypes() {
        return MapLoader.uniqueTypesOfTea.size();
    }

    @Override
    public Double getNumberOfPeopleByTeaAndCountry(String teaType, String country) {
        HashMap<String, Double> value = MapLoader.teaType2countryAndPeopleEnjoyIt.get(teaType);
        if (value == null) {
            throw new RuntimeException("Tea type does not exist!");
        }
        if (!value.containsKey(country)) {
            throw new RuntimeException("Country does not exist!");
        }
        return value.get(country);

    }

    private String valueByCondition(Condition condition, TreeMap<String, Double> map) {
        switch (condition) {
            case MAX:
                return map.firstEntry().getKey();
            case MIN:
                return map.lastEntry().getKey();
            default:
                throw new RuntimeException("Condition should be MAX or MIN");
        }
    }
}
