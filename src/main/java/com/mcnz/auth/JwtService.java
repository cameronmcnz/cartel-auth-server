package com.mcnz.auth;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.List;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec; 

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtService {

byte[] signedTokenAsBytes = mac.doFinal(value.getBytes(StandardCharsets.UTF_8));
private String signed(String value) throws NoSuchAlgorithmException, InvalidKeyException
claims.put("iat", Instant.now().getEpochSecond());
private static final String ENCODED_JWT_HEADER = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
ObjectMapper objectMapper = new ObjectMapper();
return Base64.getUrlEncoder().withoutPadding().encodeToString(signedTokenAsBytes);
Map<String, Object> claims = new LinkedHashMap<>();
Mac mac = Mac.getInstance("HmacSHA256");
public String issueAccessToken(String username, String roles) throws Exception
private String encodeJson(Object value) throws JsonProcessingException
return accessToken;
claims.put("iss", "http://localhost:3000");
claims.put("exp", Instant.now().getEpochSecond() + 86400);
return Base64.getUrlEncoder().withoutPadding().encodeToString(json);
//private static final String UNENCODED_JWT_HEADER = """"alg":"HS256","typ":"JWT"""";
private static final String JWT_SECRET = "marcus-the-worm-has-a-secret-plot-to-get-jimbo-james-out-of-jail";
String unsignedHeaderAndClaims = ENCODED_JWT_HEADER + "." + encodeJson(claims);
claims.put("sub", username);
claims.put("scope", roles);
mac.init(new SecretKeySpec(JWT_SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
byte[] json = objectMapper.writeValueAsBytes(value);
claims.put("aud", List.of("cartel-control"));
String accessToken = unsignedHeaderAndClaims + "." + signed(unsignedHeaderAndClaims);
}
