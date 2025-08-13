package com.codewithmosh.store.controllers;

import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

  private final UserRepository userRepository;

//  public UserController(UserRepository userRepository) {
//    this.userRepository = userRepository;
//  }


  //* Iterable
  //* method: GET, POST, PUT, DELETE
  //@RequestMapping("/users")
  @GetMapping("/users")
  //public List<User> getAllUsers() {
  public Iterable<User> getAllUsers() {
    return userRepository.findAll();

  }
}
