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

    private static final String geonamesAndCoordinatesDati = "data/geonames-and-coordinates.csv";
    private static final String centroMonitoraggioDati = "data/centroMonitoraggio.dati";
    private static final String coordinateMonitoraggioDati = "data/coordinateMonitoraggio.dati";
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
                    BufferedWriter bWriter = new BufferedWriter(new FileWriter(coordinateMonitoraggioFile));
                    String line = "geonameID;AsciiName;CountryCode;CountryName;latitude,longitude\n";
                    bWriter.write(line);
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
