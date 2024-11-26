package com.sit.View_or_Client;

import com.sit.Controller.*;
import java.util.Scanner;

public class ProductManagementSystem {
    private static ProductController controller = new ProductController();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(" --- Product Management System --- ");
            System.out.println("1. Add Product");
            System.out.println("2. Display All Products");
            System.out.println("3. Find Product By ID");
            System.out.println("4. Update Product By ID");
            System.out.println("5. Delete Product By ID");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    controller.addProduct(scanner);
                    break;
                case 2:
                    controller.displayAllProducts();
                    break;
                case 3:
                    controller.findProductById(scanner);
                    break;
                case 4:
                    controller.updateProduct(scanner);
                    break;
                case 5:
                    controller.deleteProductById(scanner);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close(); // Always close scanner when done
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
