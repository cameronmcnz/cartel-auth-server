package com.mcnz.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestControllerAdvice
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    
    @Autowired
    private UserAccountRepository users;

    @Bean
    CommandLineRunner seedAlice() {
        return args -> {
            if (!users.existsById("marcus")) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                users.insert(new UserAccount("marcus", passwordEncoder.encode("abc123"), "client admin advisor analyst", null));
            }
            if (!users.existsById("robert")) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                users.insert(new UserAccount("robert", passwordEncoder.encode("abc123"), "client", null));
            }
            if (!users.existsById("jimbo")) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                users.insert(new UserAccount("jimbo", passwordEncoder.encode("abc123"), "advisor mob-boss", null));
            }
        };
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleBadRequest(IllegalArgumentException ex, HttpServletRequest request) {
        return buildProblem(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(SecurityException.class)
    public ProblemDetail handleUnauthorized(SecurityException ex, HttpServletRequest request) {
        return buildProblem(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }

    @ExceptionHandler({IllegalStateException.class, DuplicateKeyException.class})
    public ProblemDetail handleConflict(Exception ex, HttpServletRequest request) {
        return buildProblem(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ProblemDetail handleJsonProcessing(JsonProcessingException ex, HttpServletRequest request) {
        return buildProblem(HttpStatus.INTERNAL_SERVER_ERROR, "Token serialization failed", request);
    }

    @ExceptionHandler(InvalidKeyException.class)
    public ProblemDetail handleInvalidKey(InvalidKeyException ex, HttpServletRequest request) {
        return buildProblem(HttpStatus.INTERNAL_SERVER_ERROR, "Token signing failed due to invalid key", request);
    }

    @ExceptionHandler(NoSuchAlgorithmException.class)
    public ProblemDetail handleMissingAlgorithm(NoSuchAlgorithmException ex, HttpServletRequest request) {
        return buildProblem(HttpStatus.INTERNAL_SERVER_ERROR, "Token signing failed due to missing algorithm", request);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpected(Exception ex, HttpServletRequest request) {
        return buildProblem(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error", request);
    }

    private ProblemDetail buildProblem(HttpStatus status, String detail, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        problem.setTitle(status.getReasonPhrase());
        problem.setProperty("path", request.getRequestURI());
        return problem;
    }
}
