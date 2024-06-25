package application.BackEnd;
//Token PassowrdReset Table
//Function Update Password

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javafx.fxml.FXML;

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
    
    public static int IsRightToken(String email ,String Token) {
    	email = email.replace(" ", "");
    	LocalDateTime Now = LocalDateTime.now();
    	Timestamp NowSQL = Timestamp.valueOf(Now);
    	int IsSameToken = 0;
    	String SqlNestedQuery="SELECT expiresat,token FROM public.\"auth_tokens\" WHERE userid IN(SELECT userid FROM public.\"pendinguser\" WHERE email = ?);";
    	   try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                   
                   PreparedStatement PstQuery = con.prepareStatement(SqlNestedQuery)){
                   PstQuery.setString(1, email);
                   String fetchedToken="";
                   Timestamp ExpiresAt= null;
                   try (ResultSet rs = PstQuery.executeQuery()) {
                       while (rs.next()) {
                           fetchedToken = rs.getString("token");
                           ExpiresAt = rs.getTimestamp("expiresat");
                       }
                   }
           
                   if(NowSQL.after(ExpiresAt)) {
                	   IsSameToken=-1;
                   }
                   
                   else if(fetchedToken.equals(Token)) {
                	   IsSameToken = 1;
                   }
                   
                  
                  

              } catch (SQLException e) {
                  e.printStackTrace();
              }


    	
     return IsSameToken;
    }
    
    public static boolean UpdatePassword(String NewPassword,String email) {
    	email = email.replace(" ", "");
        boolean UpdatedSucessfull = false;
    	String SqlUpdate = "UPDATE public.\"User\" SET password = ? , salting = ? WHERE email = ?;";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement PstUpdate = con.prepareStatement(SqlUpdate)){
               Hashing hash = new Hashing(NewPassword);
               String Salting = hash.getSalt();
               String HashedPassword = hash.hashWrapper();
        	   PstUpdate.setString(1, HashedPassword);
        	   PstUpdate.setString(2,Salting);
        	   PstUpdate.setString(3, email);
               
               
               int rowsUpdated = PstUpdate.executeUpdate();
               if (rowsUpdated > 0) {
               	UpdatedSucessfull=true;
                   System.out.println("A new Row was Updated successfully!");
               } else {
                   System.out.println("No rows Updated.");
               }

           } catch (SQLException e) {
               e.printStackTrace();
           }

   
    	return UpdatedSucessfull;
    }
    
    public static boolean MailExist(String email) {
    	email = email.replace(" ", "");
    	boolean MailExisting=false;
    	String SqlQuery="SELECT email FROM public.\"User\" WHERE email = ?";

    	try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
           
                PreparedStatement PstQuery = con.prepareStatement(SqlQuery)){
                PstQuery.setString(1, email);
                
                try (ResultSet rs = PstQuery.executeQuery()) {
                    while (rs.next()) {
                        MailExisting = true;    
                    }
                }
       
                
           } catch (SQLException e) {
               e.printStackTrace();
           }

    	return MailExisting;
    }
    
    public static boolean InjectTokenReset(String email,String Token) {
    	email = email.replace(" ", "");	
    	boolean InjectionSuccessfull = false;
    	String SqlInsert = "INSERT INTO public.\"reset_password_tokens\" (token,expiresat,userid) VALUES(?,?,?); ";
        String SqlQuery="SELECT userid FROM public.\"User\" WHERE email = ?";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = createdAt.plusHours(1);  // 1-hour expiry
        
    	
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement PstInsert = con.prepareStatement(SqlInsert); 
                PreparedStatement PstQuery = con.prepareStatement(SqlQuery)){
                PstQuery.setString(1, email);
                int fetchedID=0;
                try (ResultSet rs = PstQuery.executeQuery()) {
                    while (rs.next()) {
                        fetchedID = rs.getInt("userid");    
                    }
                }
       
                
               PstInsert.setString(1, Token);
               PstInsert.setTimestamp(2, java.sql.Timestamp.valueOf(expiresAt));
               PstInsert.setInt(3, fetchedID);
               
               int rowsAffected = PstInsert.executeUpdate();
               if (rowsAffected > 0) {
               	InjectionSuccessfull=true;
                   System.out.println("A new token was inserted successfully!");
               } else {
                   System.out.println("No rows affected.");
               }

           } catch (SQLException e) {
               e.printStackTrace();
           }

    	
    	return InjectionSuccessfull;

    }
    
    
    public static boolean InjectToken(String UserName , String email,String Token) {
    	UserName = UserName.replace(" ", "");
    	email = email.replace(" ", "");
    	boolean InjectionSuccessfull = false;
    	String SqlInsert = "INSERT INTO public.\"auth_tokens\" (token,createdat,expiresat,userid) VALUES(?,?,?,?); ";
        String SqlQuery="SELECT pendinguserid FROM public.\"pendinguser\" WHERE email = ?";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = createdAt.plusHours(1);  // 1-hour expiry
        
    	
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement PstInsert = con.prepareStatement(SqlInsert); 
                PreparedStatement PstQuery = con.prepareStatement(SqlQuery)){
                PstQuery.setString(1, email);
                int fetchedID=0;
                try (ResultSet rs = PstQuery.executeQuery()) {
                    while (rs.next()) {
                        fetchedID = rs.getInt("pendinguserid");    
                    }
                }
       
                
               PstInsert.setString(1, Token);
               PstInsert.setTimestamp(2, java.sql.Timestamp.valueOf(createdAt));
               PstInsert.setTimestamp(3, java.sql.Timestamp.valueOf(expiresAt));
               PstInsert.setInt(4, fetchedID);
               
               int rowsAffected = PstInsert.executeUpdate();
               if (rowsAffected > 0) {
               	InjectionSuccessfull=true;
                   System.out.println("A new token was inserted successfully!");
               } else {
                   System.out.println("No rows affected.");
               }

           } catch (SQLException e) {
               e.printStackTrace();
           }

    	
    	return InjectionSuccessfull;
    }
    
    public static boolean InjectPendingUser(String UserName,String email,String Password) {
    	System.out.println("Injectin Pending User");
    	UserName = UserName.replace(" ", "");
    	email = email.replace(" ", "");
    	boolean InjectionSuccessfull = false;
    	String SqlInsert = "INSERT INTO public.\"pendinguser\" (username , email , password ,salting) VALUES(?,?,?,?);";
    	Hashing hash = new Hashing(Password);
    	String HashedPassword =hash.hashWrapper();
    	String Salting = hash.getSalt();

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement pst = con.prepareStatement(SqlInsert)) {

               pst.setString(1, UserName);
               pst.setString(2, email);
               pst.setString(3, HashedPassword);
               pst.setString(4, Salting);
               
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
    
    public static boolean InjectInDB(String userName, String email) {
        userName= userName.replace(" ","");
        email = email.replace(" ", "");
    	boolean InjectionSuccessfull = false;
    	String Role = "user";
    	String sqlInsert = "INSERT INTO public.\"User\" (username, email, password,role,salting) VALUES (?, ?, ?,?,?);";
        String SqlQuery = "SELECT password , salting FROM public.\"pendinguser\" WHERE email = ?;";
        String SqlDel = "DELETE FROM public.\"pendinguser\" WHERE email = ? ";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement PstInsert = con.prepareStatement(sqlInsert);
        	 PreparedStatement PstQuery = con.prepareStatement(SqlQuery);
        	 PreparedStatement PstDel = con.prepareStatement(SqlDel);) {
            
        	PstQuery.setString(1,email);
        	String HashedPassword="";
        	String Salting="";
        	try (ResultSet rs = PstQuery.executeQuery()) {
                while (rs.next()) {
                    HashedPassword = rs.getString("password");
                    Salting = rs.getString("salting");
                }
            }
            PstInsert.setString(1, userName);
            PstInsert.setString(2, email);
            PstInsert.setString(3, HashedPassword);
            PstInsert.setString(4, Role);
            PstInsert.setString(5, Salting);
            
            int rowsAffected = PstInsert.executeUpdate();
            if (rowsAffected > 0) {
            	InjectionSuccessfull=true;
                System.out.println("A new user was inserted successfully!");
            } else {
                System.out.println("No rows affected.");
            }
            
            PstDel.setString(1, email);
            int rowDeleted = PstDel.executeUpdate();
            if(rowDeleted==1) {
            	System.out.println("Deleted Pending Succecfully");
            }
            else {
            	System.out.println("ERROR");
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
    	String sqlQuery = "SELECT username, email , password,salting FROM public.\"User\" WHERE (username = ? OR email = ?) OR password = ?;";
    	try(Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
    		PreparedStatement pst = con.prepareStatement(sqlQuery);){
    		pst.setString(1, User);
    		pst.setString(2, User);
    		pst.setString(3, Password);
    		
    		 try (ResultSet rs = pst.executeQuery()) {
                 while (rs.next()) {
                	 String fetchedUserName = rs.getString("username");
                     String fetchedEmail = rs.getString("email");
                     String fetchedPassword = rs.getString("password");
                     String Salt = rs.getString("salting");
                     Hashing hash = new Hashing(Password,Salt);
                     String HashedPassword = hash.hashWrapper();
                     if(fetchedUserName.equals(User)||fetchedEmail.equals(User)) {
                    	 UserExist = true;
                     }
                     if(fetchedPassword.equals(HashedPassword)) {
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
    
    public static void deleteExpiredTokens() {
        String sqlDelete = "DELETE FROM public.\"auth_tokens\" WHERE ExpiresAt < NOW()";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pst = con.prepareStatement(sqlDelete)) {

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " expired tokens were deleted successfully!");
            } else {
                System.out.println("No expired tokens found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
