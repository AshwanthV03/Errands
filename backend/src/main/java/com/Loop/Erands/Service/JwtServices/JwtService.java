package com.Loop.Erands.Service.JwtServices;

import com.Loop.Erands.Models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // Secret key length must be at least 256 bits for HMAC SHA-256
    private final String SECRET_KEY = generateSecureSecretKey(); // Generate a secure random secret key

    // Method to generate a secure random secret key
    private String generateSecureSecretKey() {
        byte[] key = new byte[32]; // 256 bits
        new SecureRandom().nextBytes(key); // Fill the key with random bytes
        return Encoders.BASE64.encode(key); // Encode the key to Base64 for safe storage
    }

    // Generate a JWT token based on the provided user details
    public String generateToken(User user) {
        // Build the JWT token with the user's information
        String token = Jwts.builder()
                .subject(user.getEmail()) // Set the subject as the user's email
                .claim("userId", user.getUserId()) // Add the userId as a claim
                .claim("userName", user.getFirstName() + " " + user.getLastName()) // Add the user's full name as a claim
                .claim("role", user.getRole()) // Add the user's role as a claim
                .issuedAt(new Date(System.currentTimeMillis())) // Set the issued date
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Set the expiration time (24 hours)
                .signWith(getSigninKey()) // Sign the token with the secret key
                .compact(); // Compact the token into a string
        return token; // Return the generated token
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigninKey()) // Use the signing key for verification
                .build()
                .parseClaimsJws(token) // Parse the token
                .getBody(); // Get the claims body
    }

    // Extract a specific claim from the token
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token); // Extract all claims
        return resolver.apply(claims); // Apply the resolver function to get the desired claim
    }

    // Extract the username (subject) from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // Extract the subject claim
    }

    // Validate the token against the provided user details
    public boolean isValid(String token, UserDetails userDetails) {
        String username = extractUsername(token); // Extract the username from the token
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); // Check if the token is valid
    }

    // Check if the token has expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Compare expiration date with current date
    }

    // Extract the expiration date from the token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // Get the expiration claim
    }

    // Extract userId from the token
    public String getUserIdFromToken(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigninKey()) // Use the signing key for verification
                .build()
                .parseClaimsJws(jwt) // Parse the token
                .getBody(); // Get the claims body

        String userId = String.valueOf(claims.get("userId")); // Get userId from claims
        return userId; // Return the extracted userId
    }

    // Get the signing key for token signing and verification
    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); // Decode the Base64 secret key
        return Keys.hmacShaKeyFor(keyBytes); // Generate a signing key
    }
}
