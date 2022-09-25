package com.villvay.blog.filter;


import com.villvay.blog.exceptions.AuthException;
import com.villvay.blog.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@Order(1)
public class RequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    private static final String AUTH_HEADER = "Authorization";

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            if (request.getRequestURI().startsWith("/v1") && !request.getRequestURI().equals("/v1/auth")) {
                checkAuth(request);
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            resolver.resolveException(request, response, null, ex);
        }

    }

    private void checkAuth(HttpServletRequest req) {
        String[] values = req.getHeader(AUTH_HEADER) != null ? req.getHeader(AUTH_HEADER).split(" ") : new String[2];
        String authToken = values[1];
        if(!"Bearer".equals(values[0]) || !StringUtils.hasText(authToken) || !jwtUtils.isTokenValid(authToken)){
            throw new AuthException("Not Authenticated Request");
        }
    }

    private List<String> allowedUrls() {
        return List.of("/swagger-ui/index.html", "/v3/api-docs/swagger-config", "/v3/api-docs", "/v1/auth");
    }
}
