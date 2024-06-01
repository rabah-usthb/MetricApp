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
    private static boolean IsMailUsed = false;
    private static boolean IsNameUsed = false;
    private static boolean UserExist = false;
    private static boolean PasswordExist = false;
    
    
    public static boolean GetExistUser() {
    	return 	UserExist;
    }
    
    public static boolean GetPasswordExist() {
    	return PasswordExist;
    }
    
    public static boolean GetIsMailUsed() {
    	return IsMailUsed;
    }
    
    public static boolean GetIsNameUsed() {
    	return IsNameUsed;
    }
    
    public static boolean InjectInDB(String userName, String email, String password) {
        userName= userName.replace(" ","");
        email = email.replace(" ", "");
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
    
    public static boolean UserExist(String User, String Password) {
    	System.out.println("hey");
    	UserExist = false;
    	PasswordExist =false;
    	User = User.replace(" ", "");
    	String sqlQuery = "SELECT username, email , password FROM public.\"User\" WHERE (username = ? OR email = ?) OR password = ?;";
    	try(Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
    		PreparedStatement pst = con.prepareStatement(sqlQuery);){
    		pst.setString(1, User);
    		pst.setString(2, User);
    		pst.setString(3, Password);
    		
    		 try (ResultSet rs = pst.executeQuery()) {
                 while (rs.next()) {
                	 System.out.println(User);
                	 
                     String fetchedUserName = rs.getString("username");
                     String fetchedEmail = rs.getString("email");
                     String fetchedPassword = rs.getString("password");
                     System.out.println(fetchedUserName);
                     if(fetchedUserName.equals(User)||fetchedEmail.equals(User)) {
                    	 UserExist = true;
                     }
                     if(fetchedPassword.equals(Password)) {
                    	 PasswordExist = true;
                     }
                    if((fetchedUserName.equals(User)||fetchedEmail.equals(User))&&fetchedPassword.equals(Password)) {
                    	return true;
                    }
                    
                 }
             }
    		
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return false;
    }
    
    public static boolean SqlFetchData(String UserName , String Email) {
   	   UserName = UserName.replace(" ", "");
   	   Email = Email.replace(" ", "");
    	IsMailUsed = false;
    	IsNameUsed =false;
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
                    if(fetchedUserName.equals(UserName)) {
                    	IsNameUsed =true;
                    }
                    if(fetchedEmail.equals(Email)) {
                    	IsMailUsed = true;
                    }
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
