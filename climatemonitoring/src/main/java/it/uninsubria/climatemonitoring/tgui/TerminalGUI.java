package it.uninsubria.climatemonitoring.tgui;

import it.uninsubria.climatemonitoring.dbref.DBInterface;
import it.uninsubria.climatemonitoring.operatore.Operatore;

public class TerminalGUI {

    private final DBInterface dbInterface;
    private Operatore loggedOperatore;

    public TerminalGUI(){
        this.dbInterface = new DBInterface();
    }

    public TerminalGUI(final DBInterface dbInterface){
        this.dbInterface = dbInterface;
    }

    /**
     * Metodo per l'inizializzazione della tGui
     */
    private void start(){

    }

    /**
     * Metodo dove girerà la gui fino al termine
     */
    private void run(){

    }

}
