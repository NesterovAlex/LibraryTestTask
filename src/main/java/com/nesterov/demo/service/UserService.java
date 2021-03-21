package com.nesterov.demo.service;

import com.nesterov.demo.model.Book;
import com.nesterov.demo.model.User;
import java.util.List;

public interface UserService {
    User get(long id);
    User create(User user);
    User update(User user);
    void delete(long id);
    List<User> getAll();
    User getBook(long userId, long bookId);

    User returnBook(long userId, long bookId);
}
