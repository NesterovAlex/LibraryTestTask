package com.nesterov.demo.controller;

import com.nesterov.demo.model.User;
import com.nesterov.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<User> getUserById(@PathVariable long id) {
    return ResponseEntity.ok().body(userService.get(id));
  }

  @GetMapping("/users")
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.ok().body(userService.getAll());
  }

  @PostMapping("/users/create")
  public ResponseEntity<User> createUser(@RequestBody User user) {
    return ResponseEntity.ok().body(userService.create(user));
  }

  @PutMapping("/user/{id}")
  public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User user) {
    user.setId(id);
    return ResponseEntity.ok().body(userService.update(user));
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable long id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/users/{userId}/takeBook/{bookId}")
  public ResponseEntity<User> getBook(@PathVariable long userId, @PathVariable long bookId) {
      return ResponseEntity.ok().body(userService.getBook(userId, bookId));
  }

  @PutMapping("/users/{userId}/returnBook/{bookId}")
  public ResponseEntity<User> returnBook(@PathVariable long userId, @PathVariable long bookId) {
    return ResponseEntity.ok().body(userService.returnBook(userId, bookId));
  }

}
