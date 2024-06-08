package application.BackEnd;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TokenCleaner {


    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            System.out.println("Running task to delete expired tokens...");
            SQLBackEnd.deleteExpiredTokens();
        };

        // Schedule the task to run every hour
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.HOURS);
    }

    
}

