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
    private final File geonamesCoordinatesFile = new File("data/geonames-and-coordinates.csv");
    private final File centroMonitoraggioFile = new File("data/CentroMonitoraggio.dati");
    private final File coordinateMonitoraggioFile = new File("data/CoordinateMonitoraggio.dati");
    private final File operatoriAutorizzatiFile = new File("data/OperatoriAutorizzati.dati");
    private final File operatoriRegistratiFile = new File("data/OperatoriRegistrati.dati");
    private final File parametriClimaticiFile = new File("data/ParametriClimatici.dati");

    private final boolean DEBUG = true;

    private HashMap<String, City> geonamesDBCache;

    private ReaderDB readerREF;
    private WriterDB writerREF;

    public DBInterface() throws IOException {
        initialize();
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

    private void initialize() throws IOException {
        readerREF = new ReaderDB(this);
        writerREF = new WriterDB(this);

        checkFilesExistence();
        writeHeaders();
    }

    private void writeHeaders() throws IOException {
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

    private void checkFilesExistence() throws IOException {
        checkFileExistence(geonamesCoordinatesFile);
        checkFileExistence(centroMonitoraggioFile);
        checkFileExistence(coordinateMonitoraggioFile);
        checkFileExistence(operatoriAutorizzatiFile);
        checkFileExistence(operatoriRegistratiFile);
        checkFileExistence(parametriClimaticiFile);
    }

    private void checkFileExistence(File file) throws IOException {
        if(!file.exists()) {
            boolean res = file.createNewFile();
            if (res && DEBUG) System.out.println("Created new file at: " +
                    file.getAbsolutePath());
        }
    }

    private HashMap<String, City> getGeonamesDBCache(){return this.geonamesDBCache;}
}
