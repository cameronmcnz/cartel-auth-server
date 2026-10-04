package com.mcnz.auth;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
public class UserAccount {
    @Id
    private String username;
    private String passwordHash;
    private String roles;
    private String refreshTokenHash;
    private String lineOfBusiness;

    public String getLineOfBusiness() {
		return lineOfBusiness;
	}

	public UserAccount() {
    }

    public UserAccount(String username, String passwordHash, String roles, String refreshTokenHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.roles = roles;
        this.refreshTokenHash = refreshTokenHash;
        this.lineOfBusiness = "cartel-operations";
        
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRoles() {
        return roles;
    }

    public String getRefreshTokenHash() {
        return refreshTokenHash;
    }

    public void setRefreshTokenHash(String refreshTokenHash) {
        this.refreshTokenHash = refreshTokenHash;
    }
}
