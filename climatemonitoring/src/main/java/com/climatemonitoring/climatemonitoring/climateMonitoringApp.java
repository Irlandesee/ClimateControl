package com.climatemonitoring.climatemonitoring;

import com.climatemonitoring.climatemonitoring.city.City;
import com.climatemonitoring.climatemonitoring.dbref.DBInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class climateMonitoringApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(climateMonitoringApp.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello World!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        DBInterface dbRef = new DBInterface();
        HashMap<String, City> geonamesCache = dbRef.readGeonamesFile();

        for(String tmp : geonamesCache.keySet())
            System.out.println(geonamesCache.get(tmp).toString());

        dbRef.writeGeonamesAndCoordinates(geonamesCache);
        System.out.println("Done");
//File contenente l'elenco delle aree geografiche da cui scegliere per associarle ad un centro di monitoraggio.
//Una volta scelta, l'area può venir rimossa da questo elenco per non essere associata a nessun altro centro.
//Nell'eventualità che l'area venga disassociata dal suo centro, può venire reinserita in questo elenco.
        //dbRef.writeCoordinateMonitoraggioFile(geonamesCache);
        //System.out.println("Writing of coordinateMonitoraggio.dati complete.");
    }
}