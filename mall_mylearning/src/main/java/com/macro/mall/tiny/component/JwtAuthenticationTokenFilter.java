package com.macro.mall.tiny.component;

import com.macro.mall.tiny.common.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 过滤器，用户每次请求时，都要执行次方法
 如果有token执行 登录操作
 这样就能做到 每次请求等能检查用户是否登录状态
 */

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter
{

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {


       String authHeader = request.getHeader(this.tokenHeader);
       if(authHeader!=null&&authHeader.startsWith(this.tokenHead)) {
           //获取token
           String authToken = authHeader.substring(this.tokenHead.length());
           //获取名字
           String username = jwtTokenUtil.getUserNameFromToken(authToken);
           if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
               //创建UserDetails
               UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
               //判断token是否有效
               if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                   UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                   authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                   SecurityContextHolder.getContext().setAuthentication(authentication);
               }
           }
       }
        //放行
        chain.doFilter(request, response);
    }
}
