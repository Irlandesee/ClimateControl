package it.uninsubria.climatemonitoring.areaInteresse;

public class AreaInteresse {

    private String areaID;
    private String denominazione;
    private String stato;
    private float latitude;
    private float longitude;

    public AreaInteresse(String areaID){
        this.areaID = areaID;
    }
    public String getAreaID(){return this.areaID;}
    public String getDenominazione(){return this.denominazione;}
    public String getStato(){return this.stato;}
    public float getLatitude(){return this.latitude;}
    public float getLongitude(){return this.longitude;}

    public boolean equals(Object o){
        //TODO:
        return false;
    }
    public String toString(){
        //TODO: return stringa rappresentante l'area di interesse
        return null;
    }
}
