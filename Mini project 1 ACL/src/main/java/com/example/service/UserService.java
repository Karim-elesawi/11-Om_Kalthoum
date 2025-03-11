package com.example.service;

import com.example.model.User;
import com.example.model.Order;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user) {
        return userRepository.addUser(user);
    }
    public ArrayList<User> getUsers() {
        return userRepository.getUsers();
    }
    public User getUserById(UUID userId) {
        return userRepository.getUserById(userId);
    }
    public List<Order> getOrdersByUserId(UUID userId) {
        return userRepository.getOrdersByUserId(userId);
    }
    public void addOrderToUser(UUID userId) {
        User user = userRepository.getUserById(userId);
        if (user != null) {
            Order newOrder = new Order(UUID.randomUUID(), userId, 0.0, new ArrayList<>()); // Empty order
            userRepository.addOrderToUser(userId, newOrder);
        }
    }
    public void emptyCart(UUID userId) {
        User user = userRepository.getUserById(userId);
        if (user != null) {
            user.getOrders().clear(); // Remove all products from cart
            userRepository.overrideData(userRepository.getUsers()); // Save changes
        }
    }
    public void removeOrderFromUser(UUID userId, UUID orderId) {
        userRepository.removeOrderFromUser(userId, orderId);
    }
    public void deleteUserById(UUID userId) {
        userRepository.deleteUserById(userId);
    }
}