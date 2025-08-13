package com.codewithmosh.store.controllers;

import com.codewithmosh.store.entities.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

  //* return String object
  @RequestMapping("/hello")
  public String sayHello() {
    return "Hello World!";
  }

  //* return JSON object
//  @RequestMapping("/hello")
//  public Message sayHello2() {
//    return new Message("Hello World!");
//  }
}
