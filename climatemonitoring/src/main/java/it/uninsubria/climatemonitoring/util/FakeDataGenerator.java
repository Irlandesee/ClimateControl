package it.uninsubria.climatemonitoring.util;
import it.uninsubria.climatemonitoring.areaInteresse.AreaInteresse;
import it.uninsubria.climatemonitoring.centroMonitoraggio.CentroMonitoraggio;
import it.uninsubria.climatemonitoring.city.City;
import it.uninsubria.climatemonitoring.climateParameters.ClimateParameter;
import it.uninsubria.climatemonitoring.dbref.DBInterface;
import it.uninsubria.climatemonitoring.operatore.Operatore;
import it.uninsubria.climatemonitoring.operatore.opeatoreAutorizzato.OperatoreAutorizzato;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

public class FakeDataGenerator {
    private DBInterface dbRef;
    public FakeDataGenerator(){

    }

    public FakeDataGenerator(DBInterface dbRef){
        this.dbRef = dbRef;
    }

    public List<AreaInteresse> generateAreeInteresse(final int limit){
        //TODO
        HashMap<String, City> tmp = (HashMap<String, City>) dbRef.read(DBInterface.objCities);
        List<City> cities = new LinkedList<City>();
        for(Map.Entry<String, City> entry : tmp.entrySet())
            cities.add(entry.getValue());
        LinkedList<AreaInteresse> v= new LinkedList<AreaInteresse>();
        Random rand = new Random();
        for(int i = 0; i < limit; i++){
            int index = rand.nextInt(cities.size());
            City c = cities.get(index);
            AreaInteresse ai =
                    new AreaInteresse(IDGenerator.generateID(), c.getAsciiName(), c.getCountry(), c.getLatitude(), c.getLongitude());
            v.add(ai);
        }
        return v;
    }

    public List<CentroMonitoraggio> generateCentroMonitoraggio(final int limit){
        //TODO
        HashMap<String, City> citiesMap = (HashMap<String, City>) dbRef.read(DBInterface.objCities);
        HashMap<String, AreaInteresse> areeInteresseMap = (HashMap<String, AreaInteresse>) dbRef.read(DBInterface.objClassAreaInteresse);
        List<AreaInteresse> areeInteresse = new LinkedList<AreaInteresse>(areeInteresseMap.values());
        List<City> cities = new LinkedList<City>(citiesMap.values());
        Random rand = new Random();
        List<CentroMonitoraggio> res = new LinkedList<CentroMonitoraggio>();
        for(int i = 0; i < limit; i++){
            String centroID = IDGenerator.generateID();
            City c = cities.get(rand.nextInt(cities.size()));
            String comune = c.getAsciiName();
            String nomeCentro = comune + "Centro";
            String country = c.getCountry();
            int numAree = rand.nextInt(1, 6);
            CentroMonitoraggio cm = new CentroMonitoraggio(centroID, nomeCentro, comune, country);
            for(int j = 0; j < numAree; j++){
                cm.addAreaInteresse(areeInteresse.get(rand.nextInt(areeInteresse.size())));
            }
            res.add(cm);
        }
        return res;
    }

    public List<OperatoreAutorizzato> generatoreOpAutorizzati(final int limit){
        //TODO
        List<OperatoreAutorizzato> res = new LinkedList<OperatoreAutorizzato>();
        Random rand = new Random();
        String[] maleNames = {
                "Leonardo",
                "Francesco",
                "Alessandro",
                "Lorenzo",
                "Mattia",
                "Andrea",
                "Tommaso",
                "Gabriele",
                "Riccardo",
                "Edoardo",
                "Matteo"
        };
        String[] femaleNames = {
                "Sofia",
                "Nicole",
                "Giorgia",
                "Andrea",
                "Francesca",
                "Giulia",
                "Ginevra",
                "Alice",
                "Emma",
                "Caterina"
        };
        String[] surnames = {
                "Rossi",
                "Russo",
                "Ferrari",
                "Bianchi",
                "Ricci",
                "Brambilla",
                "Colombo",
                "Lombardi",
                "Greco",
                "Mancini"
        };
        for(int i = 0; i < limit; i++){
            int maleOrFemale = rand.nextInt(limit);
            int surnameIndex = rand.nextInt(limit);
            if(maleOrFemale % 2 == 0) {//male
                int maleIndex = rand.nextInt(limit);
                String maleName = maleNames[maleIndex];
                String surname = surnames[surnameIndex];
                String codFisc = maleName + surname + maleOrFemale;
                String mail = maleName + surname + "@gmail.com";
                res.add(new OperatoreAutorizzato(maleName, surname, codFisc, mail));
            }
            else{ //female
                int femaleIndex = rand.nextInt(limit);
                String femaleName = femaleNames[femaleIndex];
                String surname = surnames[surnameIndex];
                String codFisc = femaleName+ surname + maleOrFemale;
                String mail = femaleName + surname + "@gmail.com";
                res.add(new OperatoreAutorizzato(femaleName, surname, codFisc, mail));
            }
        }


        return res;
    }

    public List<ClimateParameter> generateClimateParameters(final int limit){
        HashMap<String, CentroMonitoraggio> centroMonitoraggioMap =
                (HashMap<String, CentroMonitoraggio>) dbRef.readCache(DBInterface.objClassCentroMonitoraggio);
        HashMap<String, AreaInteresse> areaInteresseMap = (HashMap<String, AreaInteresse>)
                dbRef.readCache(DBInterface.objClassAreaInteresse);
        List<CentroMonitoraggio> cms = new LinkedList<CentroMonitoraggio>(centroMonitoraggioMap.values());
        List<AreaInteresse> ais = new LinkedList<AreaInteresse>(areaInteresseMap.values());
        List<ClimateParameter> climateParameters = new LinkedList<ClimateParameter>();
        String[] possibileVentoNotes = {"forte", "debole", "brezza"};
        String[] possibileUmiditaNotes = {"umido", "bagnato", "secco"};
        String[] possiblePressione = {"alta", "bassa"};
        String[] possibleTempNotes = {"caldo", "freddo", "chill"};
        String[] possibleAltGhiacciaiNotes = {"alto", "bassi"};
        String[] posssibleMassaGhiacciaiNotes = {"grossi", "poca roba"};

        Random rand = new Random();
        for(int i = 0; i < limit; i++){
            String parameterID = IDGenerator.generateID();
            String cmID = cms.get(rand.nextInt(cms.size())).getCentroID();
            String aiID = ais.get(rand.nextInt(ais.size())).getAreaID();
            //Generating random climate values
            //params -> vento, umidita, presisone, temp, altGhiacciai, massaGhiacciai
            short vento = (short)rand.nextInt(1, 6);
            short umidita = (short)rand.nextInt(1, 6);
            short pressione = (short)rand.nextInt(1, 6);
            short temp = (short)rand.nextInt(1, 6);
            short altGhiacciai = (short)rand.nextInt(1, 6);
            short massaGhiacciai = (short)rand.nextInt(1, 6);
            //generating random notes with length of 256 chars
            String ventoNotes = possibileVentoNotes[rand.nextInt(0, possibileVentoNotes.length)];
            String umiditaNotes = possibileUmiditaNotes[rand.nextInt(0, possibileUmiditaNotes.length)];
            String pressioneNotes = possiblePressione[rand.nextInt(0, possiblePressione.length)];
            String tempNotes = possibleTempNotes[rand.nextInt(0, possibleTempNotes.length)];
            String altGhiacciaiNotes = possibleAltGhiacciaiNotes[rand.nextInt(0, possibleAltGhiacciaiNotes.length)];
            String massaGhiacciaiNotes = posssibleMassaGhiacciaiNotes[rand.nextInt(0, posssibleMassaGhiacciaiNotes.length)];


            //Generating random date
            while(true){
                int day = rand.nextInt(1, 32);
                int month = rand.nextInt(1, 13);
                int year = rand.nextInt(1980, 2023);
                try{
                    LocalDate date = LocalDate.of(year, month, day);
                    ClimateParameter cp = new ClimateParameter(
                            parameterID,
                            cmID,
                            aiID,
                            date
                    );
                    cp.addParameter(ClimateParameter.paramVento, vento);
                    cp.addParameter(ClimateParameter.paramUmidita, umidita);
                    cp.addParameter(ClimateParameter.paramPressione, pressione);
                    cp.addParameter(ClimateParameter.paramTemp, temp);
                    cp.addParameter(ClimateParameter.paramAltGhiacciai, altGhiacciai);
                    cp.addParameter(ClimateParameter.paramMassaGhiacciai, massaGhiacciai);
                    cp.setNotes(ClimateParameter.notaVento, ventoNotes);
                    cp.setNotes(ClimateParameter.notaUmidita, umiditaNotes);
                    cp.setNotes(ClimateParameter.notaPressione, pressioneNotes);
                    cp.setNotes(ClimateParameter.notaTemp, tempNotes);
                    cp.setNotes(ClimateParameter.noteAltGhiacciai, altGhiacciaiNotes);
                    cp.setNotes(ClimateParameter.noteMassaGhiacciai, massaGhiacciaiNotes);
                    climateParameters.add(cp);
                    break;
                }catch(DateTimeException dte){dte.printStackTrace();}
            }
        }
        return climateParameters;
    }

}
