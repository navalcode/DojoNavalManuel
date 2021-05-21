package com.salesianostriana.dam.controladores;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.modelo.Curso;
import com.salesianostriana.dam.servicios.AlumnoServicio;
import com.salesianostriana.dam.servicios.CursoServicio;
import com.salesianostriana.dam.servicios.SenseiServicio;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CursoController {
	
	@Autowired
	private CursoServicio servicio;
	
	@Autowired
	private SenseiServicio senseiServicio;
	
	@Autowired
	private AlumnoServicio alumnoServicio;

	@GetMapping("/cursos")
	public String todosLosCursos(Model model) {
		model.addAttribute("cursos", servicio.findAll());
		model.addAttribute("senseis", senseiServicio.findAll());
		model.addAttribute("alumnos",alumnoServicio.findAll());
		return "cursos";
	}
	
	@GetMapping("/nuevoCurso")
	public String formularioCurso(Model model) {
		model.addAttribute("curso", new Curso());
		model.addAttribute("senseis",senseiServicio.findAll());
		return "agregarCurso";
	}
	
	@PostMapping("/addCurso")
	public String submitCurso(@ModelAttribute("curso") Curso curso, Model model) {
		servicio.save(curso);
		return "redirect:/cursos";
	}
	
	@GetMapping("/editarCurso/{id}")
	public String editarCurso(@PathVariable("id") Long id, Model model) {
		
		Curso curso= servicio.findById(id);
	
		if (curso != null) {
			model.addAttribute("curso", curso);
			model.addAttribute("senseis", senseiServicio.findAll());
			return "agregarCurso";
		} else {
			return "redirect:/";
		}
		
	}
	
	@PostMapping("/editarCursoSubmit")
	public String guardarEdicionCurso(@ModelAttribute("curso") Curso c) {
		servicio.edit(c);
		return "redirect:/cursos";
	}
	
	@GetMapping("/borrarCurso/{id}")
public String borrarCurso(@PathVariable("id") Long id, Model model) {
		
		String direccion="redirect:/cursos";
		
		Curso curso =servicio.findById(id);
		
		
		if(curso!=null && curso.getAlumnos().isEmpty()) {
			servicio.delete(curso);
			
		} else {
			direccion="errorReferencialAlumnos";
		}
		return direccion;
	}
	
	
}
