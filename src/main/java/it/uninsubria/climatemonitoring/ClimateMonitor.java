package it.uninsubria.climatemonitoring;

import it.uninsubria.climatemonitoring.gestioneFile.FileInterface;

import java.io.IOException;

/**
 * @author Mattia Mauro Lunardi 736898 mmlunardi@studenti.uninsubria.it VA
 * @author Andrea Quaglia 753166 aquaglia2@studenti.uninsubria.it VA
 */
public class ClimateMonitor {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Applicazione climateMonitor = new Applicazione(new FileInterface());
        climateMonitor.run();
    }
}