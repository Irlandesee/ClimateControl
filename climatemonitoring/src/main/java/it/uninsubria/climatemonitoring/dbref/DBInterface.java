package it.uninsubria.climatemonitoring.dbref;

import it.uninsubria.climatemonitoring.areaInteresse.AreaInteresse;
import it.uninsubria.climatemonitoring.centroMonitoraggio.CentroMonitoraggio;
import it.uninsubria.climatemonitoring.city.City;
import it.uninsubria.climatemonitoring.climateParameters.ClimateParameter;
import it.uninsubria.climatemonitoring.operatore.Operatore;
import it.uninsubria.climatemonitoring.operatore.opeatoreAutorizzato.OperatoreAutorizzato;
import it.uninsubria.climatemonitoring.operatore.opeatoreRegistrato.OperatoreRegistrato;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.javatuples.Pair;
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
    protected static final String areeInteresseHeader = "areaID:denominazione:stato:latitude:longitude";

    private static final String error_invalid_op = "Invalid op";
    private static final String error_invalid_class = "Invalid class";

    public static final String objClassGeoname = "geoname";
    public static final String objClassCoordinateMonitoraggio = "coordinateMonitoraggio";

    public static final String objClassCentroMonitoraggio = "centroMonitoraggio";
    public static final String objClassParamClimatici = "paramClimatici";
    public static final String objClassOpAutorizzati = "opAutorizzati";
    public static final String objClassOpRegistrati = "opRegistrati";
    public static final String objClassAreaInteresse = "areeInteresse";


    public static final String opRead = "read";
    public static final String opWrite = "write";

    private final File geonamesCoordinatesFile;
    private final File centroMonitoraggioFile;
    private final File coordinateMonitoraggioFile;

    private final File parametriClimaticiFile;
    private final File operatoriAutorizzatiFile;
    private final File operatoriRegistratiFile;
    private final File areeInteresseFile;
    private final boolean DEBUG = true;

    private HashMap<String, City> geonamesCache;
    private HashMap<String, CentroMonitoraggio> centroMonitoraggioCache;
    private HashMap<String, ClimateParameter> climateParameterCache;
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
        operatoriRegistratiFile = new File(DBInterface.operatoriRegistratiDati);
        areeInteresseFile = new File(DBInterface.areeInteresseDati);

        //init caches
        geonamesCache = new HashMap<String, City>();
        centroMonitoraggioCache = new HashMap<String, CentroMonitoraggio>();
        operatoreRegistratoCache = new HashMap<String, Operatore>();
        operatoreAutorizzatoCache = new HashMap<String, Operatore>();
        areeInteresseCache = new HashMap<String, AreaInteresse>();
        climateParameterCache = new HashMap<String, ClimateParameter>();

        this.readerREF = new ReaderDB(this);
        this.writerREF = new WriterDB(this);
        BufferedWriter bWriter;
        try{
            if (!geonamesCoordinatesFile.exists()) {
                boolean res = geonamesCoordinatesFile.createNewFile();
                try{
                    bWriter = new BufferedWriter(new FileWriter(geonamesCoordinatesFile));
                    bWriter.write(geonamesFileHeader);
                    bWriter.write("\n");
                    bWriter.close();
                }catch(IOException ioe){ioe.printStackTrace();}
                if (res && DEBUG) System.out.println("Created new file at: " +
                        geonamesCoordinatesFile.getAbsolutePath());
            }
            if (!coordinateMonitoraggioFile.exists()) {
                boolean res = coordinateMonitoraggioFile.createNewFile();
                try {
                    bWriter = new BufferedWriter(new FileWriter(coordinateMonitoraggioFile));
                    bWriter.write(coordinateFileHeader);
                    bWriter.write("\n");
                    bWriter.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                if (res && DEBUG) System.out.println("Created new file at: " +
                        coordinateMonitoraggioFile.getAbsolutePath());
            }
            if (!centroMonitoraggioFile.exists()) {
                boolean res = centroMonitoraggioFile.createNewFile();
                try{
                    bWriter = new BufferedWriter(new FileWriter(centroMonitoraggioFile));
                    bWriter.write(centroMonitoraggioFileHeader);
                    bWriter.write("\n");
                    bWriter.close();
                }catch(IOException ioe){ioe.printStackTrace();}
                if (res && DEBUG) System.out.println("Created new file at: " +
                        centroMonitoraggioFile.getAbsolutePath());
            }else{
                try {
                    this.centroMonitoraggioCache = (HashMap<String, CentroMonitoraggio>) this.read(DBInterface.objClassCentroMonitoraggio);
                }catch(ClassCastException cce){cce.printStackTrace();}
            }
            if(!parametriClimaticiFile.exists()){
                boolean res = parametriClimaticiFile.createNewFile();
                try{
                    bWriter = new BufferedWriter(new FileWriter(parametriClimaticiFile));
                    bWriter.write(parametriClimaticiHeader);
                    bWriter.write("\n");
                    bWriter.close();
                }catch(IOException ioe){ioe.printStackTrace();}
                if (res && DEBUG) System.out.println("Created new file at: " +
                        parametriClimaticiFile.getAbsolutePath());
            }else{
                try{
                    this.climateParameterCache = (HashMap<String, ClimateParameter>) this.read(DBInterface.objClassParamClimatici);
                }catch(ClassCastException cce){cce.printStackTrace();}
            }
            if(!operatoriAutorizzatiFile.exists()){
                boolean res = operatoriAutorizzatiFile.createNewFile();
                try{
                    bWriter = new BufferedWriter(new FileWriter(operatoriAutorizzatiFile));
                    bWriter.write(operatoriAutorizzatiHeader);
                    bWriter.write("\n");
                    bWriter.close();
                }catch(IOException ioe){ioe.printStackTrace();}
                if (res && DEBUG) System.out.println("Created new file at: " +
                        operatoriAutorizzatiFile.getAbsolutePath());
            }else{
                try{
                    this.operatoreAutorizzatoCache = (HashMap<String, Operatore>) this.read(DBInterface.objClassOpAutorizzati);
                }catch (ClassCastException cce){cce.printStackTrace();}
            }
            if(!operatoriRegistratiFile.exists()){
                boolean res = operatoriRegistratiFile.createNewFile();
                try{
                    bWriter = new BufferedWriter(new FileWriter(operatoriRegistratiFile));
                    bWriter.write(operatoriRegistratiHeader);
                    bWriter.write("\n");
                    bWriter.close();
                }catch(IOException ioe){ioe.printStackTrace();}
                if (res && DEBUG) System.out.println("Created new file at: " +
                        operatoriRegistratiFile.getAbsolutePath());
            }else{
                try{
                    this.operatoreRegistratoCache = (HashMap<String, Operatore>) this.read(DBInterface.objClassOpRegistrati);
                }catch(ClassCastException cce){cce.printStackTrace();}
            }
            if(!areeInteresseFile.exists()){
                boolean res = areeInteresseFile.createNewFile();
                try{
                    bWriter = new BufferedWriter(new FileWriter(areeInteresseFile));
                    bWriter.write(areeInteresseHeader);
                    bWriter.close();
                }catch(IOException ioe){ioe.printStackTrace();}
                if (res && DEBUG) System.out.println("Created new file at: " +
                        areeInteresseFile.getAbsolutePath());
            }else{
                try{
                    this.areeInteresseCache = (HashMap<String, AreaInteresse>) this.read(DBInterface.objClassAreaInteresse);
                }catch(ClassCastException cce){cce.printStackTrace();}
            }
        }catch(IOException ioe){ioe.printStackTrace();}

    }

    public File getGeonamesCoordinatesFile() {
        return this.geonamesCoordinatesFile;
    }
    public File getCoordinateMonitoraggioFile() {
        return  this.coordinateMonitoraggioFile;
    }
    public File getCentroMonitoraggioFile(){return this.centroMonitoraggioFile;}
    public File getParametriClimaticiFile(){return this.parametriClimaticiFile;}
    public File getOperatoriAutorizzatiFile(){return this.operatoriAutorizzatiFile;}
    public File getOperatoriRegistratiFile(){return this.operatoriRegistratiFile;}
    public File getAreeInteresseFile(){return this.areeInteresseFile;}


    private HashMap<String, City> getGeonamesCache(){return this.geonamesCache;}
    private HashMap<String, CentroMonitoraggio> getCentroMonitoraggioCache(){return this.centroMonitoraggioCache;}
    private HashMap<String, Operatore> getOperatoreRegistratoCache(){return this.operatoreRegistratoCache;}
    private HashMap<String, Operatore> getOperatoreAutorizzatoCache(){return this.operatoreAutorizzatoCache;}
    private HashMap<String, AreaInteresse> getAreeInteresseCache(){return this.areeInteresseCache;}

    public boolean isDEBUG(){ return this.DEBUG;}

    private boolean readGeonameCache(Object o){
        //TODO:
        return false;
    }

    private boolean readCoordinateMonitoraggioCache(Object o){
        //TODO:
        return false;
    }

    //returns true if it already is present in the cache
    private boolean readCentroMonitoraggioCache(CentroMonitoraggio cm){
        return centroMonitoraggioCache.containsKey(cm.getCentroID());
    }

    private boolean readParamClimaticiCache(ClimateParameter cp){
        return climateParameterCache.containsKey(cp.getParameterID());
    }

    private boolean readAreaInteresseCache(AreaInteresse ai){
        return areeInteresseCache.containsKey(ai.getAreaID());
    }

    private boolean readOpAutorizzatiCache(Operatore o){
        return operatoreAutorizzatoCache.containsKey(o.getEmail()); //email? codFisc?
    }

    private boolean readOpRegistratiCache(Operatore o){
        return operatoreRegistratoCache.containsKey(o.getEmail()); //email? codFisc?
    }

    public HashMap<String, ?> readCache(String objClass){
        switch(objClass){
            case DBInterface.objClassCentroMonitoraggio -> {
                return this.centroMonitoraggioCache;
            }
            case DBInterface.objClassAreaInteresse -> {
                return this.areeInteresseCache;
            }
            default -> throw new IllegalArgumentException(DBInterface.error_invalid_class);
        }
    }

    //objClass -> the class of the object to check
    //obj -> Object to check
    //returns false if the object has been written to the file
    //returns true if the object was present in the file
    private boolean checkCache(String objClass, Object o) {
        switch (objClass) {
            case DBInterface.objClassCentroMonitoraggio -> {
                //if not true -> write it
                return !readCentroMonitoraggioCache((CentroMonitoraggio) o);
            }
            case DBInterface.objClassParamClimatici -> {
                return !readParamClimaticiCache((ClimateParameter) o);
            }
            case DBInterface.objClassAreaInteresse -> {
                return !readAreaInteresseCache((AreaInteresse) o);
            }
            case DBInterface.objClassOpAutorizzati -> {
                return !readOpAutorizzatiCache((Operatore) o);
            }
            case DBInterface.objClassOpRegistrati -> {
                return !readOpRegistratiCache((Operatore) o);
            }
            default -> throw new IllegalArgumentException(error_invalid_class);
        }
    }

    public List<AreaInteresse> getAreeInteresseWithKey(List<String> keys){
        List<AreaInteresse> aree = new LinkedList<AreaInteresse>();
        keys.forEach((key) -> {
            if(areeInteresseCache.containsKey(key)) aree.add(areeInteresseCache.get(key));
        });
        return aree;
    }

    public void write(Object o){
        //TODO: in base alla stringa passata, chiama il metodo writer corrispondente
        boolean res;
        if(o instanceof AreaInteresse){
            res = checkCache(DBInterface.objClassAreaInteresse, o);
            if(res) this.writerREF.writeAreaInteresse((AreaInteresse) o);
            else System.out.println("Cache already contains object");
        }else if(o instanceof CentroMonitoraggio){
            res = checkCache(DBInterface.objClassAreaInteresse, o);
            if(res) this.writerREF.writeCentroMonitoraggio((CentroMonitoraggio) o);
            else System.out.println("Cache already contains object");
        }else if(o instanceof ClimateParameter){
            res = checkCache(DBInterface.objClassParamClimatici, o);
            if(res) this.writerREF.writeParametroClimatico((ClimateParameter) o);
            else System.out.println("Cache already contains object");
        }else if(o instanceof Operatore){
            if(o instanceof OperatoreAutorizzato){
                res = checkCache(DBInterface.objClassOpAutorizzati, o);
                if(res) this.writerREF.writeOperatoreAutorizzato((OperatoreAutorizzato) o);
                else System.out.println("Cache already contains object");
            }else if(o instanceof  OperatoreRegistrato){
                res = checkCache(DBInterface.objClassOpRegistrati, o);
                if(res) this.writerREF.writeOperatoreRegistrato((OperatoreRegistrato) o);
                else System.out.println("Cache already contains object");
            }
        }
    }

    public void writeObjects(HashMap<String, Object> objs){
        //TODO: determine objects class at compile time and call appropriate method
    }

    public HashMap<String, ?> read(String objClass) {
        //TODO: in base alla stringa passata, chiama il metodo reader corrispondente }
        return switch (objClass) {
            case DBInterface.objClassAreaInteresse -> this.readerREF.readAreeInteresseFile();
            case DBInterface.geonamesAndCoordinatesDati -> this.readerREF.readGeonamesAndCoordinatesFile();
            case DBInterface.objClassOpRegistrati -> this.readerREF.readOperatoriRegistratiFile();
            case DBInterface.objClassOpAutorizzati-> this.readerREF.readOperatoriAutorizzatiFile();
            case DBInterface.objClassParamClimatici-> this.readerREF.readClimateParametersFile();
            case DBInterface.objClassCentroMonitoraggio -> this.readerREF.readCentroMonitoraggiFile();
            default -> throw new IllegalArgumentException("invalid argument");
        };
    }

    public <T> Pair<String, T>readLast(T objClass){
        //TODO:
        return null;
    }
}
