package it.uninsubria.climatemonitoring;

import it.uninsubria.climatemonitoring.gestioneFile.DBInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author : Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * @author : Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 **/
public class ClimateMonitor extends Application {
    /**
     *
     * @param args non usato
     * @throws IOException non è stato possibile creare uno dei file richiesti
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DBInterface dbRef = new DBInterface();
        HashMap<String, AreaInteresse> geonamesCache = dbRef.readGeonamesFile();
        LinkedList<String> operatoriAutorizzatiCache = dbRef.readOperatoriAutorizzatiFile();
        LinkedList<Operatore> operatoriRegistratiCache = dbRef.readOperatoriRegistratiFile();
        LinkedList<CentroMonitoraggio> centriMonitoraggioCache = dbRef.readCentriMonitoraggioFile();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean loggedIn = false;

        while (true) {
            while (!loggedIn) {
                System.out.println("""
                        Benvenuto, digitare 'cerca' per visualizzare le aree di interesse disponibili.
                        Digitare 'login' per effettuare il login (solo operatori registrati).
                        Digitare 'registrazione' per effettuare la registrazione all'applicazione\s
                        (solo operatori autorizzati).
                        Digitare 'uscita' per terminare il programma.""");
                switch (reader.readLine()) {
                    case "cerca" -> cercaAreaGeografica(geonamesCache, reader);
                    case "login" -> loggedIn = login(operatoriRegistratiCache, reader);
                    case "registrazione" -> loggedIn = registrazione(dbRef, operatoriAutorizzatiCache,
                            operatoriRegistratiCache, centriMonitoraggioCache, reader);
                    case "uscita" -> System.exit(0);
                }
            }

            while (loggedIn) {
                System.out.println("""
                        Area riservata, digitare 'aggiungi' per aggiungere un aree di interesse al centro di monitoraggio.
                        Digitare 'logout' per effettuare il logout e tornare al menù principale.
                        Digitare 'uscita' per terminare il programma.""");

                switch (reader.readLine()) {
                    case "aggiungi" -> {
                        //TODO
                    }
                    case "logout" -> loggedIn = false;
                    case "uscita" -> System.exit(0);
                }
            }
        }
    }

    private static boolean registrazione(DBInterface dbRef, LinkedList<String> operatoriAutorizzatiCache,
                                         LinkedList<Operatore> operatoriRegistratiCache,
                                         LinkedList<CentroMonitoraggio> centriMonitoraggioCache,
                                         BufferedReader reader) throws IOException {
        boolean loggedIn;
        String cognome, nome, codiceFiscale, email, userID, password, nomeCentroAfferenza;
        System.out.println("Digitare il cognome:");
        cognome = reader.readLine();
        System.out.println("Digitare il nome:");
        nome = reader.readLine();
        System.out.println("Digitare il codice fiscale:");
        codiceFiscale = reader.readLine();
        System.out.println("Digitare l'email aziendale:");
        email = reader.readLine();
        boolean isEmailValid = false;
        for (Operatore operatore : operatoriRegistratiCache) {
            if (operatore.getEmail().equals(email)) {
                System.out.println("Utente gia' registrato!");
                break;
            }
        }
        for (String tmp : operatoriAutorizzatiCache) {
            if (tmp.equals(email)) {
                isEmailValid = true;
                break;
            }
        }
        if(!isEmailValid) {
            System.out.println("Email non valida!");
            return false;
        }
        System.out.println("Digitare l'userID:");
        userID = reader.readLine();
        System.out.println("Digitare la password:");
        password = reader.readLine();
        System.out.println("Digitare il geonameID del centro di afferenza:");
        nomeCentroAfferenza = reader.readLine();
        CentroMonitoraggio centroAfferenza = null;
        for (CentroMonitoraggio centroMonitoraggio : centriMonitoraggioCache) {
            if (centroMonitoraggio.getNomeCentro().equals(nomeCentroAfferenza))
                centroAfferenza = centroMonitoraggio;
            else {
                String via, comune, provincia;
                int numeroCivico, cap;
                System.out.println("Digitare la via del centro di afferenza:");
                via = reader.readLine();
                System.out.println("Digitare il comune del centro di afferenza:");
                comune = reader.readLine();
                System.out.println("Digitare la provincia del centro di afferenza:");
                provincia = reader.readLine();
                System.out.println("Digitare il numero civico del centro di afferenza:");
                numeroCivico = Integer.parseInt(reader.readLine());
                System.out.println("Digitare il CAP del centro di afferenza:");
                cap = Integer.parseInt(reader.readLine());
                centriMonitoraggioCache.add(new CentroMonitoraggio(nomeCentroAfferenza,
                        new Indirizzo(via, comune, provincia, numeroCivico, cap)));
            }
        }
        Operatore operatore = new Operatore(cognome, nome, codiceFiscale, email, userID,
                password, centroAfferenza);
        operatoriRegistratiCache.add(operatore);
        loggedIn = true;
        dbRef.registraOperatore(operatoriRegistratiCache);
        System.out.println("Accesso effettuato!\nBenvenuto " + cognome + " " + nome + "\n");
        return loggedIn;
    }

    private static boolean login(LinkedList<Operatore> operatoriRegistratiCache,
                                 BufferedReader reader) throws IOException {
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("Digitare l'userID:");
            String userID = reader.readLine();
            if (userID.equals(""))
                break;
            String password;
            String enteredPassword;
            for (Operatore operatore : operatoriRegistratiCache) {
                if (userID.equals(operatore.getUserID())) {
                    password = operatore.getPassword();
                    System.out.println("Digitare la password:");
                    while (!loggedIn) {
                        enteredPassword = reader.readLine();
                        if (enteredPassword.equals(""))
                            break;
                        if (password.equals(enteredPassword)) {
                            System.out.println("Accesso effettuato!\nBenvenuto " + operatore.getCognome()
                                    + " " + operatore.getNome());
                            loggedIn = true;
                        } else {
                            System.out.println("Password errata!");
                        }
                    }
                    break;
                }
            }
            if (!loggedIn)
                System.out.println("userID errato!");
        }
        return loggedIn;
    }

    private static void cercaAreaGeografica(HashMap<String, AreaInteresse> geonamesCache, BufferedReader reader) throws IOException {
        printCache(geonamesCache);
        System.out.println("\nE' possibile visualizzare i dati climatici di un area di interesse digitando" +
                " il suo geonameID");
        String geonameID = reader.readLine();
        AreaInteresse areaGeografica;
        while ((areaGeografica = geonamesCache.get(geonameID)) == null) {
            System.out.println("GeonameID inserito non valido. Riprovare.");
            geonameID = reader.readLine();
        }
        System.out.println("Dati climatici di: " + areaGeografica);
    }

    /**
     *
     * @param stage
     * @throws IOException file main-view.fxml non trovato
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClimateMonitor.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello World!");
        stage.setScene(scene);
        stage.show();
    }

    private static void printCache(HashMap<String, ?> cache) {
        cache.forEach((key, value) -> System.out.println(value));
    }
}