package it.uninsubria.climatemonitoring;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * @author : Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * @author : Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 **/
public class CentroMonitoraggio implements Serializable {
    private String nomeCentro;
    private Indirizzo indirizzo;
    private LinkedList<AreaInteresse> areeInteresse;

    public CentroMonitoraggio(String nomeCentro, Indirizzo indirizzo){
        this.nomeCentro = nomeCentro;
        this.indirizzo = indirizzo;
        areeInteresse = new LinkedList<>();
    }

    public void addAreaInteresse(AreaInteresse areaInteresse) {
        areeInteresse.add(areaInteresse);
    }

    public String getNomeCentro() {
        return nomeCentro;
    }

    public void setNomeCentro(String nomeCentro) {
        this.nomeCentro = nomeCentro;
    }

    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }

    public LinkedList<AreaInteresse> getAreeInteresse() {
        return areeInteresse;
    }

    public void setAreeInteresse(LinkedList<AreaInteresse> areeInteresse) {
        this.areeInteresse = areeInteresse;
    }
}
