package it.uninsubria.climatemonitoring.centroMonitoraggio;

import it.uninsubria.climatemonitoring.areaInteresse.AreaInteresse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CentroMonitoraggio {

    private String centroID;
    private String nomeCentro;
    private String comune;
    private String country;

    //key: String => areaID
    private HashMap<String, AreaInteresse> areeInteresse;

    final String emptyAreeInteresse = "empty";
    final String generalSeparator = ";";
    final String areeSeparator = ",";

    public CentroMonitoraggio(String centroID){
        this.centroID = centroID;
        this.areeInteresse = new HashMap<String, AreaInteresse>();
    }

    public CentroMonitoraggio(String centroID, String nomeCentro,
                              String comune, String country){
        this.centroID = centroID;
        this.nomeCentro = nomeCentro;
        this.comune = comune;
        this.country = country;
        this.areeInteresse = new HashMap<String, AreaInteresse>();
    }

    public String getCentroID() {
        return centroID;
    }

    public void setCentroID(String centroID) {
        this.centroID = centroID;
    }

    public String getNomeCentro() {
        return nomeCentro;
    }

    public void setNomeCentro(String nomeCentro) {
        this.nomeCentro = nomeCentro;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getAreeInteresse(){
        StringBuilder builder = new StringBuilder();
        if(!areeInteresse.isEmpty()) {
            for (String key : areeInteresse.keySet())
                builder.append(key).append(areeSeparator);
            builder.append("\n");
        }
        else
            builder.append(emptyAreeInteresse);
        return builder.toString();
    }


    public boolean rmAreaInteresse(String areaID){
        if(!areeInteresse.containsKey(areaID)) return false;
        areeInteresse.remove(areaID);
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(centroID, nomeCentro);
    }

    public boolean addAreaInteresse(AreaInteresse area){
        if(!this.areeInteresse.containsKey(area.getAreaID())) {
            this.areeInteresse.put(area.getAreaID(), area);
            return true;
        }
        return false;
    }

    //Only checks if centroID is the same
    public boolean equals(Object obj){
        if(obj.getClass() == CentroMonitoraggio.class){
            return ((CentroMonitoraggio) obj).getCentroID().equals(this.centroID);
        }
        return false;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(centroID).append(generalSeparator)
                .append(nomeCentro).append(generalSeparator)
                .append(comune).append(generalSeparator)
                .append(country).append(generalSeparator);

        if(areeInteresse.isEmpty()) builder.append(emptyAreeInteresse);
        else{
            for (String tmp : areeInteresse.keySet()) //append the keys
                builder.append(tmp).append(areeSeparator);
        }
        return builder.toString();
    }

    public void setCountry(String s) {
        this.country = s;
    }
}
