package com.example.artemqa.formloggingonelab.util;
import android.util.Base64;
import android.util.Log;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by artemqa on 28.10.2017.
 */

public class Utils {
    public final static String ACCESS_APPLICATION_KEY = "12345678" ;
    public final static String ADMIN_LOGIN = "ADMIN";
    public final static String ADMIN_PASSWORD = "1";
    private final static String ALGORITHM_NAME_HASHING = "MD5";
    public final static boolean ADMIN_IS_BLOCKED = false;
    public final static boolean ADMIN_IS_LIMITATION = false;
    public final static String NEW_USER_PASSWORD = "";
    public final static boolean NEW_USER_IS_BLOCKED = false;
    public final static boolean NEW_USER_IS_LIMITATION = false;
    private final static String LOG = "MyLog";
    private final static String IV = "initVect";



    public static String toMD4(final String s) {

        try {
            // Create MD4 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(ALGORITHM_NAME_HASHING);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0".concat(h) ;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String encryptStr(String encryptingStr){

        String strCipherText = null;

        try {

            byte[] decodedKey = Utils.ACCESS_APPLICATION_KEY.getBytes();
            IvParameterSpec iv = new IvParameterSpec(Utils.IV.getBytes());
            SecretKey secretKey = new SecretKeySpec(decodedKey, "DES");
            Cipher desCipher = Cipher.getInstance("DES/OFB32/NoPadding");
            desCipher.init(Cipher.ENCRYPT_MODE, secretKey,iv);
            byte[] byteDataToEncrypt = encryptingStr.getBytes();
            byte[] byteCipherText = desCipher.doFinal(byteDataToEncrypt);
            strCipherText = Base64.encodeToString(byteCipherText, Base64.DEFAULT);
            Log.d(LOG, " EncryptingStringText :" + strCipherText);

        } catch (NoSuchAlgorithmException noSuchAlgo) {
                System.out.println(" No Such Algorithm exists " + noSuchAlgo);
            }

        catch (NoSuchPaddingException noSuchPad) {
                System.out.println(" No Such Padding exists " + noSuchPad);
            }

        catch (InvalidKeyException invalidKey) {
                System.out.println(" Invalid Key " + invalidKey);
            }

        catch (BadPaddingException badPadding)
            {
                System.out.println(" Bad Padding " + badPadding);
            }

        catch (IllegalBlockSizeException illegalBlockSize)
            {
                System.out.println(" Illegal Block Size " + illegalBlockSize);
            } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        return strCipherText;
    }

    public static String decryptStr(String decryptStr){

        String strDecryptedText = null;
        try {

            byte[] decodedKey = Base64.decode(Utils.ACCESS_APPLICATION_KEY, Base64.DEFAULT);
            SecretKey secretKey = new SecretKeySpec(decodedKey, "DES");
            Cipher desCipher = Cipher.getInstance("DES/OFB32/NoPadding");
            desCipher.init(Cipher.DECRYPT_MODE,secretKey,desCipher.getParameters());
            byte[] byteCipherText = decryptStr.getBytes();
            byte[] byteDecryptedText = desCipher.doFinal(byteCipherText);
            strDecryptedText = new String(byteDecryptedText);
            System.out.println(" Decrypted Text message is " +strDecryptedText);

        } catch (NoSuchAlgorithmException noSuchAlgo) {
            System.out.println(" No Such Algorithm exists " + noSuchAlgo);
        }

        catch (NoSuchPaddingException noSuchPad) {
            System.out.println(" No Such Padding exists " + noSuchPad);
        }

        catch (InvalidKeyException invalidKey) {
            System.out.println(" Invalid Key " + invalidKey);
        }

        catch (BadPaddingException badPadding) {
            System.out.println(" Bad Padding " + badPadding);
        }

        catch (IllegalBlockSizeException illegalBlockSize) {
            System.out.println(" Illegal Block Size " + illegalBlockSize);
        }

        catch (InvalidAlgorithmParameterException invalidParam) {
            System.out.println(" Invalid Parameter " + invalidParam);
        }

        return strDecryptedText;
    }
}
