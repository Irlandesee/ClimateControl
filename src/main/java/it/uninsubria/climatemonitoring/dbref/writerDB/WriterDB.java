package it.uninsubria.climatemonitoring.dbref.writerDB;

import it.uninsubria.climatemonitoring.city.City;
import it.uninsubria.climatemonitoring.dbref.DBInterface;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @author : Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * @author : Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 **/
public class WriterDB {
    private final DBInterface dbRef;

    /**
     *
     * @param dbRef
     */
    public WriterDB(DBInterface dbRef){
        this.dbRef = dbRef;
    }

    /**
     *
     * @param cache copia in memoria del file geonames-and-coordinates.csv
     * @throws IOException il file non esiste
     */
    public void writeCoordinateMonitoraggioFile(HashMap<String, City> cache) throws IOException {
        if(!isFileEmpty()) return;
        writeFile(cache);
        debug("Copia della cache coordinate monitoraggio nel file coordinateMonitoraggio.dati...");
    }

    private void writeFile(HashMap<String, City> cache) throws IOException {
        PrintWriter fout = new PrintWriter(new BufferedWriter(new FileWriter(dbRef.getCoordinateMonitoraggioFile(), true)));
        cache.forEach(
                (key, value) -> {
                    fout.println(value);
                    fout.flush();
                });
        fout.close();
    }

    private void debug(String s) {
        if(dbRef.isDEBUG()) System.out.println(s);
    }

    private boolean isFileEmpty() {
        int headerLength = 64;
        return dbRef.getCoordinateMonitoraggioFile().length() <= headerLength;
    }
}

