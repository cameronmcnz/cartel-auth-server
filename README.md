# Spring Auth Server

A deliberately small Java/Spring Boot auth service backed by embedded MongoDB.

## Endpoints

The endpoints use request parameters rather than JSON DTOs. Username and password are submitted as `application/x-www-form-urlencoded` form data, so passwords are kept out of the URL query string.

### Register

```bash
curl -X POST "http://localhost:3000/auth/register" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  --data-urlencode "username=carol" \
  --data-urlencode "password=mission123"
```

### Login

```bash
curl -X POST "http://localhost:3000/auth/login" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  --data-urlencode "username=carol" \
  --data-urlencode "password=mission123"
```

Example response:

```json
{
  "accessToken": "...",
  "refreshToken": "..."
}
```

### Refresh

```bash
curl -X POST "http://localhost:3000/auth/refresh?refreshToken=YOUR_REFRESH_TOKEN"
```

## What it does

- stores users in embedded MongoDB
- hashes passwords with BCrypt cost 10
- issues HS256 JWT access tokens
- access tokens last 15 minutes
- JWTs contain `sub`, `roles`, `iss`, `aud`, `iat`, and `exp`
- creates opaque 32-byte refresh tokens
- stores only SHA-256 hashes of refresh tokens
- seeds `alice / mission123` with the `MISSION_OPERATOR` role
- logs successful login events with `System.out.println`

## Deliberately simple structure

The project intentionally uses field injection and avoids extra configuration abstractions for this example.

- no login/register/refresh DTOs
- no `AuthProperties`
- no `AuthLogger`
- no `TokenHasher` bean
- no injected `PasswordEncoder`
- the BCrypt strength, JWT secret, issuer, audience, and access-token duration are constants in the Java code
- the seed `CommandLineRunner` lives directly in `AuthServerApplication`

## Local auth vs Cognito

This auth server contains no Cognito code. The consuming application should choose its authentication provider independently.

Conceptually:

```text
Application
    |
    +-- LocalAuthProvider   -> this Spring auth server
    |
    +-- CognitoAuthProvider -> AWS Cognito
```

That keeps this server independent of AWS and lets clients switch providers without changing the local auth service.

## Run

Requirements:

- Java 21
- Maven 3.9+

```bash
mvn spring-boot:run
```

The service listens on:

```text
http://localhost:3000
```

## Configuration

The remaining application configuration is intentionally minimal:

```properties
server.port=3000
spring.application.name=spring-auth-server
spring.data.mongodb.database=mission-auth
de.flapdoodle.mongodb.embedded.version=7.0.14
server.error.include-message=always
```

> Register and login now send credentials in an `application/x-www-form-urlencoded` request body. The controller still uses simple `@RequestParam` arguments, but the password no longer appears in the URL. Use HTTPS in production.
