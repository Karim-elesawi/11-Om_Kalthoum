package com.example.repository;

import com.example.model.User;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.UUID;
import com.example.model.Order;
import java.util.List;

@Repository
@SuppressWarnings("rawtypes")
public class UserRepository extends MainRepository<User>{
    public UserRepository() {
    }

    @Override
    protected String getDataPath() {
        return "src/main/java/com/example/data/users.json";
    }

    @Override
    protected Class<User[]> getArrayType() {
        return User[].class;
    }

    public ArrayList<User> getUsers() {
        return findAll();
    }
    public User getUserById(UUID userId) {
        return getUsers().stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElse(null);
    }
    public User addUser(User user) {
        if (user.getId() == null) { 
            user.setId(UUID.randomUUID());
        }
        save(user);
        return user;
    }
    public List<Order> getOrdersByUserId(UUID userId) {
        User user = getUserById(userId);
        return (user != null) ? user.getOrders() : new ArrayList<>();
    }
    public void addOrderToUser(UUID userId, Order order) {
        User user = getUserById(userId);
        if (user != null) {
            user.getOrders().add(order);
            overrideData(getUsers());
        }
    }
    public void removeOrderFromUser(UUID userId, UUID orderId) {
        User user = getUserById(userId);
        if (user != null) {
            user.getOrders().removeIf(order -> order.getId().equals(orderId));
            overrideData(getUsers());
        }
    }
    public void deleteUserById(UUID userId) {
        ArrayList<User> users = getUsers();
        users.removeIf(user -> user.getId().equals(userId));
        overrideData(users);
    }
}
