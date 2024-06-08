package application.BackEnd;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

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
    
    public static boolean IsRightToken(String email ,String Token) {
    	email = email.replace(" ", "");
    	boolean IsSameToken = false;
    	String SqlNestedQuery="SELECT token FROM public.\"Auth_Tokens\" WHERE userid IN(SELECT userid FROM public.\"PendingUser\" WHERE email = ?);";
    	   try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                   
                   PreparedStatement PstQuery = con.prepareStatement(SqlNestedQuery)){
                   PstQuery.setString(1, email);
                   String fetchedToken="";
                   try (ResultSet rs = PstQuery.executeQuery()) {
                       while (rs.next()) {
                           fetchedToken = rs.getString("token");    
                       }
                   }
           
                   if(fetchedToken.equals(Token)) {
                	   IsSameToken = true;
                   }
                   
                  
                  

              } catch (SQLException e) {
                  e.printStackTrace();
              }


    	
     return IsSameToken;
    }
    
    public static boolean InjectToken(String UserName , String email) {
    	UserName = UserName.replace(" ", "");
    	email = email.replace(" ", "");
    	boolean InjectionSuccessfull = false;
    	String SqlInsert = "INSERT INTO public.\"Auth_Tokens\" (token,createdat,expiresat,userid) VALUES(?,?,?,?); ";
        String SqlQuery="SELECT email FROM public.\"PendingUser\" WHERE email = ?";
    	
    	TokenGenerator TOKENGEN = new TokenGenerator(UserName, email);
    	String Token = TOKENGEN.Wrapper();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = createdAt.plusHours(1);  // 1-hour expiry
        
    	
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement PstInsert = con.prepareStatement(SqlInsert); 
                PreparedStatement PstQuery = con.prepareStatement(SqlQuery)){
                PstQuery.setString(1, email);
                String fetchedEmail="";
                try (ResultSet rs = PstQuery.executeQuery()) {
                    while (rs.next()) {
                        fetchedEmail = rs.getString("email");    
                    }
                }
       
                
               PstInsert.setString(1, Token);
               PstInsert.setTimestamp(2, java.sql.Timestamp.valueOf(createdAt));
               PstInsert.setTimestamp(3, java.sql.Timestamp.valueOf(expiresAt));
               PstInsert.setString(4, fetchedEmail);
               
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
    	UserName = UserName.replace(" ", "");
    	email = email.replace(" ", "");
    	boolean InjectionSuccessfull = false;
    	String SqlInsert = "INSERT INTO public.\"PendingUser\" (username , email , password ,salting) VALUES(?,?,?,?);";
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
    
    public static boolean InjectInDB(String userName, String email, String password) {
        userName= userName.replace(" ","");
        email = email.replace(" ", "");
    	boolean InjectionSuccessfull = false;
    	String Role = "user";
    	String sqlInsert = "INSERT INTO public.\"User\" (username, email, password,role,salting) VALUES (?, ?, ?,?,?);";
        Hashing hash = new Hashing(password);
        String HashedPassword = hash.hashWrapper();
        String Salting = hash.getSalt();
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pst = con.prepareStatement(sqlInsert)) {

            pst.setString(1, userName);
            pst.setString(2, email);
            pst.setString(3, HashedPassword);
            pst.setString(4, Role);
            pst.setString(5, Salting);
            
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
