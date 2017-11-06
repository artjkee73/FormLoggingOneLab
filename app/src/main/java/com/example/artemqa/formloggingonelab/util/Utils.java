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
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by artemqa on 28.10.2017.
 */

public class Utils {
    public final static String ACCESS_APPLICATION_KEY = "12345678901" ;
    public final static String ADMIN_LOGIN = "ADMIN";
    public final static String ADMIN_PASSWORD = "1";
    public final static boolean ADMIN_IS_BLOCKED = false;
    public final static boolean ADMIN_IS_LIMITATION = false;
    public final static String NEW_USER_PASSWORD = "";
    public final static boolean NEW_USER_IS_BLOCKED = false;
    public final static boolean NEW_USER_IS_LIMITATION = false;
    public final static String LOG = "MyLog";


    public static String toMD4(final String s) {
        final String MD4 = "MD4";
        try {
            // Create MD4 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD4);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
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
            byte[] decodedKey = Base64.decode(Utils.ACCESS_APPLICATION_KEY, Base64.DEFAULT);
            SecretKey secretKey = new SecretKeySpec(decodedKey, 0,
                    decodedKey.length, "DES");

            Cipher desCipher = Cipher.getInstance("DES/OFB/NoPadding");

            desCipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] byteDataToEncrypt = encryptingStr.getBytes();
            byte[] byteCipherText = desCipher.doFinal(byteDataToEncrypt);
            strCipherText = Base64.encodeToString(byteCipherText, Base64.DEFAULT);
            Log.d(LOG, " EncryptingStringText :" + strCipherText);
        }catch (NoSuchAlgorithmException noSuchAlgo)
            {
                System.out.println(" No Such Algorithm exists " + noSuchAlgo);
            }

        catch (NoSuchPaddingException noSuchPad)
            {
                System.out.println(" No Such Padding exists " + noSuchPad);
            }

        catch (InvalidKeyException invalidKey)
            {
                System.out.println(" Invalid Key " + invalidKey);
            }

        catch (BadPaddingException badPadding)
            {
                System.out.println(" Bad Padding " + badPadding);
            }

        catch (IllegalBlockSizeException illegalBlockSize)
            {
                System.out.println(" Illegal Block Size " + illegalBlockSize);
            }

        return strCipherText;
    }

    public static String decryptStr(String decryptStr){

        String strDecryptedText = null;
        try {
            byte[] decodedKey = Base64.decode(Utils.ACCESS_APPLICATION_KEY, Base64.DEFAULT);
            SecretKey secretKey = new SecretKeySpec(decodedKey, 0,
                    decodedKey.length, "DES");

            Cipher desCipher = Cipher.getInstance("DES/OFB/NoPadding");
            desCipher.init(Cipher.DECRYPT_MODE,secretKey,desCipher.getParameters());
            //desCipher.init(Cipher.DECRYPT_MODE,secretKey);

            byte[] byteCipherText = decryptStr.getBytes();
            byte[] byteDecryptedText = desCipher.doFinal(byteCipherText);
            strDecryptedText = new String(byteDecryptedText);
            System.out.println(" Decrypted Text message is " +strDecryptedText);
        }

        catch (NoSuchAlgorithmException noSuchAlgo)
        {
            System.out.println(" No Such Algorithm exists " + noSuchAlgo);
        }

        catch (NoSuchPaddingException noSuchPad)
        {
            System.out.println(" No Such Padding exists " + noSuchPad);
        }

        catch (InvalidKeyException invalidKey)
        {
            System.out.println(" Invalid Key " + invalidKey);
        }

        catch (BadPaddingException badPadding)
        {
            System.out.println(" Bad Padding " + badPadding);
        }

        catch (IllegalBlockSizeException illegalBlockSize)
        {
            System.out.println(" Illegal Block Size " + illegalBlockSize);
        }

        catch (InvalidAlgorithmParameterException invalidParam)
        {
            System.out.println(" Invalid Parameter " + invalidParam);
        }

        return strDecryptedText;
    }
}

///**
// * @author Joe Prasanna Kumar
// * This program provides the following cryptographic functionalities
// * 1. Encryption using DES
// * 2. Decryption using DES
// *
// * The following modes of DES encryption are supported by SUNJce provider
// * 1. ECB (Electronic code Book) - Every plaintext block is encrypted separately
// * 2. CBC (Cipher Block Chaining) - Every plaintext block is XORed with the previous ciphertext block
// * 3. PCBC (Propogating Cipher Block Chaining) -
// * 4. CFB (Cipher Feedback Mode) - The previous ciphertext block is encrypted and this enciphered block is XORed with the plaintext block to produce the corresponding ciphertext block
// * 5. OFB (Output Feedback Mode) -
// *
// *	High Level Algorithm :
// * 1. Generate a DES key
// * 2. Create the Cipher (Specify the Mode and Padding)
// * 3. To Encrypt : Initialize the Cipher for Encryption
// * 4. To Decrypt : Initialize the Cipher for Decryption
// *
// * Need for Padding :
// * Block ciphers operates on data blocks on fixed size n.
// * Since the data to be encrypted might not always be a multiple of n, the remainder of the bits are padded.
// * PKCS#5 Padding is what will be used in this program
// *
// */
//
// class DES {
//    public static void main(String[] args) {
//
//        String strDataToEncrypt ;
//        String strCipherText ;
//        String strDecryptedText ;
//
//        try{
//            /**
//             *  Step 1. Generate a DES key using KeyGenerator
//             *
//             */
//            String keyString = Utils.ACCESS_APPLICATION_KEY;
//            byte[] decodedKey = Base64.decode(keyString, Base64.DEFAULT);
//            SecretKey secretKey = new SecretKeySpec(decodedKey, 0,
//                    decodedKey.length, "DES");
//
//            /**
//             *  Step2. Create a Cipher by specifying the following parameters
//             * 			a. Algorithm name - here it is DES
//             * 			b. Mode - here it is CBC
//             * 			c. Padding - PKCS5Padding
//             */
//
//            Cipher desCipher = Cipher.getInstance("DES/OFB/NoPadding"); /* Must specify the mode explicitly as most JCE providers default to ECB mode!! */
//
//            /**
//             *  Step 3. Initialize the Cipher for Encryption
//             */
//
//            desCipher.init(Cipher.ENCRYPT_MODE,secretKey);
//
//            /**
//             *  Step 4. Encrypt the Data
//             *  		1. Declare / Initialize the Data. Here the data is of type String
//             *  		2. Convert the Input Text to Bytes
//             *  		3. Encrypt the bytes using doFinal method
//             */
//            strDataToEncrypt = "Hello World of Encryption using DES ";
//            byte[] byteDataToEncrypt = strDataToEncrypt.getBytes();
//            byte[] byteCipherText = desCipher.doFinal(byteDataToEncrypt);
//            strCipherText =  Base64.encodeToString(byteCipherText, Base64.DEFAULT);
//            System.out.println("Cipher Text generated using DES with CBC mode and PKCS5 Padding is " + strCipherText);
//
//            /**
//             *  Step 5. Decrypt the Data
//             *  		1. Initialize the Cipher for Decryption
//             *  		2. Decrypt the cipher bytes using doFinal method
//             */
//            desCipher.init(Cipher.DECRYPT_MODE,secretKey,desCipher.getParameters());
//            //desCipher.init(Cipher.DECRYPT_MODE,secretKey);
//            byte[] byteDecryptedText = desCipher.doFinal(byteCipherText);
//            strDecryptedText = new String(byteDecryptedText);
//            System.out.println(" Decrypted Text message is " +strDecryptedText);
//        }
//
//        catch (NoSuchAlgorithmException noSuchAlgo)
//        {
//            System.out.println(" No Such Algorithm exists " + noSuchAlgo);
//        }
//
//        catch (NoSuchPaddingException noSuchPad)
//        {
//            System.out.println(" No Such Padding exists " + noSuchPad);
//        }
//
//        catch (InvalidKeyException invalidKey)
//        {
//            System.out.println(" Invalid Key " + invalidKey);
//        }
//
//        catch (BadPaddingException badPadding)
//        {
//            System.out.println(" Bad Padding " + badPadding);
//        }
//
//        catch (IllegalBlockSizeException illegalBlockSize)
//        {
//            System.out.println(" Illegal Block Size " + illegalBlockSize);
//        }
//
//        catch (InvalidAlgorithmParameterException invalidParam)
//        {
//            System.out.println(" Invalid Parameter " + invalidParam);
//        }
//    }
//
//}