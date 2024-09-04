package com.vinay.food_ordering_app.Food.Ordering.App.controllers;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Data
public class HealthCheckerController {

    @GetMapping(path = "/")
    public ResponseEntity<String> healthChecker() {
        return ResponseEntity.ok("OK");
    }

}

