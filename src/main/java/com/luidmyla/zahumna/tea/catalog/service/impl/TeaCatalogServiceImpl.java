package com.luidmyla.zahumna.tea.catalog.service.impl;

import com.luidmyla.zahumna.tea.catalog.dao.TeaDAO;
import com.luidmyla.zahumna.tea.catalog.service.TeaCatalogService;

import java.util.HashSet;

public class TeaCatalogServiceImpl implements TeaCatalogService {

    private TeaDAO teaDAO;

    public TeaCatalogServiceImpl(TeaDAO teaDAO) {
        this.teaDAO = teaDAO;
    }

    @Override
    public HashSet<String> availableTeaTypesInCountry(String country) {
        return teaDAO.getTeaTypesByCountry(country);
    }

    @Override
    public String popularTeaTypeInCountry(String country, TeaDAO.Condition condition) {
        return teaDAO.getPopularTeaByCountry(country, condition);
    }

    @Override
    public String famousTeaTypeInWorld(TeaDAO.Condition condition) {
        return teaDAO.getFamousTeaInWorldByCondition(condition);
    }

    @Override
    public Double numberOfPeopleDrinkParticularTeaTypeInWorld(String teaType) {
        return teaDAO.getNumberOfPeopleEnjoyTea(teaType);
    }

    @Override
    public String countryWithDifferentTea(TeaDAO.Condition condition) {
        return teaDAO.getCountryWithUniqueTeaByCondition(condition);
    }

    @Override
    public Integer numberOfUniqueTeaTypesInCatalog() {
        return teaDAO.countUniqueTeaTypes();
    }

    @Override
    public Double numberOfPeopleDrinksParticularTeaInCountry(String teaType, String country) {
        return teaDAO.getNumberOfPeopleByTeaAndCountry(teaType, country);
    }
}
