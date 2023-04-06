package it.uninsubria.climatemonitoring.gestioneFile;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

import it.uninsubria.climatemonitoring.AreaInteresse;
import it.uninsubria.climatemonitoring.CentroMonitoraggio;
import it.uninsubria.climatemonitoring.Indirizzo;
import it.uninsubria.climatemonitoring.Operatore;
import javafx.util.Pair;

/**
 * @author : Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * @author : Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 **/
public class ReaderDB {
    private final DBInterface dbRef;

    /**
     *
     * @param dbRef
     */
    public ReaderDB(DBInterface dbRef){
        this.dbRef = dbRef;
    }

    /**
     *
     * @return HashMap contenente l'elenco delle aree d'interesse salvate sul file geonames-and-coordinates.csv
     * @throws IOException non è stato trovato il file geonames-and-coordinates.csv
     */
    public LinkedList<AreaInteresse> readGeonamesAndCoordinatesFile() throws IOException {
        LinkedList<AreaInteresse> res = new LinkedList<>();
        BufferedReader bReader = new BufferedReader(new FileReader(dbRef.getGeonamesCoordinatesFile()));
        bReader.readLine();
        String line;
        while((line = bReader.readLine()) != null)
            res.add(parseGeoname(line));
        bReader.close();
        return res;
    }

    /**
     *
     * @return HashMap contenente l'elenco degli operatori autorizzati salvati sul file OperatoriAutorizzati.dati
     * @throws IOException non è stato possibile creare il file OperatoriAutorizzati.dati
     */
    public LinkedList<String> readOperatoriAutorizzatiFile() throws IOException {
        LinkedList<String> list = new LinkedList<>();
        BufferedReader br = new BufferedReader(new FileReader(dbRef.getOperatoriAutorizzatiFile()));
        String line;
        while ((line = br.readLine()) != null)
            list.add(line);
        br.close();
        return list;
    }

    private AreaInteresse parseGeoname(String line) {
        String generalRegex = ";";
        String coordinatesRegex = ",";
        String[] res = line.split(generalRegex);
        String[] coordinatesTmp = res[5].split(coordinatesRegex);
        return new AreaInteresse(res[0], res[2], res[3], res[4],
                Float.parseFloat(coordinatesTmp[0]), Float.parseFloat(coordinatesTmp[1]));
    }

    public Object serializeFileIn(String fileName) throws IOException, ClassNotFoundException {
        if(new File(fileName).length() == 0)
            return new LinkedList<>();
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
        return ois.readObject();
    }
}