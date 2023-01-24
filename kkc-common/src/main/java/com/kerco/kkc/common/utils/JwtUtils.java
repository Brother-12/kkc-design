package com.kerco.kkc.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    //生成的token 可持续2天
    public static final long EXPIRE = 1000 * 60 * 60 * 24 * 2;
    private static String key="ssssssssssdfdsafasfdssdfsfsfssfs";

    //设置加密算法
    private static SignatureAlgorithm signatureAlgorithm=SignatureAlgorithm.HS256;
    /**
     * 获取转换后的私钥对象
     * @return
     */
    private static SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(key.getBytes());
    }
    /**
     * 生成JWT
     * @param payLoad 携带的数据
     * @return
     */
    public static String createJwt(Map<String,Object> payLoad){
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setClaims(payLoad)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE))
                .signWith(getSecretKey(),signatureAlgorithm)
                .compact();
    }

    /**
     * 不管是否过期，都返回claims对象
     * @param jwsString token
     * @return Claims
     */
    public static Claims parseJwt(String jwsString){
        Claims claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(jwsString).getBody();
        }catch (ExpiredJwtException  e){
            claims= e.getClaims();
        }
        return claims;
    }

    /**
     * 解析JWS，返回一个布尔结果
     * @param jwsString
     * @return
     */
    public static Boolean checkToken(String jwsString){
        boolean result= false;
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(jwsString);
            result=true;
        }catch (JwtException e){
            result=false;
        }finally {
            return result;
        }
    }

    /**
     * 解析Jws,返回一个Jws对象
     * @param jwsString
     * @return
     */
    public static Jws parseJwtResultJws(String jwsString){
        Jws<Claims> claims=null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(jwsString);
        }catch (JwtException e){
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 获取header中的数据
     * @param jwsString
     * @return
     */
    public static Map<String,Object> getHeader(String jwsString){
        return parseJwtResultJws(jwsString).getHeader();
    }

    /**
     * 获取PayLoad中携带的数据
     * @param jwsString
     * @return
     */
    public static Map<String,Object> getPayLoad(String jwsString){
        return ((Map<String, Object>) (parseJwtResultJws(jwsString)).getBody());
    }

    /**
     * 获取除去exp和iat的数据，exp：过期时间，iat：JWT生成的时间
     * @param jwsString
     * @return
     */
    public static Map<String,Object> getPayLoadALSOExcludeExpAndIat(String jwsString){
        Map<String, Object> map = getPayLoad(jwsString);
        map.remove("exp");
        map.remove("iat");
        return map;
    }

    public static void main(String[] args) {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MTAwMDAwLCJhdmF0YXIiOiJo" +
                "dHRwczovL2trYy1waWN0dXJlLm9zcy1jbi1zaGVuemhlbi5hbGl5dW5jcy5jb20vMjAyMy0wMS0xNC83YzNkNTJkNS1mZWQ1LTRhNzktOGZjYS" +
                "1kYzhlMDBhMzFjZWMuanBnIiwidXNlcm5hbWUiOiJrZXJjbyIsImlhdCI6MTY3NDIwOTQzMCwiZXhwIjoxNjc0MzgyMjMwfQ.Xfdg9TCkb4z3Q" +
                "HgRiG5eMKOjVElxTZF3mo3V4tkqadI";

        Claims claims = parseJwt(token);
        Date expiration = claims.getExpiration();
        System.out.println(expiration.after(new Date()));
    }
}
