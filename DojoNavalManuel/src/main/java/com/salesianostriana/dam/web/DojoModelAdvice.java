package com.salesianostriana.dam.web;

import com.salesianostriana.dam.modelo.*;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@ControllerAdvice
public class DojoModelAdvice {
  @Value("${spring.datasource.url}")
  String databaseUrl;

  @ModelAttribute
  public void common(Model model, HttpServletRequest request) {
    String path = request.getRequestURI().toLowerCase(java.util.Locale.ROOT);
    model.addAttribute(
        "section",
        path.contains("sensei") ? "instructors" : path.contains("curso") ? "courses" : "students");
    model.addAttribute("editorPage", false);
    model.addAttribute("currentPath", request.getRequestURI());
    model.addAttribute("memoryOnly", databaseUrl.startsWith("jdbc:h2:mem:"));
    model.addAttribute("beltOptions", DatosCinturones.lista_cinturones());
    for (String language : List.of("es", "en"))
      model.addAttribute(
          language + "Url",
          UriComponentsBuilder.fromPath(
                  request.getRequestURI().equals("/error") ? "/" : request.getRequestURI())
              .query(request.getQueryString())
              .replaceQueryParam("lang", language)
              .build()
              .toUriString());
  }

  @ExceptionHandler(ResponseStatusException.class)
  public String problem(
      ResponseStatusException exception,
      Model model,
      HttpServletResponse response,
      HttpServletRequest request) {
    common(model, request);
    response.setStatus(exception.getStatus().value());
    model.addAttribute(
        "problemKey", exception.getStatus().value() == 404 ? "problem.missing" : "problem.invalid");
    return "problem";
  }
}
