package com.salesianostriana.dam.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.servicios.AlumnoServicio;


import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AlumnoController {
	
	private final AlumnoServicio servicio;

	
	@GetMapping("/alumnos")
	public String todosLosAlumnos(Model model) {
		model.addAttribute("alumnos", servicio.findAll());		
		return "index";
	}
	
	
	

}
