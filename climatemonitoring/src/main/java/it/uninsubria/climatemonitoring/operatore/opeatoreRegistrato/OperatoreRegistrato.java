package it.uninsubria.climatemonitoring.operatore.opeatoreRegistrato;

import it.uninsubria.climatemonitoring.operatore.Operatore;

import java.util.Objects;

public class OperatoreRegistrato extends Operatore {

    private String userID;
    private String password;
    private String centroID;
    public OperatoreRegistrato(String nome, String cognome,
                               String codFiscale, String email,
                               String userID, String password,
                               String centroID){
        super(nome, cognome, codFiscale, email);
        this.userID = userID;
        this.password = password;
        this.centroID = centroID;
    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public String getCentroID() {
        return centroID;
    }

    public void setCentroID(String centroID) {
        this.centroID = centroID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OperatoreRegistrato that = (OperatoreRegistrato) o;
        return Objects.equals(userID, that.userID)
                && Objects.equals(password, that.password)
                && Objects.equals(centroID, that.centroID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userID, centroID);
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(this.userID).append(Operatore.generalSep)
                .append(this.password).append(Operatore.generalSep)
                .append(super.getNome()).append(Operatore.generalSep)
                .append(super.getCognome()).append(Operatore.generalSep)
                .append(super.getCodFiscale()).append(Operatore.generalSep)
                .append(super.getEmail()).append(Operatore.generalSep)
                .append(this.centroID).append(Operatore.terminatingChar);
        return builder.toString();
    }
}
