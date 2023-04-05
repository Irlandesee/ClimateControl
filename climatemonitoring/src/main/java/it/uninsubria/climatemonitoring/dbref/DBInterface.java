package it.uninsubria.climatemonitoring.dbref;

import it.uninsubria.climatemonitoring.areaInteresse.AreaInteresse;
import it.uninsubria.climatemonitoring.centroMonitoraggio.CentroMonitoraggio;
import it.uninsubria.climatemonitoring.city.City;
import it.uninsubria.climatemonitoring.climateParameters.ClimateParameter;
import it.uninsubria.climatemonitoring.dbref.readerDB.ReaderDB;
import it.uninsubria.climatemonitoring.dbref.writerDB.WriterDB;
import it.uninsubria.climatemonitoring.operatore.Operatore;
import it.uninsubria.climatemonitoring.operatore.opeatoreAutorizzato.OperatoreAutorizzato;
import it.uninsubria.climatemonitoring.operatore.opeatoreRegistrato.OperatoreRegistrato;

import java.io.*;
import java.util.HashMap;

public class DBInterface {

    protected static final String geonamesAndCoordinatesDati = "data/geonames-and-coordinates.csv";
    protected static final String centroMonitoraggioDati = "data/centroMonitoraggio.dati";
    protected static final String coordinateMonitoraggioDati = "data/coordinateMonitoraggio.dati";
    protected static final String parametriClimaticiDati = "data/parametriClimatici.dati";
    protected static final String operatoriRegistratiDati = "data/operatoriRegistrati.dati";
    protected static final String operatoriAutorizzatiDati = "data/operatoriAutorizzati.dati";
    protected static final String areeInteresseDati = "data/areeInteresse.dati";

    protected static final String centroMonitoraggioFileHeader = "centroID;nomeCentro;via/piazza;numCivico;cap;comune;provincia;areaInteresse1,areaInteresseN";
    protected static final String geonamesFileHeader = "Geoname ID;Name;ASCII Name;Country Code;Country Name;Coordinates";
    protected static final String coordinateFileHeader = "geonameID;ASCII Name;CountryCode;Country;latitude,longitude";
    protected static final String operatoriRegistratiHeader = "userid;pwd;nomeOp;cognomeOp;codFiscale;mail;centroMonitoraggio";
    protected static final String operatoriAutorizzatiHeader = "cognomeOp;nomeOp;codFiscale;mailOp";
    protected static final String parametriClimaticiHeader = "centroID;areaInteresse;data;params1,paramN;note";

    private final File geonamesCoordinatesFile;
    private final File centroMonitoraggioFile;
    private final File coordinateMonitoraggioFile;

    private final File parametriClimaticiFile;
    private final File operatoriAutorizzatiFile;
    private final File operatoriRegistrati;
    private final File areeInteresseFile;
    private final boolean DEBUG = true;

    private HashMap<String, City> geonamesCache;
    private HashMap<String, CentroMonitoraggio> centroMonitoraggioCache;
    private HashMap<String, Operatore> operatoreRegistratoCache;
    private HashMap<String, Operatore> operatoreAutorizzatoCache;
    private HashMap<String, AreaInteresse> areeInteresseCache;

    private final ReaderDB readerREF;
    private final WriterDB writerREF;

    public DBInterface(){
        geonamesCoordinatesFile = new File(DBInterface.geonamesAndCoordinatesDati);
        centroMonitoraggioFile = new File(DBInterface.centroMonitoraggioDati);
        coordinateMonitoraggioFile = new File(DBInterface.coordinateMonitoraggioDati);
        parametriClimaticiFile = new File(DBInterface.parametriClimaticiDati);
        operatoriAutorizzatiFile = new File(DBInterface.operatoriAutorizzatiDati);
        operatoriRegistrati = new File(DBInterface.operatoriRegistratiDati);
        areeInteresseFile = new File(DBInterface.areeInteresseDati);

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
                    BufferedWriter bWriter = new BufferedWriter(new FileWriter(coordinateMonitoraggioFile));
                    bWriter.write(coordinateFileHeader);
                    bWriter.write("\n");
                    bWriter.close();
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

    private HashMap<String, City> getGeonamesCache(){return this.geonamesCache;}

    public boolean isDEBUG(){ return this.DEBUG;}


    private void writeCoordinataMonitoraggio(City c){
        //TODO:
    }

    private void writeCentroMonitoraggio(CentroMonitoraggio c){
        //TODO:
    }

    //TODO: refactor
    private void writeCoordinateMonitoraggioCache(HashMap<String, City> cache) {
        writerREF.writeCoordinateMonitoraggioFile(cache);
    }

    //TODO: refactor
    private void writeGeonamesAndCoordinatesCache(HashMap<String, City> cache){
        writerREF.writeGeonamesAndCoordinates(cache);
    }

    private boolean writeOperatoreRegistrato(OperatoreRegistrato o){
        //TODO:
        return false;
    }

    private boolean writeOperatoreAutorizzato(OperatoreAutorizzato o){
        //TODO:
        return false;
    }

    private boolean writeParam(ClimateParameter c){
        //TODO:
        return false;
    }

    private boolean writeArea(AreaInteresse a){
        //TODO:
        return false;
    }
    
    public void write(String op){
        //TODO: in base alla stringa passata, chiama il metodo writer corrispondente
    }

    //TODO: refactor
    private HashMap<String, City> readGeonamesCache(){ return readerREF.readGeonamesAndCoordinatesFile();}

    private HashMap<String, Operatore> readOperatoreRegistrato(){
        //TODO:
        return null;
    }

    private HashMap<String, Operatore> readOperatoreAutorizzato(){
        //TODO:
        return null;
    }

    private HashMap<String, ClimateParameter> readClimateParameter(){
        //TODO:
        return null;
    }

    private HashMap<String, AreaInteresse> readAreaInteresse(){
        //TODO:
        return null;
    }

    public void read(String op) {
        //TODO: in base alla stringa passata, chiama il metodo reader corrispondente }
    }
}
