package com.vinay.food_ordering_app.Food.Ordering.App.controllers;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.utilityDto.HealthCheckerDto;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckerController {

    @GetMapping("/")
    public ResponseEntity<HealthCheckerDto> healthChecker() {
        return ResponseEntity.ok(new HealthCheckerDto("OK"));
    }

}

