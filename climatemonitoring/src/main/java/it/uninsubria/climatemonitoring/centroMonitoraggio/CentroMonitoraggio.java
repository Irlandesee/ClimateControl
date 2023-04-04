package it.uninsubria.climatemonitoring.centroMonitoraggio;

import it.uninsubria.climatemonitoring.areaInteresse.AreaInteresse;

import java.util.HashMap;

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

    public String toString(){
        //TODO: return new string representing this instance
        return null;
    }

}
