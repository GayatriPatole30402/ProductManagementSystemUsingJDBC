package com.sit.Controller;

import com.sit.Model.*;
import java.util.Scanner;

public class ProductController {
    private ProductDAO productDAO = new ProductDAO();

    // Add product
    public void addProduct(Scanner scanner) {
        System.out.print("Enter Product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume the newline
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Product Type: ");
        String type = scanner.nextLine();
        System.out.print("Enter Product Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Product Quantity: ");
        int quantity = scanner.nextInt();

        Product product = new Product(id, name, type, price, quantity);
        if (productDAO.addProduct(product)) {
            System.out.println("Product added successfully.");
        } else {
            System.out.println("Error adding product.");
        }
    }

    // Display all products
    public void displayAllProducts() {
        var products = productDAO.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            products.forEach(product -> {
                System.out.println("ID: " + product.getId());
                System.out.println("Name: " + product.getName());
                System.out.println("Type: " + product.getType());
                System.out.println("Price: " + product.getPrice());
                System.out.println("Quantity: " + product.getQuantity());
                System.out.println("--------------");
            });
        }
    }

    // Find product by ID
    public void findProductById(Scanner scanner) {
        System.out.print("Enter Product ID to find: ");
        int id = scanner.nextInt();
        Product product = productDAO.findProductById(id);
        if (product != null) {
            System.out.println("ID: " + product.getId());
            System.out.println("Name: " + product.getName());
            System.out.println("Type: " + product.getType());
            System.out.println("Price: " + product.getPrice());
            System.out.println("Quantity: " + product.getQuantity());
        } else {
            System.out.println("Product not found.");
        }
    }

    // Update product by ID
    public void updateProduct(Scanner scanner) {
        System.out.print("Enter Product ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline after reading int

        Product existingProduct = productDAO.findProductById(id);
        if (existingProduct != null) {
            // Show existing product details
            System.out.println("Current Product Details:");
            System.out.println("ID: " + existingProduct.getId());
            System.out.println("Name: " + existingProduct.getName());
            System.out.println("Type: " + existingProduct.getType());
            System.out.println("Price: " + existingProduct.getPrice());
            System.out.println("Quantity: " + existingProduct.getQuantity());

            // Allow user to update product details
            System.out.print("Enter new Name (leave blank to keep current): ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                existingProduct.setName(name);
            }

            System.out.print("Enter new Type (leave blank to keep current): ");
            String type = scanner.nextLine();
            if (!type.isEmpty()) {
                existingProduct.setType(type);
            }

            System.out.print("Enter new Price (leave blank to keep current): ");
            String priceInput = scanner.nextLine();
            if (!priceInput.isEmpty()) {
                double price = Double.parseDouble(priceInput);
                existingProduct.setPrice(price);
            }

            System.out.print("Enter new Quantity (leave blank to keep current): ");
            String quantityInput = scanner.nextLine();
            if (!quantityInput.isEmpty()) {
                int quantity = Integer.parseInt(quantityInput);
                existingProduct.setQuantity(quantity);
            }

            // Now update the product in the database
            if (productDAO.updateProduct(existingProduct)) {
                System.out.println("Product updated successfully.");
            } else {
                System.out.println("Error updating product.");
            }
        } else {
            System.out.println("Product with ID " + id + " not found.");
        }
    }

    // method to delete product by its specific id
    public void deleteProductById(Scanner scanner) {
        System.out.print("Enter Product ID to delete: ");
        int id = scanner.nextInt();
        if (productDAO.deleteProductById(id)) {
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Error deleting product.");
        }
    }

}
