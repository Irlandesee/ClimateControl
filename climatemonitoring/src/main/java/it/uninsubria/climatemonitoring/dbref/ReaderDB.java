package it.uninsubria.climatemonitoring.dbref;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import it.uninsubria.climatemonitoring.areaInteresse.AreaInteresse;
import it.uninsubria.climatemonitoring.centroMonitoraggio.CentroMonitoraggio;
import it.uninsubria.climatemonitoring.city.City;
import it.uninsubria.climatemonitoring.climateParameters.ClimateParameter;
import it.uninsubria.climatemonitoring.operatore.Operatore;
import it.uninsubria.climatemonitoring.operatore.opeatoreAutorizzato.OperatoreAutorizzato;
import it.uninsubria.climatemonitoring.operatore.opeatoreRegistrato.OperatoreRegistrato;

import org.javatuples.Pair;

public class ReaderDB {

    private static final String incorrect_file_name_error = "Incorrect file name";
    public static final String generalSeparator = ";";
    public static final String secondarySeparator = ",";
    private final DBInterface dbRef;

    public ReaderDB(DBInterface dbRef){
        this.dbRef = dbRef;
    }

    public HashMap<String, CentroMonitoraggio> readCentroMonitoraggiFile(){
        HashMap<String, CentroMonitoraggio> res = new HashMap<String, CentroMonitoraggio>();
        try{
            String line;
            BufferedReader bReader = new BufferedReader(new FileReader(dbRef.getCentroMonitoraggioFile()));
            while((line = bReader.readLine()) != null){
                Pair<String, CentroMonitoraggio> tmp = parseCentroMonitoraggio(line);
                res.put(tmp.getValue0(), tmp.getValue1());
            }
        }catch(IOException ioe){ioe.printStackTrace();}
        return res;
    }

    public HashMap<String, Operatore> readOperatoriRegistratiFile(){
        HashMap<String, Operatore> res = new HashMap<String, Operatore>();
        try{
            String line;
            BufferedReader bReader = new BufferedReader(new FileReader(dbRef.getOperatoriRegistratiFile()));
            while((line = bReader.readLine()) != null){
                Pair<String, Operatore> tmp = parseOperatoreRegistrato(line);
                res.put(tmp.getValue0(), tmp.getValue1());
            }
        }catch(IOException ioe){ioe.printStackTrace();}
        return res;
    }

    public HashMap<String, Operatore> readOperatoriAutorizzatiFile(){
        HashMap<String, Operatore> res = new HashMap<String, Operatore>();
        try{
            String line;
            BufferedReader bReader = new BufferedReader(new FileReader(dbRef.getOperatoriAutorizzatiFile()));
            while((line = bReader.readLine()) != null){
                Pair<String, Operatore> tmp = parseOperatoreAutorizzato(line);
                res.put(tmp.getValue0(), tmp.getValue1());
            }
        }catch(IOException ioe){ioe.printStackTrace();}
        return res;
    }

    public HashMap<String, ClimateParameter> readClimateParametersFile(){
        HashMap<String, ClimateParameter> res = new HashMap<String, ClimateParameter>();
        try{
            BufferedReader bReader = new BufferedReader(new FileReader(dbRef.getParametriClimaticiFile()));
            String line = bReader.readLine(); //reading headers
            System.out.println(line);
            while((line = bReader.readLine()) != null){
                Pair<String, ClimateParameter> tmp = parseParametroClimatico(line);
                res.put(tmp.getValue0(), tmp.getValue1());
            }
        }catch(IOException ioe){ioe.printStackTrace();}
        return res;
    }

    public HashMap<String, AreaInteresse> readAreeInteresseFile(){
        HashMap<String, AreaInteresse> res = new HashMap<String, AreaInteresse>();
        try{
            String line;
            BufferedReader bReader = new BufferedReader(new FileReader(dbRef.getAreeInteresseFile()));
            while((line = bReader.readLine()) != null){
                Pair<String, AreaInteresse> tmp = parseAreaInteresse(line);
                res.put(tmp.getValue0(), tmp.getValue1());
            }
        }catch(IOException ioe){ioe.printStackTrace();}
        return res;
    }


    public HashMap<String, City> readGeonamesAndCoordinatesFile(){
        HashMap<String, City> res = new HashMap<String, City>();
        try{
            BufferedReader bReader = new BufferedReader(new FileReader(dbRef.getGeonamesCoordinatesFile()));
            String line = bReader.readLine(); //read first line
            while((line = bReader.readLine()) != null){
                Pair<String, City> tmp = parseGeoname(line);
                res.put(tmp.getValue0(), tmp.getValue1());
            }
            bReader.close();
        }catch(IOException ioe){ioe.printStackTrace();}
        return res;
    }

    private Pair<String, ?> parseLine(String line, String filename){
        return switch (filename) {
            case "centroMonitoraggio.dati" -> parseCentroMonitoraggio(line);
            case "areeInteresse.dati" -> parseAreaInteresse(line);
            case "coordinateMonitoraggio.dati" -> parseCoordinataMonitoraggio(line);
            case "operatoriAutorizzati.dati" -> parseOperatoreAutorizzato(line);
            case "operatoriRegistrati.dati" -> parseOperatoreRegistrato(line);
            case "parametriClimatici.dati" -> parseParametroClimatico(line);
            case "geonames-and-coordinates.csv" -> parseGeoname(line);
            default -> throw new IllegalArgumentException(ReaderDB.incorrect_file_name_error + filename + "\n");
        };
    }

    //pos 0 -> centro id
    //pos 6 -> provincia
    //pos 7+ -> area interesse1 etc...
    //aree interesse -> keys for already existing AreaInteresse
    private Pair<String, CentroMonitoraggio> parseCentroMonitoraggio(String line){
        String[] tmp = line.split(ReaderDB.generalSeparator);
        String[] areeInteresseArray = tmp[7].split(ReaderDB.secondarySeparator);
        List<String> keys = Arrays.stream(areeInteresseArray).toList();
        List<AreaInteresse> aree = dbRef.getAreeInteresseWithKey(keys);
        String centroID = tmp[0];
        CentroMonitoraggio c = new CentroMonitoraggio(centroID);
        try{
            c.setNomeCentro(tmp[1]);
            c.setVia(tmp[2]);
            c.setNumCivico(Short.parseShort(tmp[3]));
            c.setCap(Integer.parseInt(tmp[4]));
            c.setComune(tmp[5]);
            c.setProvincia(tmp[6]);
            aree.forEach(c::addAreaInteresse);
        }catch(NumberFormatException nfe){nfe.printStackTrace();}
        return new Pair<String, CentroMonitoraggio>(centroID, c);
    }

    private Pair<String, AreaInteresse> parseAreaInteresse(String line){
        String[] tmp = line.split(AreaInteresse.separatorArea);
        String[] coords = tmp[3].split(AreaInteresse.separatorCoords);
        String areaID = tmp[0];
        AreaInteresse a = new AreaInteresse(areaID);
        try{
            a.setDenominazione(tmp[1]);
            a.setStato(tmp[2]);
            a.setLatitude(Float.parseFloat(coords[0]));
            a.setLongitude(Float.parseFloat(coords[1]));
        }catch(NumberFormatException nfe){nfe.printStackTrace();}
        return new Pair<String, AreaInteresse>(areaID, a);
    }

    private Pair<String, City> parseCoordinataMonitoraggio(String line){
        //TODO:
        return null;
    }

    private Pair<String, Operatore> parseOperatoreAutorizzato(String line){
        String[] tmp = line.split(Operatore.generalSep);
        String codFiscale = tmp[2];
        Operatore o = new OperatoreAutorizzato(
            tmp[0], tmp[1], codFiscale, tmp[3]);
        return new Pair<String, Operatore>(codFiscale, o);
    }

    private Pair<String, Operatore> parseOperatoreRegistrato(String line){
        String[] tmp = line.split(Operatore.generalSep);
        String userID = tmp[0];
        Operatore o = new OperatoreRegistrato(
                tmp[2], tmp[3], tmp[4], tmp[5], userID, tmp[1], tmp[6]);
        return new Pair<String, Operatore>(userID, o);
    }

    private Pair<String, ClimateParameter> parseParametroClimatico(String line){
        System.out.println("line: "+ line);
        String[] tmp = line.split(ClimateParameter.generalSeparator);
        List<String> params=
                Arrays
                .stream(tmp[4].split(ClimateParameter.generalParamSeparator))
                .toList();
        List<Pair<String, Short>> keyWithParam = new LinkedList<Pair<String, Short>>();
        params.forEach((tuple) -> {
            String[] xs = tuple.split(ClimateParameter.paramKeySeparator);
            try{
                keyWithParam.add(new Pair<String, Short>(xs[0], Short.parseShort(xs[1])));
            }catch(NumberFormatException nfe){nfe.printStackTrace();}
        });
        String parameterID = tmp[0];
        ClimateParameter c = new ClimateParameter(parameterID);
        try{
            c.setIdCentro(tmp[1]);
            c.setAreaInteresse(tmp[2]);
            c.setPubDate(LocalDate.parse(tmp[3]));
            keyWithParam.forEach((tuple) -> c.addParameter(tuple.getValue0(), tuple.getValue1()));
            //c.setNotes(tmp[5]);
            LinkedList<Pair<String, String>> parsedNotes = parseNotes(tmp[5]);
            parsedNotes.forEach(tuple -> c.setNotes(tuple.getValue0(), tuple.getValue1()));
        }catch(NumberFormatException nfe){nfe.printStackTrace();}
        return new Pair<String, ClimateParameter>(parameterID, c);
    }

    private LinkedList<Pair<String, String>> parseNotes(String line){
        List<String> notes =
                Arrays
                .stream(line.split(ClimateParameter.generalParamSeparator))
                .toList();
        LinkedList<Pair<String, String>> res = new LinkedList<Pair<String, String>>();
        notes.forEach(
                note -> {
                    String[] xs = note.split(ClimateParameter.paramKeySeparator);
                    res.add(new Pair<String, String>(xs[0], xs[1]));
                }
        );
        return res;
    }

    private Pair<String, City> parseGeoname(String line){
        String[] res = line.split(ReaderDB.generalSeparator);
        String[] coordsTmp = res[5].split(ReaderDB.secondarySeparator);
        City c = new City(res[0], res[2], res[3], res[4],
                Float.parseFloat(coordsTmp[0]), Float.parseFloat(coordsTmp[1]));
        return new Pair<String, City>(res[0], c);
    }

}
