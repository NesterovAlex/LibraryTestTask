package com.nesterov.demo.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.nesterov.demo.model.Book;
import com.nesterov.demo.model.User;
import com.nesterov.demo.service.BookService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private BookService bookService;

  @Test
  void givenResponseBody_whenCreateBook_thenExpectedResponseBodyAndHttpStatusReturned()
      throws Exception {
    String responseBody = "{\"id\":0,\"name\":\"Book\",\"author\":\"Author\",\"description\":\"Description\"}";
    String requestBody = "{\"name\":\"Book\", \"author\":\"Author\", \"description\":\"Description\"}";
    Book mockBook = new Book("Book", "Author", "Description");
    Mockito.when(
        bookService.create(Mockito.any(Book.class))).thenReturn(mockBook);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/books/create")
        .accept(MediaType.APPLICATION_JSON)
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(response.getContentAsString(), responseBody);
  }

  @Test
  public void givenExistingBook_whenUpdateBook_thenExpectedResponseReturned() throws Exception {
    String expectedResponseBody = "{\"id\":1,\"name\":\"updatedBook\",\"author\":\"updatedAuthor\",\"description\":\"updatedDescription\"}";
    Book mockBook = new Book(1, "updatedBook", "updatedAuthor", "updatedDescription");

    Mockito.when(
        bookService.update(Mockito.any(Book.class))).thenReturn(mockBook);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
        "/books/1").accept(
        MediaType.APPLICATION_JSON)
        .content(expectedResponseBody)
        .contentType(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(expectedResponseBody, response.getContentAsString());
  }

  @Test
  public void givenExistingBookId_whenGetBook_thenExpectedResponseReturned() throws Exception {
    String expectedResponseBody = "{\"id\":3,\"name\":\"testBook\",\"author\":\"testAuthor\",\"description\":\"testDescription\"}";
    Book mockBook = new Book(3, "testBook", "testAuthor", "testDescription");

    Mockito.when(
        bookService.get(mockBook.getId())).thenReturn(mockBook);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
        "/books/3").accept(
        MediaType.APPLICATION_JSON)
        .content(expectedResponseBody)
        .contentType(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(expectedResponseBody, response.getContentAsString());
  }

  @Test
  public void givenExistingBookId_whenGetAllBooks_thenExpectedResponseReturned() throws Exception {
    String expectedResponseBody = "[{\"id\":1,\"name\":\"firstName\",\"author\":\"firstAuthor\",\"description\":\"firstDescription\"},{\"id\":2,\"name\":\"secondName\",\"author\":\"secondAuthor\",\"description\":\"secondDescription\"},{\"id\":3,\"name\":\"thirdName\",\"author\":\"thirdAuthor\",\"description\":\"thirdDescription\"}]";
    List<Book> mockBook = Arrays.asList(new Book(1, "firstName", "firstAuthor", "firstDescription"),
        new Book(2, "secondName", "secondAuthor", "secondDescription"),
        new Book(3, "thirdName", "thirdAuthor", "thirdDescription"));

    Mockito.when(
        bookService.getAll()).thenReturn(mockBook);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
        "/books").accept(
        MediaType.APPLICATION_JSON)
        .content(expectedResponseBody)
        .contentType(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  public void givenExistingBookId_whenDeleteBook_thenExpectedResponseReturned() throws Exception {
    int expectedStatus = 204;
    RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
        "/books/5");
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    MockHttpServletResponse response = result.getResponse();

    assertEquals(expectedStatus, response.getStatus());
  }

}