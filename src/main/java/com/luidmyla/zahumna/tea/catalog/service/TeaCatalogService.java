package com.luidmyla.zahumna.tea.catalog.service;

import com.luidmyla.zahumna.tea.catalog.dao.TeaDAO;

import java.util.HashSet;

public interface TeaCatalogService {

    HashSet<String> availableTeaTypesInCountry(String country);

    String popularTeaTypeInCountry(String country, TeaDAO.Condition condition);

    String famousTeaTypeInWorld(TeaDAO.Condition condition);

    Double numberOfPeopleDrinkParticularTeaTypeInWorld(String teaType);

    String countryWithDifferentTea(TeaDAO.Condition condition);

    Integer numberOfUniqueTeaTypesInCatalog();

    Double numberOfPeopleDrinksParticularTeaInCountry(String teaType, String country);
}
