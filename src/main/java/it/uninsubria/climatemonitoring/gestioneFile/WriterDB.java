package it.uninsubria.climatemonitoring.gestioneFile;

import it.uninsubria.climatemonitoring.AreaInteresse;
import it.uninsubria.climatemonitoring.Operatore;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

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
    public void writeCoordinateMonitoraggioFile(HashMap<String, AreaInteresse> cache) throws IOException {
        if(!isFileEmpty()) return;
        writeFile(cache);
        debug("Copia della cache coordinate monitoraggio nel file CoordinateMonitoraggio.dati...");
    }

    private void writeFile(HashMap<String, AreaInteresse> cache) throws IOException {
        PrintWriter fout = new PrintWriter(new BufferedWriter(new FileWriter(dbRef.getCoordinateMonitoraggioFile(), true)));
        cache.forEach(
                (key, value) -> {
                    fout.println(value);
                    fout.flush();
                });
        fout.close();
    }

    public void serializeFileOut(Object object, String fileName) throws IOException {
        ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(fileName));
        write.writeObject(object);
    }

    private void debug(String s) {
        if(dbRef.isDEBUG()) System.out.println(s);
    }

    private boolean isFileEmpty() {
        int headerLength = 64;
        return dbRef.getCoordinateMonitoraggioFile().length() <= headerLength;
    }
}

