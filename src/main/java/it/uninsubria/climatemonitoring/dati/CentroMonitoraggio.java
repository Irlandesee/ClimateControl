package it.uninsubria.climatemonitoring.dati;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Rappresenta un centro di monitoraggio.
 * @author : Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * @author : Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 **/
@SuppressWarnings("unused")
public class CentroMonitoraggio implements Serializable {
    private String nomeCentro;
    private Indirizzo indirizzo;
    private LinkedList<AreaInteresse> areeInteresse;

    /**
     * Crea un centro di monitoraggio.
     * @param nomeCentro nome del centro di monitoraggio.
     * @param indirizzo indirizzo stradale del centro di monitoraggio.
     */
    public CentroMonitoraggio(String nomeCentro, Indirizzo indirizzo){
        this.nomeCentro = nomeCentro;
        this.indirizzo = indirizzo;
        areeInteresse = new LinkedList<>();
    }

    /**
     * Aggiunge un'area d'interesse al centro di monitoraggio.
     * @param areaInteresse area d'interesse da aggiungere.
     */
    public void addAreaInteresse(AreaInteresse areaInteresse) {
        areeInteresse.add(areaInteresse);
    }

    /**
     * Crea una stringa per la stampa del centro di monitoraggio.
     * @return una stringa che descrive il centro di monitoraggio.
     */
    @Override
    public String toString() {
        return nomeCentro + "\n" +
                indirizzo.toString() + "\n" +
                areeInteresse.toString() + "\n";
    }

    //getters and setters

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
