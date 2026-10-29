package com.salesianostriana.dam.controladores;

import com.salesianostriana.dam.web.*;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Original coursework by Manuel Naval; reviewed navigation and explicit form contracts. */
@Controller
@RequiredArgsConstructor
public class AlumnoController {
  private final DojoService service;

  @GetMapping("/nuevo")
  public String create(Model model) {
    return show(new PersonForm(), model);
  }

  @GetMapping("/editarAlumno/{id}")
  public String edit(@PathVariable long id, Model model) {
    return show(service.personForm(service.student(id)), model);
  }

  private String show(PersonForm form, Model model) {
    model.addAttribute("editorPage", true);
    model.addAttribute("form", form);
    model.addAttribute("staff", false);
    model.addAttribute("cursos", service.courses());
    return "agregar";
  }

  @PostMapping("/addAlumno")
  public String create(
      @ModelAttribute("form") PersonForm form,
      BindingResult errors,
      Model model,
      HttpServletResponse response,
      RedirectAttributes redirect,
      @RequestParam(required = false) String lang) {
    return save(form, errors, model, response, redirect, true, lang);
  }

  @PostMapping("/editarSubmit")
  public String edit(
      @ModelAttribute("form") PersonForm form,
      BindingResult errors,
      Model model,
      HttpServletResponse response,
      RedirectAttributes redirect,
      @RequestParam(required = false) String lang) {
    return save(form, errors, model, response, redirect, false, lang);
  }

  private String save(
      PersonForm form,
      BindingResult errors,
      Model model,
      HttpServletResponse response,
      RedirectAttributes redirect,
      boolean create,
      String lang) {
    if ("es".equals(lang) || "en".equals(lang)) return show(form, model);
    service.validate(form, errors, false, create);
    if (errors.hasErrors()) {
      response.setStatus(400);
      return show(form, model);
    }
    service.saveStudent(form, create);
    redirect.addFlashAttribute("notice", "notice.saved");
    return "redirect:/";
  }

  @GetMapping("/borrarAlumno/{id}")
  public String confirm(@PathVariable long id, Model model) {
    model.addAttribute("record", service.student(id));
    model.addAttribute("deletePath", "/borrarAlumno/" + id);
    model.addAttribute("backPath", "/");
    return "confirm";
  }

  @PostMapping("/borrarAlumno/{id}")
  public String delete(
      @PathVariable long id,
      Model model,
      HttpServletResponse response,
      RedirectAttributes redirect) {
    if (!service.deleteStudent(id)) {
      response.setStatus(409);
      model.addAttribute("problemKey", "problem.invalid");
      return "problem";
    }
    redirect.addFlashAttribute("notice", "notice.deleted");
    return "redirect:/";
  }

  @GetMapping("/detalleAlumnos/{id}")
  public String detail(@PathVariable long id, Model model) {
    model.addAttribute("alumno", service.student(id));
    return "detalleAlumno";
  }
}
