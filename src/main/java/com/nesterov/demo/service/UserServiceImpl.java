package com.nesterov.demo.service;

import static java.lang.Math.abs;
import static java.lang.String.format;

import com.nesterov.demo.exception.NotFoundException;
import com.nesterov.demo.model.Book;
import com.nesterov.demo.model.User;
import com.nesterov.demo.model.DBEntity;
import com.nesterov.demo.repository.UserRepository;
import com.nesterov.demo.repository.BookRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private BookRepository bookRepository;

  public UserServiceImpl(UserRepository userRepository, BookRepository bookRepository) {
    this.userRepository = userRepository;
    this.bookRepository = bookRepository;
  }

  @Override
  public User get(long id) {
    Optional<User> customer = userRepository.findById(id);
    if (customer.isPresent()) {
      return customer.get();
    } else {
      String message = format("Book with id = '%s' not found", id);
      throw new NotFoundException(message);
    }
  }


  @Override
  public User create(User user) {
    return userRepository.save(user);
  }

  @Override
  public User update(User user) {
    Optional<User> userOptional = userRepository.findById(user.getId());
    if (userOptional.isPresent()) {
      User userUpdate = userOptional.get();
      userUpdate.setId(user.getId());
      userUpdate.setFirstName(user.getFirstName());
      userUpdate.setLastName(user.getLastName());
      userUpdate.setEmail(user.getEmail());
      userUpdate.setPhone(user.getPhone());
      userUpdate.setBooks(user.getBooks());
      userRepository.save(userUpdate);
      return userUpdate;
    } else {
      String message = format("User with id = '%s' not found", user.getId());
      throw new NotFoundException(message);
    }
  }

  @Override
  public void delete(long id) {
    Optional<User> customer = userRepository.findById(id);
    if (customer.isPresent()) {
      userRepository.delete(customer.get());
    }
  }

  @Override
  public List<User> getAll() {
    return userRepository.findAll();
  }

  @Override
  public User getBook(long userId, long bookId) {
    Book book = (Book) verifyIsPresent(bookRepository, bookId);
    User user = (User) verifyIsPresent(userRepository, userId);
    if (book.getUser() == null) {
      user.getBooks().add(book);
      userRepository.save(user);
    }
    return user;
  }

  @Override
  public User returnBook(long userId, long bookId) {
    User user = (User) verifyIsPresent(userRepository, userId);
    user.getBooks().removeIf(book -> book.getId() == bookId);
    return userRepository.save(user);
  }


  private DBEntity verifyIsPresent(JpaRepository jpaRepository, long id) {
    Optional<DBEntity> optional = jpaRepository.findById(id);
    if (optional.isPresent()) {
      return optional.get();
    } else {
      String message = format("'%s' with id = '%s' not found", jpaRepository.getClass(), id);
      throw new NotFoundException(message);
    }
  }

}
