package application.BackEnd;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Hashing {
    private String password;
    private String salt;

    Hashing(String Password, String Salt) {
        this.password = Password;
        this.salt = Salt;
    }

    Hashing(String Password) {
        this.password = Password;
        this.salt = generateSalt();
    }

    String getSalt() {
        return this.salt;
    }

    String hashWrapper() {
        return getHash();
    }

    private String getHash() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] saltBytes = new byte[12]; 
        sr.nextBytes(saltBytes);
        String encodedSalt = Base64.getEncoder().encodeToString(saltBytes);
        return encodedSalt.substring(0, 15);
    }

   
}
