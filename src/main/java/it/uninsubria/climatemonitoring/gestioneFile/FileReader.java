package it.uninsubria.climatemonitoring.gestioneFile;

import java.io.*;
import java.util.LinkedList;

import it.uninsubria.climatemonitoring.dati.AreaInteresse;

/**
 * Rappresenta l'interfaccia per leggere da file.
 *
 * @author Mattia Mauro Lunardi 736898 mmlunardi@studenti.uninsubria.it VA
 * @author Andrea Quaglia 753166 aquaglia2@studenti.uninsubria.it VA
 */
public class FileReader {
    /**
     * Interfaccia che gestisce la lettura e scrittura su file.
     */
    private final FileInterface fileInterface;

    /**
     * Crea l'interfaccia per leggere da file.
     *
     * @param fileInterface interfaccia che gestisce la lettura e la scrittura da file.
     */
    public FileReader(FileInterface fileInterface){
        this.fileInterface = fileInterface;
    }

    /**
     * Crea una {@code LinkedList<AreaInteresse>} contenente il contenuto del file geonames-and-coordinates.csv
     * che servira' come cache.
     *
     * @return HashMap contenente l'elenco delle aree d'interesse salvate sul file geonames-and-coordinates.csv
     * @throws IOException non è stato trovato il file geonames-and-coordinates.csv
     */
    public LinkedList<AreaInteresse> readGeonamesAndCoordinatesFile() throws IOException {
        LinkedList<AreaInteresse> res = new LinkedList<>();
        BufferedReader bReader = new BufferedReader(new java.io.FileReader(fileInterface.getGeonamesCoordinatesFile()));
        bReader.readLine();
        String line;
        while((line = bReader.readLine()) != null)
            res.add(parseGeoname(line));
        bReader.close();
        return res;
    }

    /**
     * Legge il file contenente le email degli operatori autorizzati a registrarsi.
     *
     * @return {@code LinkedList<String>} contenente l'elenco delle email degli operatori autorizzati a registrarsi
     * salvati sul file OperatoriAutorizzati.dati
     * @throws IOException non è stato possibile creare il file OperatoriAutorizzati.dati
     */
    public LinkedList<String> readOperatoriAutorizzatiFile() throws IOException {
        LinkedList<String> list = new LinkedList<>();
        BufferedReader br = new BufferedReader(new java.io.FileReader(fileInterface.getOperatoriAutorizzatiFile()));
        String line;
        while ((line = br.readLine()) != null)
            list.add(line);
        br.close();
        return list;
    }

    /**
     * Deserializza un oggetto da un file.
     *
     * @param fileName il percorso del file da cui deserializzare.
     * @return un Object contenente la {@code LinkedList<>} deserializzata dal file.
     * @throws IOException il percorso del file passato come argomento e' errato.
     * @throws ClassNotFoundException il contenuto del file non rappresenta una {@code LinkedList<>}.
     */
    public Object serializeFileIn(String fileName) throws IOException, ClassNotFoundException {
        if(new File(fileName).length() == 0)
            return new LinkedList<>();
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
        return ois.readObject();
    }

    private AreaInteresse parseGeoname(String line) {
        String generalRegex = ";";
        String coordinatesRegex = ",";
        String[] res = line.split(generalRegex);
        String[] coordinatesTmp = res[5].split(coordinatesRegex);
        return new AreaInteresse(res[0], res[2], res[3], res[4],
                Float.parseFloat(coordinatesTmp[0]), Float.parseFloat(coordinatesTmp[1]));
    }
}