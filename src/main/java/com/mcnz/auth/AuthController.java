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

public class AuthController {
    
    private AuthService authService;
    
    public String register(
            String username,
            String password) throws Exception {

        return authService.register(username, password);
    }
    
    public String login(
            String username,
            String password) throws Exception {

        return authService.login(username, password);
    }
}
