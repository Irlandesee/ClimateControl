package it.uninsubria.climatemonitoring.dati;

import it.uninsubria.climatemonitoring.parametriClimatici.ParametroClimatico;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.LinkedList;

/**
 * Rappresenta un'area d'interesse.
 *
 * @author Mattia Mauro Lunardi 736898 mmlunardi@studenti.uninsubria.it VA
 * @author Andrea Quaglia 753166 aquaglia2@studenti.uninsubria.it VA
 */
@SuppressWarnings("FieldMayBeFinal")
public class AreaInteresse implements Serializable {
    /**
     * Nome ufficiale.
     */
    private String geonameID;
    private String nomeASCII;
    private String nomeStato;
    private String codiceStato;

    private double latitudine;
    private double longitudine;

    /**
     * Lista contenente una lista per parametro climatico, ciascuna contiene tutti i dati inseriti dagli operatori
     * relativi al proprio parametro climatico.
     */
    private LinkedList<LinkedList<ParametroClimatico>> parametriClimatici;

    private final int NUMERO_PARAMETRI = 7;

    /**
     * Crea un'area d'interesse.
     *
     * @param geonameID geoname ufficiale dell'area d'interesse.
     * @param nomeASCII nome dell'area d'interesse.
     * @param codiceStato codice dello stato dell'area d'interesse.
     * @param nomeStato stato dell'area d'interesse.
     * @param latitudine latitudine dell'area d'interesse.
     * @param longitudine longitudine dell'area d'interesse.
     */
    public AreaInteresse(String geonameID, String nomeASCII, String codiceStato, String nomeStato, double latitudine,
                         double longitudine) {
        this.geonameID = geonameID;
        this.nomeASCII = nomeASCII;
        this.nomeStato = nomeStato;
        this.codiceStato = codiceStato;
        this.latitudine = latitudine;
        this.longitudine = longitudine;

        parametriClimatici = new LinkedList<>();
        for (int i = 0; i < NUMERO_PARAMETRI; i++)
            parametriClimatici.add(new LinkedList<>());
    }

    /**
     * Salva un nuovo gruppo di parametri climatici nella cache.
     *
     * @param parametriClimatici linkedList contenente i parametri climatici da aggiungere.
     */
    public void addParametriClimatici(LinkedList<ParametroClimatico> parametriClimatici) {
        for (int i = 0; i < NUMERO_PARAMETRI; i++) {
            if(parametriClimatici.get(i).getPunteggio() == 0)
                continue;
            this.parametriClimatici.get(i).add(parametriClimatici.get(i));
        }
    }

    /**
     * @return una {@code String} contenente {@link AreaInteresse#geonameID}, {@link AreaInteresse#nomeASCII},
     * {@link AreaInteresse#codiceStato} e {@link AreaInteresse#nomeStato} separati dal carattere ';', seguiti da
     * {@link AreaInteresse#latitudine} e {@link AreaInteresse#longitudine} separati dal carattere ','.
     */
    @Override
    public String toString() {
        return geonameID + ";" +
                nomeASCII + ";" +
                codiceStato + ";" +
                nomeStato + ";" +
                latitudine + "," +
                longitudine;
    }

    /**
     * Restituisce i dati in forma aggregata calcolata come media dei punteggi inseriti dagli operatori.
     * Le note vengono aggregate come concatenazione di data d'inserimento e nota.
     *
     * @return i dati in forma aggregata.
     */
    public String getDatiAggregati() {
        StringBuilder datiAggregati = new StringBuilder();
        String categoria = "";
        double punteggioAggregato = 0.0;
        StringBuilder noteAggregate = new StringBuilder();
        for (LinkedList<ParametroClimatico> listaParametro : parametriClimatici) {
            if (listaParametro.size() == 0)
                return "Non sono ancora stati inseriti dati.";
            for (ParametroClimatico parametroClimatico : listaParametro) {
                punteggioAggregato += parametroClimatico.getPunteggio();
                noteAggregate.append(parametroClimatico.getDataRilevazione()).append(" : ").
                        append(parametroClimatico.getNote()).append("\n");
                categoria = parametroClimatico.getCategoria();
            }
            punteggioAggregato /= listaParametro.size();

            DecimalFormat df = new DecimalFormat("#.##");
            String tmp = df.format(punteggioAggregato);

            datiAggregati.append(categoria).append(": ").append(tmp).append("\nNote:\n").
                    append(noteAggregate).append("\n");
            punteggioAggregato = 0;
            noteAggregate = new StringBuilder();
        }
        return datiAggregati.toString();
    }

    //getters and setters
    /**
     * @return {@link AreaInteresse#nomeASCII};
     */
    public String getNomeASCII() { return nomeASCII; }

    /**
     * @return {@link AreaInteresse#longitudine}.
     */
    public double getLongitudine() {
        return longitudine;
    }

    /**
     * @return {@link AreaInteresse#latitudine}.
     */
    public double getLatitudine() {
        return latitudine;
    }
}
