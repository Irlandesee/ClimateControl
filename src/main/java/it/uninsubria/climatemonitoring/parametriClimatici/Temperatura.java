package it.uninsubria.climatemonitoring.parametriClimatici;

import java.time.LocalDate;

/**
 * Rappresenta il parametro climatico temperatura.
 *
 * @author Mattia Mauro Lunardi 736898 mmlunardi@studenti.uninsubria.it VA
 * @author Andrea Quaglia 753166 aquaglia2@studenti.uninsubria.it VA
 */
public class Temperatura extends ParametroClimatico {
    /**
     * Crea il parametro climatico temperatura.
     *
     * @param punteggio       {@link Temperatura#punteggio} del parametro climatico da 1 (critico) a 5 (ottimale).
     * @param note            {@link Temperatura#note} del parametro climatico (massimo 256 caratteri).
     * @param dataRilevazione {@link Temperatura#dataRilevazione} del parametro climatico.
     */
    public Temperatura(int punteggio, String note, LocalDate dataRilevazione) {
        super(punteggio, note, dataRilevazione);
        CATEGORIA = "Temperatura";
        SPIEGAZIONE = "In C°, suddivisa in fasce ";
    }
}
