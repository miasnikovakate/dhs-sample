package com.epam.sample.dhsserver.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenService {

    public static final String PUBLIC_KEY_FILE = "public.pem";

    private final PublicKey publicKey;

    public JwtTokenService() {
        try (InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(PUBLIC_KEY_FILE);
             BufferedInputStream br = new BufferedInputStream(resourceAsStream)) {
            byte[] keyBytes = br.readAllBytes();
            String temp = new String(keyBytes);
            String publicKeyPEM = temp
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\n", "");

            X509EncodedKeySpec spec =
                    new X509EncodedKeySpec(Base64.decodeBase64(publicKeyPEM));
            KeyFactory kf = KeyFactory.getInstance("RSA");
            publicKey = kf.generatePublic(spec);
        } catch (Exception e) {
            throw new IllegalArgumentException("Can't read public key file", e);
        }
    }

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token).getBody();
    }

    //validate token
    public Boolean validateToken(String token) {
        // TODO: add correct check
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .after(new Date());
    }

}
