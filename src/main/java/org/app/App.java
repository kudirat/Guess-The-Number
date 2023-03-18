package org.app;

import org.app.controller.GameController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    @Autowired
    GameController controller;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}