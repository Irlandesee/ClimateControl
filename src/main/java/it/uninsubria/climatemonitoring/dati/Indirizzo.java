package it.uninsubria.climatemonitoring.dati;

import java.io.Serializable;

/**
 * Rappresenta un indirizzo stradale.
 *
 * @author Mattia Mauro Lunardi 736898 mmlunardi@studenti.uninsubria.it VA
 * @author Andrea Quaglia 753166 aquaglia2@studenti.uninsubria.it VA
 */
@SuppressWarnings("FieldMayBeFinal")
public class Indirizzo implements Serializable {
    private int numeroCivico;
    private int cap;

    private String via;
    private String comune;
    private String provincia;

    /**
     * Crea un indirizzo stradale.
     *
     * @param via via dell'indirizzo stradale.
     * @param comune comune dell'indirizzo stradale.
     * @param provincia provincia dell'indirizzo stradale.
     * @param numeroCivico numero civico dell'indirizzo stradale.
     * @param cap CAP dell'indirizzo stradale.
     */
    public Indirizzo(String via, String comune, String provincia, int numeroCivico, int cap) {
        this.via = via;
        this.comune = comune;
        this.provincia = provincia;
        this.numeroCivico = numeroCivico;
        this.cap = cap;
    }

    /**
     * Crea una stringa per la stampa dell'indirizzo.
     *
     * @return una {@code String} contenente {@link Indirizzo#via}, {@link Indirizzo#numeroCivico},
     * {@link Indirizzo#cap}, {@link Indirizzo#comune} e {@link Indirizzo#provincia} separati da uno spazio.
     */
    @Override
    public String toString() {
        return via + " " +
                numeroCivico + " " +
                cap + " " +
                comune + " " +
                provincia;
    }
}
