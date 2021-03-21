package com.nesterov.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.nesterov.demo.model.User;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  @Rollback(value = false)
  void givenNewUser_whenSave_thenExpectedUserIdReturned(){
    int expectedId = 1;
    User user = userRepository.save(new User("testFirstName", "testLastName", "testEmail", "testPhone"));
    assertNotNull(user);
    assertEquals(expectedId, user.getId());
  }

  @Test
  @Rollback(value = false)
  void givenNewUser_whenUpdate_thenExpectedUserReturned(){
    User updated = new User(1,"updatedFirstName", "updatedLastName", "updatedEmail", "updatedPhone");

    User actual = userRepository.save(updated);

    assertEquals(updated, actual);
  }

  @Test
  @Rollback(value = false)
  void givenIdOfExistingUser_whenGetOne_thenExpectedUserReturned(){
    User expected = new User(1,"updatedFirstName", "updatedLastName", "updatedEmail", "updatedPhone");


    User repository = userRepository.getOne(expected.getId());
    User actual = new User(repository.getId(), repository.getFirstName(), repository.getLastName(), repository.getEmail(), repository.getPhone());

    assertEquals(expected, actual);
  }

  @Test
  @Rollback(value = false)
  void givenIdOfExistingUser_whenGetAll_thenExpectedUserReturned(){

    List<User> users = userRepository.findAll();

    assertTrue(users.size() == 0);
  }

  @Test
  @Rollback(value = false)
  void givenIdOfExistingUser_whenDelete_thenUserDeleted(){
    User expected = new User(1,"updatedFirstName", "updatedLastName", "updatedEmail", "updatedPhone");

   userRepository.delete(expected);

   boolean isExist = userRepository.findById(expected.getId()).isPresent();

   assertFalse(isExist);
  }

}