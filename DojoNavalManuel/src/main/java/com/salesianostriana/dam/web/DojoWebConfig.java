package com.salesianostriana.dam.web;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.*;

@Configuration
public class DojoWebConfig implements WebMvcConfigurer {
  @Bean
  public LocaleResolver localeResolver() {
    CookieLocaleResolver r = new CookieLocaleResolver();
    r.setDefaultLocale(new Locale("es"));
    r.setCookieName("dojo-language");
    r.setCookieHttpOnly(true);
    return r;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    LocaleChangeInterceptor language = new LocaleChangeInterceptor();
    language.setParamName("lang");
    registry.addInterceptor(language);
    registry.addInterceptor(
        new HandlerInterceptor() {
          @Override
          public boolean preHandle(
              HttpServletRequest request, HttpServletResponse response, Object handler)
              throws Exception {
            if (request.getRequestURI().startsWith("/fonts/")
                || request.getRequestURI().equals("/dojo.css")
                || request.getRequestURI().equals("/dojo.js")) return true;
            if (request.getDispatcherType() == javax.servlet.DispatcherType.ERROR) return true;
            HttpSession session = request.getSession();
            String expected = (String) session.getAttribute("formToken");
            if (expected == null) {
              expected = UUID.randomUUID().toString();
              session.setAttribute("formToken", expected);
            }
            if ("POST".equals(request.getMethod())) {
              String supplied = request.getParameter("csrfToken");
              if (supplied == null
                  || !MessageDigest.isEqual(
                      expected.getBytes(StandardCharsets.UTF_8),
                      supplied.getBytes(StandardCharsets.UTF_8))) {
                response.sendError(403);
                return false;
              }
            }
            return true;
          }
        });
  }
}
