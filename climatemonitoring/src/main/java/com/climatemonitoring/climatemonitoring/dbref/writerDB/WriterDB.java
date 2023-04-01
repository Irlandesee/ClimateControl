package com.climatemonitoring.climatemonitoring.dbref.writerDB;

import com.climatemonitoring.climatemonitoring.city.City;
import com.climatemonitoring.climatemonitoring.dbref.DBInterface;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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


}

