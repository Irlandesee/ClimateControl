package it.uninsubria.climatemonitoring.parametriClimatici;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Rappresenta un parametro climatico.
 * @author <pre> Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 * </pre>
 **/
@SuppressWarnings({"unused", "FieldMayBeFinal"})
public abstract class ParametroClimatico implements Serializable {
    /**
     * Categoria.
     */
    protected String CATEGORIA;
    /**
     * Spiegazione contenente unita' di misura e metodo di suddivisione.
     */
    protected String SPIEGAZIONE;
    /**
     * Punteggio relativo alla categoria. I valori ammessi sono da 1 (critico) a 5 (ottimale).
     */
    private int punteggio;
    /**
     * Note testuali di dimensione massima 256 caratteri.
     */
    private String note;
    /**
     * Data di rilevazione.
     */
    private LocalDate dataRilevazione;

    /**
     * Crea un parametro climatico.
     * @param punteggio punteggio del parametro climatico da 1 (critico) a 5 (ottimale).
     * @param note note del parametro climatico (massimo 256 caratteri).
     * @param dataRilevazione data di rilevazione del parametro climatico.
     */
    public ParametroClimatico(int punteggio, String note, LocalDate dataRilevazione) {
        this.punteggio = punteggio;
        this.note = note;
        this.dataRilevazione = dataRilevazione;
    }

    /**
     * Crea una stringa contenente tutti i dati del parametro climatico per la stampa.
     * @return una stringa contenente tutti i dati del parametro climatico.
     */
    @Override
    public String toString() {
        return dataRilevazione.toString() + " " + CATEGORIA + " " + SPIEGAZIONE + " " + punteggio + " " + note + "\n";
    }

    //getters and setters

    /**
     * Restituisce il punteggio.
     * @return il punteggio compreso tra 1 (critico) e 5 (ottimale).
     */
    public int getPunteggio() {
        return punteggio;
    }

    /**
     * Restituisce la data di rilevazione.
     * @return la data di rilevazione come LocalDate.
     */
    public LocalDate getDataRilevazione() {
        return dataRilevazione;
    }

    /**
     * Restituisce una stringa contente la nota.
     * @return la nota di massimo 256 caratteri.
     */
    public String getNote() {
        return note;
    }

    /**
     * Restituisce la categoria.
     * @return una stringa contenente la categoria.
     */
    public String getCategoria() {
        return CATEGORIA;
    }
}
