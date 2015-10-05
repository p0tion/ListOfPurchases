package com.antontulskih.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * @author Tulskih Anton
 * @{NAME} 22.09.2015
 */
public class PasswordEncryptor {

    static final MyLogger LOGGER = new MyLogger(PasswordEncryptor.class);

    private static String nameCrypt = "PBKDF2WithHmacSHA512";

    public static String getCryptString(final String string) {
        String saltString = "some salt string";
        byte[] hash = new byte[0];
        try {
            byte[] salt = saltString.getBytes("UTF-8");
            KeySpec spec = new PBEKeySpec(string.toCharArray(), salt, 65536, 128);
            SecretKeyFactory f = SecretKeyFactory.getInstance(nameCrypt);
            hash = f.generateSecret(spec).getEncoded();
        } catch(NoSuchAlgorithmException nsae) {
            LOGGER.error("NoSuchAlgorithmException occurred", nsae);
        } catch(UnsupportedEncodingException uee) {
            LOGGER.error("UnsupportedEncodingException occurred", uee);
        } catch(InvalidKeySpecException ikse) {
            LOGGER.error("InvalidKeySpecException occurred", ikse);
        }
        Base64 enc = new Base64();
        return  enc.encodeToString(hash);
    }

    public static void main(String[] args) {

        System.out.println(getCryptString("Qw1234"));

    }

}
