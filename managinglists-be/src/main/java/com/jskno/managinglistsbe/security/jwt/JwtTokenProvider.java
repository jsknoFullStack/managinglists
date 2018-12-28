package com.jskno.managinglistsbe.security.jwt;

import com.jskno.managinglistsbe.security.persistence.User;
import com.jskno.managinglistsbe.security.utils.SecurityConstants;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    public String generateToken(UserDetails userDetails) {
        Date now = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);
        //String userId = Long.toString(userDetails.getId());

        Map<String, Object> claims = new HashMap<>();
        //claims.put("id", userId);
        claims.put(SecurityConstants.CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(SecurityConstants.CLAIM_USER_DETAILS, userDetails);
        claims.put(SecurityConstants.CLAIM_KEY_CREATED, now);

        return Jwts.builder()
                //.setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }


    public boolean validateToken(String token) {

        try {
            Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token);
            return true;
        } catch(SignatureException ex) {
            System.out.println("Invalid JWT Signature");
        } catch(MalformedJwtException ex) {
            System.out.println("Invalid JWT Token");
        } catch(ExpiredJwtException ex) {
            System.out.println("Expired JWT Token");
        } catch(UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT Token");
        } catch(IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty");
        }
        return false;
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token).getBody();
        String id = (String) claims.get("id");

        return Long.parseLong(id);
    }


    public String getUsernameFromJWT(String token) {
        Claims claims = getClaimsFromJWT(token);
        String username = (String) claims.get(SecurityConstants.CLAIM_KEY_USERNAME);

        return username;
    }

    private Claims getClaimsFromJWT(String token) {
        Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(token)
                    .getBody();

        return claims;
    }
}
