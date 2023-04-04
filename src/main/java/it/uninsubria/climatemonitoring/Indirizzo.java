package it.uninsubria.climatemonitoring;

import java.io.Serializable;

/**
 * @author : Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * @author : Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 **/
public class Indirizzo implements Serializable {
    private String via;
    private int numeroCivico;
    private int cap;
    private String comune;
    private String provincia;

    public Indirizzo(String via, String comune, String provincia, int numeroCivico, int cap) {
        this.via = via;
        this.comune = comune;
        this.provincia = provincia;
        this.numeroCivico = numeroCivico;
        this.cap = cap;
    }
}
