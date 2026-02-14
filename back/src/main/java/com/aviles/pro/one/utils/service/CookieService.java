package com.aviles.pro.one.utils.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CookieService {

    @Value("${app.cookie.secure}")
    private boolean cookieSecure;

    /**
     * que hace este metodo pues
     * 
     * @param response es la respuesta del servidor
     * @param name     es el nombre de la cookie
     * @param value    es el valor de la cookie
     * @param maxAge   es la fecha de expiracion de la cookie esto es en segundos
     */
    public void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);// esto es para que no se pueda acceder a la cookie desde el cliente
        cookie.setSecure(cookieSecure);// esto es para desarrollo, en producción debe ser true
        cookie.setPath("/");
        cookie.setAttribute("SameSite", "Lax"); // esto es para que la cookie no se envie en peticiones cross-site
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 
     * @param response es la respuesta del servidor
     * @param name     es el nombre de la cookie
     */
    public void removeCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setHttpOnly(true); // esto es para que no se pueda acceder a la cookie desde el cliente
        cookie.setSecure(cookieSecure); // esto es para desarrollo, en producción debe ser true
        cookie.setPath("/");
        cookie.setAttribute("SameSite", "Lax"); // esto es para que la cookie no se envie en peticiones cross-site
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }



    /**
     * 
     * @param request  es la peticion del cliente
     * @param response es la respuesta del servidor
     * @param name     es el nombre de la cookie
     */
    public void deleteUserCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    removeCookie(response, cookie.getName());
                }
            }
        }
    }

    /**
     * 
     * @param request es la peticion del cliente
     * @param name    es el nombre de la cookie
     * @return el valor de la cookie
     */
    public String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
