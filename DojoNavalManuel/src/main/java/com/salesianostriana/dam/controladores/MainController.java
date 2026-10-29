package com.salesianostriana.dam.controladores;

import com.salesianostriana.dam.web.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/** Original coursework by Manuel Naval; reviewed navigation and explicit form contracts. */
@Controller
@RequiredArgsConstructor
public class MainController {
  private final DojoService service;

  @GetMapping({"/", "/ordenarAlfabeticamente", "/ordenarEdad"})
  public String students(
      @RequestParam(required = false) Long idCurso,
      @RequestParam(defaultValue = "name") String sort,
      javax.servlet.http.HttpServletRequest request,
      Model model) {
    if (request.getRequestURI().equals("/ordenarEdad")) sort = "age";
    if (request.getRequestURI().equals("/ordenarAlfabeticamente")) sort = "name";
    if (!sort.equals("name") && !sort.equals("age")) sort = "name";
    model.addAttribute("alumnos", service.students(idCurso, sort));
    model.addAttribute("cursos", service.courses());
    model.addAttribute("selectedCourse", idCurso);
    model.addAttribute(
        "selectedCourseName", idCurso == null ? null : service.course(idCurso).getNombre());
    model.addAttribute("sort", sort);
    return "index";
  }
}
