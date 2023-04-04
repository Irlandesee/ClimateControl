package it.uninsubria.climatemonitoring.areaInteresse;

import java.util.Objects;

public class AreaInteresse {

    private String areaID;
    private String denominazione;
    private String stato;
    private float latitude;
    private float longitude;

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

    @Override
    public int hashCode() {
        return Objects.hash(areaID, denominazione);
    }

    public boolean equals(Object o){
        if(o.getClass() == AreaInteresse.class)
            return ((AreaInteresse) o).getAreaID().equals(this.getAreaID());
        return false;
    }
    @Override
    public String toString(){
        String separatorArea = ":";
        StringBuilder builder = new StringBuilder();
        builder.append(areaID).append(separatorArea)
                .append(denominazione).append(separatorArea)
                .append(stato).append(separatorArea)
                .append(latitude).append(separatorArea)
                .append(longitude).append("\n");

        return builder.toString();
    }
}
