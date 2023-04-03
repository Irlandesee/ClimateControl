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

    private HashMap<String, AreaInteresse> areeInteresse;

    public CentroMonitoraggio(String centroID){
        this.centroID = centroID;
    }


    public String toString(){
        //TODO: return new string representing this instance
        return null;
    }

}
