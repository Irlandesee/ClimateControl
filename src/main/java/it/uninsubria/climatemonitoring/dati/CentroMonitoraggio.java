package it.uninsubria.climatemonitoring.dati;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Rappresenta un centro di monitoraggio.
 * @author <pre> Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 * </pre>
 **/
@SuppressWarnings("FieldMayBeFinal")
public class CentroMonitoraggio implements Serializable {
    /**
     * Nome identificativo del centro di monitoraggio, deve essere unico.
     */
    private String nomeCentro;
    /**
     * Indirizzo della sede del centro di monitoraggio.
     */
    private Indirizzo indirizzo;
    /**
     * {@code LinkedList<AreaInteresse>} contenente le aree d'interesse associate con il centro di monitoraggio.
     */
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

    /**
     * Restituisce il nome del centro.
     * @return il nome del centro.
     */
    public String getNomeCentro() {
        return nomeCentro;
    }

    /**
     * Restituisce la lista contenente le aree d'interesse associate.
     * @return la lista delle aree d'interesse associate.
     */
    public LinkedList<AreaInteresse> getAreeInteresse() {
        return areeInteresse;
    }
}
