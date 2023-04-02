package com.climatemonitoring.climatemonitoring.dbref.writerDB;

import com.climatemonitoring.climatemonitoring.city.City;
import com.climatemonitoring.climatemonitoring.dbref.DBInterface;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class WriterDB {


    private final DBInterface dbRef;


    public WriterDB(DBInterface dbRef){
        this.dbRef = dbRef;
    }

    public void writeGeonamesAndCoordinates(HashMap<String, City> cache){
        StringBuilder builder = new StringBuilder();
        if(dbRef.isDEBUG()) System.out.println("Writing geoname cache to file");
        try{
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(dbRef.getGeonamesCoordinatesFile()));
            cache.forEach(
                    (key, value) -> {
                        builder.append(key).append(value.toString());
                        try{
                            bWriter.write(builder.toString());
                        }catch(IOException ioe2){ioe2.printStackTrace();}
                        builder.setLength(0); //clear the string builder
                    }
            );

            bWriter.close();
        }catch(IOException ioe){ioe.printStackTrace();}

    }

    public void writeCoordinateMonitoraggioFile(HashMap<String, City> cache) {
        //Se il file non è vuoto (ha una lunghezza > 64 cioè l'intestazione) non riempirlo con i dati del file
        //geonames-and-coordinates.csv
        if(dbRef.getCoordinateMonitoraggioFile().length() > 64) return;

        StringBuilder builder = new StringBuilder();
        if(dbRef.isDEBUG()) System.out.println("Writing coordinate monitoraggio cache to coordinateMonitoraggio.dati...");
        try{
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(dbRef.getCoordinateMonitoraggioFile(), true)));
            cache.forEach(
                    (key, value) -> {
                        builder.append(value.toString());
                        out.println(builder);
                        out.flush();
                        builder.setLength(0); //clear the string builder
                    }
            );
            out.close();
        }catch(IOException ioe){ioe.printStackTrace();}
    }
}

