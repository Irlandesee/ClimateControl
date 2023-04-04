package it.uninsubria.climatemonitoring.centroMonitoraggio;

import it.uninsubria.climatemonitoring.areaInteresse.AreaInteresse;

import java.util.HashMap;
import java.util.Map;

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

    public HashMap<String, AreaInteresse> getAreeInteresse() {
        return areeInteresse;
    }

    //Only checks if centroID is the same
    public boolean equals(Object obj){
        if(obj.getClass() == CentroMonitoraggio.class){
            return ((CentroMonitoraggio) obj).getCentroID().equals(this.centroID);
        }
        return false;
    }

    public String toString(){
        final String generalSeparator = ";";
        final String areeSeparator = ",";
        StringBuilder builder = new StringBuilder();
        builder.append(centroID).append(generalSeparator)
                .append(nomeCentro).append(generalSeparator)
                .append(via).append(generalSeparator)
                .append(numCivico).append(generalSeparator)
                .append(comune).append(generalSeparator)
                .append(cap).append(generalSeparator)
                .append(provincia).append(generalSeparator);

        if(areeInteresse.isEmpty()) builder.append("\n");
        else
            for(String tmp: areeInteresse.keySet()) //append the keys
                builder.append(tmp).append(areeSeparator);
            builder.append("\n");

        return builder.toString();
    }

}
