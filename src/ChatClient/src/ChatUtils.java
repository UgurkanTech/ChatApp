import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/*
 * Useful utilities.
 */

public class ChatUtils {
	/*
	 * Password Manager.
	 */
	public static class Password{
		private static String salt = "ChatApp";
		private static String secret = "SmolKitten";
		
		public static String toHash(String password) {
			return ChatUtils.AES.encrypt(password + salt, secret);
 
		}
		
		public static String toPassword(String hash) {
			return ChatUtils.AES.decrypt(hash, secret);

		}
	}
	
	
	public static class Serializer{
		
		public static byte[] serialize(Object obj) throws IOException {
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    ObjectOutputStream os = new ObjectOutputStream(out);
		    os.writeObject(obj);
		    return out.toByteArray();
		}
		
		public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
		    ByteArrayInputStream in = new ByteArrayInputStream(data);
		    ObjectInputStream is = new ObjectInputStream(in);
		    return is.readObject();
		}
		
	}
	/*
	 * Gzip compressor
	 */

	public static class Compressor{
		
		public static byte[] Compress(byte[] uncompressedData) {
	        byte[] result = new byte[]{};
	        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(uncompressedData.length);
	            GZIPOutputStream2 gzipOS = new GZIPOutputStream2(bos)) {
	            gzipOS.setLevel(Deflater.DEFAULT_COMPRESSION); //compression level
	            gzipOS.write(uncompressedData);
	            gzipOS.close();
	            result = bos.toByteArray();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
	
		public static byte[] Uncompress(byte[] compressedData) {
	        byte[] result = new byte[]{};
	        try (ByteArrayInputStream bis = new ByteArrayInputStream(compressedData);
	             ByteArrayOutputStream bos = new ByteArrayOutputStream();
	             GZIPInputStream gzipIS = new GZIPInputStream(bis)) {
	            byte[] buffer = new byte[1024];
	            int len;
	            while ((len = gzipIS.read(buffer)) != -1) {
	                bos.write(buffer, 0, len);
	            }
	            result = bos.toByteArray();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
		
		private static class GZIPOutputStream2 extends GZIPOutputStream {

		    public GZIPOutputStream2(ByteArrayOutputStream out) throws IOException {
		        super(out);
		    } 
	
		    public void setLevel(int level) {
		        def.setLevel(level);
		    }
		}
		
	}
	
	/*
	 * AES Encryption
	 */
	public static class AES {
		 
	 
	    private static SecretKeySpec setKey(String myKey) 
	    {
	    	byte[] key;
	        MessageDigest sha = null;
	        try {
	            key = myKey.getBytes("UTF-8");
	            sha = MessageDigest.getInstance("SHA-1");
	            key = sha.digest(key);
	            key = Arrays.copyOf(key, 16); 
	            return new SecretKeySpec(key, "AES");
	        } 
	        catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        } 
	        catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
	    public static String encrypt(String stringToEncrypt, String secret) 
	    {
	        try
	        {
	        	SecretKeySpec secretKey = setKey(secret);
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	            return Base64.getEncoder().encodeToString(cipher.doFinal(stringToEncrypt.getBytes("UTF-8")));
	        } 
	        catch (Exception e) 
	        {
	            System.out.println("Error while encrypting: " + e.toString());
	        }
	        return null;
	    }
	    
	    public static String decrypt(String stringToDecrypt, String secret) 
	    {
	        try
	        {
	        	SecretKeySpec secretKey = setKey(secret);
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
	            cipher.init(Cipher.DECRYPT_MODE, secretKey);
	            return new String(cipher.doFinal(Base64.getDecoder().decode(stringToDecrypt)));
	        } 
	        catch (Exception e) 
	        {
	            System.out.println("Error while decrypting: " + e.toString());
	        }
	        return null;
	    }
	    
	    public static byte[] encrypt(byte[] bytesToEncrypt, String secret) 
	    {
	        try
	        {
	        	SecretKeySpec secretKey = setKey(secret);
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	            return cipher.doFinal(bytesToEncrypt);
	        } 
	        catch (Exception e) 
	        {
	            System.out.println("Error while encrypting: " + e.toString());
	        }
	        return null;
	    }
	 
	    public static byte[] decrypt(byte[] bytesToDecrypt, String secret) 
	    {
	        try
	        {
	        	SecretKeySpec secretKey = setKey(secret);
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
	            cipher.init(Cipher.DECRYPT_MODE, secretKey);
	            return cipher.doFinal(bytesToDecrypt);
	        } 
	        catch (Exception e) 
	        {
	            System.out.println("Error while decrypting: " + e.toString());
	        }
	        return null;
	    }
	}
	
	
	
}
