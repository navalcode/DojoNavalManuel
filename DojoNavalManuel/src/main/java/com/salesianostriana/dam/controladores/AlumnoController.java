package com.salesianostriana.dam.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.modelo.Alumno;
import com.salesianostriana.dam.servicios.AlumnoServicio;


import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AlumnoController {
	
	private final AlumnoServicio servicio;

	
	@GetMapping("/")
	public String todosLosAlumnos(Model model) {
		model.addAttribute("alumnos", servicio.findAll());		
		return "index";
	}
	
	@GetMapping("/nuevo")
	public String formularioAlumno(Model model) {
		model.addAttribute("alumno", new Alumno());
		return "agregar";
	}
	
	@PostMapping ("/addAlumno")
	public String submit(@ModelAttribute("alumno") Alumno alumno,  Model model) {

		model.addAttribute("alumno", alumno);
		servicio.save(alumno);
		return "redirect:/";
	}
	

}
