package it.uninsubria.climatemonitoring.dati;

import java.io.Serializable;

/**
 * Rappresenta un indirizzo stradale.
 * @author <pre> Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 * </pre>
 **/
@SuppressWarnings("FieldMayBeFinal")
public class Indirizzo implements Serializable {
    /**
     * Via/piazza.
     */
    private String via;
    /**
     * Numero civico.
     */
    private int numeroCivico;
    /**
     * CAP.
     */
    private int cap;
    /**
     * Comune.
     */
    private String comune;
    /**
     * Provincia.
     */
    private String provincia;

    /**
     * Crea un indirizzo stradale.
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
     * @return una stringa con separatore '\s' che descrive l'indirizzo.
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
