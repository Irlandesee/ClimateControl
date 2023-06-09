package it.uninsubria.climatemonitoring;


import it.uninsubria.climatemonitoring.centroMonitoraggio.CentroMonitoraggio;
import it.uninsubria.climatemonitoring.climateParameters.ClimateParameter;
import it.uninsubria.climatemonitoring.dbref.DBInterface;
import it.uninsubria.climatemonitoring.operatore.Operatore;
import it.uninsubria.climatemonitoring.operatore.opeatoreAutorizzato.OperatoreAutorizzato;
import it.uninsubria.climatemonitoring.operatore.opeatoreRegistrato.OperatoreRegistrato;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.HashMap;

public class Main {
    public static String createMD5Hash(final String input){
        String hashText = null;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            hashText = convertToHex(messageDigest);
        }catch(NoSuchAlgorithmException nsae){nsae.printStackTrace();}
        return hashText;
    }

    private static String convertToHex(final byte[] messageDigest){
        BigInteger bigint = new BigInteger(1, messageDigest);
        String hexText = bigint.toString(16);
        while(hexText.length() < 32) hexText = "0".concat(hexText);
        return hexText;
    }

    private static String buildInputStringAreaInteresse(
            String denominazione,
            String stato,
            float latitude,
            float longitude
    ){
        return new StringBuilder()
                .append(denominazione)
                .append(" ")
                .append(stato)
                .append(" ")
                .append(stato)
                .append(" ")
                .append(latitude)
                .append(" ")
                .append(longitude)
                .toString();
    }

    private static String buildInputStringCentroMonitoraggio(
            String nomeCentro,
            String via,
            short numCivico,
            int cap,
            String comune,
            String provincia
    ){
        return new StringBuilder()
                .append(nomeCentro)
                .append(" ")
                .append(via)
                .append(" ")
                .append(numCivico)
                .append(" ")
                .append(cap)
                .append(" ")
                .append(comune)
                .append(" ")
                .append(provincia)
                .toString();
    }

    private static String buildInputStringClimateParameter(
            String idCentro,
            String areaInteresse,
            LocalDate pubDate
    ){
        return new StringBuilder()
                .append(idCentro)
                .append(" ")
                .append(areaInteresse)
                .append(" ")
                .append(pubDate.toString())
                .toString();
    }

    public static void main(String[] args) throws Exception{
        DBInterface dbRef = new DBInterface();
        HashMap<String, CentroMonitoraggio> cmMap =
                (HashMap<String, CentroMonitoraggio>) dbRef.readCache(DBInterface.objClassCentroMonitoraggio);
        cmMap.forEach(
                (key, value) -> System.out.println(value)
        );

        HashMap<String, OperatoreRegistrato> opReg =
                (HashMap<String, OperatoreRegistrato>) dbRef.readCache(DBInterface.objClassOpRegistrati);
        opReg.forEach(
                (key, value) -> System.out.println(value)
        );

        HashMap<String, OperatoreAutorizzato> opAutorizzato =
                (HashMap<String, OperatoreAutorizzato>) dbRef.readCache(DBInterface.objClassOpAutorizzati);
        opAutorizzato.forEach(
                (key, value) -> System.out.println(value)
        );
        HashMap<String, ClimateParameter> parameterMap =
                (HashMap<String, ClimateParameter>) dbRef.readCache(DBInterface.objClassParamClimatici);
        parameterMap.forEach(
                (key, value) -> System.out.println(value)
        );
    }
}