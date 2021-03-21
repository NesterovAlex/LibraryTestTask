package com.nesterov.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.nesterov.demo.model.Book;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class BookRepositoryTest {

  @Autowired
  private BookRepository bookRepository;

  @Test
  void givenNewBook_whenSave_thenExpectedBookIdReturned() {
    int expectedId = 1;
    Book book = bookRepository.save(new Book("testName", "testAuthor", "description"));
    assertNotNull(book);
    assertEquals(expectedId, book.getId());
  }

  @Test
  @Rollback(value = false)
  void givenNewBook_whenUpdate_thenExpectedBookReturned() {
    Book updated = bookRepository
        .save(new Book(1, "updatedName", "updatedAuthor", "updatedDescription"));

    Book actual = bookRepository.save(updated);

    assertEquals(updated, actual);
  }

  @Test
  @Rollback(value = false)
  void givenIdOfExistingUser_whenGetAll_thenExpectedUserReturned() {

    List<Book> books = bookRepository.findAll();

    assertTrue(books.size() == 0);
  }

  @Test
  @Rollback(value = false)
  void givenIdOfExistingBook_whenDelete_thenBookDeleted() {
    Book expected = new Book(1, "updatedName", "updatedAuthor", "updatedDescription");

    bookRepository.delete(expected);

    boolean isExist = bookRepository.findById(expected.getId()).isPresent();

    assertFalse(isExist);
  }

}