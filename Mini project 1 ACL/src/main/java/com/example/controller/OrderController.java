package com.example.controller;

import com.example.model.Order;
import com.example.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/")
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
        Order savedOrder = orderService.addOrder(order);
        if (savedOrder != null) {
            return ResponseEntity.ok(savedOrder);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable UUID orderId) {
        return orderService.getOrderById(orderId);
    }
    @GetMapping("/")
    public ArrayList<Order> getOrders() {
        return orderService.getOrders();
    }
    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<String> deleteOrderById(@PathVariable UUID orderId) {
        boolean deleted = orderService.deleteOrderById(orderId);

        if (deleted) {
            return ResponseEntity.ok("Order deleted successfully");
        } else {
            return ResponseEntity.ok("Order not found"); 
        }
    }


}