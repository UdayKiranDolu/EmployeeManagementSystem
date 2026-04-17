package com.employeemanagementsystem.ems.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTUtilizer {

    private final String SECRET_KEY_STRING = "7cbdb6113016589c63ccdfed716b6fcb";

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());


    public String generateJJWTToken(String username,String role){
        Map<String,Object> mp = new HashMap<>();
        //We created a map to hold custom data that we want to include in the JWT

        mp.put("Username",username);
        mp.put("role", role);

        return Jwts.builder().
                setClaims(mp).// Attaches custom payload
                        setSubject(username).//A general field for identifying the token(it can be used for username)
                        setIssuedAt(new Date()).//When the token was created
                        setExpiration(new Date(System.currentTimeMillis()+1000*60*60*2)).//When thr token with our secret key
                        signWith(key,SignatureAlgorithm.HS256).//secures the token with our secret key
                        compact();//Final step to return the token as a string
    }

    public Map<String,String> validateToken(String Token){
        Map<String,String> res = new HashMap<>();
        try{
            Claims c = Jwts.parserBuilder() //Used to configure the parser
                    .setSigningKey(key)//Same key which is used for generation (Secret key that used to verify)
                    .build()
                    .parseClaimsJwt(Token)//it Verifies and parses the token
                    .getBody();//returns the actual data inside the token
            res.put("username", c.get("username",String.class));
            res.put("role",c.get("role",String.class));
            res.put("code","200");
        }
        catch (ExpiredJwtException e){
            res.put("Error Code","401");
            res.put("Error","Token Expired, Please Login Again");
        }
        return res;
    }

}
