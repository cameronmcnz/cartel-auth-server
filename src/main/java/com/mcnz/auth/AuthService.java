package com.mcnz.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

	@Autowired
	private UserAccountRepository users;

	@Autowired
	private JwtService jwtService;

	public String register(String username, String password)  throws Exception  {

		UserAccount user = new UserAccount(username, passwordEncoder.encode(password), "client", null);

		try {
			users.insert(user);
		} catch (DuplicateKeyException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, username + " is already registered", e);
		}

		System.out.println("[auth] register_success username=" + username);

		return jwtService.issueAccessToken(username, user.getRoles());
	}

	public String login(String username, String password)  throws Exception {

		UserAccount user = users.findById(username).orElse(null);

		if (user == null || !passwordEncoder.matches(password, user.getPasswordHash())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid username or password");
		}

		System.out.println("[auth] login_success username=" + username);

		return jwtService.issueAccessToken(username, user.getRoles());
	}

}