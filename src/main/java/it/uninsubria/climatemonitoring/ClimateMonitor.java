package it.uninsubria.climatemonitoring;

import it.uninsubria.climatemonitoring.city.City;
import it.uninsubria.climatemonitoring.dbref.DBInterface;
import it.uninsubria.climatemonitoring.operatore.Operatore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
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

        String email00 = "example00@climateMonitor.it";
        String email01 = "example01@climateMonitor.it";
        String email02 = "example10@climateMonitor.it";

        debug("Registrazione in corso...");
        signUp(dbRef, operatoriAutorizzatiCache, operatoriRegistratiCache, email00);
        signUp(dbRef, operatoriAutorizzatiCache, operatoriRegistratiCache, email01);
        signUp(dbRef, operatoriAutorizzatiCache, operatoriRegistratiCache, email02);

        debug("Stampa cache coordinate monitoraggio...");
        printCache(geonamesCache);
        debug("Stampa cache coordinate monitoraggio completata.\n");

        debug("Stampa cache operatori autorizzati...");
        printCache(operatoriAutorizzatiCache);
        debug("Stampa cache operatori autorizzati completata.\n");

        debug("Stampa cache operatori registrati...");
        printCache(operatoriRegistratiCache);
        debug("Stampa cache operatori registrati completata.\n");

        dbRef.writeCoordinateMonitoraggioFile(geonamesCache);
        debug("Copia sul file CoordinateMonitoraggio.dati completata.\n");
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