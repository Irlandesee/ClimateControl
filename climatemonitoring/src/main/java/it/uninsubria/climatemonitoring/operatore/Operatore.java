package it.uninsubria.climatemonitoring.operatore;

import java.util.Objects;

public class Operatore {

    private String nomeOp;
    private String cognomeOp;
    private String codFiscale;
    private String email;

    private static final String generalSep = ";";

    public Operatore(String nome, String cognome, String codFiscale, String email){
        this.nomeOp = nome;
        this.cognomeOp = cognome;
        this.codFiscale = codFiscale;
        this.email = email;
    }

    public String getNomeOp() {
        return nomeOp;
    }

    public void setNomeOp(String nomeOp) {
        this.nomeOp = nomeOp;
    }

    public String getCognomeOp() {
        return cognomeOp;
    }

    public void setCognomeOp(String cognomeOp) {
        this.cognomeOp = cognomeOp;
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
        builder.append(this.nomeOp).append(Operatore.generalSep)
                .append(this.cognomeOp).append(Operatore.generalSep)
                .append(this.codFiscale).append(Operatore.generalSep)
                .append(this.email);
        return builder.toString();
    }

}
