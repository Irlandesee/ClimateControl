package it.uninsubria.climatemonitoring.dbref;

import it.uninsubria.climatemonitoring.city.City;
import it.uninsubria.climatemonitoring.dbref.readerDB.ReaderDB;
import it.uninsubria.climatemonitoring.dbref.writerDB.WriterDB;

import java.io.*;
import java.util.HashMap;

public class DBInterface {

    private final String geonamesAndCoordinatesDati = "data/geonames-and-coordinates.csv";
    private final String centroMonitoraggioDati = "data/centroMonitoraggio.dati";
    private final String coordinateMonitoraggioDati = "data/coordinateMonitoraggio.dati";


//    private final String geonamesAndCoordinatesDati = "climatemonitoring/data/geonames-and-coordinates.csv";
//    private final String centroMonitoraggioDati = "climatemonitoring/data/centroMonitoraggio.dati";
//    private final String coordinateMonitoraggioDati = "climatemonitoring/data/coordinateMonitoraggio.dati";


    private final File geonamesCoordinatesFile;
    private final File centroMonitoraggioFile;
    private final File coordinateMonitoraggioFile;

    private final boolean DEBUG = true;

    private HashMap<String, City> geonamesDBCache;

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

    private HashMap<String, City> getGeonamesDBCache(){return this.geonamesDBCache;}

    public boolean isDEBUG(){ return this.DEBUG;}

    public HashMap<String, City> readGeonamesFile(){ return readerREF.readGeonamesAndCoordinatesFile();}

    public void writeCoordinateMonitoraggioFile(HashMap<String, City> cache) {
        writerREF.writeCoordinateMonitoraggioFile(cache);
    }

    public void writeGeonamesAndCoordinates(HashMap<String, City> cache){
        writerREF.writeGeonamesAndCoordinates(cache);
    }
}
