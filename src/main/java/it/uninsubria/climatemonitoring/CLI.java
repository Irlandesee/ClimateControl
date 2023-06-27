package it.uninsubria.climatemonitoring;

import it.uninsubria.climatemonitoring.dati.AreaInteresse;
import it.uninsubria.climatemonitoring.dati.CentroMonitoraggio;
import it.uninsubria.climatemonitoring.dati.Indirizzo;
import it.uninsubria.climatemonitoring.dati.Operatore;
import it.uninsubria.climatemonitoring.gestioneFile.FileInterface;
import it.uninsubria.climatemonitoring.dati.parametriClimatici.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Locale;

/**
 * Rappresenta l'applicazione ClimateMonitor che permette la memorizzazione di parametri climatici forniti da centri
 * di monitoraggio sul territorio mondiale, in grado di rendere disponibili a operatori ambientali e comuni
 * cittadini, i dati relativi alla propria zona d'interesse.
 *
 * @author Mattia Mauro Lunardi 736898 mmlunardi@studenti.uninsubria.it VA
 * @author Andrea Quaglia 753166 aquaglia2@studenti.uninsubria.it VA
 */
public class CLI {
    /**
     * Contiene l'{@link Operatore} che ha effettuato il login.
     */
    private Operatore loggedOperator;

    private final LinkedList<AreaInteresse> parametriClimaticiCache;
    private final LinkedList<CentroMonitoraggio> centriMonitoraggioCache;
    private final LinkedList<AreaInteresse> areeInteresseDisponibiliCache;
    private final LinkedList<String> operatoriAutorizzatiCache;
    private final LinkedList<Operatore> operatoriRegistratiCache;

    private final FileInterface fileInterface;

    BufferedReader reader;

    public CLI(FileInterface fileInterface) {
        this.fileInterface = fileInterface;

        parametriClimaticiCache = FileInterface.getParametriClimaticiCache();
        centriMonitoraggioCache = FileInterface.getCentriMonitoraggioCache();
        areeInteresseDisponibiliCache = FileInterface.getAreeInteresseDisponibiliCache();
        operatoriAutorizzatiCache = FileInterface.getOperatoriAutorizzatiCache();
        operatoriRegistratiCache = FileInterface.getOperatoriRegistratiCache();

        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Esegue l'applicazione ClimateMonitor.
     *
     * @throws IOException quando i file richiesti non esistono.
     */
    public void run() throws IOException {
        while (true) {
            while (loggedOperator == null) {
                System.out.println("""
                        
                        Benvenuto!
                        
                        Digitare 'cerca' per visualizzare le aree di interesse disponibili.
                        Digitare 'login' per effettuare il login (solo operatori registrati).
                        Digitare 'registrazione' per effettuare la registrazione all'applicazione\s
                        (solo operatori autorizzati).
                        Digitare 'salva' per salvare i dati su file.
                        Digitare 'uscita' per terminare il programma.""");
                switch (reader.readLine()) {
                    case "cerca", "c" -> cercaAreaGeografica(parametriClimaticiCache);
                    case "login", "l" -> loggedOperator = login();
                    case "registrazione", "r" -> loggedOperator = registrazione();
                    case "salva", "s" -> salvaSuFile();
                    case "uscita", "u" -> System.exit(0);
                }
            }

            while (loggedOperator != null) {
                CentroMonitoraggio centroAfferenza = loggedOperator.getCentroAfferenza();
                System.out.println("\nArea riservata - Centro di monitoraggio " +
                        centroAfferenza.getNomeCentro() + "\n");
                System.out.println("""
                        Digitare 'aggiungi' per aggiungere un'area di interesse al centro di monitoraggio.
                        Digitare 'cerca' per visualizzare le aree di interesse disponibili.
                        Digitare 'logout' per effettuare il logout e tornare al menu' principale.
                        Digitare 'uscita' per terminare il programma.
                        Digitare 'inserisci' per inserire i dati relativi ad una delle aree di interesse.
                        Digitare 'salva' per salvare i dati su file.
                        
                        Aree di interesse registrate:""");
                printCache(loggedOperator.getCentroAfferenza().getAreeInteresse());

                switch (reader.readLine()) {
                    case "aggiungi", "a" -> aggiungiAreaInteresse();
                    case "inserisci", "i" -> inserisciDatiParametri();
                    case "cerca", "c" -> cercaAreaGeografica(parametriClimaticiCache);
                    case "salva", "s" -> salvaSuFile();
                    case "logout", "l" -> loggedOperator = null;
                    case "uscita", "u" -> System.exit(0);
                }
            }
        }
    }

    private void cercaAreaGeografica(LinkedList<AreaInteresse> areeInteresse) throws IOException {
        AreaInteresse areaInteresse = cercaArea(areeInteresse);
        if (areaInteresse == null)
            System.out.println("Area di interesse non trovata!\n");
        else {
            System.out.println("Dati meteorologici di " + areaInteresse + "\n");
            System.out.println(areaInteresse.getDatiAggregati());
        }
    }

    private void inserisciDatiParametri() throws IOException {
        LinkedList<AreaInteresse> areeInteresse = loggedOperator.getCentroAfferenza().getAreeInteresse();
        AreaInteresse areaInteresse = cercaArea(areeInteresse);
        if(areaInteresse == null) {
            System.out.println("Area di interesse non trovata!");
            return;
        }

        System.out.println("Area di interesse scelta:\n" + areaInteresse + "\n");
        LinkedList<ParametroClimatico> parametriClimatici = new LinkedList<>();
        LinkedList<Integer> punteggi = new LinkedList<>();
        LinkedList<String> note = new LinkedList<>();
        ArrayList<String> nomiParametri = new ArrayList<>(Arrays.asList("al Vento", "all'Umidita", "alla Pressione",
                "alla Temperatura", "alle Precipitazioni", "all'Altitudine dei ghiacciai", "alla Massa dei ghiacciai"));

        for(int i = 0; i < 7; i++) {
            System.out.println("Digitare il punteggio relativo " +
                    nomiParametri.get(i) + "(da 1 critico a 5 ottimale, 0 per nessun inserimento):");
            String punteggio = reader.readLine();
            while (isNotNumeric(punteggio) || isNotaInt(punteggio) || Integer.parseInt(punteggio) < 0 ||
                    Integer.parseInt(punteggio) > 5) {
                System.err.println("Il punteggio deve essere un numero compreso tra 1-critico e 5-ottimale!");
                System.out.println("Digitare il punteggio relativo " +
                        nomiParametri.get(i) + "(da 1 critico a 5 ottimale, 0 per nessun inserimento):");
                punteggio = reader.readLine();
            }
            punteggi.add(i, Integer.parseInt(punteggio));

            if (Integer.parseInt(punteggio) == 0) {
                note.add(i, "");
                continue;
            }
            System.out.println("Digitare le note relative " + nomiParametri.get(i) + " (massimo 256 caratteri):");
            String nota = reader.readLine();
            while (nota.length() > 256) {
                System.err.println("Nota troppo lunga!\nLa nota deve essere al massimo di 256 caratteri!");
                System.out.println("Digitare le note relative " + nomiParametri.get(i) + " (massimo 256 caratteri):");
                nota = reader.readLine();
            }
            note.add(i, nota);
        }

        String annoRilevazione;
        String meseRilevazione;
        String giornoRilevazione;
        String data;

        do {
            System.out.println("Digitare la l'anno di rilevazione:");
            annoRilevazione = reader.readLine();
            while (isNotNumeric(annoRilevazione) || isNotaInt(annoRilevazione) ||
                    Integer.parseInt(annoRilevazione) > 9999) {
                System.out.println("Anno inserito non valido! L'anno deve essere un numero intero.");
                System.out.println("Digitare la l'anno di rilevazione:");
                annoRilevazione = reader.readLine();
            }
            switch (annoRilevazione.length()) {
                case (3) -> annoRilevazione = "0" + annoRilevazione;
                case (2) -> annoRilevazione = "00" + annoRilevazione;
                case (1) -> annoRilevazione = "000" + annoRilevazione;
                case (0) -> annoRilevazione = "0000";
                default -> {}
            }

            System.out.println("Digitare il mese di rilevazione:");
            meseRilevazione = reader.readLine();
            while (isNotNumeric(meseRilevazione) || isNotaInt(meseRilevazione) ||
                    Integer.parseInt(meseRilevazione) < 1 || Integer.parseInt(meseRilevazione) > 12) {
                System.out.println("Mese inserito non valido! Il mese deve essere un numero" +
                        " intero compreso tra 1 e 12.");
                System.out.println("Digitare la il mese di rilevazione:");
                meseRilevazione = reader.readLine();
            }
            if (meseRilevazione.length() == 1)
                meseRilevazione = "0" + meseRilevazione;

            System.out.println("Digitare il giorno di rilevazione:");
            giornoRilevazione = reader.readLine();
            while (isNotNumeric(giornoRilevazione) || isNotaInt(giornoRilevazione) ||
                    Integer.parseInt(giornoRilevazione) < 1 || Integer.parseInt(giornoRilevazione) > 31) {
                System.out.println("Giorno inserito non valido! Il giorno deve essere un " +
                        "numero intero compreso tra 1 e 31");
                System.out.println("Digitare il giorno di rilevazione:");
                giornoRilevazione = reader.readLine();
            }
            if (giornoRilevazione.length() == 1)
                giornoRilevazione = "0" + giornoRilevazione;

            data = giornoRilevazione + "-" + meseRilevazione + "-" + annoRilevazione;
            if(isDateInvalid(data))
                System.out.println("Data non valida! Riprova.");
        } while (isDateInvalid(data));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ITALY);
        LocalDate date = LocalDate.parse(data, formatter);

        parametriClimatici.add(0, new Vento(punteggi.get(0), note.get(0), date));
        parametriClimatici.add(1, new Umidita(punteggi.get(1), note.get(1), date));
        parametriClimatici.add(2, new Pressione(punteggi.get(2), note.get(2), date));
        parametriClimatici.add(3, new Temperatura(punteggi.get(3), note.get(3), date));
        parametriClimatici.add(4, new Precipitazioni(punteggi.get(4), note.get(4), date));
        parametriClimatici.add(5, new AltitudineGhiacciai(punteggi.get(5), note.get(5), date));
        parametriClimatici.add(6, new MassaGhiacciai(punteggi.get(6), note.get(6), date));

        areaInteresse.addParametriClimatici(parametriClimatici);
        fileInterface.writeCacheFile();

        System.out.println("Parametri climatici aggiunti con successo!");
    }

    private void aggiungiAreaInteresse() throws IOException {
        AreaInteresse areaInteresse = cercaArea(areeInteresseDisponibiliCache);
        if (areaInteresse == null) {
            System.out.println("Area di interesse non trovata!");
            return;
        } else {
            areeInteresseDisponibiliCache.remove(areaInteresse);
            parametriClimaticiCache.add(areaInteresse);
            loggedOperator.getCentroAfferenza().addAreaInteresse(areaInteresse);
            fileInterface.writeCacheFile();
        }
        System.out.println("Area di interesse aggiunta con successo!");
    }

    private Operatore registrazione() throws IOException {
        String cognome, nome, codiceFiscale = null, email, userID, password, nomeCentroAfferenza;

        System.out.println("Digitare il cognome:");
        cognome = reader.readLine();

        System.out.println("Digitare il nome:");
        nome = reader.readLine();

        boolean isCFValid = false;
        while (!isCFValid) {
            System.out.println("Digitare il codice fiscale:");
            codiceFiscale = reader.readLine();
            
            while (codiceFiscale.length() != 16) {
                System.out.println("Codice fiscale errato! Riprovare:");
                codiceFiscale = reader.readLine();
                if(codiceFiscale.equals(""))
                    break;
            }

            if (codiceFiscale.equals(""))
                break;

            for (int i = 0; i < 6; i++) {
                if (codiceFiscale.charAt(i) < 'A' || codiceFiscale.charAt(i) > 'Z') {
                    isCFValid = false;
                    break;
                } else {
                    isCFValid = true;
                }
            }

            if (!isCFValid) {
                System.out.println("Codice fiscale errato!");
                continue;
            }

            for (int i = 6; i < 8; i++) {
                if (codiceFiscale.charAt(i) < '0' || codiceFiscale.charAt(i) > '9') {
                    isCFValid = false;
                    break;
                }
            }

            if (!isCFValid) {
                System.out.println("Codice fiscale errato!");
                continue;
            }

            if (codiceFiscale.charAt(8) < 'A' || codiceFiscale.charAt(8) > 'Z') {
                isCFValid = false;
            }

            if (!isCFValid) {
                System.out.println("Codice fiscale errato!");
                continue;
            }

            for (int i = 9; i < 11; i++) {
                if (codiceFiscale.charAt(i) < '0' || codiceFiscale.charAt(i) > '9') {
                    isCFValid = false;
                    break;
                }
            }

            if (!isCFValid) {
                System.out.println("Codice fiscale errato!");
                continue;
            }

            if (codiceFiscale.charAt(11) < 'A' || codiceFiscale.charAt(11) > 'Z') {
                isCFValid = false;
            }

            if (!isCFValid) {
                System.out.println("Codice fiscale errato!");
                continue;
            }

            for (int i = 12; i < 15; i++) {
                if (codiceFiscale.charAt(i) < '0' || codiceFiscale.charAt(i) > '9') {
                    isCFValid = false;
                    break;
                }
            }

            if (!isCFValid) {
                System.out.println("Codice fiscale errato!");
                continue;
            }

            if (codiceFiscale.charAt(15) < 'A' || codiceFiscale.charAt(15) > 'Z') {
                isCFValid = false;
            }

            if (!isCFValid)
                System.out.println("Codice fiscale errato!");
        }
        if (codiceFiscale.equals(""))
            return null;

        boolean isEmailValid = false;
        System.out.println("Digitare l'email aziendale:");
        email = reader.readLine();
        for (Operatore operatore : operatoriRegistratiCache) {
            if (operatore.getEmail().equals(email)) {
                System.out.println("Utente gia' registrato!");
                return null;
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
        for (Operatore operatore : operatoriRegistratiCache) {
            if (operatore.getUserID().equals(userID)) {
                System.out.println("Utente gia' registrato!");
                return null;
            }
        }

        System.out.println("Digitare la password:");
        password = reader.readLine();

        System.out.println("Digitare il nome del centro di afferenza:");
        nomeCentroAfferenza = reader.readLine();

        CentroMonitoraggio centroAfferenza = null;
        boolean centroTrovato = false;
        if(centriMonitoraggioCache.isEmpty()) {
            centroAfferenza = registraCentroAree(nomeCentroAfferenza);
            centroTrovato = true;
        }
        if(!centroTrovato)
            for (CentroMonitoraggio centroMonitoraggio : centriMonitoraggioCache)
                if (centroMonitoraggio.getNomeCentro().equals(nomeCentroAfferenza)) {
                    centroAfferenza = centroMonitoraggio;
                    centroTrovato = true;
                }
        if(!centroTrovato)
            centroAfferenza = registraCentroAree(nomeCentroAfferenza);
        Operatore operatore = new Operatore(cognome, nome, codiceFiscale, email, userID,
                password, centroAfferenza);
        operatoriRegistratiCache.add(operatore);
        fileInterface.writeCacheFile();
        System.out.println("Accesso effettuato!\nBenvenuto " + cognome + " " + nome + "\n");
        return operatore;
    }

    private CentroMonitoraggio registraCentroAree(String nomeCentroAfferenza) throws IOException {
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
        while (isNotNumeric(numeroCivico) || isNotaInt(numeroCivico) || Integer.parseInt(numeroCivico) < 1) {
            System.out.println("Il numero civico deve essere un numero maggiore di 0!");
            System.out.println("Digitare il numero civico del centro di afferenza:");
            numeroCivico = reader.readLine();
        }
        numeroCivicoNumerico = Integer.parseInt(numeroCivico);

        System.out.println("Digitare il CAP del centro di afferenza:");
        cap = reader.readLine();
        while (isNotNumeric(cap) ||isNotaInt(cap) || Integer.parseInt(cap) < 1) {
            System.out.println("Il CAP deve essere un numero maggiore di 0!");
            System.out.println("Digitare il CAP del centro di afferenza:");
            cap = reader.readLine();
        }
        capNumerico = Integer.parseInt(cap);

        CentroMonitoraggio centroMonitoraggio = new CentroMonitoraggio(nomeCentroAfferenza,
                new Indirizzo(via, comune, provincia, numeroCivicoNumerico, capNumerico));
        centriMonitoraggioCache.add(centroMonitoraggio);
        fileInterface.writeCacheFile();
        System.out.println("Centro registrato con successo!");
        return centroMonitoraggio;
    }

    private Operatore login() throws IOException {
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
                    while (loggedOperator == null) {
                        System.out.println("Digitare la password:");
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
                System.out.println("Login fallito!");
        }
        return loggedOperator;
    }

    private AreaInteresse cercaArea(LinkedList<AreaInteresse> areeInteresse) throws IOException {
        System.out.println("Digitare 'nome' per ricercare l'area di interesse per nome.\n" +
                "Digitare 'coordinate' per cercare l'area di interesse per coordinate geografiche.");
        AreaInteresse areaInteresse = null;
        switch (reader.readLine()) {
            case "nome", "n" -> {
                System.out.println("Digitare il nome dell'area di interesse:");
                areaInteresse = cercaArea(areeInteresse, reader.readLine());
            }
            case "coordinate", "c" -> {
                System.out.println("Digitare la latitudine dell'area di interesse:");
                double latitudine = Double.parseDouble(reader.readLine());
                System.out.println("Digitare la longitudine dell'area di interesse:");
                double longitudine = Double.parseDouble(reader.readLine());
                areaInteresse = cercaArea(areeInteresse, latitudine, longitudine);
            }
        }
        return areaInteresse;
    }

    private AreaInteresse cercaArea(LinkedList<AreaInteresse> areeInteresse, String nome) {
        for(AreaInteresse tmp : areeInteresse)
            if(tmp.getNomeASCII().contains(nome))
                return tmp;
        return null;
    }

    private AreaInteresse cercaArea(LinkedList<AreaInteresse> areeInteresse, double latitude, double longitude) {
        if(areeInteresse.isEmpty())
            return null;
        AreaInteresse primoElemento = areeInteresse.get(0);
        double minimumDistance = Math.hypot(latitude - primoElemento.getLatitudine(),
                longitude - primoElemento.getLongitudine());
        int minimumIndex = 0;
        for(AreaInteresse areaInteresse : areeInteresse) {
            double distance = Math.hypot(latitude - areaInteresse.getLatitudine(),
                    longitude - areaInteresse.getLongitudine());
            if (minimumDistance > distance) {
                minimumDistance = distance;
                minimumIndex = areeInteresse.indexOf(areaInteresse);
            }
        }
        return areeInteresse.get(minimumIndex);
    }

    private boolean isDateInvalid(String date) {
        final String DATE_FORMAT = "dd-MM-yyyy";
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    private boolean isNotNumeric(String str) {
        try {
            Double.parseDouble(str);
            return false;
        } catch(NumberFormatException e){
            return true;
        }
    }

    private boolean isNotaInt(String number) {
        return Double.parseDouble(number) != (int) Double.parseDouble(number);
    }

    private void printCache(LinkedList<?> cache) {
        cache.forEach(System.out::println);
    }

    public void salvaSuFile() throws IOException {
        fileInterface.writeOperatoriRegistratiFile();
        fileInterface.writeCoordinateMonitoraggioFile();
        fileInterface.writeCentriMonitoraggioFile();
        fileInterface.writeParametriClimaticiFile();
        System.out.println("Operazione completata!");
    }
}
