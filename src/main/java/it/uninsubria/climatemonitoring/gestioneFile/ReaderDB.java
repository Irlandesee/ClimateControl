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
     * @return HashMap contenente l'elenco delle aree di interesse salvate sul file geonames-and-coordinates.csv
     * @throws IOException non è stato trovato il file geonames-and-coordinates.csv
     */
    public HashMap<String, AreaInteresse> readGeonamesAndCoordinatesFile() throws IOException {
        HashMap<String, AreaInteresse> res = new HashMap<>();
        BufferedReader bReader = new BufferedReader(new FileReader(dbRef.getGeonamesCoordinatesFile()));
        bReader.readLine();
        String line;
        while((line = bReader.readLine()) != null){
            Pair<String, AreaInteresse> tmp = parseGeoname(line);
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
    public LinkedList<String> readOperatoriAutorizzatiFile() throws IOException {
        LinkedList<String> list = new LinkedList<>();
        BufferedReader br = new BufferedReader(new FileReader(dbRef.getOperatoriAutorizzatiFile()));
        br.readLine();
        String line;
        while ((line = br.readLine()) != null)
            list.add(line);
        br.close();
        return list;
    }

//    /**
//     *
//     * @return HashMap contenente l'elenco degli operatori registrati salvati sul file OperatoriRegistrati.dati
//     * @throws IOException non è stato possibile creare il file OperatoriRegistrati.dati
//     */
//    public HashMap<String, Operatore> readOperatoriRegistratiFile() throws IOException {
//        HashMap<String, Operatore> res = new HashMap<>();
//        BufferedReader br = new BufferedReader(new FileReader(dbRef.getOperatoriRegistratiFile()));
//        br.readLine();
//        String line;
//        while ((line = br.readLine()) != null) {
//            Pair<String, Operatore> tmp = parseOperatoreRegistrato(line);
//            res.put(tmp.getKey(), tmp.getValue());
//        }
//        br.close();
//        return res;
//    }

    public LinkedList<CentroMonitoraggio> readCentriMonitoraggioFile() throws IOException {
        LinkedList<CentroMonitoraggio> res = new LinkedList<>();
        BufferedReader br = new BufferedReader(new FileReader(dbRef.getCentriMonitoraggioFile()));
        br.readLine();
        String line;
        while ((line = br.readLine()) != null)
             res.add(parseCentroMonitoraggio(line));
        br.close();
        return res;
    }

    private Pair<String, AreaInteresse> parseGeoname(String line) {
        String generalRegex = ";";
        String coordsRegex = ",";
        String[] res = line.split(generalRegex);
        String[] coordsTmp = res[5].split(coordsRegex);
        AreaInteresse c = new AreaInteresse(res[0], res[2], res[3], res[4],
                Float.parseFloat(coordsTmp[0]), Float.parseFloat(coordsTmp[1]));
        return new Pair<>(res[0], c);
    }

    public Object serializeFileIn(String fileName) throws IOException, ClassNotFoundException {
        if(new File(fileName).length() == 0)
            return new LinkedList<>();
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
        return ois.readObject();
    }

    private Pair<String, Operatore> parseOperatoreAutorizzato(String line) {
        String generalRegex = ";";
        String[] res = line.split(generalRegex);
        Operatore operatore = new Operatore(res[0]);
        return new Pair<>(res[0], operatore);
    }

//    private Pair<String, Operatore> parseOperatoreRegistrato(String line) {
//        String generalRegex = ";";
//        String[] res = line.split(generalRegex);
//        Operatore operatore = new Operatore(res[0], res[1], res[2], res[3], res[4], res[5], res[6]);
//        return new Pair<>(res[3], operatore);
//    }

    private CentroMonitoraggio parseCentroMonitoraggio(String line) {
        String generalRegex = ";";
        String[] res = line.split(generalRegex);
        return new CentroMonitoraggio(res[1], new Indirizzo(res[2], res[3], res[4],
                Integer.parseInt(res[5]), Integer.parseInt(res[6])));
    }
}