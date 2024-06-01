package application.BackEnd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLBackEnd {
	private static final String DB_URL = System.getenv("MetricDB_URL");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
    
    
    public static boolean InjectInDB(String userName, String email, String password) {
        boolean InjectionSuccessfull = false;
    	String Role = "user";
    	String sqlInsert = "INSERT INTO public.\"User\" (username, email, password,role) VALUES (?, ?, ?,?);";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pst = con.prepareStatement(sqlInsert)) {

            pst.setString(1, userName);
            pst.setString(2, email);
            pst.setString(3, password);
            pst.setString(4, Role);
            
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
            	InjectionSuccessfull=true;
                System.out.println("A new user was inserted successfully!");
            } else {
                System.out.println("No rows affected.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return InjectionSuccessfull;
    }
    
    public static boolean SqlFetchData(String UserName , String Email) {
   	 String sqlQuery = "SELECT username, email FROM public.\"User\" WHERE username = ? OR email = ?;";
        boolean AlreadyUsed = false;
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pst = con.prepareStatement(sqlQuery)) {

            pst.setString(1, UserName);
            pst.setString(2, Email);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    String fetchedUserName = rs.getString("username");
                    String fetchedEmail = rs.getString("email");
                    System.out.println("Username: " + fetchedUserName + ", Email: " + fetchedEmail);
                    AlreadyUsed = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return AlreadyUsed;
    }
    
    
    public String GetURL() {
    	return DB_URL;
    }
    
    public String GetUSER() {
    	return DB_USER;
    }
    
    public String GetPASSWORD() {
    	return DB_PASSWORD;
    }
}
