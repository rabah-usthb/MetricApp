package application.BackEnd;

import java.security.SecureRandom;
import java.time.LocalDateTime;


public class TokenGenerator {
    static final String[] ListAlphabetMin = "abcdefghijklmnopqrstuvwxyz".split("");
    static final String[] ListAlphabetMaj = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
    static final String[] ListSpecialChar = {"-","[","/","\\","@","'","+","?",":",";",")","*","^","]","%","£","!","_","{",">","~","#","<","}","("};
    static final String[] ListNumber = "0123456789".split("");
    private final String name;
    private final String email;
    private final String randomNum;

    public TokenGenerator(String name, String email) {
        this.name = name;
        this.email = email;
        this.randomNum = getRandomTokenNum();
    }

    public String Wrapper() {
        return getToken();
    }

    private String getToken() {
        String salt = getSalting();
        int thirdSize = salt.length() / 3;
        String firstPart = salt.substring(0, thirdSize);
        String secondPart = salt.substring(thirdSize, 2 * thirdSize);
        String thirdPart = salt.substring(2 * thirdSize);
        return firstPart + randomNum.charAt(0) + secondPart + randomNum.charAt(1) + thirdPart + randomNum.charAt(2);
    }

    private String getRandomTokenNum() {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        int day = now.getDayOfMonth();
        int month = now.getMonthValue();
        int second = now.getSecond();
        int minute = now.getMinute();
        int token = (day * 69 + hour / 2 + month + 20 + second * 34) / 3 + minute - name.length() + email.length();
        SecureRandom random = new SecureRandom();
        while (token < 100) {
            token = token + 1 + random.nextInt(10);
        }
        return String.format("%03d", token); 
    }

    private String getSalting() {
        StringBuilder salt = new StringBuilder();
        int num = Integer.parseInt(randomNum);
        int sum = 0;
        while (num > 0) {
            sum += (num % 10);
            num /= 10;
        }
        
        int length = sum / 2;

       
        while (length > 0) {
            salt.append(getRandomFunction());
            length--;
        }
        return salt.toString();
    }

    private static String getRandomFunction() {
    	int randomNum = 1 + new SecureRandom().nextInt(4);
        switch (randomNum) {
            case 1:
                return getRandomMin();
            case 2:
                return getRandomMaj();
            case 3:
                return getRandomSpecial();
            default:
                return getRandomNum();
        }
    }


    private static String getRandomSpecial() {
        return ListSpecialChar[new SecureRandom().nextInt(ListSpecialChar.length)];
    }

    private static String getRandomNum() {
        return ListNumber[new SecureRandom().nextInt(ListNumber.length)];
    }

    private static String getRandomMaj() {
        return ListAlphabetMaj[new SecureRandom().nextInt(ListAlphabetMaj.length)];
    }

    private static String getRandomMin() {
        return ListAlphabetMin[new SecureRandom().nextInt(ListAlphabetMin.length)];
    }


    public static void main(String[] args) {
        String email = "popolssa032@gmail.com";
        String name = "faithy_90";
        TokenGenerator toto = new TokenGenerator(name, email);
        System.out.println(toto.Wrapper());
    }
}
