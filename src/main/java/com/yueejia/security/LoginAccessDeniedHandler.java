package com.yueejia.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       org.springframework.security.access.AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth !=null) {
            System.out.print(auth.getName()+" was trying to access protected resource:"+request.getRequestURI());
        }
        response.sendRedirect(request.getContextPath()+"/access-denied");
    }



}

