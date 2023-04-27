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

    public List<CentroMonitoraggio> generateCentroMonitorgaggio(final int limit){
        //TODO
        return null;
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
        //TODO
        return null;
    }

}
