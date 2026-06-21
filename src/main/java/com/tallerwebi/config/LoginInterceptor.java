package com.tallerwebi.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        // Si el usuario ya está logueado, lo dejamos pasar libremente
        if (session != null && session.getAttribute("usuarioId") != null) {
            return true;
        }

        // Si no está logueado, lo redirigimos a la pantalla de login
        response.sendRedirect(request.getContextPath() + "/login");
        return false;
    }
}
