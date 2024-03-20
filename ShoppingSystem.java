package depaul.edu;

import java.util.Map;
import java.util.Scanner;

public class ShoppingSystem {
    private static final LoggingSystem logger = LoggingSystem.getInstance();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (authenticateUser(username, password)) {
                System.out.println("Welcome, " + username + "!");
                logger.logLogin(username); // Log user login
                shoppingLoop(username, scanner);
            } else {
                System.out.println("Authentication failed. Please try again.");
            }
        }
    }

    private static boolean authenticateUser(String username, String password) {
        UserAuthentication userAuth = new UserAuthentication();
        return userAuth.authenticate(username, password);
    }


    private static void shoppingLoop(String username, Scanner scanner) {
        ShoppingCart cart = ShoppingCart.getInstance();

        while (true) {
            System.out.println("Available products:");
            ProductCatalog.getInstance().displayProducts();

            while (true) {
                try {
                    System.out.print("Enter the product number to add to cart, 0 to proceed to payment, " +
                                     "-1 to view cart contents, or -2 to view product catalogue again: ");
                    int choice = Integer.parseInt(scanner.nextLine());

                    if (choice == 0) {
                        if (cart.getItems().isEmpty()) {
                            System.out.println("Cart is empty. Please add products before proceeding to payment.");
                            continue; // Continue shopping
                        } else {
                            break; // Proceed to payment
                        }
                    } else if (choice == -1) {
                        System.out.println("Cart contents:");
                        for (Map.Entry<Integer, Integer> entry : cart.getItems().entrySet()) {
                            System.out.println("Product: " + ProductCatalog.getInstance().getProductNameByNumber(entry.getKey()) +
                                    " - Quantity: " + entry.getValue());
                        }
                        continue; // Continue shopping
                    } else if (choice == -2) {
                        System.out.println("Available products:");
                        ProductCatalog.getInstance().displayProducts();
                        continue; // Re-display products
                    } else {
                        if (!ProductCatalog.getInstance().isValidProduct(choice)) {
                            System.out.println("Invalid product number.");
                            continue; // Retry entering product number
                        }

                        System.out.print("Enter the quantity: ");
                        int quantity = Integer.parseInt(scanner.nextLine());

                        if (!cart.addProduct(choice, quantity)) {
                            System.out.println("Failed to add product to cart. Please try again.");
                        } else {
                            double totalAmount = calculateTotalAmount(cart);
                            System.out.println("Cart total: $" + totalAmount);
                            logger.logTransaction(username, "Add to Cart", ProductCatalog.getInstance().getProductNameByNumber(choice), quantity, totalAmount);
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }
            System.out.println("Cart contents:");
            for (Map.Entry<Integer, Integer> entry : cart.getItems().entrySet()) {
                System.out.println("Product: " + ProductCatalog.getInstance().getProductNameByNumber(entry.getKey()) +
                        " - Quantity: " + entry.getValue());
            }

            System.out.print("Proceed to payment (Y/N)? ");
            String proceed = scanner.nextLine();
            if (!proceed.equalsIgnoreCase("Y")) {
                if (proceed.equalsIgnoreCase("N")) {
                    continue; // Continue shopping
                } else {
                    break; // Exit the shopping loop
                }
            }

            double totalAmount = calculateTotalAmount(cart);
            if (totalAmount <= 0) {
                System.out.println("Total amount is $0.0. Please add products to the cart before proceeding to payment.");
                continue; // Continue shopping
            }

            System.out.println("Total amount: $" + totalAmount);
            PaymentProcessor.getInstance().processPayment(username, totalAmount);
            logger.logPayment(username,totalAmount);
            cart.clearCart(); // Empty the cart after successful purchase
            break; // Exit the shopping loop after the order is placed
        }

        System.out.print("Do you want to log out (Y/N)? ");
        String logout = scanner.nextLine();
        if (logout.equalsIgnoreCase("Y")) {
            System.out.println("Logged out.");
            cart.clearCart(); // Empty the cart
        } else {
            // Continue shopping with the same user
            shoppingLoop(username, scanner);
        }
    }


    private static double calculateTotalAmount(ShoppingCart cart) {
        double totalAmount = 0;
        for (Map.Entry<Integer, Integer> entry : cart.getItems().entrySet()) {
            int productNumber = entry.getKey();
            int quantity = entry.getValue();
            double price = ProductCatalog.getInstance().getProductPrice(productNumber);
            totalAmount += price * quantity;
        }
        return totalAmount;
    }
}
