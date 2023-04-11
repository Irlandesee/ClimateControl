package it.uninsubria.climatemonitoring.areaInteresse;

import java.util.Objects;

public class AreaInteresse {

    private String areaID;
    private String denominazione;
    private String stato;
    private float latitude;
    private float longitude;

    public static final String separatorArea = ";";
    public static final String separatorCoords = ",";

    public AreaInteresse(String areaID){
        this.areaID = areaID;
    }

    public AreaInteresse(String areaID, String denominazione,
                         String stato, float latitude, float longitude) {
        this.areaID = areaID;
        this.denominazione = denominazione;
        this.stato = stato;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAreaID(){return this.areaID;}
    public String getDenominazione(){return this.denominazione;}
    public String getStato(){return this.stato;}
    public float getLatitude(){return this.latitude;}
    public float getLongitude(){return this.longitude;}

    public void setAreaID(String areaID) {
        this.areaID = areaID;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreaInteresse that = (AreaInteresse) o;
        return Float.compare(that.latitude, latitude) == 0
                && Float.compare(that.longitude, longitude) == 0
                && Objects.equals(areaID, that.areaID)
                && Objects.equals(denominazione, that.denominazione)
                && Objects.equals(stato, that.stato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(areaID, denominazione, stato, latitude, longitude);
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(areaID).append(separatorArea)
                .append(denominazione).append(separatorArea)
                .append(stato).append(separatorArea)
                .append(latitude).append(separatorCoords)
                .append(longitude).append("\n");

        return builder.toString();
    }
}
