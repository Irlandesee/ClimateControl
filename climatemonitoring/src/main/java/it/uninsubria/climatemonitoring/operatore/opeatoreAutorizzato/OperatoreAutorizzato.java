package it.uninsubria.climatemonitoring.operatore.opeatoreAutorizzato;

import it.uninsubria.climatemonitoring.operatore.Operatore;

import java.util.Objects;

public class OperatoreAutorizzato extends Operatore {

    private String nome;
    private String cognome;

    private String codFiscale;
    private String email;

    public OperatoreAutorizzato(String nome, String cognome, String codFiscale, String email){
        super(nome, cognome, codFiscale, email);
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

    @Override
    public String getCodFiscale() {
        return codFiscale;
    }

    @Override
    public void setCodFiscale(String codFiscale) {
        this.codFiscale = codFiscale;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(nome).append(Operatore.generalSep)
                .append(cognome).append(Operatore.generalSep)
                .append(codFiscale).append(Operatore.generalSep)
                .append(email);
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OperatoreAutorizzato that = (OperatoreAutorizzato) o;
        return Objects.equals(nome, that.nome)
                && Objects.equals(cognome, that.cognome)
                && Objects.equals(codFiscale, that.codFiscale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nome, cognome, codFiscale);
    }
}
