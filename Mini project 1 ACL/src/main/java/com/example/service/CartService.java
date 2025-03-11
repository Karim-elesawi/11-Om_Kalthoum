package com.example.service;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.repository.CartRepository;
import com.example.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart addCart(Cart cart) {
        return cartRepository.addCart(cart);
    }

    public ArrayList<Cart> getCarts() {
        return cartRepository.getCarts();
    }

    public Cart getCartById(UUID cartId) {
        return cartRepository.getCartById(cartId);
    }

    public Cart getCartByUserId(UUID userId) {
        return cartRepository.getCartByUserId(userId);
    }

    public void addProductToCart(UUID userId, UUID productId) {
        Cart cart = cartRepository.getCartByUserId(userId);

        if (cart == null) {
            cart = new Cart();
            cart.setId(UUID.randomUUID());
            cart.setUserId(userId);
            cart.setProducts(new ArrayList<>());
            cartRepository.addCart(cart); 
        }

        Product product = productRepository.getProductById(productId);
        if (product != null) {
            cart.getProducts().add(product);

            ArrayList<Cart> allCarts = cartRepository.getCarts();
            for (int i = 0; i < allCarts.size(); i++) {
                if (allCarts.get(i).getId().equals(cart.getId())) {
                    allCarts.set(i, cart);
                    break;
                }
            }
            cartRepository.overrideData(allCarts);
        }
    }

    public void deleteProductFromCart(UUID userId, UUID productId) {
        Cart cart = cartRepository.getCartByUserId(userId);
        if (cart != null) {
            cart.getProducts().removeIf(p -> p.getId().equals(productId)); 
            cartRepository.overrideData(cartRepository.getCarts());
        }
    }

    public void deleteCartById(UUID cartId) {
        cartRepository.deleteCartById(cartId);
    }
}
