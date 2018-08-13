package com.luidmyla.zahumna.tea.catalog.dao.impl.map;

import com.luidmyla.zahumna.tea.catalog.domain.Tea;

import java.util.*;

import static com.luidmyla.zahumna.tea.catalog.utils.JSONTeaLoader.teaList;

class MapLoader {

    //    Map(country, Map(teaType, peopleEnjoyIt))
    static final Map<String, TreeMap<String, Double>> countryToTeaTypeAndPeopleEnjoyIt = new HashMap<>();
    //    Map (teaType, peopleEnjoyIt)
    static final TreeMap<String, Double> sortedTeaType2peopleEnjoyIt;
    //    Map (country, setTeaType)
    static TreeMap<String, HashSet<String>> sortedCountryToTeaTypes;
    //    Set (uniqueTeaType)
    static final Set<String> uniqueTypesOfTea = new HashSet<>();
    //    Map(teaType, Map(country, peopleEnjoyIt))
    static final Map<String, HashMap<String, Double>> teaType2countryAndPeopleEnjoyIt = new HashMap<>();

    static {
        countryToTeaTypeAndpeopleEnjoyItLoad(teaList);
        sortedTeaType2peopleEnjoyIt = teaType2peopleEnjoyItLoad(teaList);
        sortedCountryToTeaTypes = countryToTeaTypesload(teaList);
        uniqueTypesOfTeaLoad(teaList);
        teaType2countryAndPeopleEnjoyItLoad(teaList);
    }

    private static void countryToTeaTypeAndpeopleEnjoyItLoad(List<Tea> teaList) {
        Map<String, HashMap<String, Double>> map = new HashMap<>();

        for (Tea tea : teaList) {
            String country = tea.getCountry();
            HashMap<String, Double> value = map.get(country);
            if (value != null) {
                Double peopleEnjoyIt = value.get(tea.getType());

                if (peopleEnjoyIt == null) {
                    value.put(tea.getType(), tea.getPeopleEnjoyIt());
                } else {
                    value.put(tea.getType(), peopleEnjoyIt + tea.getPeopleEnjoyIt());
                }
            } else {

                HashMap<String, Double> valueMap1 = new HashMap<>();
                valueMap1.put(tea.getType(), tea.getPeopleEnjoyIt());
                map.put(country, valueMap1);
            }
        }

        for (Map.Entry<String, HashMap<String, Double>> entry : map.entrySet()) {
            HashMap<String, Double> value = entry.getValue();
            TreeMap<String, Double> treeMap = new TreeMap<>(new ComparePeopleEnjoyIt(value));
            treeMap.putAll(value);
            countryToTeaTypeAndPeopleEnjoyIt.put(entry.getKey(), treeMap);
        }
    }

    private static TreeMap<String, Double> teaType2peopleEnjoyItLoad(List<Tea> teaList) {
        HashMap<String, Double> teaType2peopleEnjoyIt = new HashMap<>();

        for (Tea tea : teaList) {
            String teaType = tea.getType();
            Double peopleEnjoyIt = tea.getPeopleEnjoyIt();
            if (teaType2peopleEnjoyIt.get(teaType) != null) {
                teaType2peopleEnjoyIt.put(tea.getType(), peopleEnjoyIt + tea.getPeopleEnjoyIt());
            } else {
                teaType2peopleEnjoyIt.put(tea.getType(), tea.getPeopleEnjoyIt());
            }
        }
        ComparePeopleEnjoyIt comparatorPepleEnjoyInWorld = new ComparePeopleEnjoyIt(teaType2peopleEnjoyIt);
        TreeMap<String, Double> sortedMap = new TreeMap<>(comparatorPepleEnjoyInWorld);

        sortedMap.putAll(teaType2peopleEnjoyIt);
        return sortedMap;
    }

    private static TreeMap<String, HashSet<String>> countryToTeaTypesload(List<Tea> teaList) {
        Map<String, HashSet<String>> countryToTeaTypes = new HashMap<>();
        CompareTypeOfTeaInCountry compareTypeOfTeaInCountry = new CompareTypeOfTeaInCountry(countryToTeaTypes);
        TreeMap<String, HashSet<String>> sortedMap = new TreeMap<>(compareTypeOfTeaInCountry);

        for (Tea tea : teaList) {
            String country = tea.getCountry();
            HashSet<String> teaTypes = countryToTeaTypes.get(country);
            if (teaTypes == null) {
                HashSet<String> teaSet = new HashSet<>();
                teaSet.add(tea.getType());
                countryToTeaTypes.put(tea.getCountry(), teaSet);
            } else {
                teaTypes.add(tea.getType());
            }
        }

        // todo check comparator. it is not working properly

        sortedMap.putAll(countryToTeaTypes);
        return sortedMap;
    }

    private static void uniqueTypesOfTeaLoad(List<Tea> teaList) {
        for (Tea tea : teaList) {
            uniqueTypesOfTea.add(tea.getType());
        }
    }

    private static void teaType2countryAndPeopleEnjoyItLoad(List<Tea> teaList) {
        for (Tea tea : teaList) {
            String teaType = tea.getType();
            HashMap<String, Double> value = teaType2countryAndPeopleEnjoyIt.get(teaType);
            if (value != null) {
                Double peopleEnjoyIt = value.get(tea.getType());
                if (peopleEnjoyIt == null) {
                    value.put(tea.getCountry(), tea.getPeopleEnjoyIt());
                } else {
                    value.put(tea.getCountry(), peopleEnjoyIt + tea.getPeopleEnjoyIt());
                }
            } else {
                HashMap<String, Double> valueMap = new HashMap<>();
                valueMap.put(tea.getCountry(), tea.getPeopleEnjoyIt());
                teaType2countryAndPeopleEnjoyIt.put(teaType, valueMap);
            }
        }
    }

    private static class ComparePeopleEnjoyIt implements Comparator<String> {

        private final HashMap<String, Double> map;

        private ComparePeopleEnjoyIt(HashMap<String, Double> map) {
            this.map = map;
        }

        @Override
        public int compare(String key1, String key2) {
            if (map.get(key1) > map.get(key2)) {
                return -1;
            } else if (map.get(key1) < map.get(key2)) {
                return 1;
            }
            return 0;
        }
    }

    private static class CompareTypeOfTeaInCountry implements Comparator<String> {
        private final Map<String, HashSet<String>> map;

        CompareTypeOfTeaInCountry(Map<String, HashSet<String>> map) {
            this.map = map;
        }

        @Override
        public int compare(String key1, String key2) {
            if (map.get(key1).size() > map.get(key2).size()) {
                return -1;
            } else if (map.get(key1).size() < map.get(key2).size()) {
                return 1;
            }
            return 0;
        }
    }
}