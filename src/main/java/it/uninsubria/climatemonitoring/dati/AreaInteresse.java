package it.uninsubria.climatemonitoring.dati;

import it.uninsubria.climatemonitoring.parametriClimatici.ParametroClimatico;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.LinkedList;

/**
 * Rappresenta un'area d'interesse.
 * @author : Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * @author : Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 **/
@SuppressWarnings("unused")
public class AreaInteresse implements Serializable {
    private String geonameID;
    private String asciiName;
    private String country;
    private String countryCode;
    private double latitude;
    private double longitude;
    private LinkedList<LinkedList<ParametroClimatico>> parametriClimatici;

    private final int NUMERO_PARAMETRI = 7;

    /**
     * Crea un'area d'interesse.
     * @param geonameID geoname ufficiale dell'area d'interesse.
     * @param asciiName nome dell'area d'interesse.
     * @param countryCode codice dello stato dell'area d'interesse.
     * @param country stato dell'area d'interesse.
     * @param latitude latitudine dell'area d'interesse.
     * @param longitude longitudine dell'area d'interesse.
     */
    public AreaInteresse(String geonameID, String asciiName, String countryCode, String country, double latitude,
                         double longitude) {
        this.geonameID = geonameID;
        this.asciiName = asciiName;
        this.country = country;
        this.countryCode = countryCode;
        this.latitude = latitude;
        this.longitude = longitude;

        parametriClimatici = new LinkedList<>();
        for (int i = 0; i < NUMERO_PARAMETRI; i++)
            parametriClimatici.add(new LinkedList<>());
    }

    /**
     * Salva un nuovo gruppo di parametri climatici nella cache.
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
     * Crea una stringa per la stampa o per il salvataggio su file csv dell'area d'interesse.
     * @return una stringa csv con separatore ';' che descrive l'area d'interesse.
     */
    @Override
    public String toString() {
        return geonameID + ";" +
                asciiName + ";" +
                countryCode + ";" +
                country + ";" +
                latitude + "," +
                longitude;
    }

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

    public String getGeonameID() {
        return geonameID;
    }

    public void setGeonameID(String geonameID) {
        this.geonameID = geonameID;
    }

    public String getAsciiName() {
        return asciiName;
    }

    public void setAsciiName(String asciiName) {
        this.asciiName = asciiName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LinkedList<LinkedList<ParametroClimatico>> getParametriClimatici() {
        return parametriClimatici;
    }

    public void setParametriClimatici(LinkedList<LinkedList<ParametroClimatico>> parametriClimatici) {
        this.parametriClimatici = parametriClimatici;
    }
}
