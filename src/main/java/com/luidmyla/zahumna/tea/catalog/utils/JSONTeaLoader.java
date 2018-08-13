package com.luidmyla.zahumna.tea.catalog.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luidmyla.zahumna.tea.catalog.domain.Tea;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.*;

public class JSONTeaLoader {

    //    Set (uniqueTeaTypes)
    public static final Set<String> uniqueTypesOfTea = new HashSet<>();
    //    Set (uniqueCountries)
    public static final Set<String> uniqueCountries = new HashSet<>();

    public static final List<Tea> teaList;

    static {
        teaList = load();
        loadUniqueTypesAndCountries(teaList);
    }

    private static void loadUniqueTypesAndCountries(List<Tea> teaList) {
        for (Tea tea : teaList) {
            uniqueTypesOfTea.add(tea.getType());
            uniqueCountries.add(tea.getCountry());
        }
    }

    //    Get List <String> from json file.
    private static List<Tea> load() {
        Gson gson = new Gson();
        try (InputStream stream = new ClassPathResource("hugeTeaCatalog.json").getInputStream()) {
            String s = IOUtils.toString(stream);
            Type listType = new TypeToken<ArrayList<Tea>>() {
            }.getType();
            return gson.fromJson(s, listType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
