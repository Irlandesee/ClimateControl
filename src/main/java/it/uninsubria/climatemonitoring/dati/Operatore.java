package it.uninsubria.climatemonitoring.dati;

import java.io.Serializable;

/**
 * Rappresenta un operatore registrato.
 * @author <pre> Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 * </pre>
 **/
@SuppressWarnings("FieldMayBeFinal")
public class Operatore implements Serializable {
    /**
     * Cognome.
     */
    private String cognome;
    /**
     * Nome.
     */
    private String nome;
    /**
     * Codice fiscale.
     */
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

    /**
     * Restituisce il cognome.
     * @return il cognome.
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Restituisce il nome.
     * @return il nome.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Restituisce l'email.
     * @return l'email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Restituisce l'userID.
     * @return l'userID.
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Restituisce la password.
     * @return la password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Restituisce il centro di afferenza.
     * @return il centro di afferenza.
     */
    public CentroMonitoraggio getCentroAfferenza() {
        return centroAfferenza;
    }
}
