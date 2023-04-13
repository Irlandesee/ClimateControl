package it.uninsubria.climatemonitoring.gestioneFile;

import it.uninsubria.climatemonitoring.dati.AreaInteresse;
import it.uninsubria.climatemonitoring.dati.CentroMonitoraggio;
import it.uninsubria.climatemonitoring.dati.Operatore;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Rappresenta l'interfaccia per leggere e scrivere da file. Fa uso sia di @File
 *
 * @author Mattia Mauro Lunardi 736898 mmlunardi@studenti.uninsubria.it VA
 * @author Andrea Quaglia 753166 aquaglia2@studenti.uninsubria.it VA
 */
@SuppressWarnings("unchecked")
public class FileInterface {
    private File cacheFile;

    /**
     * File contenente tutte le aree d'interesse in formato .csv. Un esempio viene generato nel caso in cui nessun file
     * venga fornito dall'esterno.
     */
    private File geonamesCoordinatesFile;

    /**
     * File in cui e' possibile inserire le email che gli operatori utilizzeranno per registrarsi. Un esempio viene
     * generato nel caso in cui nessun file venga fornito dall'esterno.
     */
    private File operatoriAutorizzatiFile;

    /**
     * Interfaccia per la scrittura su file.
     */
    private final FileReader fileReader;
    /**
     * Interfaccia per la lettura da file.
     */
    private final FileWriter fileWriter;

    private ArrayList<Object> cache;

    private static LinkedList<AreaInteresse> parametriClimaticiCache;
    private static LinkedList<CentroMonitoraggio> centriMonitoraggioCache;
    private static LinkedList<AreaInteresse> areeInteresseDisponibiliCache;
    private static LinkedList<String> operatoriAutorizzatiCache;
    private static LinkedList<Operatore> operatoriRegistratiCache;

    /**
     * Crea l'interfaccia per la lettura e scrittura su file.
     *
     * @throws IOException non e' stato possibile creare uno dei file o la cartella richiesta.
     */
    public FileInterface() throws IOException, ClassNotFoundException {
        fileReader = new FileReader(this);
        fileWriter = new FileWriter(this);

        parametriClimaticiCache = new LinkedList<>();
        centriMonitoraggioCache = new LinkedList<>();
        areeInteresseDisponibiliCache = new LinkedList<>();
        operatoriAutorizzatiCache = new LinkedList<>();
        operatoriRegistratiCache = new LinkedList<>();

        cache = new ArrayList<>();

        creaFile();
        creaFileDiEsempio();
        if(cacheFile.length() == 0) scriviCache();
        else leggiCache();
    }

    private void creaFile() throws IOException {
        File directory = new File("data");

        if (!directory.exists())
            if(directory.mkdir())
                System.out.println("Nuova cartella creata in: " + directory.getPath());

        cacheFile = new File("data/Cache.dati");
        geonamesCoordinatesFile = new File("data/geonames-and-coordinates.csv");
        operatoriAutorizzatiFile = new File("data/OperatoriAutorizzati.dati");

        checkFileExistence(cacheFile);
        checkFileExistence(geonamesCoordinatesFile);
        checkFileExistence(operatoriAutorizzatiFile);
    }

    private void creaFileDiEsempio() throws IOException {
        writeOperatoriAutorizzatiFile();
        writeGeonamesAndCoordinatesFile();
    }

    private void leggiCache() throws IOException, ClassNotFoundException {
        cache = (ArrayList<Object>) fileReader.serializeFileIn(cacheFile.getPath());

        parametriClimaticiCache = (LinkedList<AreaInteresse>) cache.get(0);
        centriMonitoraggioCache = (LinkedList<CentroMonitoraggio>) cache.get(1);
        areeInteresseDisponibiliCache = (LinkedList<AreaInteresse>) cache.get(2);
        operatoriAutorizzatiCache = (LinkedList<String>) cache.get(3);
        operatoriRegistratiCache = (LinkedList<Operatore>) cache.get(4);
    }

    private void scriviCache() throws IOException {
        cache.add(parametriClimaticiCache);
        cache.add(centriMonitoraggioCache);
        cache.add(areeInteresseDisponibiliCache);
        cache.add(operatoriAutorizzatiCache);
        cache.add(operatoriRegistratiCache);

        if(areeInteresseDisponibiliCache.isEmpty())
            areeInteresseDisponibiliCache = readGeonamesAndCoordinatesFile();
        if(operatoriAutorizzatiCache.isEmpty())
            operatoriAutorizzatiCache = readOperatoriAutorizzatiFile();

        writeCacheFile();
    }

    private void checkFileExistence(File file) throws IOException {
        if(file == null || file.exists()) return;

        if(file.createNewFile()) System.out.println("Nuovo file creato in: " + file.getAbsolutePath());
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
     * Scrive sul file geonames-and-coordinates.csv un esempio dei dati in ingresso.
     *
     * @throws IOException il file geonames-and-coordinates.csv non esiste.
     */
    public void writeGeonamesAndCoordinatesFile() throws IOException {
        fileWriter.writeGeonamesAndCoordinatesFile();
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

    public void writeCacheFile() throws IOException {
        fileWriter.serializeFileOut(cache, cacheFile.getPath());
    }

    //getters and setters
    public File getGeonamesCoordinatesFile() {
        return geonamesCoordinatesFile;
    }

    public File getOperatoriAutorizzatiFile() {
        return operatoriAutorizzatiFile;
    }

    public static LinkedList<AreaInteresse> getParametriClimaticiCache() {
        return parametriClimaticiCache;
    }

    public static LinkedList<CentroMonitoraggio> getCentriMonitoraggioCache() {
        return centriMonitoraggioCache;
    }

    public static LinkedList<AreaInteresse> getAreeInteresseDisponibiliCache() {
        return areeInteresseDisponibiliCache;
    }

    public static LinkedList<String> getOperatoriAutorizzatiCache() {
        return operatoriAutorizzatiCache;
    }

    public static LinkedList<Operatore> getOperatoriRegistratiCache() {
        return operatoriRegistratiCache;
    }
}
