package it.uninsubria.climatemonitoring.operatore;

/**
 * @author : Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * @author : Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 **/
public class Operatore {
    private String cognome, nome, codiceFiscale, email, userID, password;
    //Questo sarà un oggetto di tipo centroAfferenza
    private String centroAfferenza;

    /**
     *
     * @param cognome cognome dell'operatore
     * @param nome nome dell'operatore
     * @param codiceFiscale codice fiscale dell'operatore
     * @param email email aziendale dell'operatore
     * @param userID userID scelto dall'operatore al momento della registrazione
     * @param password password scelta dall'operatore al momento della registrazione
     * @param centroAfferenza centro di afferenza scelto dall'operatore al momento della registrazione. L'operatore
     *                        potrà inserire dati solo qui
     */
    public Operatore(String cognome, String nome, String codiceFiscale, String email, String userID, String password,
                     String centroAfferenza){
        this.cognome = cognome;
        this.nome = nome;
        this.codiceFiscale = codiceFiscale;
        this.email = email;
        this.userID = userID;
        this.password = password;
        this.centroAfferenza = centroAfferenza;
    }

    public Operatore(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        if(cognome == null)
            return email;
        return cognome + ";" +
                nome + ";" +
                codiceFiscale + ";" +
                email + ";" +
                userID + ";" +
                password + ";" +
                centroAfferenza;
    }

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

    public String getCentroAfferenza() {
        return centroAfferenza;
    }

    public void setCentroAfferenza(String centroAfferenza) {
        this.centroAfferenza = centroAfferenza;
    }
}
