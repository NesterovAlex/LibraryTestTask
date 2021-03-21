package com.nesterov.demo.service;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;

import com.nesterov.demo.model.User;
import com.nesterov.demo.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

  @MockBean
  private UserRepository userRepository;
  @InjectMocks
  private UserServiceImpl userService;

  @Test
  void givenExpectedBook_whenCreate_thenEqualBookReturned() {
    User expected = new User("testFirstName", "testLastName", "testEmail", "testPhone");
    Mockito.when(userRepository.save(expected)).thenReturn(expected);

    User actual = userService.create(expected);

    assertEquals(expected, actual);
  }

  @Test
  void givenExpectedIdOfExistingUser_whenGet_thenEqualUserReturned() {
    User expected = new User("testFirstName", "testLastName", "testEmail", "testPhone");
    Mockito.when(userRepository.findById(expected.getId())).thenReturn(
        of(expected));

    User actual = userService.get(expected.getId());

    assertEquals(expected, actual);
  }

  @Test
  void givenExpectedIdOfExistingUser_whenUpdate_thenEqualUserReturned() {
    User expected = new User(1,"updatedFirstName", "updatedLastName", "updatedEmail", "updatedPhone");
    Mockito.when(userRepository.findById(expected.getId())).thenReturn(
        of(expected));

    User actual = userService.update(expected);

    assertEquals(expected, actual);
  }

  @Test
  void givenExpectedListOfUsers_whenGetAll_thenEqualListUsersReturned() {
    User user = new User(1,"FirstName", "LastName", "Email", "Phone");
    List<User> expected = new ArrayList<>();
    expected.add(user);
    Mockito.when(userRepository.findAll()).thenReturn(expected);

    List<User> actual = userService.getAll();

    assertEquals(expected, actual);
  }

}