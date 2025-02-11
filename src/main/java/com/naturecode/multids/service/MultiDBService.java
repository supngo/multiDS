package com.naturecode.multids.service;

import org.springframework.stereotype.Service;

import com.naturecode.multids.model.datasource1.User;
import com.naturecode.multids.model.datasource2.Order;
import com.naturecode.multids.repo.datasource1.UserRepo;
import com.naturecode.multids.repo.datasource2.OrderRepo;

@Service
public class MultiDBService {
  private final UserRepo userRepository;
  private final OrderRepo orderRepository;

  public MultiDBService(UserRepo userRepository, OrderRepo orderRepository) {
    this.userRepository = userRepository;
    this.orderRepository = orderRepository;
  }

  public void saveUser(String name) {
    User user = new User();
    user.setName(name);
    userRepository.save(user);
  }

  public void saveOrder(String orderNumber) {
    Order order = new Order();
    order.setOrderNumber(orderNumber);
    orderRepository.save(order);
  }
}
