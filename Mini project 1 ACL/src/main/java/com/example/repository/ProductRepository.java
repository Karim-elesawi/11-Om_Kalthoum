package com.example.repository;

import com.example.model.Product;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class ProductRepository extends MainRepository<Product> {
    public ProductRepository() {
    }

    @Override
    protected String getDataPath() {
        return "src/main/java/com/example/data/products.json";
    }

    @Override
    protected Class<Product[]> getArrayType() {
        return Product[].class;
    }

    public Product addProduct(Product product) {
        product.setId(UUID.randomUUID()); // Assign a unique ID
        save(product); // Save product to JSON file
        return product;
    }
    public ArrayList<Product> getProducts() {
        return findAll();
    }
    public Product getProductById(UUID productId) {
        return getProducts().stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElse(null);
    }
    public Product updateProduct(UUID productId, String newName, double newPrice) {
        ArrayList<Product> products = getProducts();
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                product.setName(newName);
                product.setPrice(newPrice);
                overrideData(products); // Save updated products list
                return product;
            }
        }
        return null; // Product not found
    }
    public void applyDiscount(double discount, ArrayList<UUID> productIds) {
        ArrayList<Product> products = getProducts();
        for (Product product : products) {
            if (productIds.contains(product.getId())) {
                double newPrice = product.getPrice() * (1 - discount / 100.0);
                product.setPrice(newPrice);
            }
        }
        overrideData(products); // Save updated products list
    }
    public void deleteProductById(UUID productId) {
        ArrayList<Product> products = getProducts();
        products.removeIf(product -> product.getId().equals(productId));
        overrideData(products); // Save updated products list
    }
}
