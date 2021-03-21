package com.nesterov.demo.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.nesterov.demo.model.Book;
import com.nesterov.demo.model.User;
import com.nesterov.demo.service.UserService;
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
@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private UserService userService;

  @Test
  void givenResponseBody_whenCreateUser_thenExpectedResponseBodyAndHttpStatusReturned()
      throws Exception {
    String responseBody = "{\"id\":0,\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"email\":\"email\",\"phone\":\"phone\",\"books\":[]}";
    String requestBody = "{\"firstName\":\"firstName\", \"lastName\":\"lastName\", \"email\":\"email\",\"phone\":\"phone\"}";
    User mockUser = new User("firstName", "lastName", "email", "phone");
    Mockito.when(
        userService.create(Mockito.any(User.class))).thenReturn(mockUser);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/users/create")
        .accept(MediaType.APPLICATION_JSON)
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(response.getContentAsString(), responseBody);
  }

  @Test
  public void givenExistingUser_whenUpdateUser_thenExpectedResponseReturned() throws Exception {
    String expectedResponseBody = "{\"id\":1,\"firstName\":\"updatedName\",\"lastName\":\"updatedName\",\"email\":\"updatedEmail\",\"phone\":\"updatedPhone\",\"books\":[]}";
    User mockUser = new User(1, "updatedName", "updatedName", "updatedEmail", "updatedPhone");

    Mockito.when(
        userService.update(Mockito.any(User.class))).thenReturn(mockUser);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
        "/user/1").accept(
        MediaType.APPLICATION_JSON)
        .content(expectedResponseBody)
        .contentType(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(expectedResponseBody, response.getContentAsString());
  }

  @Test
  public void givenExistingUserId_whenGetUser_thenExpectedResponseReturned() throws Exception {
    String expectedResponseBody = "{\"id\":4,\"firstName\":\"testName\",\"lastName\":\"testName\",\"email\":\"testEmail\",\"phone\":\"testPhone\",\"books\":[]}";
    User mockUser = new User(4, "testName", "testName", "testEmail", "testPhone");

    Mockito.when(
        userService.get(mockUser.getId())).thenReturn(mockUser);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
        "/users/4").accept(
        MediaType.APPLICATION_JSON)
        .content(expectedResponseBody)
        .contentType(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(expectedResponseBody, response.getContentAsString());
  }

  @Test
  public void givenExistingUserId_whenGetAllUsers_thenExpectedResponseReturned() throws Exception {
    String expectedResponseBody = "[{\"id\":1,\"firstName\":\"firstUser\",\"lastName\":\"firstUser\",\"email\":\"firstEmail\",\"phone\":\"firstPhone\",\"books\":[]},{\"id\":2,\"firstName\":\"secondUser\",\"lastName\":\"secondUser\",\"email\":\"secondEmail\",\"phone\":\"secondPhone\",\"books\":[]},{\"id\":3,\"firstName\":\"thirdUser\",\"lastName\":\"thirdUser\",\"email\":\"thirdEmail\",\"phone\":\"thirdPhone\",\"books\":[]}]";
    List<User> mockUser = Arrays
        .asList(new User(1, "firstUser", "firstUser", "firstEmail", "firstPhone"),
            new User(2, "secondUser", "secondUser", "secondEmail", "secondPhone"),
            new User(3, "thirdUser", "thirdUser", "thirdEmail", "thirdPhone"));

    Mockito.when(
        userService.getAll()).thenReturn(mockUser);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
        "/users").accept(
        MediaType.APPLICATION_JSON)
        .content(expectedResponseBody)
        .contentType(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(expectedResponseBody, response.getContentAsString());
  }

  @Test
  public void givenExistingUserId_whenDeleteUser_thenExpectedResponseReturned() throws Exception {
    int expectedStatus = 204;
    RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
        "/users/5");
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    MockHttpServletResponse response = result.getResponse();

    assertEquals(expectedStatus, response.getStatus());
  }

  @Test
  public void givenExistingBookId_whenGetBook_thenExpectedResponseReturned() throws Exception {
    String endpoint = "/users/4/takeBook/3";
    long bookId = 3;
    String expectedResponseBody = "{\"id\":4,\"firstName\":\"testName\",\"lastName\":\"testName\",\"email\":\"testEmail\",\"phone\":\"testPhone\",\"books\":[{\"id\":3,\"name\":\"testBook\",\"author\":\"testAuthor\",\"description\":\"testDescription\"}]}";
    User mockUser = new User(4, "testName", "testName", "testEmail", "testPhone");
    mockUser.getBooks().add(new Book(bookId, "testBook", "testAuthor", "testDescription"));

    Mockito.when(
        userService.getBook(mockUser.getId(), bookId)).thenReturn(mockUser);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
        endpoint).accept(
        MediaType.APPLICATION_JSON)
        .content(expectedResponseBody)
        .contentType(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(expectedResponseBody, response.getContentAsString());
  }

  @Test
  public void givenExistingBookId_whenReturnBook_thenExpectedResponseReturned() throws Exception {
    String endpoint = "/users/4/returnBook/3";
    long bookId = 3;
    String expectedResponseBody = "{\"id\":4,\"firstName\":\"testName\",\"lastName\":\"testName\",\"email\":\"testEmail\",\"phone\":\"testPhone\",\"books\":[]}";
    User mockUser = new User(4, "testName", "testName", "testEmail", "testPhone");

    Mockito.when(
        userService.returnBook(mockUser.getId(), bookId)).thenReturn(mockUser);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
        endpoint).accept(
        MediaType.APPLICATION_JSON)
        .content(expectedResponseBody)
        .contentType(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(expectedResponseBody, response.getContentAsString());
  }
}