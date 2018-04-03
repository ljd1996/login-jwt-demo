package com.example.loginjwtdemo.filter;

import com.example.loginjwtdemo.requestCached.HttpSessionRequestCache;
import com.example.loginjwtdemo.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFilter implements Filter {

    @Value("${token.header}")
    private String tokenHeader;

    @Autowired
    private TokenUtils tokenUtils;

    private HttpSessionRequestCache requestCache;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        requestCache = new HttpSessionRequestCache();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("doFilter...");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

//        HttpServletRequest request1 = requestCache.getMatchingRequest(request, response);
//        if (request1 != null) {
//            System.out.println("request1:" + request1.getRequestURL().toString());
//            filterChain.doFilter(request1, servletResponse);
//            return;
//        }

        String authToken = request.getHeader(this.tokenHeader);
        if (authToken != null) {
            String username = this.tokenUtils.getUsernameFromToken(authToken);
            String password = this.tokenUtils.getPasswordFromToken(authToken);

            // 如果上面解析 token 成功并且拿到了 username 并且本次会话的权限还未被写入 且token有效
            if (username != null && password != null && this.tokenUtils.validateToken(authToken)) {
                System.out.println("validate token!");
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                requestCache.saveRequest(request, response);
                response.sendRedirect("/login");
            }
        } else {
            requestCache.saveRequest(request, response);
            response.sendRedirect("/login");
        }
    }

    @Override
    public void destroy() {
    }
}
