package it.uninsubria.climatemonitoring.parametriClimatici;

import java.time.LocalDate;

/**
 * Rappresenta il parametro climatico temperatura.
 * @author <pre> Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 * </pre>
 **/
public class Temperatura extends ParametroClimatico {
    /**
     * Crea il parametro climatico temperatura.
     *
     * @param punteggio       punteggio del parametro climatico da 1 (critico) a 5 (ottimale).
     * @param note            note del parametro climatico (massimo 256 caratteri).
     * @param dataRilevazione data di rilevazione del parametro climatico.
     */
    public Temperatura(int punteggio, String note, LocalDate dataRilevazione) {
        super(punteggio, note, dataRilevazione);
        CATEGORIA = "Temperatura";
        SPIEGAZIONE = "In C°, suddivisa in fasce ";
    }
}
