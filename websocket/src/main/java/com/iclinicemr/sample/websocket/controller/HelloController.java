package com.iclinicemr.sample.websocket.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello World. It is " + new Date());
    }
}
