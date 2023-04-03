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
        //Singleton Pattern?
        DBInterface dbRef = DBInterface.getInstance();
        HashMap<String, City> geonamesCache = dbRef.readGeonamesFile();
        HashMap<String, Operatore> operatoriAutorizzatiCache = dbRef.readOperatoriAutorizzatiFile();

        HashMap<String, Operatore> operatoriRegistratiCache = dbRef.readOperatoriRegistratiFile();

        registraOperatore(dbRef, operatoriAutorizzatiCache, operatoriRegistratiCache,
                "example00@climateMonitor.it");
        operatoriRegistratiCache = dbRef.readOperatoriRegistratiFile();

        registraOperatore(dbRef, operatoriAutorizzatiCache, operatoriRegistratiCache,
                "example01@climateMonitor.it");
        operatoriRegistratiCache = dbRef.readOperatoriRegistratiFile();

        printCache(geonamesCache);
        debug("Done");

        debug("Stampa cache operatori autorizzati...");
        printCache(operatoriAutorizzatiCache);
        debug("Stampa cache operatori autorizzati completata.");

        debug("Stampa cache operatori registrati...");
        printCache(operatoriRegistratiCache);
        debug("Stampa cache operatori registrati completata.");

        dbRef.writeCoordinateMonitoraggioFile(geonamesCache);
        debug("Copia sul file CoordinateMonitoraggio.dati completata.");
    }

    private static void registraOperatore(DBInterface dbRef, HashMap<String, Operatore> operatoriAutorizzatiCache,
                                          HashMap<String, Operatore> operatoriRegistratiCache,
                                          String email) throws IOException {
        if(!operatoriRegistratiCache.containsKey(email))
            dbRef.registraOperatore(operatoriAutorizzatiCache.get(email));
        else
            debug("Operatore già registrato!");
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

    private static void printCache(HashMap<String, ?> cache) {
        cache.forEach((key, value) -> System.out.println(value));
    }

    private static void debug(String s) {
        System.out.println(s);
    }
}