package com.guavapay.adminservice.common;

import com.guavapay.adminservice.model.UserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TokenProvider {

    @Value("${security.jwt.base64-secret}")
    private String base64Secret;
    @Value("${security.jwt.access-token-validity-in-milliseconds}")
    private Long accessTokenValidityInMilliseconds;
    private Key key;

    @PostConstruct
    public void init() {
        byte[] bytes = Decoders.BASE64.decode(base64Secret);
        this.key = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date tokenValidity = new Date(new Date().getTime() + accessTokenValidityInMilliseconds * 60 * 24);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("username", userDetails.getUsername())
                .claim("fullName", userDetails.getFullName())
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(tokenValidity)
                .compact();
    }

    public String createRefreshToken(String expiredToken) {
        try {
            validateAndExtractClaim(expiredToken);
            throw new RuntimeException("Token isn't expired. Pls dont force to refresh!");
        } catch (ExpiredJwtException ex) {
            Date tokenValidity = new Date(new Date().getTime() + accessTokenValidityInMilliseconds * 15);
            Claims claims = ex.getClaims();
            return Jwts.builder()
                    .setSubject((String) claims.get("sub"))
                    .claim("username", claims.get("username"))
                    .claim("fullName", claims.get("fullName"))
                    .signWith(key, SignatureAlgorithm.HS512)
                    .setExpiration(tokenValidity)
                    .compact();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Authentication parseAuthentication(String token) {
        Claims claims = this.validateAndExtractClaim(token);
        UserDetails principal = this.getPrincipal(claims);
        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }

    private UserDetails getPrincipal(Claims claims) {
        String username = claims.get("username", String.class);
        String fullName = claims.get("fullName", String.class);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        return new UserDetails(username, "", authorities, fullName);
    }

    public Claims validateAndExtractClaim(String authToken) {
        return Jwts.parser().setSigningKey(this.key).parseClaimsJws(authToken).getBody();
    }

    //YOU CAN USE BELOW METHODS ADD SECURE COOKIE AUTH TO JWT

    private void checkCookieIsCorrect(Claims claims, Cookie cookie) {
        if ("userUniqueKey".equals(cookie.getName())) {
            String userUniqueKey = cookie.getValue();
            if (!claims.get("userUniqueKeyHash").equals(generateHash(userUniqueKey)))
                throw new MalformedJwtException("Invalid JWT token");
        }
    }

    private String getUserUniqueKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

    private Cookie getCustomAuthCookie(String userUniqueKey) {
        Cookie cookie = new Cookie("userUniqueKey", userUniqueKey);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

    @SneakyThrows
    private String generateHash(String userUniqueKey) {
        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        byte[] uniqueKeyHash = digest.digest(userUniqueKey.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(uniqueKeyHash);
    }

}
