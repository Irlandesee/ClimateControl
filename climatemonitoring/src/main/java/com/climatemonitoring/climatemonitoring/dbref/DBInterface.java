package com.climatemonitoring.climatemonitoring.dbref;

import com.climatemonitoring.climatemonitoring.city.City;
import com.climatemonitoring.climatemonitoring.dbref.readerDB.ReaderDB;
import com.climatemonitoring.climatemonitoring.dbref.writerDB.WriterDB;

import java.io.*;
import java.util.HashMap;

public class DBInterface {
    private final String geonamesAndCoordinatesDati = "data/geonames-and-coordinates.csv";
    private final String centroMonitoraggioDati = "data/centroMonitoraggio.dati";
    private final String coordinateMonitoraggioDati = "data/coordinateMonitoraggio.dati";

//    Nella mia versione ho dovuto usare questi percorsi perchè il mio progetto inizia dalla cartella ClimateControl
//    e non dalla cartella climatemonitoring.

//    private final String geonamesAndCoordinatesDati = "climatemonitoring/data/geonames-and-coordinates.csv";
//    private final String centroMonitoraggioDati = "climatemonitoring/data/centroMonitoraggio.dati";
//    private final String coordinateMonitoraggioDati = "climatemonitoring/data/coordinateMonitoraggio.dati";


    private File geonamesCoordinatesFile;
    private File centroMonitoraggioFile;
    private File coordinateMonitoraggioFile;

    private final boolean DEBUG = true;

    private HashMap<String, City> geonamesDBCache;

    private final ReaderDB readerREF;
    private final WriterDB writerREF;

    public DBInterface(){
        geonamesCoordinatesFile = new File(geonamesAndCoordinatesDati);
        centroMonitoraggioFile = new File(centroMonitoraggioDati);
        coordinateMonitoraggioFile = new File(coordinateMonitoraggioDati);

        this.readerREF = new ReaderDB(this);
        this.writerREF = new WriterDB(this);

        try {
            if (!geonamesCoordinatesFile.exists()) {
                boolean res = geonamesCoordinatesFile.createNewFile();
                if (res && DEBUG) System.out.println("Created new file at: " +
                        geonamesCoordinatesFile.getAbsolutePath());
            }
            if (!centroMonitoraggioFile.exists()) {
                boolean res = centroMonitoraggioFile.createNewFile();
                if (res && DEBUG) System.out.println("Created new file at: " +
                        centroMonitoraggioFile.getAbsolutePath());
            }
            if (!coordinateMonitoraggioFile.exists()) {
                boolean res = coordinateMonitoraggioFile.createNewFile();
                try {
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(coordinateMonitoraggioFile)));
                    out.println("geonameID;AsciiName;CountryCode;CountryName;latitude,longitude");
                    out.flush();
                    out.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                if (res && DEBUG) System.out.println("Created new file at: " +
                        coordinateMonitoraggioFile.getAbsolutePath());
            }
        }catch(IOException ioe){ioe.printStackTrace();}

    }

    public File getGeonamesCoordinatesFile() {
        return this.geonamesCoordinatesFile;
    }
    public File getCoordinateMonitoraggioFile() {
        return  this.coordinateMonitoraggioFile;
    }

    private HashMap<String, City> getGeonamesDBCache(){return this.geonamesDBCache;}

    public boolean isDEBUG(){ return this.DEBUG;}

    public HashMap<String, City> readGeonamesFile(){ return readerREF.readGeonamesAndCoordinatesFile();}

    public void writeCoordinateMonitoraggioFile(HashMap<String, City> cache) {
        writerREF.writeCoordinateMonitoraggioFile(cache);
    }
}
