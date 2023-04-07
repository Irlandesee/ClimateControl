package it.uninsubria.climatemonitoring.dati;

import java.io.Serializable;

/**
 * Rappresenta un indirizzo stradale.
 * @author : Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * @author : Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 **/
@SuppressWarnings("unused")
public class Indirizzo implements Serializable {
    private String via;
    private int numeroCivico;
    private int cap;
    private String comune;
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

    //getters and setters

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public int getNumeroCivico() {
        return numeroCivico;
    }

    public void setNumeroCivico(int numeroCivico) {
        this.numeroCivico = numeroCivico;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
