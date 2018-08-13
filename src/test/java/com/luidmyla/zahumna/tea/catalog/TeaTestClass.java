package com.luidmyla.zahumna.tea.catalog;

import com.luidmyla.zahumna.tea.catalog.dao.TeaDAO;
import com.luidmyla.zahumna.tea.catalog.dao.impl.map.TeaInMemoryDAO;
import com.luidmyla.zahumna.tea.catalog.service.TeaCatalogService;
import com.luidmyla.zahumna.tea.catalog.service.impl.TeaCatalogServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TeaTestClass {

    private TeaCatalogService test = new TeaCatalogServiceImpl(new TeaInMemoryDAO());

//     ________________________POSITIVE CASES_______________________________

    @Test
    public void numberOfPeopleDrinkParticularTeaTypeInWorldTest() throws Exception {
        Double expectedNumberOfPeople = 1.278869552E9;
        Double numberOfPeople = test.numberOfPeopleDrinkParticularTeaTypeInWorld("WHITE");
        Assert.assertEquals(expectedNumberOfPeople, numberOfPeople);
    }

    @Test
    public void availableTeaTypesInCountryTest() throws Exception {
        Set<String> expectedTeaSet = new HashSet<String>();
        Collections.addAll(expectedTeaSet, "WHITE", "HERBAL", "PUER", "OOLONG");
        HashSet<String> teaSet = test.availableTeaTypesInCountry("Ukraine");
        Assert.assertEquals(expectedTeaSet, teaSet);
    }

    @Test
    public void numberOfUniqueTeaTypesInCatalogTest() {
        Integer expectedNumber = 7;
        Integer number = test.numberOfUniqueTeaTypesInCatalog();
        Assert.assertEquals(expectedNumber, number);
    }

    @Test
    public void numberOfPeopleDrinksParticularTeaInCountryTest() {
        Double expectedNumber = 3.75605454E8;
        Double number = test.numberOfPeopleDrinksParticularTeaInCountry("HERBAL", "Italy");
        Assert.assertEquals(expectedNumber, number);
    }

    @Test
    public void mostPopularTeaTypeInCountryTest() {
        String expectedTea = "PUER";
        String country = "Aruba";
        String mostPopularTea = test.popularTeaTypeInCountry(country, TeaDAO.Condition.MAX);
        Assert.assertEquals(expectedTea, mostPopularTea);
    }

    @Test
    public void lessPopularTeaTypeInCountryTest() {
        String expectedTea = "GREEN";
        String country = "Angola";
        String lessPopularTea = test.popularTeaTypeInCountry(country, TeaDAO.Condition.MIN);
        Assert.assertEquals(expectedTea, lessPopularTea);
    }

    @Test
    public void mostFamousTeaTypeInWorld() {
        String expectedTea = "YELLOW";
        String mostPopularTea = test.famousTeaTypeInWorld(TeaDAO.Condition.MAX);
        Assert.assertEquals(expectedTea, mostPopularTea);
    }

    @Test
    public void lessFamousTeaTypeInWorld() {
        String expectedTea = "BLACK";
        String lessPopularTea = test.famousTeaTypeInWorld(TeaDAO.Condition.MIN);
        Assert.assertEquals(expectedTea, lessPopularTea);
    }

    @Test
    public void countryWithMostDifferentTea() {
        String expectedCountry = "Saint Pierre and Miquelon";
        String mostDifferentTea = test.countryWithDifferentTea(TeaDAO.Condition.MAX);
        Assert.assertEquals(expectedCountry, mostDifferentTea);
    }

    @Test
    public void countryWithLessDifferentTea() {
        String expectedCountry = "Northern Mariana Islands";
        String lessDifferentTea = test.countryWithDifferentTea(TeaDAO.Condition.MIN);
        Assert.assertEquals(expectedCountry, lessDifferentTea);
    }

//     ________________________NEGATIVE CASES_______________________________

    @Test(expected = RuntimeException.class)
    public void expectedTeaTypeNotValidTest() throws Exception {
        test.numberOfPeopleDrinkParticularTeaTypeInWorld("GREEN-WHITE");
    }

    @Test(expected = RuntimeException.class)
    public void countryIsNotValidTest() throws Exception {
        test.availableTeaTypesInCountry("Venice");
    }

    @Test(expected = RuntimeException.class)
    public void conditionNotValidTest() {
        test.popularTeaTypeInCountry("Ukraine", TeaDAO.Condition.LENGTH);
    }
}
