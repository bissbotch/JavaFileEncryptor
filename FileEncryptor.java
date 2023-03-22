package fileEncryptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

public class FileEncryptor {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: FileEncryptor <inputFilePath> <password>");
            System.exit(1);
        }

        String inputFile = args[0];
        String password = args[1];
        String encryptedFile = "path/to/encrypted/file";
        String decryptedFile = "path/to/decrypted/file";

        try {
            // Encrypt the input file
            encryptFile(inputFile, encryptedFile, password);
            System.out.println("File encrypted successfully");

            // Decrypt the encrypted file
            decryptFile(encryptedFile, decryptedFile, password);
            System.out.println("File decrypted successfully");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void encryptFile(String inputFile, String outputFile, String password) throws Exception {
        byte[] key = password.getBytes("UTF-8");
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        InputStream inputStream = new FileInputStream(inputFile);
        OutputStream outputStream = new CipherOutputStream(new FileOutputStream(outputFile), cipher);

        byte[] buffer = new byte[8192];
        int count;
        while ((count = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, count);
        }

        outputStream.close();
        inputStream.close();
    }

    public static void decryptFile(String inputFile, String outputFile, String password) throws Exception {
        byte[] key = password.getBytes("UTF-8");
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        InputStream inputStream = new CipherInputStream(new FileInputStream(inputFile), cipher);
        OutputStream outputStream = new FileOutputStream(outputFile);

        byte[] buffer = new byte[8192];
        int count;
        while ((count = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, count);
        }

        outputStream.close();
        inputStream.close();
    }
}
