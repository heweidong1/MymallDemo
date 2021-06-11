package com.macro.mall.tiny.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken生成的工具类
 * JWT token的格式：header.payload.signature
 * header的格式（算法、token的类型）：
 * {"alg": "HS512","typ": "JWT"}
 * payload的格式（用户名、创建时间、生成时间）：
 * {"sub":"wang","created":1489079981393,"exp":1489684781}
 * signature的生成算法：
 * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 * Created by macro on 2018/4/26.
 */
@Component
public class JwtTokenUtil
{
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;


    /**
     * 根据负责生成JWT的token
     */
    private String generateToken(Map<String,Object> claims)
    {
        return Jwts.builder()
                //设置payload
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                //设置header
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    /**
     * 从token中获取JWT中的负载就是payload
     * {"sub":"wang","created":1489079981393,"exp":1489684781}
     */
    private Claims getClaimsFormToken(String token)
    {
        Claims claims =null;
        try
        {
            claims  = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();


        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 生成token的过期时间
     */
    private Date generateExpirationDate()
    {
        return new Date(System.currentTimeMillis()+expiration*1000);
    }

    /**
     * 从token中获取登录用户名
     */
    public   String getUserNameFromToken(String token)
    {
        String userName;
        try
        {
            Claims claims = getClaimsFormToken(token);
            userName = claims.getSubject();

        }catch (Exception e)
        {
            userName =null;
        }
        return userName;
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     *  UserDetails 从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDateils)
    {
        String username = getUserNameFromToken(token);
        return username.equals(userDateils.getUsername()) && !isTokenExpired(token);
    }
    /**
     * 判断token是否已经失效
     */
    private boolean isTokenExpired(String token)
    {
        Date expiredDate = getExpiredDateFormToken(token);
        //和当前时间做对比，小于 就是过期
        //当过期时间大于当前时间 返回false
        //expiredDate < Date ?
        //过期返回true
        return expiredDate.before(new Date());
    }

    /**
     *从token中获取过期时间
     */
    private Date getExpiredDateFormToken(String token)
    {
        Claims claims = getClaimsFormToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据用户信息生成token
     */
    public String generteToken(UserDetails userDetails)
    {
        Map<String,Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }

    /**
     * 判断token是否可以被刷新
     */
    public boolean canRefresh(String token)
    {
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     */
    public String refreshToken(String token)
    {
        Claims claims = getClaimsFormToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }









































}
