package org.example.demo.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import org.example.demo.security.CustomUserDetails;

import java.security.Key;
import java.util.Date;

//********תפקיד המחלקה:
//
@Component
public class JwtUtils {

    //לקבל מעוגיה קיימת
    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "securitySample");
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();

    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid jwt token " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("jwt is expired " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("Signature is wrong " + e.getMessage());
        }
        return false;
    }

    //מייצר טוקן
    public String generateTokenFromEmail(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 86400000))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    //קוד סודי שנבחר ע"י המתכנת
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode("=============================================sec=============================================================================================================================="));
    }

    //מייצר עוגיה
    public ResponseCookie generateJwtCookie(CustomUserDetails userPrincipal) {
        String jwt = generateTokenFromEmail(userPrincipal.getUsername());
        ResponseCookie cookie = ResponseCookie.from("securitySample", jwt)
                .path("/api").maxAge(24 * 60 * 60).httpOnly(true).build();
        return cookie;
    }

    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from("securitySample", null).path("/api").build();
        return cookie;
    }

    //GEMINI
    public ResponseCookie generateNullJwtCookie() {
        // יוצרים עוגייה עם אותו שם ("jwt"), אבל עם ערך ריק וזמן תפוגה מיידי.
        return ResponseCookie.from("jwt", "")
                .path("/")
                .maxAge(0) // **הגדרת MaxAge ל-0 מורה לדפדפן למחוק את העוגייה**
                .httpOnly(true)
                // .secure(true) // יש להשאיר את זה אם משתמשים ב-HTTPS בייצור
                .build();
    }
}
