package it.uninsubria.climatemonitoring.city;

import java.util.Objects;

public class City {

    private String geonameID;
    private String asciiName;
    private String country;
    private String countryCode;
    private float latitude;
    private float longitude;

    public City(String geonameID, String asciiName, String countryCode, String country, float latitude, float longitude){
        this.geonameID = geonameID;
        this.asciiName = asciiName;
        this.country = country;
        this.countryCode = countryCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String getGeonameID() {
        return geonameID;
    }

    public void setGeonameID(String geonameID) {
        this.geonameID = geonameID;
    }

    public String getAsciiName() {
        return asciiName;
    }

    public void setAsciiName(String asciiName) {
        this.asciiName = asciiName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Float.compare(city.latitude, latitude) == 0
                && Float.compare(city.longitude, longitude) == 0
                && Objects.equals(geonameID, city.geonameID)
                && Objects.equals(asciiName, city.asciiName)
                && Objects.equals(country, city.country)
                && Objects.equals(countryCode, city.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(geonameID, asciiName, country, countryCode, latitude, longitude);
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(this.geonameID).append(";")
                .append(this.asciiName).append(";")
                .append(this.countryCode).append(";")
                .append(this.country).append(";")
                .append(this.latitude).append(",")
                .append(this.longitude);
        return builder.toString();
    }
}
