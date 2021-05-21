package com.salesianostriana.dam.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.salesianostriana.dam.modelo.Alumno;
import com.salesianostriana.dam.modelo.Curso;
import com.salesianostriana.dam.modelo.DatosCinturones;
import com.salesianostriana.dam.modelo.DatosFederados;
import com.salesianostriana.dam.modelo.DatosProvincias;
import com.salesianostriana.dam.servicios.AlumnoServicio;
import com.salesianostriana.dam.servicios.CursoServicio;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	@Autowired
	private AlumnoServicio servicio;

	@Autowired
	private CursoServicio cursoServicio;

	/**
	 * todosLosAlumnos nos muestra una lista con los alumnos en la pagina index
	 * 
	 * @param model
	 * @return index.html
	 */
	
	
	@GetMapping("/")
	public String todosLosAlumnos(@RequestParam(name="idCurso", required=false) Long idCurso, Model model) {		
		
	
		model.addAttribute("cursos", cursoServicio.findAll());
		
		List<Alumno> alumnos;
	
		
		
		if (idCurso == null) {
			alumnos = servicio.findAll();
		} else {			
			alumnos= servicio.findByCursoId(idCurso);
		}
		
		model.addAttribute("alumnos", alumnos);
		
		return "index";
	}
	

	/**
	 * Proxima funcionalidad, VER DETALLES, permitirá ver los detalles de un alumno
	 * por su id.
	 * 
	 * @return
	 */
	@GetMapping("/alumno/{id}")
	public String showDetails(@PathVariable("id") Long id, Model model) {

		Alumno a = servicio.findById(id);

		if (a != null) {
			model.addAttribute("alumno", a);
			return "detalle";
		}

		return "redirect:/";

	}

	@ModelAttribute("lista_cursos")
	public List<Curso> cursos() {
		return cursoServicio.findAll();
	}

	@ModelAttribute("lista_cinturones")
	public List<String> tiposCinturones() {
		return DatosCinturones.lista_cinturones();
	}

	@ModelAttribute("lista_provincias")
	public List<String> tiposProvincias() {
		return DatosProvincias.lista_provincias();
	}

	@ModelAttribute("lista_federados")
	public List<String> tiposFederados() {
		return DatosFederados.lista_federados();
	}
}
