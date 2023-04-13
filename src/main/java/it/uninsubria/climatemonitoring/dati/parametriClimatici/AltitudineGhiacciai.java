package it.uninsubria.climatemonitoring.dati.parametriClimatici;

import java.time.LocalDate;

/**
 * Rappresenta il parametro climatico altitudine dei ghiacciai.
 *
 * @author Mattia Mauro Lunardi 736898 mmlunardi@studenti.uninsubria.it VA
 * @author Andrea Quaglia 753166 aquaglia2@studenti.uninsubria.it VA
 */
public class AltitudineGhiacciai extends ParametroClimatico {
    /**
     * Crea il parametro climatico altitudine dei ghiacciai.
     *
     * @param punteggio       {@link AltitudineGhiacciai#punteggio} del parametro climatico da 1 (critico) a 5 (ottimale).
     * @param note            {@link AltitudineGhiacciai#note} del parametro climatico (massimo 256 caratteri).
     * @param dataRilevazione {@link AltitudineGhiacciai#dataRilevazione} del parametro climatico.
     */
    public AltitudineGhiacciai(int punteggio, String note, LocalDate dataRilevazione) {
        super(punteggio, note, dataRilevazione);
        CATEGORIA = "Altitudine dei ghiacciai";
        SPIEGAZIONE = "In m, suddivisa in piogge";
    }
}
