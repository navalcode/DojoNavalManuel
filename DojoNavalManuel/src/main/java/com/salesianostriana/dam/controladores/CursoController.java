package com.salesianostriana.dam.controladores;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.salesianostriana.dam.modelo.Curso;
import com.salesianostriana.dam.modelo.Sensei;
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
	
	@GetMapping("/borrarCurso/{id}")
public String borrarCurso(@PathVariable("id") Long id, Model model) {
		
		String direccion="redirect:/cursos";
		
		Curso curso =servicio.findById(id);
		Sensei sensei=senseiServicio.findById(curso.getSensei().getId());
		
		
		if(curso!=null && curso.getAlumnos().isEmpty()) {
			curso.removeSensei(sensei);
			senseiServicio.edit(sensei);
			servicio.delete(curso);
			
		} else {
			direccion="errorReferencialAlumnos";
		}
		return direccion;
	}
	
	
}
