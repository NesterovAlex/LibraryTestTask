package com.nesterov.demo.service;

import static java.util.Optional.*;
import static org.junit.jupiter.api.Assertions.*;

import com.nesterov.demo.model.Book;
import com.nesterov.demo.repository.BookRepository;
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
class BookServiceImplTest {

  @MockBean
  private BookRepository bookRepository;
  @InjectMocks
  private BookServiceImpl bookService;

  @Test
  void givenExpectedBook_whenCreate_thenEqualBookReturned() {
    Book expected = new Book("testName", "testAuthor", "testDescription");
    Mockito.when(bookRepository.save(expected)).thenReturn(expected);

    Book actual = bookService.create(expected);

    assertEquals(expected, actual);
  }

  @Test
  void givenExpectedIdOfExistingBook_whenGet_thenEqualBookReturned() {
    Book expected = new Book(1, "testName", "testAuthor", "testDescription");
    Mockito.when(bookRepository.findById(expected.getId())).thenReturn(
        of(expected));

    Book actual = bookService.get(expected.getId());

    assertEquals(expected, actual);
  }

  @Test
  void givenExpectedIdOfExistingUser_whenUpdate_thenEqualUserReturned() {
    Book expected = new Book(1, "updatedName", "updatedAuthor", "updatedDescription");
    Mockito.when(bookRepository.findById(expected.getId())).thenReturn(
        of(expected));

    Book actual = bookService.update(expected);

    assertEquals(expected, actual);
  }

  @Test
  void givenExpectedListOfBooks_whenGetAll_thenEqualListBooksReturned() {
    Book book = new Book(1, "Name", "Author", "Description");
    List<Book> expected = new ArrayList<>();
    expected.add(book);
    Mockito.when(bookRepository.findAll()).thenReturn(expected);

    List<Book> actual = bookService.getAll();

    assertEquals(expected, actual);
  }
}