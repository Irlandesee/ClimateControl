package it.uninsubria.climatemonitoring.operatore;

import java.util.Objects;

public class Operatore {

    private String nome;
    private String cognome;
    private String codFiscale;
    private String email;

    public static final String generalSep = ";";
    public static final String terminatingChar = ",";

    public Operatore(String nome, String cognome, String codFiscale, String email){
        this.nome = nome;
        this.cognome = cognome;
        this.codFiscale = codFiscale;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodFiscale() {
        return codFiscale;
    }

    public void setCodFiscale(String codFiscale) {
        this.codFiscale = codFiscale;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codFiscale);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operatore operatore = (Operatore) o;
        return Objects.equals(codFiscale, operatore.codFiscale);
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(this.nome).append(Operatore.generalSep)
                .append(this.cognome).append(Operatore.generalSep)
                .append(this.codFiscale).append(Operatore.generalSep)
                .append(this.email);
        return builder.toString();
    }

}
