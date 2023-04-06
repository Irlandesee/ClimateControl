package it.uninsubria.climatemonitoring;

import java.util.Date;

/**
 * Rappresenta un parametro climatico.
 * @author : Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * @author : Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 **/
@SuppressWarnings("unused")
public class ParametroClimatico {
    private String categoria;
    private String spiegazione;
    private int punteggio;
    private String note;
    private Date dataRilevazione;

    /**
     * Crea un parametro climatico.
     * @param categoria categoria del parametro climatico.
     * @param spiegazione spiegazione del parametro climatico.
     * @param punteggio punteggio del parametro climatico da 1 (critico) a 5 (ottimale).
     * @param note note del parametro climatico (massimo 256 caratteri).
     * @param dataRilevazione data di rilevazione del parametro climatico.
     */
    public ParametroClimatico(String categoria, String spiegazione, int punteggio, String note, Date dataRilevazione) {
        this.categoria = categoria;
        this.spiegazione = spiegazione;
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
        return dataRilevazione.toString() + " " + categoria + " " + spiegazione + " " + punteggio + " " + note + "\n";
    }
}
