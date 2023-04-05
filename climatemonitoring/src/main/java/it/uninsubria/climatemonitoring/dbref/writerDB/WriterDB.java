package it.uninsubria.climatemonitoring.dbref.writerDB;

import it.uninsubria.climatemonitoring.areaInteresse.AreaInteresse;
import it.uninsubria.climatemonitoring.centroMonitoraggio.CentroMonitoraggio;
import it.uninsubria.climatemonitoring.city.City;
import it.uninsubria.climatemonitoring.climateParameters.ClimateParameter;
import it.uninsubria.climatemonitoring.dbref.DBInterface;
import it.uninsubria.climatemonitoring.operatore.opeatoreAutorizzato.OperatoreAutorizzato;
import it.uninsubria.climatemonitoring.operatore.opeatoreRegistrato.OperatoreRegistrato;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class WriterDB {

    private final DBInterface dbRef;

    public WriterDB(DBInterface dbRef){
        this.dbRef = dbRef;
    }

    public boolean writeCentroMonitoraggio(CentroMonitoraggio c){
        //TODO:
    }

    public boolean writeOperatoreRegistrato(OperatoreRegistrato o){
        //TODO:
    }

    public boolean writeOperatoreAutorizzato(OperatoreAutorizzato o){
        //TODO:
    }

    public boolean writeParametroClimatico(ClimateParameter c){
        //TODO:
    }

    public boolean writeAreaInteresse(AreaInteresse a){
        //TODO:
    }

    public boolean writeCoordinateMonitoraggioCache(HashMap<String, City> cache){
        //TODO:
    }

    public void writeGeonamesAndCoordinates(HashMap<String, City> cache){
        StringBuilder builder = new StringBuilder();
        if(dbRef.isDEBUG()) System.out.println("Writing geoname cache to file");
        try{
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(dbRef.getCoordinateMonitoraggioFile()));
            cache.forEach(
                    (key, value) -> {
                        builder.append(value.toString()).append("\n");
                        if(dbRef.isDEBUG()) System.out.println(builder.toString());
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
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(dbRef.getCoordinateMonitoraggioFile()));
            cache.forEach(
                    (key, value) -> {
                        builder.append(value);
                    }
            );
            bWriter.close();
        }catch(IOException ioe){ioe.printStackTrace();}
    }


}

