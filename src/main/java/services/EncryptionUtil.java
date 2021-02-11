package services;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class EncryptionUtil {

    /**
     *
     * @param data
     * @param key
     * @param vector
     * @return String decrypted text
     * @throws Exception
     */
    public static String decryptUsingAesWithPadding(String data, String key, String vector) throws Exception {
        if (key == null || vector == null) {
            throw new IllegalArgumentException("key or vector is missing");
        }
        try {
            IvParameterSpec iv = new IvParameterSpec(vector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(data));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex);
        }
    }

    /**
     *
     * @param encryptedData
     * @param key
     * @param vector
     * @return aes encrypted String
     * @throws Exception
     */
    public static String  encryptUsingAesWithPadding( String encryptedData, String key, String vector) throws Exception {
        if(key==null || vector == null){
            throw new IllegalArgumentException("key or vector is missing");
        }
        try {
            IvParameterSpec iv = new IvParameterSpec(vector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(encryptedData.getBytes());
            String url = Base64.encodeBase64String(encrypted).replace("\n","").replace("\r","");

            System.out.println("encrypted string: " + url);
            return url;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex);
        }
    }

    /**
     *This function is used to decrypt aes encoded text
     * @param encryptedText The aes encrypted text
     * @param key byte array key of 16 bits is required
     * @return String decrypted text
     */
    public static  String decryptUsingAes(String encryptedText ,byte[] key) throws Exception {
        if (encryptedText == null) {
            return null;
        }
        try {
            SecretKeySpec ks = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, ks);
            byte[] encrypted = cipher.doFinal(Base64.decodeBase64(encryptedText.getBytes("UTF-8")));
            return new String(encrypted, "UTF-8");
        }
        catch (BadPaddingException bpx){
            bpx.printStackTrace();
            throw new BadPaddingException();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }


    /**
     *This function is used to encrypt aes encoded text
     * @param text The text
     * @param key byte array key of 16 bits is required
     * @return String encrypted text
     */
    public static  String encryptUsingAes(String text ,byte[] key) throws Exception {
        if (text == null) {
            return null;
        }
        try {
            SecretKeySpec ks = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, ks);
            byte[] encrypted = cipher.doFinal((text.getBytes("UTF-8")));
            return  Base64.encodeBase64String(encrypted).replace("\n","").replace("\r","");

//            return new String(encrypted, "UTF-8");
        }
        catch (BadPaddingException bpx){
            bpx.printStackTrace();
            throw new BadPaddingException();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

}
