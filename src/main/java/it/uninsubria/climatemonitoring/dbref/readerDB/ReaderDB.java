package it.uninsubria.climatemonitoring.dbref.readerDB;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import it.uninsubria.climatemonitoring.city.City;
import it.uninsubria.climatemonitoring.dbref.DBInterface;
import it.uninsubria.climatemonitoring.operatore.Operatore;
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
     * @return HashMap contenente l'elenco delle aree di interesse salvate sul file geonames-and-coordinates.csv
     * @throws IOException non è stato trovato il file geonames-and-coordinates.csv
     */
    public HashMap<String, City> readGeonamesAndCoordinatesFile() throws IOException {
        HashMap<String, City> res = new HashMap<>();
        BufferedReader bReader = new BufferedReader(new FileReader(dbRef.getGeonamesCoordinatesFile()));
        bReader.readLine();
        String line;
        while((line = bReader.readLine()) != null){
            Pair<String, City> tmp = parseGeoname(line);
            res.put(tmp.getKey(), tmp.getValue());
        }
        bReader.close();
        return res;
    }

    /**
     *
     * @return HashMap contenente l'elenco degli operatori autorizzati salvati sul file OperatoriAutorizzati.dati
     * @throws IOException non è stato possibile creare il file OperatoriAutorizzati.dati
     */
    public HashMap<String, Operatore> readOperatoriAutorizzatiFile() throws IOException {
        HashMap<String, Operatore> res = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(dbRef.getOperatoriAutorizzatiFile()));
        br.readLine();
        String line;
        while ((line = br.readLine()) != null) {
            Pair<String, Operatore> tmp = parseOperatoreAutorizzato(line);
            res.put(tmp.getKey(), tmp.getValue());
        }
        br.close();
        return res;
    }

    /**
     *
     * @return HashMap contenente l'elenco degli operatori registrati salvati sul file OperatoriRegistrati.dati
     * @throws IOException non è stato possibile creare il file OperatoriRegistrati.dati
     */
    public HashMap<String, Operatore> readOperatoriRegistratiFile() throws IOException {
        HashMap<String, Operatore> res = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(dbRef.getOperatoriRegistratiFile()));
        br.readLine();
        String line;
        while ((line = br.readLine()) != null) {
            Pair<String, Operatore> tmp = parseOperatoreRegistrato(line);
            res.put(tmp.getKey(), tmp.getValue());
        }
        br.close();
        return res;
    }

    private Pair<String, City> parseGeoname(String line){
        String generalRegex = ";";
        String coordsRegex = ",";
        String[] res = line.split(generalRegex);
        String[] coordsTmp = res[5].split(coordsRegex);
        City c = new City(res[0], res[2], res[3], res[4],
                Float.parseFloat(coordsTmp[0]), Float.parseFloat(coordsTmp[1]));
        return new Pair<>(res[0], c);
    }

    private Pair<String, Operatore> parseOperatoreAutorizzato(String line){
        String generalRegex = ";";
        String[] res = line.split(generalRegex);
        Operatore operatore = new Operatore(res[0]);
        return new Pair<>(res[0], operatore);
    }

    private Pair<String, Operatore> parseOperatoreRegistrato(String line){
        String generalRegex = ";";
        String[] res = line.split(generalRegex);
        Operatore operatore = new Operatore(res[0], res[1], res[2], res[3], res[4], res[5], res[6]);
        return new Pair<>(res[3], operatore);
    }
}