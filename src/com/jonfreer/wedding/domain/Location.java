package com.jonfreer.wedding.domain;

public class Location {

    private String city;
    private String stateOrProvince;
    private String stateOrProvinceCode;
    private String country;
    private String countryCode;

    public Location() {
    }

    public Location(String city, String stateOrProvince, String stateOrProvinceCode, String country, String countryCode) {
        this.city = city;
        this.stateOrProvince = stateOrProvince;
        this.stateOrProvinceCode = stateOrProvinceCode;
        this.country = country;
        this.countryCode = countryCode;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateOrProvince() {
        return this.stateOrProvince;
    }

    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    public String getStateOrProvinceCode() {
        return this.stateOrProvinceCode;
    }

    public void setStateOrProvinceCode(String stateOrProvinceCode) {
        this.stateOrProvinceCode = stateOrProvinceCode;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
