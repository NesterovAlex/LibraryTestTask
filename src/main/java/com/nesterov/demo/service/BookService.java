package com.nesterov.demo.service;

import com.nesterov.demo.model.Book;

import java.util.List;

public interface BookService {
    Book get(long id);
    Book create(Book book);
    Book update(Book book);
    void delete(long id);
    List<Book> getAll();
}
