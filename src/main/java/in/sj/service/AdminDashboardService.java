package in.sj.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.sj.entity.Order;
import in.sj.repository.OrderRepository;
import in.sj.repository.ProductRepository;
import in.sj.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public long getTotalProducts() {
        return productRepository.count();
    }

    public long getTotalUsers() {
        return userRepository.count();
    }

    public long getTotalOrders() {
        return orderRepository.count();
    }

    public Double getTotalRevenue() {
        return orderRepository.getTotalRevenue();
    }
    
    public List<Object[]> getMonthlySales() {
        return orderRepository.findMonthlySales();
    }

    // ================= REPORT DATA =================

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
