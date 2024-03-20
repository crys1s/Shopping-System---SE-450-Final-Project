package depaul.edu;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggingSystem {

    private static LoggingSystem instance;
    private FileWriter writer;

    private LoggingSystem() {
        try {
            writer = new FileWriter("transaction_log.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized LoggingSystem getInstance() {
        if (instance == null) {
            instance = new LoggingSystem();
        }
        return instance;
    }

    public synchronized void logLogin(String username) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        try {
            writer.write(formattedDateTime + " - User: " + username + " logged in.\n");
            writer.flush(); // Ensure that the data is written immediately
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void logTransaction(String username, String action, String productName, int quantity, double amount) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        try {
            writer.write(formattedDateTime + " - User: " + username + ", Action: " + action +
                    " - Product: " + productName + ", Quantity: " + quantity + ", Total Cart Value: $" + amount + "\n");
            writer.flush(); // Ensure that the data is written immediately
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public synchronized void logPayment(String username, double totalAmount) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        try {
            writer.write(formattedDateTime + " - User: " + username + ", Action: Payment, Amount: $" + totalAmount + "\n");
            writer.flush(); // Ensure that the data is written immediately
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public synchronized void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
