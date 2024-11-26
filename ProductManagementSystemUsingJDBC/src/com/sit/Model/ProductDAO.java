package com.sit.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private Connection conn;

    public ProductDAO() {
        try {
            // Establish connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/productmanagementsystem", "root", "root");
        } catch (SQLException e) {
            System.out.println("Database Connection Error: " + e.getMessage());
        }
    }

    // Method to check if the product ID already exists in the database
    public boolean isProductIdExists(int id) {
        try {
            String query = "SELECT * FROM products WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            // Return true if the product with the given ID already exists
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error checking product ID: " + e.getMessage());
            return false;
        }
    }

    // Add product to the database
    public boolean addProduct(Product product) {
        try {
            // Check if product with the same ID already exists
            String checkQuery = "SELECT * FROM products WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, product.getId());
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                // ID already exists
                System.out.println("Error adding product: ID is already present.");
                return false;
            }

            // Insert new product
            String query = "INSERT INTO products (id, name, type, price, quantity) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, product.getId());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getType());
            stmt.setDouble(4, product.getPrice());
            stmt.setInt(5, product.getQuantity());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
            return false;
        }
    }

    // Find product by ID
    public Product findProductById(int id) {
        try {
            String query = "SELECT * FROM products WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(rs.getInt("id"), rs.getString("name"), rs.getString("type"), rs.getDouble("price"), rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            System.out.println("Error finding product: " + e.getMessage());
        }
        return null;
    }

    // Get all products
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try {
            String query = "SELECT * FROM products";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                products.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getString("type"), rs.getDouble("price"), rs.getInt("quantity")));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving products: " + e.getMessage());
        }
        return products;
    }
    
    // Update product in the database
    public boolean updateProduct(Product product) {
        try {
            String query = "UPDATE products SET name = ?, type = ?, price = ?, quantity = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getType());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getQuantity());
            stmt.setInt(5, product.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating product: " + e.getMessage());
            return false;
        }
    }

    // Delete product by ID
    public boolean deleteProductById(int id) {
        try {
            String query = "DELETE FROM products WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting product: " + e.getMessage());
            return false;
        }
    }
}
