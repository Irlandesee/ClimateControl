package it.uninsubria.climatemonitoring.dati;

import java.io.Serializable;

/**
 * Rappresenta un operatore registrato.
 * @author : Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * @author : Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 **/
@SuppressWarnings("unused")
public class Operatore implements Serializable {
    private String cognome, nome, codiceFiscale, email, userID, password;
    private CentroMonitoraggio centroAfferenza;

    /**
     * Crea un operatore registrato.
     * @param cognome cognome dell'operatore.
     * @param nome nome dell'operatore.
     * @param codiceFiscale codice fiscale dell'operatore.
     * @param email email aziendale dell'operatore.
     * @param userID userID scelto dall'operatore al momento della registrazione.
     * @param password password scelta dall'operatore al momento della registrazione.
     * @param centroAfferenza centro di afferenza scelto dall'operatore al momento della registrazione. L'operatore
     *                        potrà inserire dati solo qui.
     */
    public Operatore(String cognome, String nome, String codiceFiscale, String email, String userID, String password,
                     CentroMonitoraggio centroAfferenza){
        this.cognome = cognome;
        this.nome = nome;
        this.codiceFiscale = codiceFiscale;
        this.email = email;
        this.userID = userID;
        this.password = password;
        this.centroAfferenza = centroAfferenza;
    }

    /**
     * Crea una stringa per la stampa o per il salvataggio su file csv dell'operatore.
     * @return una stringa csv con separatore ';' che descrive l'operatore.
     */
    @Override
    public String toString() {
        return cognome + ";" +
                nome + ";" +
                codiceFiscale + ";" +
                email + ";" +
                userID + ";" +
                password + ";" +
                centroAfferenza;
    }

    //getters and setters

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CentroMonitoraggio getCentroAfferenza() {
        return centroAfferenza;
    }

    public void setCentroAfferenza(CentroMonitoraggio centroAfferenza) {
        this.centroAfferenza = centroAfferenza;
    }
}
