package com.nesterov.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book implements DBEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String name;
  private String author;
  private String description;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonBackReference
  private User user;

  public void setUser(User user) {
    this.user = user;
  }

  public Book() {
  }

  public Book(String name, String author, String description) {
    this.name = name;
    this.author = author;
    this.description = description;
  }

  public Book(long id, String name, String author, String description) {
    this.id = id;
    this.name = name;
    this.author = author;
    this.description = description;
  }

  public Book(long id, String name, String author, String description,
      User user) {
    this.id = id;
    this.name = name;
    this.author = author;
    this.description = description;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public User getUser() {
    return user;
  }
}
