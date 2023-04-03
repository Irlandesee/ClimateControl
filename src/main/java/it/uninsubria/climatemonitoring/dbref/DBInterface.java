package it.uninsubria.climatemonitoring.dbref;

import it.uninsubria.climatemonitoring.city.City;
import it.uninsubria.climatemonitoring.dbref.readerDB.ReaderDB;
import it.uninsubria.climatemonitoring.dbref.writerDB.WriterDB;
import it.uninsubria.climatemonitoring.operatore.Operatore;

import java.io.*;
import java.util.HashMap;

/**
 * @author : Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * @author : Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 **/
@SuppressWarnings("FieldCanBeLocal")
public class DBInterface {
    private static final File geonamesCoordinatesFile = new File("data/geonames-and-coordinates.csv");
    private static final File centroMonitoraggioFile = new File("data/CentroMonitoraggio.dati");
    private static final File coordinateMonitoraggioFile = new File("data/CoordinateMonitoraggio.dati");
    private static final File operatoriAutorizzatiFile = new File("data/OperatoriAutorizzati.dati");
    private static final File operatoriRegistratiFile = new File("data/OperatoriRegistrati.dati");
    private static final File parametriClimaticiFile = new File("data/ParametriClimatici.dati");

    private static final boolean DEBUG = true;

    private HashMap<String, City> geonamesDBCache;

    private static ReaderDB readerREF;
    private static WriterDB writerREF;

    private static DBInterface uniqueInstance;

    private DBInterface() {}

    public static DBInterface getInstance() throws IOException {
        if (uniqueInstance == null) {
            uniqueInstance = new DBInterface();
            initialize();
        }
        return uniqueInstance;
    }

    public boolean isDEBUG(){ return DEBUG;}

    public HashMap<String, City> readGeonamesFile() throws IOException {
        return readerREF.readGeonamesAndCoordinatesFile();
    }

    public HashMap<String, Operatore> readOperatoriAutorizzatiFile() throws IOException {
        return readerREF.readOperatoriAutorizzatiFile();
    }

    public HashMap<String, Operatore> readOperatoriRegistratiFile() throws IOException {
        return readerREF.readOperatoriRegistratiFile();
    }

    public void writeCoordinateMonitoraggioFile(HashMap<String, City> cache) throws IOException {
        writerREF.writeCoordinateMonitoraggioFile(cache);
    }

    public void registraOperatore(Operatore operatore) throws IOException {
        writerREF.registraOperatore(operatore);
    }

    public File getGeonamesCoordinatesFile() {
        return geonamesCoordinatesFile;
    }
    public File getCentroMonitoraggioFile() { return centroMonitoraggioFile; }
    public File getCoordinateMonitoraggioFile() {
        return  coordinateMonitoraggioFile;
    }
    public File getOperatoriAutorizzatiFile() {
        return operatoriAutorizzatiFile;
    }
    public File getOperatoriRegistratiFile() {
        return  operatoriRegistratiFile;
    }
    public File getParametriClimaticiFile() {
        return parametriClimaticiFile;
    }

    private static void initialize() throws IOException {
        readerREF = new ReaderDB(uniqueInstance);
        writerREF = new WriterDB(uniqueInstance);

        checkFilesExistence();
        writeHeaders();
    }

    private static void writeHeaders() throws IOException {
        PrintWriter fout;

        final int coordinateMonitoraggioFileHeaderLength = 64;
        final int operatoriAutorizzatiFileHeaderLength = 7;
        final int operatoriRegistratiFileHeaderLength = 66;
        final int parametriClimaticiFileHeaderLength = 123;

        if(coordinateMonitoraggioFile.length() <= coordinateMonitoraggioFileHeaderLength) {
            fout = new PrintWriter(new BufferedWriter(new FileWriter(coordinateMonitoraggioFile)));
            fout.println("geonameID;AsciiName;CountryCode;CountryName;latitude,longitude");
            fout.flush();
            fout.close();
        }

        if(operatoriAutorizzatiFile.length() <= operatoriAutorizzatiFileHeaderLength) {
            fout = new PrintWriter(new BufferedWriter(new FileWriter(operatoriAutorizzatiFile)));
            fout.println("Email");
            fout.flush();
            fout.close();
        }

        if(operatoriRegistratiFile.length() <= operatoriRegistratiFileHeaderLength) {
            fout = new PrintWriter(new BufferedWriter(new FileWriter(operatoriRegistratiFile)));
            fout.println("Cognome;Nome;CodiceFiscale;Email;UserID;Password;CentroAfferenza");
            fout.flush();
            fout.close();
        }

        if(parametriClimaticiFile.length() <= parametriClimaticiFileHeaderLength) {
            fout = new PrintWriter(new BufferedWriter(new FileWriter(parametriClimaticiFile)));
            fout.println("Nome;AreaInteresse;DataRilevazione;Vento;Umidità;Pressione;Temperatura;Precipitazioni;" +
                    "AltitudineGhiacciai;MassaGhiacciai");
            fout.flush();
            fout.close();
        }
    }

    private static void checkFilesExistence() throws IOException {
        checkFileExistence(geonamesCoordinatesFile);
        checkFileExistence(centroMonitoraggioFile);
        checkFileExistence(coordinateMonitoraggioFile);
        checkFileExistence(operatoriAutorizzatiFile);
        checkFileExistence(operatoriRegistratiFile);
        checkFileExistence(parametriClimaticiFile);
    }

    private static void checkFileExistence(File file) throws IOException {
        if(!file.exists()) {
            boolean res = file.createNewFile();
            if (res && DEBUG) System.out.println("Created new file at: " +
                    file.getAbsolutePath());
        }
    }

    private HashMap<String, City> getGeonamesDBCache(){return this.geonamesDBCache;}
}
