package it.uninsubria.climatemonitoring.gestioneFile;

import java.io.*;

/**
 * Rappresenta l'interfaccia per scrivere su file.
 * @author <pre> Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 * </pre>
 **/
public class FileWriter {
    /**
     * Interfaccia che gestisce la lettura e scrittura su file.
     */
    private final FileInterface fileInterface;

    /**
     * Crea l'interfaccia per scrivere su file.
     * @param fileInterface interfaccia che gestisce la scrittura e lettura da file.
     */
    public FileWriter(FileInterface fileInterface){
        this.fileInterface = fileInterface;
    }

    /**
     * Popola il file geonames-and-coordinates.csv se vuoto con alcuni esempi.
     * @throws IOException il file geonames-and-coordinates.csv esiste.
     */
    public void writeGeonamesAndCoordinatesFile() throws IOException {
        File file = fileInterface.getGeonamesCoordinatesFile();
        if(file.length() > 64) return;
        PrintWriter fout = new PrintWriter(new BufferedWriter(new java.io.FileWriter(file)));
        fout.println("Geoname ID;Name;ASCII Name;Country Code;Country Name;Coordinates");
        fout.flush();
        //noinspection SpellCheckingInspection
        fout.println("""
                4946136;Oakham;Oakham;US;United States;42.35287, -72.04535
                4946372;Onset;Onset;US;United States;41.74177, -70.65781
                4947641;Plympton;Plympton;US;United States;41.95288, -70.81448
                4948087;Provincetown;Provincetown;US;United States;42.05295, -70.1864
                4950790;Sherborn;Sherborn;US;United States;42.23899, -71.36978
                4955149;Westborough;Westborough;US;United States;42.26954, -71.61618
                4955884;Winchester;Winchester;US;United States;42.45232, -71.137
                4956338;Yarmouth Port;Yarmouth Port;US;United States;41.70205, -70.24947
                4957956;Belfast;Belfast;US;United States;44.42591, -69.00642
                4957962;Belgrade;Belgrade;US;United States;44.44729, -69.83255
                4962442;Denmark;Denmark;US;United States;43.97035, -70.8034
                4967237;Hiram;Hiram;US;United States;43.87868, -70.8034
                4968937;Kittery;Kittery;US;United States;43.08814, -70.73616
                4969532;Limestone;Limestone;US;United States;46.90866, -67.82585
                4971699;Mechanic Falls;Mechanic Falls;US;United States;44.11174, -70.39172
                4974775;Patten;Patten;US;United States;45.99644, -68.44614
                4975603;Poland;Poland;US;United States;44.06063, -70.39367
                4979050;South Berwick;South Berwick;US;United States;43.23453, -70.8095
                4981750;Veazie;Veazie;US;United States;44.83868, -68.70531
                4987304;Buena Vista;Buena Vista;US;United States;43.4203, -83.89858
                4992609;Fennville;Fennville;US;United States;42.59392, -86.1017
                4994871;Grosse Pointe Woods;Grosse Pointe Woods;US;United States;42.44365, -82.90686
                4996718;Hudson;Hudson;US;United States;41.85505, -84.35384
                5004062;Novi;Novi;US;United States;42.48059, -83.47549
                5007548;Roosevelt Park;Roosevelt Park;US;United States;43.1964, -86.27228
                5010856;Spring Lake;Spring Lake;US;United States;43.07696, -86.197
                5010917;Springfield;Springfield;US;United States;42.32643, -85.23916
                5011020;Standish;Standish;US;United States;43.98308, -83.95888
                5012521;Trenton;Trenton;US;United States;42.13949, -83.17826
                5014811;White Cloud;White Cloud;US;United States;43.5503, -85.772
                5015701;Zeeland;Zeeland;US;United States;42.81252, -86.01865
                5015844;Ada;Ada;US;United States;47.29969, -96.51535
                5016496;Appleton;Appleton;US;United States;45.19691, -96.01977
                5016990;Bagley;Bagley;US;United States;47.52162, -95.39835
                5018651;Blaine;Blaine;US;United States;45.1608, -93.23495
                5019124;Branch;Branch;US;United States;45.48524, -92.96188
                5019335;Brooklyn Park;Brooklyn Park;US;United States;45.09413, -93.35634
                5023890;Dassel;Dassel;US;United States;45.08163, -94.30693
                5024846;Eagle Lake;Eagle Lake;US;United States;44.16497, -93.88134
                5026042;Excelsior;Excelsior;US;United States;44.9033, -93.56635
                5030856;Hoyt Lakes;Hoyt Lakes;US;United States;47.51965, -92.13851
                5033762;Lake Elmo;Lake Elmo;US;United States;44.9958, -92.87938
                5033962;Lakefield;Lakefield;US;United States;43.67746, -95.17166
                5037793;Minnetrista;Minnetrista;US;United States;44.9383, -93.71774
                5039675;Northfield;Northfield;US;United States;44.4583, -93.1616
                5042924;Renville;Renville;US;United States;44.78913, -95.21167
                5045258;Saint Michael;Saint Michael;US;United States;45.20996, -93.66496
                5045360;Saint Paul;Saint Paul;US;United States;44.94441, -93.09327
                5048796;Stewartville;Stewartville;US;United States;43.85552, -92.48851""");
        fout.flush();
        fout.close();
    }

    /**
     * Popola il file OperatoriAutorizzati.dai con alcuni esempi di email autorizzate a registrarsi.
     * @throws IOException il file OperatoriAutorizzati.dati non esiste.
     */
    public void writeOperatoriAutorizzatiFile() throws IOException {
        final int NOPERATORIAUTORIZZATI = 10;
        String numero;
        File file = fileInterface.getOperatoriAutorizzatiFile();
        if(file.length() != 0) return;
        PrintWriter fout = new PrintWriter(new BufferedWriter(new java.io.FileWriter(file, true)));
        for(int i = 0; i < NOPERATORIAUTORIZZATI; i++) {
            //noinspection ConstantValue
            if(i < 10)
                numero = "0" + i;
            else
                numero = String.valueOf(i);
            fout.println("example" + numero + "@climateMonitor.it");
            fout.flush();
        }
        fout.close();
    }

    /**
     * Serializza un oggetto su un file.
     * @param object oggetto da serializzare.
     * @param fileName percorso del file su cui serializzare l'oggetto.
     * @throws IOException il percorso del file passato come argomento non esiste.
     */
    public void serializeFileOut(Object object, String fileName) throws IOException {
        ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(fileName));
        write.writeObject(object);
    }
}
