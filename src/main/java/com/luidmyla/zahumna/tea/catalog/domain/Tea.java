package com.luidmyla.zahumna.tea.catalog.domain;

import java.util.Objects;

public class Tea {

    private Double peopleEnjoyIt;

    private String country;

    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tea)) return false;

        Tea tea = (Tea) o;
        return getPeopleEnjoyIt() == tea.getPeopleEnjoyIt() &&
                Objects.equals(getCountry(), tea.getCountry()) &&
                Objects.equals(getType(), tea.getType());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getPeopleEnjoyIt(), getCountry(), getType());
    }

    public Double getPeopleEnjoyIt() {
        return peopleEnjoyIt;
    }

    public void setPeopleEnjoyIt(Double peopleEnjoyIt) {
        this.peopleEnjoyIt = peopleEnjoyIt;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Tea{" +
                "peopleEnjoyIt=" + peopleEnjoyIt +
                ", country='" + country + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}