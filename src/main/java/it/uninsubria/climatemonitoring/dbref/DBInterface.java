package it.uninsubria.climatemonitoring.dbref;

import it.uninsubria.climatemonitoring.city.City;
import it.uninsubria.climatemonitoring.dbref.readerDB.ReaderDB;
import it.uninsubria.climatemonitoring.dbref.writerDB.WriterDB;

import java.io.*;
import java.util.HashMap;

/**
 * @author : Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * @author : Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 **/
@SuppressWarnings("FieldCanBeLocal")
public class DBInterface {
    private static final String geonamesAndCoordinatesDati = "data/geonames-and-coordinates.csv";
    private static final String centroMonitoraggioDati = "data/centroMonitoraggio.dati";
    private static final String coordinateMonitoraggioDati = "data/coordinateMonitoraggio.dati";

    private static File geonamesCoordinatesFile;
    private static File centroMonitoraggioFile;
    private static File coordinateMonitoraggioFile;

    private static final boolean DEBUG = true;

    private HashMap<String, City> geonamesDBCache;

    private static ReaderDB readerREF;
    private static WriterDB writerREF;

    private static DBInterface uniqueInstance;

    private DBInterface() {}

    public File getGeonamesCoordinatesFile() {
        return geonamesCoordinatesFile;
    }
    public File getCoordinateMonitoraggioFile() {
        return  coordinateMonitoraggioFile;
    }

    public static DBInterface getInstance() throws IOException {
        if (uniqueInstance == null) {
            uniqueInstance = new DBInterface();
            initialize();
        }
        return uniqueInstance;
    }

    public boolean isDEBUG(){ return this.DEBUG;}

    public HashMap<String, City> readGeonamesFile() throws IOException { return readerREF.readGeonamesAndCoordinatesFile();}

    public void writeCoordinateMonitoraggioFile(HashMap<String, City> cache) throws IOException {
        writerREF.writeCoordinateMonitoraggioFile(cache);
    }

    private static void initialize() throws IOException {
        geonamesCoordinatesFile = new File(geonamesAndCoordinatesDati);
        centroMonitoraggioFile = new File(centroMonitoraggioDati);
        coordinateMonitoraggioFile = new File(coordinateMonitoraggioDati);

        readerREF = new ReaderDB(uniqueInstance);
        writerREF = new WriterDB(uniqueInstance);

        checkFileExistence(geonamesCoordinatesFile);
        checkFileExistence(centroMonitoraggioFile);
        checkFileExistence(coordinateMonitoraggioFile);
        writeHeader();
    }

    private static void checkFileExistence(File file) throws IOException {
        if(!file.exists()) {
            boolean res = file.createNewFile();
            if (res && DEBUG) System.out.println("Created new file at: " +
                    file.getAbsolutePath());
        }
    }

    private static void writeHeader() throws IOException {
        PrintWriter fout = new PrintWriter(new BufferedWriter(new FileWriter(coordinateMonitoraggioFile)));
        fout.println("geonameID;AsciiName;CountryCode;CountryName;latitude,longitude");
        fout.flush();
        fout.close();
    }

    private HashMap<String, City> getGeonamesDBCache(){return this.geonamesDBCache;}
}
