package com.climatemonitoring.climatemonitoring.dbref;

import com.climatemonitoring.climatemonitoring.city.City;
import com.climatemonitoring.climatemonitoring.dbref.readerDB.ReaderDB;
import com.climatemonitoring.climatemonitoring.dbref.writerDB.WriterDB;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class DBInterface {

    private final String geonamesAndCoordinatesDati = "data/geonames-and-coordinates.csv";
    private final String centroMonitoraggioDati = "data/centroMonitoraggio.dati";


    private File geonamesCoordinatesFile;
    private File centroMonitoraggioFile;

    private final boolean DEBUG = true;

    private HashMap<String, City> geonamesDBCache;

    private final ReaderDB readerREF;
    private final WriterDB writerREF;

    public DBInterface(){
        geonamesCoordinatesFile = new File(geonamesAndCoordinatesDati);
        centroMonitoraggioFile = new File(centroMonitoraggioDati);

        this.readerREF = new ReaderDB(this);
        this.writerREF = new WriterDB(this);

        try{
            if(!geonamesCoordinatesFile.exists()){
                boolean res = geonamesCoordinatesFile.createNewFile();
                if(res && DEBUG) System.out.println("Created new file at: " + geonamesCoordinatesFile.getAbsolutePath());
            }
            if(!centroMonitoraggioFile.exists()){
                boolean res = centroMonitoraggioFile.createNewFile();
                if(res && DEBUG) System.out.println("Created new file at: " + centroMonitoraggioFile.getAbsolutePath());
            }
        }catch(IOException ioe){ioe.printStackTrace();}

    }

    public File getGeonamesCoordinatesFile() {
        return this.geonamesCoordinatesFile;
    }

    private HashMap<String, City> getGeonamesDBCache(){return this.geonamesDBCache;}

    public boolean isDEBUG(){ return this.DEBUG;}

    public HashMap<String, City> readGeonamesFile(){ return readerREF.readGeonamesAndCoordinatesFile();}
    public void writeGeonamesFile(HashMap<String, City> cache){writerREF.writeGeonamesAndCoordinates(cache);}


}
