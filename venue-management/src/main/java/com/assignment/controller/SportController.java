package com.assignment.controller;

import com.assignment.service.SportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sports")
public class SportController {

    private final SportService sportService;

    public SportController(SportService sportService) {
        this.sportService = sportService;
    }

    @PostMapping("/sync")
    public ResponseEntity<String> fetchSports() {
        sportService.pullSports();
        return ResponseEntity.ok("Sports fetched successfully");
    }
}
