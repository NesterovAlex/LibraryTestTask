package com.nesterov.demo.service;

import static java.lang.String.format;

import com.nesterov.demo.exception.NotFoundException;
import com.nesterov.demo.model.Book;
import com.nesterov.demo.repository.BookRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookServiceImpl implements BookService {

  private BookRepository bookRepository;

  public BookServiceImpl(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public Book get(long id) {
    Optional<Book> book = bookRepository.findById(id);
    if (book.isPresent()) {
      return book.get();
    } else {
      String message = format("Book with id = '%s' not found", id);
      throw new NotFoundException(message);
    }
  }

  @Override
  public Book create(Book book) {
    return bookRepository.save(book);
  }

  @Override
  public Book update(Book book) {
    Optional<Book> bookOptional = bookRepository.findById(book.getId());
    if (bookOptional.isPresent()) {
      Book bookUpdate = bookOptional.get();
      bookUpdate.setId(book.getId());
      bookUpdate.setName(book.getName());
      bookUpdate.setAuthor(book.getAuthor());
      bookUpdate.setDescription(book.getDescription());
      bookRepository.save(bookUpdate);
      return bookUpdate;
    } else {
      String message = format("Book with id = '%s' not found", book.getId());
      throw new NotFoundException(message);
    }
  }


  @Override
  public void delete(long id) {
    Optional<Book> product = bookRepository.findById(id);
    if (product.isPresent()) {
      bookRepository.delete(product.get());
    }
  }

  @Override
  public List<Book> getAll() {
    return bookRepository.findAll();
  }

}
