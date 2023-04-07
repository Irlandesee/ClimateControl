package it.uninsubria.climatemonitoring.parametriClimatici;

import java.time.LocalDate;

/**
 * @author : Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * @author : Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 **/
public class Umidita extends ParametroClimatico {
    /**
     * Crea un parametro climatico.
     *
     * @param punteggio       punteggio del parametro climatico da 1 (critico) a 5 (ottimale).
     * @param note            note del parametro climatico (massimo 256 caratteri).
     * @param dataRilevazione data di rilevazione del parametro climatico.
     */
    public Umidita(int punteggio, String note, LocalDate dataRilevazione) {
        super(punteggio, note, dataRilevazione);
        CATEGORIA = "Umidita'";
        SPIEGAZIONE = "Percentuale di Umidita', suddivisa in fasce";
    }
}
