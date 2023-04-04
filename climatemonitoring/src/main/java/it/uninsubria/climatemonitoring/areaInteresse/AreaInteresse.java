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
        if(o.getClass() == AreaInteresse.class)
            if(((AreaInteresse) o).getAreaID().equals(this.getAreaID()))
                return true;
        return false;
    }
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
