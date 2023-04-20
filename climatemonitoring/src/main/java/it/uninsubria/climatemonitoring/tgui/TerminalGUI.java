package it.uninsubria.climatemonitoring.tgui;

import it.uninsubria.climatemonitoring.dbref.DBInterface;
import it.uninsubria.climatemonitoring.operatore.Operatore;
import it.uninsubria.climatemonitoring.operatore.opeatoreRegistrato.OperatoreRegistrato;
import org.javatuples.Pair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class TerminalGUI {

    private final DBInterface dbInterface;
    private Operatore loggedOperatore;
    private boolean run;
    private boolean isLogged;

    /*TGui Strings*/
    private static final String welcomeText = "Benvenuto!\n" +
            "                        \n" +
            "                        Digitare 'cerca' per visualizzare le aree di interesse disponibili.\n" +
            "                        Digitare 'login' per effettuare il login (solo operatori registrati).\n" +
            "                        Digitare 'registrazione' per effettuare la registrazione all'applicazione\\s\n" +
            "                        (solo operatori autorizzati).\n" +
            "                        Digitare 'uscita' per terminare il programma.";
    private static final String cerca = "cerca";
    private static final String cercaAreaInteresse = "cerca area interesse";
    private static final String cercaCentroMonitoraggio = "cerca centro monitoraggio";
    private static final String aggiungiParametroClimatico = "aggiunta parametro climatico";
    private static final String aggiungiCentroMonitoraggio = "aggiunta centro monitoraggio";
    private static final String aggiungiAreaInteresse = "aggiunta area interesse";
    private static final String login = "login";
    private static final String registrazione = "registrazione";
    private static final String salva = "salva";
    private static final String uscita = "uscita";
    private static final String continua = "continua";
    private static final String quit = "quit";
    private static final String y = "y";
    private static final String n = "n";

    private static final String error_invalid_input = "Input non valido";


    public TerminalGUI(){
        this.dbInterface = new DBInterface();
        this.run = true;
        this.isLogged = false;
    }

    public TerminalGUI(final DBInterface dbInterface){
        this.dbInterface = dbInterface;
        this.run = true;
        this.isLogged = false;
    }

    /**
     * Metodo dove girerà la gui fino al termine
     */
    private void run(){

    }

    private void cercaAreaGeografica(){

    }

    private void cercaAreaInteresse(){

    }

    private void aggiungiAreaInteresse(){

    }

    private void cercaCentroMonitoraggio(){

    }

    private void aggiungiCentroMonitoraggio(){

    }

    private void inserisciDatiParametroClimatico(){

    }

    private boolean login(){
        System.out.println("Login");
        String email = "";
        String password = "";
        BufferedReader terminalReader;
        try {
            System.out.println("Inserisci mail e password separate da spazio");
            terminalReader = new BufferedReader(new InputStreamReader(System.in));
            boolean cont = true;
            while(cont){
                String[] tmp = terminalReader.readLine().split(" ");
                Pair<String, String> credentials = new Pair<String, String>(tmp[0], tmp[1]);
                if(dbInterface.checkCredentials(credentials)){
                    this.isLogged = true;
                    this.loggedOperatore = dbInterface.getOperatoreRegistrato(credentials);
                    cont = false;
                }else{
                    System.out.println("Vuoi continuare? y/n");
                    String res = terminalReader.readLine();
                    if(res.equals(TerminalGUI.n)) cont = false;
                }
            }
            terminalReader.close();
        }catch(IOException ioe){ioe.printStackTrace();}
        return false;
    }

    private void registrazione(){
        System.out.println("Registrazione");
        BufferedReader terminalReader;
        try{
            System.out.println("Inserisci dati operatore da registrare");
            terminalReader = new BufferedReader(new InputStreamReader(System.in));
            //query db
            //If true -> log as op, save new op to file
            //if false -> error, start process from scratch
            //return
            terminalReader.close();
        }catch(IOException ioe){ioe.printStackTrace();}
    }

    private void askLogin(){
        BufferedReader terminalReader = new BufferedReader(new InputStreamReader(System.in));
        try{
            switch(terminalReader.readLine()){
                case TerminalGUI.login -> isLogged = login();
                case TerminalGUI.registrazione -> registrazione();
                case TerminalGUI.continua -> {return;} //?
                case TerminalGUI.uscita -> System.exit(0);
            }
            terminalReader.close();
        }catch(IOException ioe){ioe.printStackTrace();}
    }
    private void readUserInput(){
        BufferedReader terminalReader = new BufferedReader(new InputStreamReader(System.in));
        try{
            switch(terminalReader.readLine()){
                case TerminalGUI.cerca -> cercaAreaGeografica();
                case TerminalGUI.cercaAreaInteresse -> cercaAreaInteresse();
                case TerminalGUI.cercaCentroMonitoraggio -> cercaCentroMonitoraggio();
                case TerminalGUI.aggiungiAreaInteresse -> aggiungiAreaInteresse();
                case TerminalGUI.aggiungiCentroMonitoraggio -> aggiungiCentroMonitoraggio();
                case TerminalGUI.aggiungiParametroClimatico -> inserisciDatiParametroClimatico();
                case TerminalGUI.uscita -> System.exit(0);
                default -> throw new IllegalArgumentException(error_invalid_input);
            }
            terminalReader.close();
        }catch(IOException ioe){ioe.printStackTrace();}
    }

}
