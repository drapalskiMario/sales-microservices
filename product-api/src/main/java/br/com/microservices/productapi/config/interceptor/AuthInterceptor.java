package br.com.microservices.productapi.config.interceptor;

import br.com.microservices.productapi.modules.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (this.isOption(request)) {
            return true;
        }
        String token = request.getHeader(AUTHORIZATION);
        this.authService.validateToken(token);
        return true;
    }

    private Boolean isOption(HttpServletRequest request) {
        return HttpMethod.OPTIONS.name().equals((request.getMethod()));
    }
}
