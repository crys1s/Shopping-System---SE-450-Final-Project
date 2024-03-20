package depaul.edu;

import java.util.HashMap;
import java.util.Map;

public class ProductCatalog {
    private static ProductCatalog instance;
    private Map<Integer, Product> products;
    private Map<Integer, Integer> inventory;

    private ProductCatalog() {
        products = new HashMap<>();
        inventory = new HashMap<>();
        // Add products to the catalog
        addProduct(1, "iPhone 15 Pro Max (Apple)", "The latest flagship smartphone from Apple with 5G connectivity.", 1099.0, 50);
        addProduct(2, "Dell XPS 13 Laptop", "A premium ultrabook with a 13.4-inch InfinityEdge display laptop.", 999.0, 50);
        addProduct(3, "Sony WH-1000XM4 Headphones", "High-end wireless headphones with exceptional sound quality.", 349.0, 50);
        addProduct(4, "Apple Watch Series 7", "The latest smartwatch from Apple, featuring a larger and more durable display.", 399.0, 50);
        addProduct(5, "Canon EOS R5 Mirrorless Camera", "A professional-grade mirrorless camera with a 45MP full-frame sensor.", 3899.0, 50);
        addProduct(6, "Fitbit Charge 5 Fitness Tracker", "A sleek fitness tracker.", 179.0, 50);
        addProduct(7, "JBL Flip 5 Bluetooth Speaker", "A portable Bluetooth speaker with powerful sound.", 119.0, 50);
        addProduct(8, "Samsung T7 Portable SSD", "A compact and fast external SSD with up to 2TB of storage capacity.", 109.0, 50);
        addProduct(9, "Apple AirPods Pro", "Premium wireless earbuds with active noise cancellation and immersive sound quality.", 249.0, 50);
        addProduct(10, "Oral-B iO Series 9 Electric Toothbrush", "An advanced electric toothbrush.", 299.0, 50);
        // Add more products here...
    }

    private void addProduct(int number, String name, String description, double price, int quantity) {
        Product product = new Product(name, description, price);
        products.put(number, product);
        inventory.put(number, quantity);
    }

    public static synchronized ProductCatalog getInstance() {
        if (instance == null) {
            instance = new ProductCatalog();
        }
        return instance;
    }

    public boolean isValidProduct(int productNumber) {
        return products.containsKey(productNumber);
    }

    public double getProductPrice(int productNumber) {
        return products.get(productNumber).getPrice();
    }

    public void displayProducts() {
        for (Map.Entry<Integer, Product> entry : products.entrySet()) {
            int number = entry.getKey();
            Product product = entry.getValue();
            int availableQuantity = getInventoryCount(number);
            System.out.println(number + ". " + product.getName() + " - $" + product.getPrice() +
                    " - Available: " + availableQuantity);
            System.out.println(product.getDescription());
            System.out.println();
        }
    }


    public int getInventoryCount(int productNumber) {
        if (inventory.containsKey(productNumber)) {
            return inventory.get(productNumber);
        }
        return 0;
    }

    public String getProductNameByNumber(int productNumber) {
        if (products.containsKey(productNumber)) {
            return products.get(productNumber).getName();
        }
        return null;
    }

    public void updateInventory(int productNumber, int quantity) {
        if (inventory.containsKey(productNumber)) {
            int currentQuantity = inventory.get(productNumber);
            inventory.put(productNumber, currentQuantity - quantity);
        }
    }


    static class Product {
        private String name;
        private String description;
        private double price;

        public Product(String name, String description, double price) {
            this.name = name;
            this.description = description;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public String getDescription() {
            return description;
        }
    }
}
