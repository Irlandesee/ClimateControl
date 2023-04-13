package it.uninsubria.climatemonitoring.gestioneFile;

import it.uninsubria.climatemonitoring.dati.AreaInteresse;
import it.uninsubria.climatemonitoring.dati.CentroMonitoraggio;
import it.uninsubria.climatemonitoring.dati.Operatore;

import java.io.*;
import java.util.LinkedList;

/**
 * Rappresenta l'interfaccia per leggere e scrivere da file. Fa uso sia di @File
 *
 * @author Mattia Mauro Lunardi 736898 mmlunardi@studenti.uninsubria.it VA
 * @author Andrea Quaglia 753166 aquaglia2@studenti.uninsubria.it VA
 */
@SuppressWarnings("unchecked")
public class FileInterface {
    /**
     * Cartella contenete tutti i file.
     */
    File directory = new File(".data");

    /**
     * File contenente tutte le aree d'interesse in formato .csv. Un esempio viene generato nel caso in cui nessun file
     * venga fornito dall'esterno.
     */
    private File geonamesCoordinatesFile;
    /**
     * File contenente i centri di monitoraggio creati dagli operatori registrati.
     */
    private File centriMonitoraggioFile;
    /**
     * File in cui viene salvata la {@code LinkedList<AreaInteresse>} utilizzata come cache dopo aver letto i dati dal
     * file geonames-and-coordinates.csv. Quando un'area d'interesse viene associata a un centro di monitoraggio, viene
     * rimossa da questa lista e aggiunta alla lista salvata nel file ParametriClimatici.dati.
     */
    private File coordinateMonitoraggioFile;
    /**
     * File in cui e' possibile inserire le email che gli operatori utilizzeranno per registrarsi. Un esempio viene
     * generato nel caso in cui nessun file venga fornito dall'esterno.
     */
    private File operatoriAutorizzatiFile;
    /**
     * File in cui viene salvata la {@code LinkedList<OperatoriRegistrati>} utilizzata come cache degli operatori che
     * hanno effettuato una registrazione.
     */
    private File operatoriRegistratiFile;
    /**
     * File in cui viene salvata la {@code LinkedList<OperatoriRegistrati>} utilizzata come cache delle aree d'interesse
     * associate a un centro di monitoraggio. Viene utilizzata per la ricerca e il calcolo dei dati aggregati.
     */
    private File parametriClimaticiFile;

    /**
     * Interfaccia per la scrittura su file.
     */
    private FileReader fileReader;
    /**
     * Interfaccia per la lettura da file.
     */
    private FileWriter fileWriter;

    /**
     * Crea l'interfaccia per la lettura e scrittura su file.
     *
     * @throws IOException non e' stato possibile creare uno dei file o la cartella richiesta.
     */
    public FileInterface() throws IOException {
        initialize();
    }

    /**
     * Legge il file contenente le email degli operatori autorizzati.
     *
     * @return {@code LinkedList<String>} contenente le email degli operatori autorizzati.
     * @throws IOException il file OperatoriAutorizzati.dati non esiste.
     * @see it.uninsubria.climatemonitoring.ClimateMonitor
     */
    public LinkedList<String> readOperatoriAutorizzatiFile() throws IOException {
        return fileReader.readOperatoriAutorizzatiFile();
    }

    /**
     * Legge il file contenente la cache coordinateMonitoraggioCache.
     *
     * @return {@code LinkedList<String>} contenente la cache coordinateMonitoraggioCache.
     * @throws IOException il file CoordinateMonitoraggio.dati non esiste.
     * @throws ClassNotFoundException il file CoordinateMonitoraggio.dati non contiene una {@code LinkedList<String>}.
     * @see it.uninsubria.climatemonitoring.ClimateMonitor
     */
    public LinkedList<AreaInteresse> readCoordinateMonitoraggioFile() throws IOException, ClassNotFoundException {
        if(coordinateMonitoraggioFile.length() == 0) {
            LinkedList<AreaInteresse> areeInteresse = fileReader.readGeonamesAndCoordinatesFile();
            fileWriter.serializeFileOut(areeInteresse, coordinateMonitoraggioFile.getPath());
            return areeInteresse;
        }
        return (LinkedList<AreaInteresse>) fileReader.serializeFileIn(coordinateMonitoraggioFile.getPath());
    }

    /**
     * Legge il file ParametriClimatici.dati.
     *
     * @return {@code LinkedList<AreaInteresse>} contenente la cache parametriClimaticiCache.
     * @throws IOException il file ParametriClimatici.dati non esiste.
     * @throws ClassNotFoundException il file ParametriClimatici.dati non contiene una
     *      {@code LinkedList<AreaInteresse>}.
     * @see it.uninsubria.climatemonitoring.ClimateMonitor
     */
    public LinkedList<AreaInteresse> readParametriClimaticiFile() throws IOException, ClassNotFoundException {
        return  (LinkedList<AreaInteresse>) fileReader.serializeFileIn(parametriClimaticiFile.getPath());
    }

    /**
     * Legge il file OperatoriRegistrati.dati.
     *
     * @return {@code LinkedList<Operatore>} contenente la cache operatoriRegistratiCache.
     * @throws IOException il file OperatoriRegistrati.dati non esiste.
     * @throws ClassNotFoundException il file OperatoriRegistrati.dati non contiene una {@code LinkedList<Operatore>}.
     * @see it.uninsubria.climatemonitoring.ClimateMonitor
     */
    public LinkedList<Operatore> readOperatoriRegistratiFile() throws IOException, ClassNotFoundException {
        return (LinkedList<Operatore>) fileReader.serializeFileIn(operatoriRegistratiFile.getPath());
    }

    /**
     * Legge il file CentroMonitoraggio.dati.
     *
     * @return {@code LinkedList<CentroMonitoraggio>} contenente la cache centroMonitoraggioCache.
     * @throws IOException il file CentroMonitoraggio.dati non esiste.
     * @throws ClassNotFoundException il file CentroMonitoraggio.dati non contiene una
     *      {@code LinkedList<CentroMonitoraggio>}.
     * @see it.uninsubria.climatemonitoring.ClimateMonitor
     */
    public LinkedList<CentroMonitoraggio> readCentriMonitoraggioFile() throws IOException, ClassNotFoundException {
        return (LinkedList<CentroMonitoraggio>) fileReader.serializeFileIn(centriMonitoraggioFile.getPath());
    }

    /**
     * Legge il file geonames-and-coordinates.csv.
     *
     * @return {@code LinkedList<AreaInteresse>} contenente la cache centroMonitoraggioCache.
     * @throws IOException il file CentroMonitoraggio.dati non esiste.
     * @see it.uninsubria.climatemonitoring.ClimateMonitor
     */
    public LinkedList<AreaInteresse> readGeonamesAndCoordinatesFile() throws IOException {
        return  fileReader.readGeonamesAndCoordinatesFile();
    }

    /**
     * Scrive sul file CoordinateMonitoraggio.dati.
     *
     * @param areeInteresseDisponibili {@code LinkedList<AreaInteresse>} contenente la cache coordinateMonitoraggioCache.
     * @throws IOException il file CoordinateMonitoraggio.dati non esiste.
     * @see it.uninsubria.climatemonitoring.ClimateMonitor
     */
    public void writeCoordinateMonitoraggioFile(LinkedList<AreaInteresse> areeInteresseDisponibili) throws IOException {
        fileWriter.serializeFileOut(areeInteresseDisponibili, coordinateMonitoraggioFile.getPath());
    }

    /**
     * Scrive sul file geonames-and-coordinates.csv un esempio dei dati in ingresso.
     *
     * @throws IOException il file geonames-and-coordinates.csv non esiste.
     */
    public void writeGeonamesAndCoordinatesFile() throws IOException {
        fileWriter.writeGeonamesAndCoordinatesFile();
    }

    /**
     * Scrive sul file ParametriClimatici.dati.
     *
     * @param parametriClimatici {@code LinkedList<AreaInteresse>} contenente la cache parametriClimaticiCache.
     * @throws IOException il file ParametriClimatici.dati non esiste.
     * @see it.uninsubria.climatemonitoring.ClimateMonitor
     */
    public void writeParametriClimaticiFile(LinkedList<AreaInteresse> parametriClimatici) throws IOException {
        fileWriter.serializeFileOut(parametriClimatici, parametriClimaticiFile.getPath());
    }

    /**
     * Scrive sul file OperatoriRegistrati.dati.
     *
     * @param operatori {@code LinkedList<Operatore>} contenente la cache operatoriRegistratiCache.
     * @throws IOException il file OperatoriRegistrati.dati non esiste.
     * @see it.uninsubria.climatemonitoring.ClimateMonitor
     */
    public void writeOperatoriRegistratiFile(LinkedList<Operatore> operatori) throws IOException {
        fileWriter.serializeFileOut(operatori, operatoriRegistratiFile.getAbsolutePath());
    }

    /**
     * Scrive sul file OperatoriAutorizzati.dati un esempio delle email autorizzate a effettuare una registrazione.
     *
     * @throws IOException il file OperatoriAutorizzati.dati non esiste.
     * @see it.uninsubria.climatemonitoring.ClimateMonitor
     */
    public void writeOperatoriAutorizzatiFile() throws IOException {
        fileWriter.writeOperatoriAutorizzatiFile();
    }

    /**
     * Scrive sul file CoordinateMonitoraggio.dati.
     *
     * @param centriMonitoraggio {@code LinkedList<CentroMonitoraggio>} contenente la cache centriMonitoraggioCache.
     * @throws IOException il file CoordinateMonitoraggio.dati non esiste.
     * @see it.uninsubria.climatemonitoring.ClimateMonitor
     */
    public void writeCentriMonitoraggioFile(LinkedList<CentroMonitoraggio> centriMonitoraggio) throws IOException {
        fileWriter.serializeFileOut(centriMonitoraggio, centriMonitoraggioFile.getAbsolutePath());
    }

    /**
     * Scrive sul file OperatoriRegistrati.dati.
     *
     * @param operatore {@code LinkedList<Operatore>} contenente la cache operatoriRegistratiCache.
     * @throws IOException il file OperatoriRegistrati.dati non esiste.
     * @see it.uninsubria.climatemonitoring.ClimateMonitor
     */
    public void registraOperatore(LinkedList<Operatore> operatore) throws IOException {
        fileWriter.serializeFileOut(operatore, operatoriRegistratiFile.getPath());
    }

    private void initialize() throws IOException {
        if (!directory.exists())
            //noinspection ResultOfMethodCallIgnored
            directory.mkdir();
        fileReader = new FileReader(this);
        fileWriter = new FileWriter(this);

        geonamesCoordinatesFile = new File(
                "data/geonames-and-coordinates.csv");
        centriMonitoraggioFile = new File(
                "data/CentriMonitoraggio.dati");
        coordinateMonitoraggioFile = new File(
                "data/CoordinateMonitoraggio.dati");
        operatoriAutorizzatiFile = new File(
                "data/OperatoriAutorizzati.dati");
        operatoriRegistratiFile = new File(
                "data/OperatoriRegistrati.dati");
        parametriClimaticiFile = new File(
                "data/ParametriClimatici.dati");
        checkFilesExistence();
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
        if(file == null) return;
        if(!file.exists())
            if (file.createNewFile()) System.out.println("Created new file at: " + file.getAbsolutePath());
    }

    //getters and setters
    /**
     * @return {@link FileInterface#geonamesCoordinatesFile}
     */
    public File getGeonamesCoordinatesFile() {
        return geonamesCoordinatesFile;
    }
    /**
     * @return {@link FileInterface#operatoriAutorizzatiFile}
     */
    public File getOperatoriAutorizzatiFile() {
        return operatoriAutorizzatiFile;
    }
}
