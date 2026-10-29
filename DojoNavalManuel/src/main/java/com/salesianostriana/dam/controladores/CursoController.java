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
public class CursoController {
  private final DojoService service;

  @GetMapping("/cursos")
  public String list(Model model) {
    model.addAttribute("cursos", service.courses());
    return "cursos";
  }

  @GetMapping("/nuevoCurso")
  public String create(Model model) {
    return show(new CourseForm(), model);
  }

  @GetMapping("/editarCurso/{id}")
  public String edit(@PathVariable long id, Model model) {
    return show(service.courseForm(service.course(id)), model);
  }

  private String show(CourseForm form, Model model) {
    model.addAttribute("editorPage", true);
    model.addAttribute("form", form);
    model.addAttribute("senseis", service.instructors());
    return "agregarCurso";
  }

  @PostMapping("/addCurso")
  public String create(
      @ModelAttribute("form") CourseForm form,
      BindingResult errors,
      Model model,
      HttpServletResponse response,
      RedirectAttributes redirect,
      @RequestParam(required = false) String lang) {
    return save(form, errors, model, response, redirect, true, lang);
  }

  @PostMapping("/editarCursoSubmit")
  public String edit(
      @ModelAttribute("form") CourseForm form,
      BindingResult errors,
      Model model,
      HttpServletResponse response,
      RedirectAttributes redirect,
      @RequestParam(required = false) String lang) {
    return save(form, errors, model, response, redirect, false, lang);
  }

  private String save(
      CourseForm form,
      BindingResult errors,
      Model model,
      HttpServletResponse response,
      RedirectAttributes redirect,
      boolean create,
      String lang) {
    if ("es".equals(lang) || "en".equals(lang)) return show(form, model);
    service.validate(form, errors, create);
    if (errors.hasErrors()) {
      response.setStatus(400);
      return show(form, model);
    }
    service.saveCourse(form, create);
    redirect.addFlashAttribute("notice", "notice.saved");
    return "redirect:/cursos";
  }

  @GetMapping("/borrarCurso/{id}")
  public String confirm(@PathVariable long id, Model model) {
    model.addAttribute("record", service.course(id));
    model.addAttribute("deletePath", "/borrarCurso/" + id);
    model.addAttribute("backPath", "/cursos");
    return "confirm";
  }

  @PostMapping("/borrarCurso/{id}")
  public String delete(
      @PathVariable long id,
      Model model,
      HttpServletResponse response,
      RedirectAttributes redirect) {
    if (!service.deleteCourse(id)) {
      response.setStatus(409);
      model.addAttribute("problemKey", "problem.courseUsed");
      return "problem";
    }
    redirect.addFlashAttribute("notice", "notice.deleted");
    return "redirect:/cursos";
  }
}
