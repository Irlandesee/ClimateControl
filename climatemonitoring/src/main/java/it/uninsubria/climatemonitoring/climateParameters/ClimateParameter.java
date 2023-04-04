package it.uninsubria.climatemonitoring.climateParameters;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class ClimateParameter {

    private String parameterID;
    private String idCentro;
    private String areaInteresse;
    private Date pubDate;

    private String notes; //max 256 chars

    private static final String ventoExp = "Velocità del vento (km/h), suddivisa in fasce";
    private static final String umiditaExp = "% di Umidità, suddivisa in fasce";
    private static final String pressioneExp = "In hPa, suddivisa in fasce";
    private static final String tempExp = "In C°, suddivisa in fasce";
    private static final String precipationiExp = "In mm di pioggia, suddivisa in fasce";
    private static final String altiGhiacciaiExp = "In m, suddivisa in piogge";
    private static final String massaGhiacciaiExp = "In kg, suddivisa in fasce";

    private static final short minVal = 1;
    private static final short maxVal = 5;
    private static final short maxNoteLength = 256;


    private HashMap<String, Short> paramValues;

    private ClimateParameter(String parameterID){
        this.parameterID = parameterID;
    }

    public boolean addParameter(String param, short value) {
        //TODO:
    }

    public boolean rmParameter(String param){
        //TODO:
    }

    public String getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(String idCentro) {
        this.idCentro = idCentro;
    }

    public String getAreaInteresse() {
        return areaInteresse;
    }

    public void setAreaInteresse(String areaInteresse) {
        this.areaInteresse = areaInteresse;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getParameterID() {
        return parameterID;
    }

    public HashMap<String, Short> getParamValues() {
        return paramValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClimateParameter that = (ClimateParameter) o;
        return Objects.equals(parameterID, that.parameterID)
                && Objects.equals(pubDate, that.pubDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameterID, pubDate);
    }

    @Override
    public String toString(){
        //TODO:
    }
}
