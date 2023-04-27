package it.uninsubria.climatemonitoring.climateParameters;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.time.LocalDate;

public class ClimateParameter {

    private String parameterID;
    private String idCentro;
    private String areaInteresse;
    private LocalDate pubDate;

    private String notes; //max 256 chars

    private static final String ventoExp = "Velocità del vento (km/h), suddivisa in fasce";
    private static final String umiditaExp = "% di Umidità, suddivisa in fasce";
    private static final String pressioneExp = "In hPa, suddivisa in fasce";
    private static final String tempExp = "In C°, suddivisa in fasce";
    private static final String precipationiExp = "In mm di pioggia, suddivisa in fasce";
    private static final String altiGhiacciaiExp = "In m, suddivisa in piogge";
    private static final String massaGhiacciaiExp = "In kg, suddivisa in fasce";

    private static final String notaVento = "Vento note";
    private static final String notaUmidita = "Umidita note";
    private static final String notaPressione = "Pressione note";
    private static final String notaTemp = "Temp. note";
    private static final String notePrecipitazioni = "Precip. note";
    private static final String noteAltGhiacciai = "Alt ghiacciai note";
    private static final String noteMassaGhiacciai = "Massa ghiacciai note";

    private static final short minVal = 1;
    private static final short maxVal = 5;
    private static final short maxNoteLength = 256;
    public static final String generalSeparator = ";";
    public static final String generalParamSeparator = ",";
    public static final String paramKeySeparator = ":";
    public static final String ERROR_STR_NOT_VALID = "param str must be a valid string!\n";
    public static final String ERROR_PARAM_KEY= "param key must be valid a valid string!\n";
    public static final String ERROR_TOO_MANY_CHARS = "note length must be under 256 chars!\n";
    public static final String ERROR_INVALID_MIN_VALUE = "min value must be >= 1\n";
    public static final String ERROR_INVALID_MAX_VALUE = "max value must be <= 5\n";
    public static final String ERROR_INVALID_KEY = "invalid parameter key!\n";

    public static final String paramVento = "vento";
    public static final String paramUmidita = "umidita";
    public static final String paramPressione = "pressione";
    public static final String paramTemp = "temperature";
    public static final String paramAltGhiacciai = "altGhiacciai";
    public static final String paramMassaGhiacciai = "massaGhiacciai";

    public static final short defaultValue = -1;

    private String ventoNotes;
    private String umiditaNotes;
    private String pressioneNotes;
    private String precipitazioniNotes;
    private String tempNotes;
    private String altGhicciaiNotes;
    private String massaGhiacciaiNotes;

    private HashMap<String, Short> paramValues;

    public ClimateParameter(String parameterID){
        this.parameterID = parameterID;
        this.paramValues = new HashMap<String, Short>();
        this.initParamValues();
    }

    public ClimateParameter(String parameterID, String idCentro
            , String areaInteresse
            , LocalDate pubDate){
        this.parameterID = parameterID;
        this.idCentro = idCentro;
        this.areaInteresse = areaInteresse;
        this.pubDate = pubDate;

        this.paramValues = new HashMap<String, Short>();
        this.initParamValues();
    }

    private void initParamValues(){
        this.paramValues.put(ClimateParameter.paramVento, ClimateParameter.defaultValue);
        this.paramValues.put(ClimateParameter.paramUmidita, ClimateParameter.defaultValue);
        this.paramValues.put(ClimateParameter.paramPressione, ClimateParameter.defaultValue);
        this.paramValues.put(ClimateParameter.paramTemp, ClimateParameter.defaultValue);
        this.paramValues.put(ClimateParameter.paramAltGhiacciai, ClimateParameter.defaultValue);
        this.paramValues.put(ClimateParameter.paramMassaGhiacciai, ClimateParameter.defaultValue);
    }

    public boolean addParameter(String param, short value) {
        if(param == null || param.isBlank())
            throw new IllegalArgumentException(ClimateParameter.ERROR_PARAM_KEY);
        else if(value < minVal)
            throw new IllegalArgumentException(ClimateParameter.ERROR_INVALID_MIN_VALUE);
        else if(value > maxVal)
            throw new IllegalArgumentException(ClimateParameter.ERROR_INVALID_MAX_VALUE);
        if(paramValues.containsKey(param)){
            paramValues.replace(param, value);
            return true;
        }
        return false;
    }

    public boolean rmParameter(String param){
        if(param == null || param.isBlank())
            throw new IllegalArgumentException(ClimateParameter.ERROR_PARAM_KEY);
        if(!paramValues.isEmpty()){
            if(paramValues.containsKey(param)){
                paramValues.replace(param, ClimateParameter.defaultValue);
                return true;
            }
            else return false;
        }
        return false;
    }

    public String getCpID(){
        return this.parameterID;
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

    public LocalDate getPubDate() {
        return pubDate;
    }

    public void setPubDate(LocalDate pubDate) {
        this.pubDate = pubDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String key, String notes) {
        if(notes.isBlank() || notes.isEmpty()) throw new IllegalArgumentException(ERROR_STR_NOT_VALID);
        else if(notes.length() > ClimateParameter.maxNoteLength) throw new IllegalArgumentException(ERROR_TOO_MANY_CHARS);
        else{
            switch(key){
                case ClimateParameter.notaVento-> this.setVentoNotes(notes);
                case ClimateParameter.notaUmidita-> this.setUmiditaNotes(notes);
                case ClimateParameter.notaTemp -> this.setTempNotes(notes);
                case ClimateParameter.notePrecipitazioni -> this.setPrecipitazioniNotes(notes);
                case ClimateParameter.notaPressione-> this.setPressioneNotes(notes);
                case ClimateParameter.noteAltGhiacciai-> this.setAltGhicciaiNotes(notes);
                case ClimateParameter.noteMassaGhiacciai-> this.setMassaGhiacciaiNotes(notes);
                default -> throw new IllegalArgumentException(ERROR_INVALID_KEY);
            }
        }
    }

    public String getVentoNotes() {
        return ventoNotes;
    }

    private void setVentoNotes(String ventoNotes) {
        this.ventoNotes = ventoNotes;
    }

    public String getUmiditaNotes() {
        return umiditaNotes;
    }

    private void setUmiditaNotes(String umiditaNotes) {
        this.umiditaNotes = umiditaNotes;
    }

    public String getPrecipitazioniNotes(){return this.precipitazioniNotes;}
    private void setPrecipitazioniNotes(String notes){this.precipitazioniNotes = notes;}

    public String getPressioneNotes() {
        return pressioneNotes;
    }

    private void setPressioneNotes(String pressioneNotes) {
        this.pressioneNotes = pressioneNotes;
    }

    public String getTempNotes() {
        return tempNotes;
    }

    private void setTempNotes(String tempNotes) {
        this.tempNotes = tempNotes;
    }

    public String getAltGhicciaiNotes() {
        return altGhicciaiNotes;
    }

    private void setAltGhicciaiNotes(String altGhicciaiNotes) {
        this.altGhicciaiNotes = altGhicciaiNotes;
    }

    public String getMassaGhiacciaiNotes() {
        return massaGhiacciaiNotes;
    }

    private void setMassaGhiacciaiNotes(String massaGhiacciaiNotes) {
        this.massaGhiacciaiNotes = massaGhiacciaiNotes;
    }

    public String getParameterID() {
        return parameterID;
    }

    //params1,...,paramN;
    public String getParamValues() {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for(Map.Entry<String, Short> tmp: paramValues.entrySet()){
            if(i == paramValues.size() - 1)
                builder.append(tmp.getKey())
                        .append(ClimateParameter.paramKeySeparator) //:
                        .append(tmp.getValue());
            else
                builder.append(tmp.getKey())
                        .append(ClimateParameter.paramKeySeparator) //:
                        .append(tmp.getValue())
                        .append(ClimateParameter.generalParamSeparator); //,
            i++;
        }
        return builder.toString();
    }

    public TreeMap<String, Short> getParamsSortedByKey(){
        return new TreeMap<>(paramValues);
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
