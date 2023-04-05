package it.uninsubria.climatemonitoring.climateParameters;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
    private static final String generalSeparator = ";";
    private static final String generalParamSeparator = ",";
    private static final String paramKeySeparator = ":";
    private static final String ERROR_STR_NOT_VALID = "param str must be a valid string!\n";
    private static final String ERROR_PARAM_KEY= "param key must be valid a valid string!\n";
    private static final String ERROR_TOO_MANY_CHARS = "note length must be under 256 chars!\n";
    private static final String ERROR_INVALID_MIN_VALUE = "min value must be >= 1\n";
    private static final String ERROR_INVALID_MAX_VALUE = "max value must be <= 5\n";

    private HashMap<String, Short> paramValues;

    private ClimateParameter(String parameterID){
        this.parameterID = parameterID;
    }

    public boolean addParameter(String param, short value) {
        if(param == null || param.isBlank())
            throw new IllegalArgumentException(ClimateParameter.ERROR_PARAM_KEY);
        else if(value < minVal)
            throw new IllegalArgumentException(ClimateParameter.ERROR_INVALID_MIN_VALUE);
        else if(value > maxVal)
            throw new IllegalArgumentException(ClimateParameter.ERROR_INVALID_MAX_VALUE);
        if(!paramValues.containsKey(param)){
            paramValues.put(param, value);
            return true;
        }
        return false;
    }

    public boolean rmParameter(String param){
        if(param == null || param.isBlank())
            throw new IllegalArgumentException(ClimateParameter.ERROR_PARAM_KEY);
        if(!paramValues.isEmpty()){
            if(paramValues.containsKey(param)){
                paramValues.remove(param);
                return true;
            }
            else return false;
        }
        return false;
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
        if(notes.isBlank() || notes.isEmpty()) throw new IllegalArgumentException(ERROR_STR_NOT_VALID);
        else if(notes.length() > ClimateParameter.maxNoteLength) throw new IllegalArgumentException(ERROR_TOO_MANY_CHARS);
        else this.notes = notes;
    }

    public String getParameterID() {
        return parameterID;
    }

    //params1,...,paramN;
    public String getParamValues() {
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<String, Short> tmp: paramValues.entrySet()){
            builder.append(tmp.getKey())
                    .append(ClimateParameter.paramKeySeparator) //:
                    .append(tmp.getValue())
                    .append(ClimateParameter.generalParamSeparator); //,
        }
        builder.append(ClimateParameter.generalSeparator);
        return builder.toString();
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

    //centroID;areaInteresse;data;params1,paramN;note
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(this.parameterID).append(ClimateParameter.generalSeparator)
                .append(this.idCentro).append(ClimateParameter.generalSeparator)
                .append(this.areaInteresse).append(ClimateParameter.generalSeparator)
                .append(this.pubDate).append(ClimateParameter.generalSeparator)
                .append(this.getParamValues()).append(ClimateParameter.generalSeparator)
                .append(this.notes).append(ClimateParameter.generalSeparator);
        return builder.toString();
    }
}