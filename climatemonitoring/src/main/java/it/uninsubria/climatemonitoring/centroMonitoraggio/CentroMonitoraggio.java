package it.uninsubria.climatemonitoring.centroMonitoraggio;

import it.uninsubria.climatemonitoring.areaInteresse.AreaInteresse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CentroMonitoraggio {

    private String centroID;
    private String nomeCentro;
    private String via; //via-piazza
    private short numCivico;
    private int cap;
    private String comune;
    private String provincia;

    //key: String => areaID
    private HashMap<String, AreaInteresse> areeInteresse;

    final String emptyAreeInteresse = "null";
    final String generalSeparator = ";";
    final String areeSeparator = ",";

    public CentroMonitoraggio(String centroID){
        this.centroID = centroID;
    }

    public CentroMonitoraggio(String centroID, String nomeCentro,
                              String via, short numCivico, int cap,
                              String comune, String provincia){
        this.centroID = centroID;
        this.nomeCentro = nomeCentro;
        this.via = via;
        this.numCivico = numCivico;
        this.cap = cap;
        this.comune = comune;
        this.provincia = provincia;
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

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public short getNumCivico() {
        return numCivico;
    }

    public void setNumCivico(short numCivico) {
        this.numCivico = numCivico;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getAreeInteresse(){
        StringBuilder builder = new StringBuilder();
        if(!areeInteresse.isEmpty()) {
            for (String key : areeInteresse.keySet())
                builder.append(key).append(areeSeparator);
            builder.append("\n");
        }
        else
            builder.append(emptyAreeInteresse).append("\n");
        return builder.toString();
    }


    //TODO: remove from file?
    //if this method returns true, then it is surely present in
    //the corresponding database file.
    public boolean rmAreaInteresse(String areaID){
        if(!areeInteresse.containsKey(areaID)) return false;
        areeInteresse.remove(areaID);
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(centroID, nomeCentro);
    }

    //If not already present, add it
    //TODO: add to file?
    //if this methods returns true, then it surely not present
    //in the corresponding database file
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
                .append(via).append(generalSeparator)
                .append(numCivico).append(generalSeparator)
                .append(comune).append(generalSeparator)
                .append(cap).append(generalSeparator)
                .append(provincia).append(generalSeparator);

        if(areeInteresse.isEmpty()) builder.append(emptyAreeInteresse).append("\n");
        else{
            for (String tmp : areeInteresse.keySet()) //append the keys
                builder.append(tmp).append(areeSeparator);
            builder.append("\n");
        }
        return builder.toString();
    }

}
