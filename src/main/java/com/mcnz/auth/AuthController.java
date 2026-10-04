package com.mcnz.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String register(
            @RequestParam @NotBlank @Size(min = 3, max = 50) String username,
            @RequestParam @NotBlank @Size(min = 3, max = 100) String password) throws Exception {

        return authService.register(username, password);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String login(
            @RequestParam @NotBlank @Size(min = 3, max = 50) String username,
            @RequestParam @NotBlank @Size(min = 3, max = 100) String password) throws Exception {

        return authService.login(username, password);
    }
}