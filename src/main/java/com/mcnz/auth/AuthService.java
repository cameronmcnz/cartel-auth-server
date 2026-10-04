package com.mcnz.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

if (user == null || !passwordEncoder.matches(password, user.getPasswordHash()))
public String register(String username, String password)  throws Exception
try
UserAccount user = users.findById(username).orElse(null);
private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
return jwtService.issueAccessToken(username, user.getRoles());
throw new ResponseStatusException(HttpStatus.CONFLICT, username + " is already registered", e);
System.out.println("[auth] register_success username=" + username);
private UserAccountRepository users;
System.out.println("[auth] login_success username=" + username);
return jwtService.issueAccessToken(username, user.getRoles());
public String login(String username, String password)  throws Exception
private JwtService jwtService;
throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid username or password");
@Autowired
@Autowired
catch (DuplicateKeyException e)
users.insert(user);
UserAccount user = new UserAccount(username, passwordEncoder.encode(password), "client", null);
}
