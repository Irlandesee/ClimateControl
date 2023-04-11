package it.uninsubria.climatemonitoring;

import it.uninsubria.climatemonitoring.areaInteresse.AreaInteresse;
import it.uninsubria.climatemonitoring.centroMonitoraggio.CentroMonitoraggio;
import it.uninsubria.climatemonitoring.city.City;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class climateMonitoringApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(climateMonitoringApp.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello World!");
        stage.setScene(scene);
        stage.show();
    }

    public static String createMD5Hash(final String input){
        String hashText = null;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            hashText = convertToHex(messageDigest);
        }catch(NoSuchAlgorithmException nsae){nsae.printStackTrace();}
        return hashText;
    }

    private static String convertToHex(final byte[] messageDigest){
        BigInteger bigint = new BigInteger(1, messageDigest);
        String hexText = bigint.toString(16);
        while(hexText.length() < 32) hexText = "0".concat(hexText);
        return hexText;
    }

    private static String buildInputStringAreaInteresse(
            String denominazione,
            String stato,
            float latitude,
            float longitude
    ){
        return new StringBuilder()
                .append(denominazione)
                .append(" ")
                .append(stato)
                .append(" ")
                .append(stato)
                .append(" ")
                .append(latitude)
                .append(" ")
                .append(longitude)
                .toString();
    }

    private static String buildInputStringCentroMonitoraggio(
            String nomeCentro,
            String via,
            short numCivico,
            int cap,
            String comune,
            String provincia
    ){
        return new StringBuilder()
                .append(nomeCentro)
                .append(" ")
                .append(via)
                .append(" ")
                .append(numCivico)
                .append(" ")
                .append(cap)
                .append(" ")
                .append(comune)
                .append(" ")
                .append(provincia)
                .toString();
    }

    public static void main(String[] args) throws Exception{
        //AreaInteresse
        final String areaID, areaID2;
        String denominazione = "Orta San Giulio";
        String denominazione2 = "Orio Litta";
        String stato = "Italy";
        String stato2 = "Italy";
        float latitude = Float.parseFloat("45.79727");
        float longitude = Float.parseFloat("8.41437");
        float latitude2 = Float.parseFloat("45.16244");
        float longitude2 = Float.parseFloat("9.55447");
        final String inputString = buildInputStringAreaInteresse(
                denominazione,
                stato,
                latitude,
                longitude);
        final String inputString2 = buildInputStringAreaInteresse(
                denominazione2,
                stato2,
                latitude,
                longitude2
        );
        areaID = createMD5Hash(inputString);
        areaID2 = createMD5Hash(inputString2);
        AreaInteresse a = new AreaInteresse(areaID, denominazione, stato, latitude, longitude);
        AreaInteresse a2 = new AreaInteresse(areaID2, denominazione2, stato2, latitude2, longitude2);
        System.out.println("hash generated for a: " + areaID);
        System.out.println("a to String: " + a.toString());
        System.out.println("hash generated for a2: " + areaID);
        System.out.println("a2 to String: " + a2.toString());

        if(a.hashCode() == a2.hashCode()) throw new Exception("Equals HashCodes");
        else System.out.printf("HashCodes different:\na: %s - a2: %s ",a.hashCode(), a2.hashCode());
        if(a.equals(a2)) throw new Exception("Objects are equal");
        else System.out.println("Objects are not equal");

        System.out.println("------------------------");
        //City
        String geoName = "Novalesa";
        String asciiName = "Novalesa";
        String countryCode = "IT";
        String country = "Italy";
        float latitudeCity = Float.parseFloat("45.19065");
        float longitudeCity = Float.parseFloat("7.01416");
        final City c = new City(geoName, asciiName, countryCode, country, latitudeCity, longitudeCity);
        String geoName2 = "Monte San Giacomo";
        String asciiName2 = "Monte San Giacomo";
        String countryCode2 = "IT";
        String country2 = "Italy";
        float latitudeCity2 = Float.parseFloat("40.34411");
        float longitudeCity2 = Float.parseFloat("15.54221");
        final City c2 = new City(geoName2, asciiName2, countryCode2, country2, latitudeCity2, longitudeCity2);
        System.out.println("c: "+c.toString());
        System.out.println("c2: "+c2.toString());
        if(c.hashCode() == c2.hashCode()) throw new Exception("Equals HashCodes");
        else System.out.printf("HashCodes different:\nc: %s - c2: %s\n",c.hashCode(), c2.hashCode());
        if(c.equals(c2)) throw new Exception("Objects are equal");
        else System.out.println("Objects are not equal");

        System.out.println("------------------------");
        //Centro monitoraggio
        String nomeCentro = "MontescagliosoCentro";
        String comune = "Montescaglioso";
        String via = "Genova";
        short numCivico = 1;
        int cap = 75024;
        String provincia = "MT";
        String centroID = buildInputStringCentroMonitoraggio(nomeCentro, via, numCivico, cap, comune, provincia);

        String nomeCentro2 = "VareseCentro";
        String comune2 = "Varese";
        String via2 = "mazzini";
        short numCivico2 = 2;
        int cap2 = 21100;
        String provincia2 = "VA";
        String centroID2 = buildInputStringCentroMonitoraggio(
                nomeCentro2,
                via2,
                numCivico2,
                cap2,
                comune2,
                provincia2
        );

        CentroMonitoraggio cm = new CentroMonitoraggio(centroID, nomeCentro, via, numCivico, cap, comune, provincia);
        CentroMonitoraggio cm2 = new CentroMonitoraggio(centroID2, nomeCentro2, via2, numCivico2, cap2, comune2, provincia2);

        System.out.println("cm: "+cm.toString());
        System.out.println("cm2: "+cm2.toString());
        if(cm.hashCode() == cm2.hashCode()) throw new Exception("Equals HashCodes");
        else System.out.printf("HashCodes different:\nc: %s - c2: %s\n",cm.hashCode(), cm2.hashCode());
        if(cm.equals(cm2)) throw new Exception("Objects are equal");
        else System.out.println("Objects are not equal");


    }
}