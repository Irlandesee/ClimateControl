package it.uninsubria.climatemonitoring.parametriClimatici;

import java.time.LocalDate;

/**
 * Rappresenta il parametro climatico massa dei ghiacciai.
 *
 * @author Mattia Mauro Lunardi 736898 mmlunardi@studenti.uninsubria.it VA
 * @author Andrea Quaglia 753166 aquaglia2@studenti.uninsubria.it VA
 */
public class MassaGhiacciai extends ParametroClimatico {
    /**
     * Crea il parametro climatico massa dei ghiacciai.
     *
     * @param punteggio       {@link MassaGhiacciai#punteggio} del parametro climatico da 1 (critico) a 5 (ottimale).
     * @param note            {@link MassaGhiacciai#note} del parametro climatico (massimo 256 caratteri).
     * @param dataRilevazione {@link MassaGhiacciai#dataRilevazione} del parametro climatico.
     */
    public MassaGhiacciai(int punteggio, String note, LocalDate dataRilevazione) {
        super(punteggio, note, dataRilevazione);
        CATEGORIA = "Massa dei ghiacciai";
        SPIEGAZIONE = "In kg, suddivisa in fasce";
    }
}
