package gamefully.service;

import gamefully.service.models.User;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

public class TokenSecurity
{
    private static final String KEY = "SmFuV29yc3QxMjNKYW5Xb3JzdDEyM0phbldvcnN0MTIz";

    public String getJWT(User user)
    {
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(KEY),
                SignatureAlgorithm.HS256.getJcaName());

        long days = 1;

        return Jwts.builder()
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("email", user.getEmail())
                .claim("admin", user.getAdmin())
                .setId(String.valueOf(user.getId()))
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(60*60*24*days)))
                .signWith(hmacKey)
                .compact();

    }

    public String checkJWT(String jwt)
    {
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(KEY),
                SignatureAlgorithm.HS256.getJcaName());

        Claims jwtClaims;
        try
        {
            jwtClaims = Jwts.parserBuilder()
                    .setSigningKey(hmacKey)
                    .build()
                    .parseClaimsJws(jwt).getBody();
        }
        catch(ExpiredJwtException e)
        {
            return "Expired";
        }
        catch (JwtException e)
        {
            return "Wrong";
        }

        return jwtClaims.get("email").toString();
    }


}
