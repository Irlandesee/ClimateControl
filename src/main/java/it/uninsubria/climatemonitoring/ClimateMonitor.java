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
        LinkedList<AreaInteresse> areeInteresseDisponibiliCache = dbRef.readCoordinateMonitoraggioFile();
        LinkedList<AreaInteresse> areeInteresseAssociateCache = dbRef.readAreeInteresseFile();
        LinkedList<String> operatoriAutorizzatiCache = dbRef.readOperatoriAutorizzatiFile();
        LinkedList<Operatore> operatoriRegistratiCache = dbRef.readOperatoriRegistratiFile();
        LinkedList<CentroMonitoraggio> centriMonitoraggioCache = dbRef.readCentriMonitoraggioFile();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Operatore loggedOperator = null;

        while (true) {
            while (loggedOperator == null) {
                System.out.println("""
                        Benvenuto!
                        Digitare 'cerca' per visualizzare le aree di interesse disponibili.
                        Digitare 'login' per effettuare il login (solo operatori registrati).
                        Digitare 'registrazione' per effettuare la registrazione all'applicazione\s
                        (solo operatori autorizzati).
                        Digitare 'uscita' per terminare il programma.""");
                switch (reader.readLine()) {
                    case "cerca" -> {
                        AreaInteresse areaInteresse = cercaAreaGeografica(areeInteresseAssociateCache, reader);
                        if (areaInteresse == null)
                            System.out.println("Area di interesse non trovata!\n");
                        else
                            System.out.println("Dati meteorologici di " + areaInteresse);
                    }
                    case "login" -> loggedOperator = login(operatoriRegistratiCache, reader);
                    case "registrazione" -> loggedOperator = registrazione(dbRef, operatoriAutorizzatiCache,
                            operatoriRegistratiCache, centriMonitoraggioCache, reader);
                    case "uscita" -> System.exit(0);
                }
            }

            while (loggedOperator != null) {
                System.out.println("""
                        Area riservata.
                        Digitare 'aggiungi' per aggiungere un aree di interesse al centro di monitoraggio.
                        Digitare 'logout' per effettuare il logout e tornare al menù principale.
                        Digitare 'uscita' per terminare il programma.
                        Aree di interesse registrate:""");
                printCache(loggedOperator.getCentroAfferenza().getAreeInteresse());

                switch (reader.readLine()) {
                    case "aggiungi" -> {
                        AreaInteresse areaInteresse = cercaAreaGeografica(areeInteresseDisponibiliCache, reader);
                        if (areaInteresse == null) {
                            System.out.println("Area di interesse non trovata!");
                        } else {
                            areeInteresseDisponibiliCache.remove(areaInteresse);
                            areeInteresseAssociateCache.add(areaInteresse);
                            loggedOperator.getCentroAfferenza().addAreaInteresse(areaInteresse);
                            dbRef.writeAreeInteresseFile(areeInteresseAssociateCache);
                            dbRef.writeCoordinateMonitoraggioFile(areeInteresseDisponibiliCache);
                            dbRef.writeCentriMonitoraggioFile(centriMonitoraggioCache);
                        }
                        System.out.println("Area di interesse aggiunta con successo!");
                    }
                    case "logout" -> loggedOperator = null;
                    case "uscita" -> System.exit(0);
                }
            }
        }
    }

    private static Operatore registrazione(DBInterface dbRef, LinkedList<String> operatoriAutorizzatiCache,
                                           LinkedList<Operatore> operatoriRegistratiCache,
                                           LinkedList<CentroMonitoraggio> centriMonitoraggioCache,
                                           BufferedReader reader) throws IOException {
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
            return null;
        }
        System.out.println("Digitare l'userID:");
        userID = reader.readLine();
        System.out.println("Digitare la password:");
        password = reader.readLine();
        System.out.println("Digitare il nome del centro di afferenza:");
        nomeCentroAfferenza = reader.readLine();
        CentroMonitoraggio centroAfferenza = null;
        boolean centroTrovato = false;
        if(centriMonitoraggioCache.isEmpty()) {
            centroAfferenza = registraCentroAree(dbRef, centriMonitoraggioCache, reader, nomeCentroAfferenza);
            centroTrovato = true;
        }
        if(!centroTrovato)
            for (CentroMonitoraggio centroMonitoraggio : centriMonitoraggioCache)
                if (centroMonitoraggio.getNomeCentro().equals(nomeCentroAfferenza)) {
                    centroAfferenza = centroMonitoraggio;
                    centroTrovato = true;
                }
        if(!centroTrovato)
            centroAfferenza = registraCentroAree(dbRef, centriMonitoraggioCache, reader, nomeCentroAfferenza);
        Operatore operatore = new Operatore(cognome, nome, codiceFiscale, email, userID,
                password, centroAfferenza);
        operatoriRegistratiCache.add(operatore);
        dbRef.registraOperatore(operatoriRegistratiCache);
        System.out.println("Accesso effettuato!\nBenvenuto " + cognome + " " + nome + "\n");
        return operatore;
    }

    private static CentroMonitoraggio registraCentroAree(DBInterface dbRef, LinkedList<CentroMonitoraggio> centriMonitoraggioCache,
                                           BufferedReader reader, String nomeCentroAfferenza) throws IOException {
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
        CentroMonitoraggio centroMonitoraggio = new CentroMonitoraggio(nomeCentroAfferenza,
                new Indirizzo(via, comune, provincia, numeroCivico, cap));
        centriMonitoraggioCache.add(centroMonitoraggio);
        dbRef.writeCentriMonitoraggioFile(centriMonitoraggioCache);
        System.out.println("Centro registrato con successo!");
        return centroMonitoraggio;
    }

    private static Operatore login(LinkedList<Operatore> operatoriRegistratiCache,
                                 BufferedReader reader) throws IOException {
        Operatore loggedOperator = null;
        while (loggedOperator == null) {
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
                    while (loggedOperator == null) {
                        enteredPassword = reader.readLine();
                        if (enteredPassword.equals(""))
                            break;
                        if (password.equals(enteredPassword)) {
                            System.out.println("Accesso effettuato!\nBenvenuto " + operatore.getCognome()
                                    + " " + operatore.getNome());
                            loggedOperator = operatore;
                        } else {
                            System.out.println("Password errata!");
                        }
                    }
                    break;
                }
            }
            if (loggedOperator == null)
                System.out.println("userID errato!");
        }
        return loggedOperator;
    }

    private static AreaInteresse cercaAreaGeografica(LinkedList<AreaInteresse> areeInteresseCache, BufferedReader reader) throws IOException {
        System.out.println("Digitare 'nome' per ricercare l'area di interesse per nome.\n" +
                "Digitare 'coordinate' per cercare l'area di interesse per coordinate geografiche.");
        AreaInteresse areaInteresse = null;
        switch (reader.readLine()) {
            case "nome" -> {
                System.out.println("Digitare il nome dell'area di interesse:");
                areaInteresse = cercaAreaGeografica(areeInteresseCache, reader.readLine());
            }
            case "coordinate" -> {
                System.out.println("Digitare la latitudine dell'area di interesse:");
                double latitudine = Double.parseDouble(reader.readLine());
                System.out.println("Digitare la longitudine dell'area di interesse:");
                double longitudine = Double.parseDouble(reader.readLine());
                areaInteresse = cercaAreaGeografica(areeInteresseCache, latitudine, longitudine);
            }
        }
        return areaInteresse;
    }

    public static AreaInteresse cercaAreaGeografica(LinkedList<AreaInteresse> areeInteresseCache, String nome) {
        for(AreaInteresse areaInteresse : areeInteresseCache)
            if(areaInteresse.getAsciiName().contains(nome))
                return areaInteresse;
        return null;
    }

    public static AreaInteresse cercaAreaGeografica(LinkedList<AreaInteresse> areeInteresseCache,
                                                    double latitude, double longitude) {
        if(areeInteresseCache.isEmpty())
            return null;
        AreaInteresse primoElemento = areeInteresseCache.get(0);
        double minimumDistance = Math.hypot(latitude - primoElemento.getLatitude(),
                longitude - primoElemento.getLongitude());
        int minimumIndex = 0;
        for(AreaInteresse areaInteresse : areeInteresseCache) {
            double distance = Math.hypot(latitude - areaInteresse.getLatitude(),
                    longitude - areaInteresse.getLongitude());
            if (minimumDistance > distance) {
                minimumDistance = distance;
                minimumIndex = areeInteresseCache.indexOf(areaInteresse);
            }
        }
        return areeInteresseCache.get(minimumIndex);
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

    private static void printCache(LinkedList<?> cache) {
        cache.forEach(System.out::println);
    }
}