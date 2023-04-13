package it.uninsubria.climatemonitoring.dati.parametriClimatici;

import java.time.LocalDate;

/**
 * Rappresenta il parametro climatico umidita'.
 *
 * @author Mattia Mauro Lunardi 736898 mmlunardi@studenti.uninsubria.it VA
 * @author Andrea Quaglia 753166 aquaglia2@studenti.uninsubria.it VA
 */
public class Umidita extends ParametroClimatico {
    /**
     * Crea il parametro climatico umidita'.
     *
     * @param punteggio       {@link Umidita#punteggio} del parametro climatico da 1 (critico) a 5 (ottimale).
     * @param note            {@link Umidita#note} del parametro climatico (massimo 256 caratteri).
     * @param dataRilevazione {@link Umidita#dataRilevazione} del parametro climatico.
     */
    public Umidita(int punteggio, String note, LocalDate dataRilevazione) {
        super(punteggio, note, dataRilevazione);
        CATEGORIA = "Umidita'";
        SPIEGAZIONE = "Percentuale di Umidita', suddivisa in fasce";
    }
}
