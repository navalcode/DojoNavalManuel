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
public class SenseiController {
  private final DojoService service;

  @GetMapping("/nuevoSensei")
  public String create(Model model) {
    return show(new PersonForm(), model);
  }

  @GetMapping("/editarSensei/{id}")
  public String edit(@PathVariable long id, Model model) {
    return show(service.personForm(service.instructor(id)), model);
  }

  private String show(PersonForm form, Model model) {
    model.addAttribute("editorPage", true);
    model.addAttribute("form", form);
    model.addAttribute("staff", true);
    model.addAttribute("cursos", service.courses());
    return "agregarSensei";
  }

  @PostMapping("/addSensei")
  public String create(
      @ModelAttribute("form") PersonForm form,
      BindingResult errors,
      Model model,
      HttpServletResponse response,
      RedirectAttributes redirect,
      @RequestParam(required = false) String lang) {
    return save(form, errors, model, response, redirect, true, lang);
  }

  @PostMapping("/editarSenseiSubmit")
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
    service.validate(form, errors, true, create);
    if (errors.hasErrors()) {
      response.setStatus(400);
      return show(form, model);
    }
    service.saveInstructor(form, create);
    redirect.addFlashAttribute("notice", "notice.saved");
    return "redirect:/senseis";
  }

  @GetMapping("/borrarSensei/{id}")
  public String confirm(@PathVariable long id, Model model) {
    model.addAttribute("record", service.instructor(id));
    model.addAttribute("deletePath", "/borrarSensei/" + id);
    model.addAttribute("backPath", "/senseis");
    return "confirm";
  }

  @PostMapping("/borrarSensei/{id}")
  public String delete(
      @PathVariable long id,
      Model model,
      HttpServletResponse response,
      RedirectAttributes redirect) {
    if (!service.deleteInstructor(id)) {
      response.setStatus(409);
      model.addAttribute("problemKey", "problem.instructorUsed");
      return "problem";
    }
    redirect.addFlashAttribute("notice", "notice.deleted");
    return "redirect:/senseis";
  }

  @GetMapping("/senseis")
  public String list(Model model) {
    model.addAttribute("senseis", service.instructors());
    return "senseis";
  }
}
