package it.uninsubria.climatemonitoring.gestioneFile;

import it.uninsubria.climatemonitoring.AreaInteresse;
import it.uninsubria.climatemonitoring.CentroMonitoraggio;
import it.uninsubria.climatemonitoring.Operatore;

import java.io.*;
import java.util.LinkedList;

/**
 * @author : Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * @author : Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 **/
@SuppressWarnings("ResultOfMethodCallIgnored")
public class DBInterface {
    File directory = new File(".data");

    private File geonamesCoordinatesFile;
    private File centriMonitoraggioFile;
    private File coordinateMonitoraggioFile;
    private File operatoriAutorizzatiFile;
    private File operatoriRegistratiFile;
    private File parametriClimaticiFile;
    private File areeInteresseFile;

    private final boolean DEBUG = true;

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

    public LinkedList<AreaInteresse> readGeonamesAndCoordinatesFile() throws IOException {
        return  readerREF.readGeonamesAndCoordinatesFile();
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

    public void writeGeonamesAndCoordinatesFile() throws IOException {
        writerREF.writeGeonamesAndCoordinatesFile();
    }

    public void writeOperatoriRegistratiFile(LinkedList<Operatore> operatori) throws IOException {
        writerREF.serializeFileOut(operatori, operatoriRegistratiFile.getAbsolutePath());
    }

    public void writeOperatoriAutorizzatiFile() throws IOException {
        writerREF.writeOperatoriAutorizzatiFile();
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
        if (!directory.exists())
            directory.mkdir();
        readerREF = new ReaderDB(this);
        writerREF = new WriterDB(this);

        geonamesCoordinatesFile = new File(
                ".data/geonames-and-coordinates.csv");
        centriMonitoraggioFile = new File(
                ".data/CentriMonitoraggio.dati");
        coordinateMonitoraggioFile = new File(
                ".data/CoordinateMonitoraggio.dati");
        operatoriAutorizzatiFile = new File(
                ".data/OperatoriAutorizzati.dati");
        operatoriRegistratiFile = new File(
                ".data/OperatoriRegistrati.dati");
        parametriClimaticiFile = new File(
                ".data/ParametriClimatici.dati");
        areeInteresseFile = new File(
                ".data/AreeInteresse.dati");

        checkFilesExistence();
    }

    private void checkFilesExistence() throws IOException {
        checkFileExistence(areeInteresseFile);
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
}
