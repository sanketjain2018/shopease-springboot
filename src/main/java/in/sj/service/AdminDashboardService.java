package in.sj.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import in.sj.repository.ProductRepository;
import in.sj.repository.UserRepository;
import in.sj.repository.OrderRepository;

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
}
