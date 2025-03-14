package com.example.service;

import com.example.model.Order;
import com.example.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order addOrder(Order order) {
        orderRepository.addOrder(order);
        return orderRepository.getOrderById(order.getId()); // Ensure it was saved
    }

    public ArrayList<Order> getOrders() {
        return orderRepository.getOrders();
    }
    public Order getOrderById(UUID orderId) {
        return orderRepository.getOrderById(orderId);
    }
    public boolean deleteOrderById(UUID orderId) {
        Order order = orderRepository.getOrderById(orderId);
        if (order == null) {
            return false; 
        }
        orderRepository.deleteOrderById(orderId);
        return true; 
    }

}