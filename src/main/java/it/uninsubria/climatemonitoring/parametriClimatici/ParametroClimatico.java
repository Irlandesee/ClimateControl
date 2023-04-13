package it.uninsubria.climatemonitoring.parametriClimatici;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Rappresenta un parametro climatico.
 *
 * @author Mattia Mauro Lunardi 736898 mmlunardi@studenti.uninsubria.it VA
 * @author Andrea Quaglia 753166 aquaglia2@studenti.uninsubria.it VA
 */
public abstract class ParametroClimatico implements Serializable {
    protected String CATEGORIA;
    /**
     * Spiegazione contenente unita' di misura e metodo di suddivisione.
     */
    protected String SPIEGAZIONE;
    /**
     * Punteggio relativo alla categoria. I valori ammessi sono da 1 (critico) a 5 (ottimale).
     */
    protected int punteggio;
    /**
     * Note testuali di dimensione massima 256 caratteri.
     */
    protected String note;

    protected LocalDate dataRilevazione;

    /**
     * Crea un parametro climatico.
     *
     * @param punteggio       {@link ParametroClimatico#punteggio} del parametro climatico da 1 (critico) a 5 (ottimale).
     * @param note            {@link ParametroClimatico#note} del parametro climatico (massimo 256 caratteri).
     * @param dataRilevazione {@link ParametroClimatico#dataRilevazione} del parametro climatico.
     */
    public ParametroClimatico(int punteggio, String note, LocalDate dataRilevazione) {
        this.punteggio = punteggio;
        this.note = note;
        this.dataRilevazione = dataRilevazione;
    }

    /**
     * @return una {@code String} contenente {@link ParametroClimatico#dataRilevazione},
     * {@link ParametroClimatico#CATEGORIA}, {@link ParametroClimatico#SPIEGAZIONE},
     * {@link ParametroClimatico#punteggio} e {@link ParametroClimatico#note} del {@link ParametroClimatico}
     * separati da uno spazio e con un andata a capo finale.
     */
    @Override
    public String toString() {
        return dataRilevazione.toString() + " " + CATEGORIA + " " + SPIEGAZIONE + " " + punteggio + " " + note + "\n";
    }

    //getters and setters
    /**
     * @return il punteggio compreso tra 1 (critico) e 5 (ottimale).
     */
    public int getPunteggio() {
        return punteggio;
    }

    public LocalDate getDataRilevazione() {
        return dataRilevazione;
    }

    /**
     * @return la nota di massimo 256 caratteri.
     */
    public String getNote() {
        return note;
    }

    public String getCategoria() {
        return CATEGORIA;
    }
}
