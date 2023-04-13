package it.uninsubria.climatemonitoring.dati;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Rappresenta un centro di monitoraggio.
 *
 * @author Mattia Mauro Lunardi 736898 mmlunardi@studenti.uninsubria.it VA
 * @author Andrea Quaglia 753166 aquaglia2@studenti.uninsubria.it VA
 */
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
     *
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
     *
     * @param areaInteresse area d'interesse da aggiungere.
     */
    public void addAreaInteresse(AreaInteresse areaInteresse) {
        areeInteresse.add(areaInteresse);
    }

    /**
     * @return una {@code String} contenente {@link CentroMonitoraggio#nomeCentro},
     * {@link CentroMonitoraggio#indirizzo} e il risultato del metodo {@link LinkedList#toString} di
     * {@link CentroMonitoraggio#areeInteresse} separati da un'andata a capo.
     */
    @Override
    public String toString() {
        return "Centro di monitoraggio: " + nomeCentro + "\n" +
                "Indirizzo: " + indirizzo + "\n" +
                "Elenco aree d'interesse associate:\n" + areeInteresse + "\n";
    }

    //getters and setters
    /**
     * @return {@link CentroMonitoraggio#nomeCentro}.
     */
    public String getNomeCentro() {
        return nomeCentro;
    }

    /**
     * @return {@link CentroMonitoraggio#areeInteresse} che rappresenta la lista delle aree d'interesse associate al
     * centro di monitoraggio.
     */
    public LinkedList<AreaInteresse> getAreeInteresse() {
        return areeInteresse;
    }
}
