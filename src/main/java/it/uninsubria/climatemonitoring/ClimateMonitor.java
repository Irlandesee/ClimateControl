package it.uninsubria.climatemonitoring;

import it.uninsubria.climatemonitoring.city.City;
import it.uninsubria.climatemonitoring.dbref.DBInterface;
import it.uninsubria.climatemonitoring.operatore.Operatore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * @author : Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * @author : Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 **/
public class ClimateMonitor extends Application {
    /**
     *
     * @param args non usato
     * @throws IOException non è stato possibile creare uno dei file richiesti
     */
    public static void main(String[] args) throws IOException {
        DBInterface dbRef = new DBInterface();
        HashMap<String, City> geonamesCache = dbRef.readGeonamesFile();
        HashMap<String, Operatore> operatoriAutorizzatiCache = dbRef.readOperatoriAutorizzatiFile();
        HashMap<String, Operatore> operatoriRegistratiCache = dbRef.readOperatoriRegistratiFile();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.println("""
                    Benvenuto, digitare 'cerca' per visualizzare le aree di interesse disponibili.
                    Digitare 'login' per effettuare il login (solo operatori registrati).
                    Digitare 'registrazione' per effettuare la registrazione all'applicazione\s
                    (solo operatori autorizzati).
                    Digitare 'uscita' per terminare il programma.""");
            switch (reader.readLine()) {
                case "cerca" -> {
                    printCache(geonamesCache);
                    System.out.println("\nE' possibile visualizzare i dati climatici di un area di interesse digitando" +
                            " il suo geonameID");
                    String geonameID = reader.readLine();
                    City areaGeografica;
                    while ((areaGeografica = geonamesCache.get(geonameID)) == null) {
                        System.out.println("GeonameID inserito non valido. Riprovare.");
                        geonameID = reader.readLine();
                    }
                    System.out.println("Dati climatici di: " + areaGeografica);
                }
                case "login" -> {
                    while (!loggedIn) {
                        System.out.println("Digitare l'userID:");
                        String userID = reader.readLine();
                        if (userID.equals(""))
                            break;
                        String password;
                        String enteredPassword;
                        for (String key : operatoriRegistratiCache.keySet()) {
                            Operatore operatore = operatoriRegistratiCache.get(key);
                            if (userID.equals(operatore.getUserID())) {
                                password = operatore.getPassword();
                                System.out.println("Digitare la password:");
                                while (!loggedIn) {
                                    enteredPassword = reader.readLine();
                                    if (enteredPassword.equals(""))
                                        break;
                                    if (password.equals(enteredPassword)) {
                                        System.out.println("Accesso effettuato!\nBenvenuto " + operatore.getCognome()
                                                + " " + operatore.getNome());
                                        loggedIn = true;
                                    } else {
                                        System.out.println("Password errata!");
                                    }
                                }
                                break;
                            }
                        }
                        if(!loggedIn)
                            System.out.println("userID errato!");
                    }
                }
                case "registrazione" -> {
                    String cognome, nome, codiceFiscale, email, userID, password, centroAfferenza;
                    System.out.println("Digitare il cognome:");
                    cognome = reader.readLine();
                    System.out.println("Digitare il nome:");
                    nome = reader.readLine();
                    System.out.println("Digitare il codice fiscale:");
                    codiceFiscale = reader.readLine();
                    System.out.println("Digitare l'email aziendale:");
                    email = reader.readLine();
                    if(operatoriRegistratiCache.containsKey(email)) {
                        System.out.println("Utente gia' registrato!");
                        break;
                    }
                    if(!operatoriAutorizzatiCache.containsKey(email)) {
                        System.out.println("Email non valida!");
                        break;
                    }
                    System.out.println("Digitare l'userID:");
                    userID = reader.readLine();
                    System.out.println("Digitare la password:");
                    password = reader.readLine();
                    System.out.println("Digitare il geonameID del centro di Afferenza:");
                    centroAfferenza = reader.readLine();
                    Operatore operatore = new Operatore(cognome, nome, codiceFiscale, email, userID,
                            password, centroAfferenza);
                    operatoriRegistratiCache.put(email, operatore);
                    loggedIn = true;
                    dbRef.registraOperatore(operatore);
                    System.out.println("Accesso effettuato!\nBenvenuto " + cognome + " " + nome + "\n");
                }
                case "uscita" -> {
                    System.exit(0);
                }
            }
        }

//        debug("Stampa cache operatori autorizzati...");
//        printCache(operatoriAutorizzatiCache);
//        debug("Stampa cache operatori autorizzati completata.\n");
//
//        debug("Stampa cache operatori registrati...");
//        printCache(operatoriRegistratiCache);
//        debug("Stampa cache operatori registrati completata.\n");
//
//        dbRef.writeCoordinateMonitoraggioFile(geonamesCache);
//        debug("Copia sul file CoordinateMonitoraggio.dati completata.\n");
    }

    /**
     *
     * @param stage
     * @throws IOException file main-view.fxml non trovato
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClimateMonitor.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello World!");
        stage.setScene(scene);
        stage.show();
    }

    private static void signUp(DBInterface dbRef, HashMap<String, Operatore> operatoriAutorizzatiCache,
                               HashMap<String, Operatore> operatoriRegistratiCache,
                               String email) throws IOException {
        if(!operatoriAutorizzatiCache.containsKey(email))
            debug("Accesso Negato!");
        else if(!operatoriRegistratiCache.containsKey(email)) {
            registrazione(dbRef, operatoriAutorizzatiCache, email);
            operatoriRegistratiCache.put(email, operatoriAutorizzatiCache.get(email));
        }
        else
            debug("Operatore già registrato!");
    }

    private static void registrazione(DBInterface dbRef, HashMap<String, Operatore> operatoriAutorizzatiCache, String email) throws IOException {
        Operatore operatore = operatoriAutorizzatiCache.get(email);
        operatore.setCognome("Cognome");
        operatore.setNome("Nome");
        operatore.setCodiceFiscale("CodiceFiscale");
        operatore.setUserID("UserID");
        operatore.setPassword("Password");
        operatore.setCentroAfferenza("CentroAfferenza");
        dbRef.registraOperatore(operatore);
    }

    private static void printCache(HashMap<String, ?> cache) {
        cache.forEach((key, value) -> System.out.println(value));
    }

    private static void debug(String s) {
        System.out.println(s);
    }
}