package it.uninsubria.climatemonitoring.gestioneFile;

import it.uninsubria.climatemonitoring.AreaInteresse;
import it.uninsubria.climatemonitoring.CentroMonitoraggio;
import it.uninsubria.climatemonitoring.Operatore;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author : Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * @author : Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 **/
public class DBInterface {
    private final File geonamesCoordinatesFile = new File("data/geonames-and-coordinates.csv");
    private final File centriMonitoraggioFile = new File("data/CentriMonitoraggio.dati");
    private final File coordinateMonitoraggioFile = new File("data/CoordinateMonitoraggio.dati");
    private final File operatoriAutorizzatiFile = new File("data/OperatoriAutorizzati.dati");
    private final File operatoriRegistratiFile = new File("data/OperatoriRegistrati.dati");
    private final File parametriClimaticiFile = new File("data/ParametriClimatici.dati");
    private final File areeInteresseFile = new File("data/AreeInteresse.dati");

    private final boolean DEBUG = true;

    private HashMap<String, AreaInteresse> geonamesDBCache;

    private ReaderDB readerREF;
    private WriterDB writerREF;

    public DBInterface() throws IOException {
        initialize();
    }

    public boolean isDEBUG(){ return DEBUG;}

    public LinkedList<String> readOperatoriAutorizzatiFile() throws IOException {
        return readerREF.readOperatoriAutorizzatiFile();
    }

    public LinkedList<AreaInteresse> readCoordinateMonitoraggioFile() throws IOException, ClassNotFoundException {
        if(coordinateMonitoraggioFile.length() == 0) {
            LinkedList<AreaInteresse> areeInteresse = readerREF.readGeonamesAndCoordinatesFile();
            writerREF.serializeFileOut(areeInteresse, coordinateMonitoraggioFile.getPath());
            return areeInteresse;
        }
        return (LinkedList<AreaInteresse>) readerREF.serializeFileIn(coordinateMonitoraggioFile.getPath());
    }

    public LinkedList<AreaInteresse> readAreeInteresseFile() throws IOException, ClassNotFoundException {
        return  (LinkedList<AreaInteresse>) readerREF.serializeFileIn(areeInteresseFile.getPath());
    }

    public LinkedList<Operatore> readOperatoriRegistratiFile() throws IOException, ClassNotFoundException {
        return (LinkedList<Operatore>) readerREF.serializeFileIn(operatoriRegistratiFile.getPath());
    }

    public LinkedList<CentroMonitoraggio> readCentriMonitoraggioFile() throws IOException, ClassNotFoundException {
        return (LinkedList<CentroMonitoraggio>) readerREF.serializeFileIn(centriMonitoraggioFile.getPath());
    }

    public void writeCoordinateMonitoraggioFile(LinkedList<AreaInteresse> areeInteresseDisponibili) throws IOException {
        writerREF.serializeFileOut(areeInteresseDisponibili, coordinateMonitoraggioFile.getPath());
    }

    public void writeOperatoriRegistrati(LinkedList<Operatore> operatori) throws IOException {
        writerREF.serializeFileOut(operatori, operatoriRegistratiFile.getAbsolutePath());
    }

    public void writeAreeInteresseFile(LinkedList<AreaInteresse> areeInteresseAssociate) throws IOException {
        writerREF.serializeFileOut(areeInteresseAssociate, areeInteresseFile.getPath());
    }

    public void writeCentriMonitoraggioFile(LinkedList<CentroMonitoraggio> centriMonitoraggio) throws IOException {
        writerREF.serializeFileOut(centriMonitoraggio, centriMonitoraggioFile.getAbsolutePath());
    }

    public void registraOperatore(LinkedList<Operatore> operatore) throws IOException {
        writerREF.serializeFileOut(operatore, operatoriRegistratiFile.getPath());
    }

    public File getGeonamesCoordinatesFile() {
        return geonamesCoordinatesFile;
    }
    public File getCentriMonitoraggioFile() { return centriMonitoraggioFile; }
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

        final int operatoriAutorizzatiFileHeaderLength = 7;
        final int parametriClimaticiFileHeaderLength = 123;

        if(operatoriAutorizzatiFile.length() <= operatoriAutorizzatiFileHeaderLength) {
            fout = new PrintWriter(new BufferedWriter(new FileWriter(operatoriAutorizzatiFile)));
            fout.println("Email");
            fout.flush();
            fout.close();
        }

        if(parametriClimaticiFile.length() <= parametriClimaticiFileHeaderLength) {
            fout = new PrintWriter(new BufferedWriter(new FileWriter(parametriClimaticiFile)));
            fout.println("CentroMonitoraggio;AreaInteresse;DataRilevazione;Vento;Umidità;Pressione;Temperatura;" +
                    "Precipitazioni;AltitudineGhiacciai;MassaGhiacciai");
            fout.flush();
            fout.close();
        }
    }

    private void checkFilesExistence() throws IOException {
        checkFileExistence(geonamesCoordinatesFile);
        checkFileExistence(centriMonitoraggioFile);
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

    private HashMap<String, AreaInteresse> getGeonamesDBCache(){return this.geonamesDBCache;}
}
