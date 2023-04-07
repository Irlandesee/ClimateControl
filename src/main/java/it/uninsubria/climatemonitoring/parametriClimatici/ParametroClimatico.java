package it.uninsubria.climatemonitoring.parametriClimatici;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Rappresenta un parametro climatico.
 * @author : Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * @author : Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 **/
@SuppressWarnings("unused")
public abstract class ParametroClimatico implements Serializable {
    protected String CATEGORIA;
    protected String SPIEGAZIONE;
    private int punteggio;
    private String note;
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

    public String getCategoria() {
        return CATEGORIA;
    }

    public void setCategoria(String categoria) {
        this.CATEGORIA = categoria;
    }

    public String getSpiegazione() {
        return SPIEGAZIONE;
    }

    public void setSpiegazione(String spiegazione) {
        this.SPIEGAZIONE = spiegazione;
    }

    public int getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getDataRilevazione() {
        return dataRilevazione;
    }

    public void setDataRilevazione(LocalDate dataRilevazione) {
        this.dataRilevazione = dataRilevazione;
    }

    public String getCATEGORIA() {
        return CATEGORIA;
    }

    public void setCATEGORIA(String CATEGORIA) {
        this.CATEGORIA = CATEGORIA;
    }

    public String getSPIEGAZIONE() {
        return SPIEGAZIONE;
    }

    public void setSPIEGAZIONE(String SPIEGAZIONE) {
        this.SPIEGAZIONE = SPIEGAZIONE;
    }
}
