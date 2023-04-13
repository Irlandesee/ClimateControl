package it.uninsubria.climatemonitoring.dati;

import java.io.Serializable;

/**
 * Rappresenta un operatore registrato.
 *
 * @author Mattia Mauro Lunardi 736898 mmlunardi@studenti.uninsubria.it VA
 * @author Andrea Quaglia 753166 aquaglia2@studenti.uninsubria.it VA
 */
@SuppressWarnings("FieldMayBeFinal")
public class Operatore implements Serializable {
    private String cognome;
    private String nome;
    private String codiceFiscale;
    /**
     * Email aziendale assegnata.
     */
    private String email;
    /**
     * UserID scelto dall'operatore al momento della registrazione.
     */
    private String userID;
    /**
     * Password scelta dall'operatore al momento della registrazione.
     */
    private String password;
    /**
     * Centro di monitoraggio scelto al momento della registrazione. L'operatore potra' inserire dati solo qui.
     */
    private CentroMonitoraggio centroAfferenza;

    /**
     * Crea un operatore registrato.
     *
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
     * Crea una {@code String} per la stampa o per il salvataggio su file csv dell'operatore.
     *
     * @return una {@code String} contenente {@link Operatore#cognome}, {@link Operatore#nome},
     * {@link Operatore#codiceFiscale}, {@link Operatore#email}, {@link Operatore#userID}, {@link Operatore#password} e
     * {@link Operatore#centroAfferenza} separati dal carattere ';'.
     */
    @Override
    public String toString() {
        return cognome + ";" +
                nome + ";" +
                codiceFiscale + ";" +
                email + ";" +
                userID + ";" +
                password + ";" +
                centroAfferenza.getNomeCentro();
    }

    //getters and setters
    /**
     * @return {@link Operatore#nome}.
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * @return {@link Operatore#cognome}.
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return {@link Operatore#email}.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return {@link Operatore#userID}.
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @return {@link Operatore#password}.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return {@link Operatore#centroAfferenza}.
     */
    public CentroMonitoraggio getCentroAfferenza() {
        return centroAfferenza;
    }
}
