package com.example.demo.controller;

import java.security.NoSuchProviderException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Match;
import com.example.demo.repository.MatchRepository;

@CrossOrigin(origins = "http://localhost:3000")
@Validated
@RestController
@RequestMapping("/api/V1/")
public class MatchController {

    @Autowired
    private MatchRepository matchRepository;

}
