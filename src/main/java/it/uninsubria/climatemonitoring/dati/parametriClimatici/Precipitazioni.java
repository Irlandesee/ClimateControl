package it.uninsubria.climatemonitoring.dati.parametriClimatici;

import java.time.LocalDate;

/**
 * Rappresenta il parametro climatico temperatura.
 *
 * @author Mattia Mauro Lunardi 736898 mmlunardi@studenti.uninsubria.it VA
 * @author Andrea Quaglia 753166 aquaglia2@studenti.uninsubria.it VA
 */
public class Precipitazioni extends ParametroClimatico {
    /**
     * Crea il parametro climatico temperatura.
     *
     * @param punteggio       {@link Precipitazioni#punteggio} del parametro climatico da 1 (critico) a 5 (ottimale).
     * @param note            {@link Precipitazioni#note} del parametro climatico (massimo 256 caratteri).
     * @param dataRilevazione {@link Precipitazioni#dataRilevazione} del parametro climatico.
     */
    public Precipitazioni(int punteggio, String note, LocalDate dataRilevazione) {
        super(punteggio, note, dataRilevazione);
        CATEGORIA = "Precipitazioni";
        SPIEGAZIONE = "In mm di pioggia, suddivisa in fasce";
    }
}
