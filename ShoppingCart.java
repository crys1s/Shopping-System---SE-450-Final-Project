package depaul.edu;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private static ShoppingCart instance;
    private Map<Integer, Integer> items;

    private ShoppingCart() {
        items = new HashMap<>();
    }

    public static ShoppingCart getInstance() {
        if (instance == null) {
            instance = new ShoppingCart();
        }
        return instance;
    }

    public boolean addProduct(int productNumber, int quantity) {
        ProductCatalog catalog = ProductCatalog.getInstance();
        if (!catalog.isValidProduct(productNumber)) {
            System.out.println("Invalid product number.");
            return false;
        }

        int inventoryCount = catalog.getInventoryCount(productNumber);
        int currentQuantity = items.getOrDefault(productNumber, 0);
        if (currentQuantity + quantity > inventoryCount) {
            System.out.println("Cannot add " + quantity + " of product number " + productNumber + " to the cart. Only " + (inventoryCount - currentQuantity) + " available.");
            return false;
        }

        items.put(productNumber, currentQuantity + quantity);
        System.out.println("Added " + quantity + " of product number " + productNumber + " to the cart. Total Items in cart: " + items.get(productNumber));
      
        catalog.updateInventory(productNumber, quantity);
        
        return true;
    }

    public double calculateTotalAmount() {
        double totalAmount = 0;
        for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
            int productNumber = entry.getKey();
            int quantity = entry.getValue();
            double price = ProductCatalog.getInstance().getProductPrice(productNumber);
            totalAmount += price * quantity;
        }
        return totalAmount;
    }
    
    public int getTotalItems() {
        int totalItems = 0;
        for (int quantity : items.values()) {
            totalItems += quantity;
        }
        return totalItems;
    }


    public Map<Integer, Integer> getItems() {
        return items;
    }
    
    public void clearCart() {
        items.clear();
    }
}
