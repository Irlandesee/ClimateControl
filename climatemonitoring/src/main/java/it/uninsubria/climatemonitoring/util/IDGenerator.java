package it.uninsubria.climatemonitoring.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class IDGenerator {

    public static String generateID(){
        //generates random alphanumeric string
        int leftLimit = 48; //0
        int rightLimit = 122; //z
        int targetStringLength = 10;
        Random ran = new Random();
        String input = ran.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i>= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        //generates md5Hash
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

}
