package it.uninsubria.climatemonitoring.dbref.readerDB;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import it.uninsubria.climatemonitoring.city.City;
import it.uninsubria.climatemonitoring.dbref.DBInterface;
import javafx.util.Pair;

public class ReaderDB {

    private static ReaderDB readerInstance;

    private final DBInterface dbRef;

    public static ReaderDB getInstance(){
        if(readerInstance == null) readerInstance = new ReaderDB();
        return readerInstance;
    }
    private ReaderDB(){
        this.dbRef = DBInterface.getInstance();
    }

    public HashMap<String, City> readGeonamesAndCoordinatesFile(){
        HashMap<String, City> res = new HashMap<String, City>();
        try{
            BufferedReader bReader = new BufferedReader(new FileReader(dbRef.getGeonamesCoordinatesFile()));
            String line = bReader.readLine(); //read first line
            while((line = bReader.readLine()) != null){
                Pair<String, City> tmp = parseGeoname(line);
                res.put(tmp.getKey(), tmp.getValue());
            }
            bReader.close();
        }catch(IOException ioe){ioe.printStackTrace();}
        return res;
    }


    private Pair<String, City> parseGeoname(String line){
        String generalRegex = ";";
        String coordsRegex = ",";
        String[] res = line.split(generalRegex);
        String[] coordsTmp = res[5].split(coordsRegex);
        City c = new City(res[0], res[2], res[3], res[4],
                Float.parseFloat(coordsTmp[0]), Float.parseFloat(coordsTmp[1]));
        return new Pair<String, City>(res[0], c);
    }

}
