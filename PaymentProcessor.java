package depaul.edu;

import java.util.Scanner;

public class PaymentProcessor {

    private static PaymentProcessor instance;

    private PaymentProcessor() {
        // Private constructor to prevent instantiation
    }

    public static synchronized PaymentProcessor getInstance() {
        if (instance == null) {
            instance = new PaymentProcessor();
        }
        return instance;
    }

    public void processPayment(String username, double amount) {
        // Simulate asking for card details
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your card details to complete the payment:");

        // Validate and set up card number
        String cardNumber;
        do {
            System.out.print("Card Number (16 digits): ");
            cardNumber = scanner.nextLine();
            if (!isValidCardNumber(cardNumber)) {
                System.out.println("Invalid card number. Please enter a 16-digit card number.");
            }
        } while (!isValidCardNumber(cardNumber));

        // Validate and set up expiration date
        String expirationDate;
        do {
            System.out.print("Expiration Date (MM/YYYY): ");
            expirationDate = scanner.nextLine();
            if (!isValidExpirationDate(expirationDate)) {
                System.out.println("Invalid expiration date. Please enter the date in MM/YYYY format.");
            }
        } while (!isValidExpirationDate(expirationDate));

        // Validate and set up CVV
        String cvv;
        do {
            System.out.print("CVV (3 digits): ");
            cvv = scanner.nextLine();
            if (!isValidCVV(cvv)) {
                System.out.println("Invalid CVV. Please enter a 3-digit CVV.");
            }
        } while (!isValidCVV(cvv));

        // Simulate payment processing
        System.out.println("Processing payment for user " + username + "...");
        System.out.println("Payment of $" + amount + " processed successfully.");
    }

    private boolean isValidCardNumber(String cardNumber) {
        // Add validation logic for card number (e.g., length, format)
        return cardNumber.length() == 16 && cardNumber.matches("\\d{16}");
    }

    private boolean isValidExpirationDate(String expirationDate) {
        // Add validation logic for expiration date (e.g., format, future date)
        return expirationDate.matches("^(0[1-9]|1[0-2])/20\\d{2}$");
    }

    private boolean isValidCVV(String cvv) {
        // Add validation logic for CVV (e.g., length, format)
        return cvv.length() == 3 && cvv.matches("\\d{3}");
    }
}
