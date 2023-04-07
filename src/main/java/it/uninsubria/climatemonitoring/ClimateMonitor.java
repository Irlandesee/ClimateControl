package it.uninsubria.climatemonitoring;

import it.uninsubria.climatemonitoring.gestioneFile.DBInterface;
import it.uninsubria.climatemonitoring.parametriClimatici.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Locale;

/**
 * @author : Mattia Mauro Lunardi, 736898, mmlunardi@studenti.uninsubria.it, VA
 * @author : Andrea Quaglia, 753166, aquaglia2@studenti.uninsubria.it, VA
 **/
public class ClimateMonitor {
    /**
     *
     * @param args non usato
     * @throws IOException non è stato possibile creare uno dei file richiesti
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DBInterface dbRef = new DBInterface();

        dbRef.writeOperatoriAutorizzatiFile();
        dbRef.writeGeonamesAndCoordinatesFile();

        dbRef.writeCoordinateMonitoraggioFile(dbRef.readGeonamesAndCoordinatesFile());
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
                        else {
                            System.out.println("Dati meteorologici di " + areaInteresse + "\n");
                            System.out.println(areaInteresse.getParametriClimatici());
                        }
                    }
                    case "login" -> loggedOperator = login(operatoriRegistratiCache, reader);
                    case "registrazione" -> loggedOperator = registrazione(dbRef, operatoriAutorizzatiCache,
                            operatoriRegistratiCache, centriMonitoraggioCache, reader);
                    case "uscita" -> System.exit(0);
                }
            }

            while (loggedOperator != null) {
                CentroMonitoraggio centroAfferenza = loggedOperator.getCentroAfferenza();
                System.out.println("Area riservata - Centro di monitoraggio " + centroAfferenza.getNomeCentro() + "\n");
                System.out.println("""
                        Digitare 'aggiungi' per aggiungere un aree di interesse al centro di monitoraggio.
                        Digitare 'logout' per effettuare il logout e tornare al menu' principale.
                        Digitare 'uscita' per terminare il programma.
                        Digitare 'inserisci' per inserire i dati relativi ad una delle aree di interesse.
                        Aree di interesse registrate:""");
                printCache(loggedOperator.getCentroAfferenza().getAreeInteresse());

                switch (reader.readLine()) {
                    case "aggiungi" -> aggiungiAreaInteresse(dbRef, areeInteresseDisponibiliCache,
                            areeInteresseAssociateCache, centriMonitoraggioCache, operatoriRegistratiCache, reader,
                            loggedOperator);
                    case "inserisci" -> inserisciDatiParametri(reader, loggedOperator, dbRef, centriMonitoraggioCache,
                            areeInteresseAssociateCache);
                    case "cerca" -> {
                        AreaInteresse areaInteresse = cercaAreaGeografica(areeInteresseAssociateCache, reader);
                        if (areaInteresse == null)
                            System.out.println("Area di interesse non trovata!\n");
                        else {
                            System.out.println("Dati meteorologici di " + areaInteresse + "\n");
                            System.out.println(areaInteresse.getParametriClimatici());
                        }
                    }
                    case "logout" -> loggedOperator = null;
                    case "uscita" -> System.exit(0);
                }
            }
        }
    }

    private static void inserisciDatiParametri
            (BufferedReader reader, Operatore loggedOperator, DBInterface dbRef,
             LinkedList<CentroMonitoraggio> centriMonitoraggioCache,
             LinkedList<AreaInteresse> areeInteresseAssociateCache) throws IOException {
        LinkedList<AreaInteresse> areeInteresse =
                loggedOperator.getCentroAfferenza().getAreeInteresse();
        AreaInteresse areaInteresse = cercaAreaGeografica(areeInteresse, reader);
        if(areaInteresse == null) {
            System.out.println("Area di interesse non trovata!");
            return;
        }
        System.out.println("Area di interesse scelta:\n" + areaInteresse);
        LinkedList<ParametroClimatico> parametriClimatici = new LinkedList<>();
        LinkedList<Integer> punteggi = new LinkedList<>();
        LinkedList<String> note = new LinkedList<>();
        ArrayList<String> nomiParametri = new ArrayList<>(Arrays.asList("al Vento", "all'Umidità", "alla Pressione",
                "alla Temperatura", "alle Precipitazioni", "all'Altitudine dei ghiacciai", "alla Massa dei ghiacciai"));

        //TODO verifica interi in lettura e max caratteri.
        for(int i = 0; i < 7; i++) {
            System.out.println("Digitare il punteggio relativo " +
                    nomiParametri.get(i) + "(da 1 critico a 5 ottimale, 0 per nessun inserimento):");
            String punteggio = reader.readLine();
            int punteggioNumerico = 0;
            if (isNumeric(punteggio)) {
                punteggioNumerico = Integer.parseInt(punteggio);
            } else {
                System.err.println("Il punteggio deve essere un numero compreso tra 1-critico e 5-ottimale!");
                return;
            }
            if(punteggioNumerico < 0 || punteggioNumerico > 5) {
                System.err.println("Il punteggio deve essere un numero compreso tra 1-critico e 5-ottimale!");
                return;
            }
            punteggi.add(i, Integer.parseInt(punteggio));
            System.out.println("Digitare le note relative " + nomiParametri.get(i) + " (massimo 256 caratteri):");
            String nota = reader.readLine();
            if (nota.length() > 256) {
                System.err.println("Nota troppo lunga!\nLa nota deve essere al massimo di 256 caratteri!");
                return;
            }
            note.add(i, nota);
        }

        //TODO verify date input
        String annoRilevazione;
        String meseRilevazione;
        String giornoRilevazione;
        System.out.println("Digitare la l'anno di rilevazione:");
        annoRilevazione = reader.readLine();
        System.out.println("Digitare il mese di rilevazione:");
        meseRilevazione = reader.readLine();
        if(meseRilevazione.length() == 1)
            meseRilevazione = "0" + meseRilevazione;
        System.out.println("Digitare il giorno di rilevazione:");
        giornoRilevazione = reader.readLine();
        if(giornoRilevazione.length() == 1)
            giornoRilevazione = "0" + giornoRilevazione;
        String string = giornoRilevazione + " " + meseRilevazione + " " + annoRilevazione;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy", Locale.ITALIAN);
        LocalDate date = LocalDate.parse(string, formatter);

        parametriClimatici.add(0, new Vento(punteggi.get(0), note.get(0), date));
        parametriClimatici.add(1, new Umidita(punteggi.get(1), note.get(1), date));
        parametriClimatici.add(2, new Pressione(punteggi.get(2), note.get(2), date));
        parametriClimatici.add(3, new Temperatura(punteggi.get(3), note.get(3), date));
        parametriClimatici.add(4, new Precipitazioni(punteggi.get(4), note.get(4), date));
        parametriClimatici.add(5, new AltitudineGhiacciai(punteggi.get(5), note.get(5), date));
        parametriClimatici.add(6, new MassaGhiacciai(punteggi.get(6), note.get(6), date));

        areaInteresse.addParametriClimatici(parametriClimatici);
        dbRef.writeCentriMonitoraggioFile(centriMonitoraggioCache);
        dbRef.writeAreeInteresseFile(areeInteresseAssociateCache);
        System.out.println("Parametri climatici aggiunti con successo!");
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private static void aggiungiAreaInteresse(DBInterface dbRef, LinkedList<AreaInteresse>
            areeInteresseDisponibiliCache, LinkedList<AreaInteresse> areeInteresseAssociateCache,
                                              LinkedList<CentroMonitoraggio> centriMonitoraggioCache,
                                              LinkedList<Operatore> operatoriRegistratiCache,
                                              BufferedReader reader, Operatore loggedOperator) throws IOException {
        AreaInteresse areaInteresse = cercaAreaGeografica(areeInteresseDisponibiliCache, reader);
        if (areaInteresse == null) {
            System.out.println("Area di interesse non trovata!");
            return;
        } else {
            areeInteresseDisponibiliCache.remove(areaInteresse);
            areeInteresseAssociateCache.add(areaInteresse);
            loggedOperator.getCentroAfferenza().addAreaInteresse(areaInteresse);
            dbRef.writeAreeInteresseFile(areeInteresseAssociateCache);
            dbRef.writeCoordinateMonitoraggioFile(areeInteresseDisponibiliCache);
            dbRef.writeCentriMonitoraggioFile(centriMonitoraggioCache);
            dbRef.writeOperatoriRegistratiFile(operatoriRegistratiCache);
        }
        System.out.println("Area di interesse aggiunta con successo!");
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

    private static CentroMonitoraggio registraCentroAree(DBInterface dbRef, LinkedList<CentroMonitoraggio>
            centriMonitoraggioCache, BufferedReader reader, String nomeCentroAfferenza) throws IOException {
        String via, comune, provincia, numeroCivico, cap;
        int numeroCivicoNumerico, capNumerico;

        System.out.println("Digitare la via del centro di afferenza:");
        via = reader.readLine();

        System.out.println("Digitare il comune del centro di afferenza:");
        comune = reader.readLine();

        System.out.println("Digitare la provincia del centro di afferenza:");
        provincia = reader.readLine();

        System.out.println("Digitare il numero civico del centro di afferenza:");
        numeroCivico = reader.readLine();
        while (!isNumeric(numeroCivico) || Integer.parseInt(numeroCivico) < 1) {
            System.out.println("Il numero civico deve essere un numero maggiore di 0!");
            System.out.println("Digitare il numero civico del centro di afferenza:");
            numeroCivico = reader.readLine();
        }
        numeroCivicoNumerico = Integer.parseInt(numeroCivico);

        System.out.println("Digitare il CAP del centro di afferenza:");
        cap = reader.readLine();
        while (!isNumeric(cap) || Integer.parseInt(cap) < 1) {
            System.out.println("Il CAP deve essere un numero maggiore di 0!");
            System.out.println("Digitare il CAP del centro di afferenza:");
            cap = reader.readLine();
        }
        capNumerico = Integer.parseInt(cap);

        CentroMonitoraggio centroMonitoraggio = new CentroMonitoraggio(nomeCentroAfferenza,
                new Indirizzo(via, comune, provincia, numeroCivicoNumerico, capNumerico));
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

    private static AreaInteresse cercaAreaGeografica(LinkedList<AreaInteresse> areeInteresseCache,
                                                     BufferedReader reader) throws IOException {
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

    private static void printCache(LinkedList<?> cache) {
        cache.forEach(System.out::println);
    }
}