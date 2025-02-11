package com.naturecode.multids.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naturecode.multids.service.MultiDBService;

@RestController
@RequestMapping("/test")
public class UserController {
  @Autowired
  private MultiDBService multiDBService;

  @PostMapping("/user/{name}")
  public ResponseEntity<String> addUser(@PathVariable String name) {
    multiDBService.saveUser(name);
    return ResponseEntity.ok("User added!");
  }

  @PostMapping("/order/{orderNumber}")
  public ResponseEntity<String> addOrder(@PathVariable String orderNumber) {
    multiDBService.saveOrder(orderNumber);
    return ResponseEntity.ok("Order added!");
  }
}
