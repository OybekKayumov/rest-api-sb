package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.RegisterUserRequest;
import com.codewithmosh.store.dtos.UpdateUserRequest;
import com.codewithmosh.store.dtos.UserDto;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.mappers.UserMapper;
import com.codewithmosh.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;

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
  public Iterable<UserDto> getAllUsers(
          //! add auth Token in postman to header, optional
          @RequestHeader(required = false, name = "x-auth-token") String authToken,
          @RequestParam(required = false, defaultValue = "", name = "sort") String sortBy) {

    //* print auth token
    System.out.println("auth token from postman: " + authToken);

    if (!Set.of("name", "email").contains(sortBy))
      sortBy = "name";

    return userRepository.findAll(Sort.by(sortBy))
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

  @PostMapping
  //public UserDto createUser(@RequestBody UserDto data) {
  public ResponseEntity<UserDto> createUser(
					@RequestBody RegisterUserRequest request,
					UriComponentsBuilder uriBuilder
  ) {

    //return  data;

	  var user = userMapper.toEntity(request);
	  System.out.println(user);

		userRepository.save(user);

		var userDto = userMapper.toDto(user);
		var uri =
						uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
		return ResponseEntity.created(uri).body(userDto);
  }

	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(@PathVariable(name = "id") Long id,
	                          @RequestBody UpdateUserRequest request) {

		var user = userRepository.findById(id).orElse(null);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}

		userMapper.update(request, user);
		userRepository.save(user);

		return ResponseEntity.ok(userMapper.toDto(user));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		var user = userRepository.findById(id).orElse(null);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}

		userRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
