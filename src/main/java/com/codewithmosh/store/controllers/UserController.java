package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.UserDto;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.mappers.UserMapper;
import com.codewithmosh.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

//  public UserController(UserRepository userRepository) {
//    this.userRepository = userRepository;
//  }


  //* Iterable
  //* method: GET, POST, PUT, DELETE
  //@RequestMapping("/users")
  //@GetMapping("/users")
  @GetMapping
  //public List<User> getAllUsers() {
  //public Iterable<User> getAllUsers() {
  public Iterable<UserDto> getAllUsers() {

    return userRepository.findAll()
            .stream()
            //.map(user -> new UserDto(user.getId(), user.getName(),
            //user.getEmail()))

            //.map(user -> userMapper.toDto(user))
            .map(userMapper::toDto)
            .toList();
  }

  //@GetMapping("/users/{id}")
  @GetMapping("/{id}")
  //public User getUser(@PathVariable Long id) {
  public ResponseEntity<UserDto> getUser(@PathVariable Long id) {

    var user = userRepository.findById(id).orElse(null);

    if (user == null) {
      //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      ResponseEntity.notFound().build();
    }
    //var userDto = new UserDto(user.getId(), user.getName(), user.getEmail());

    //return new ResponseEntity<>(user, HttpStatus.OK);
    //return ResponseEntity.ok(userDto);

    return ResponseEntity.ok(userMapper.toDto(user));
  }
}
