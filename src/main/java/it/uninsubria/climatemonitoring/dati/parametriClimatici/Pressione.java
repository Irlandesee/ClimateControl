package it.uninsubria.climatemonitoring.dati.parametriClimatici;

import java.time.LocalDate;

/**
 * Rappresenta il parametro climatico pressione.
 *
 * @author Mattia Mauro Lunardi 736898 mmlunardi@studenti.uninsubria.it VA
 * @author Andrea Quaglia 753166 aquaglia2@studenti.uninsubria.it VA
 */
public class Pressione extends ParametroClimatico {
    /**
     * Crea il parametro climatico pressione.
     *
     * @param punteggio       {@link Pressione#punteggio} del parametro climatico da 1 (critico) a 5 (ottimale).
     * @param note            {@link Pressione#note} del parametro climatico (massimo 256 caratteri).
     * @param dataRilevazione {@link Pressione#dataRilevazione} del parametro climatico.
     */
    public Pressione(int punteggio, String note, LocalDate dataRilevazione) {
        super(punteggio, note, dataRilevazione);
        CATEGORIA = "Pressione";
        SPIEGAZIONE = "In hPa, suddivisa in fasce";
    }
}
