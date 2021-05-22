package com.salesianostriana.dam.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.salesianostriana.dam.modelo.Alumno;
import com.salesianostriana.dam.modelo.Curso;
import com.salesianostriana.dam.modelo.DatosCinturones;
import com.salesianostriana.dam.modelo.DatosFederados;
import com.salesianostriana.dam.modelo.DatosProvincias;
import com.salesianostriana.dam.servicios.AlumnoServicio;
import com.salesianostriana.dam.servicios.CursoServicio;

import lombok.RequiredArgsConstructor;
/**
 * Esta clase se encarga de controlar las vistas relacionadas con la página principal.
 * @author Manuel Naval
 *
 */
@Controller
@RequiredArgsConstructor
public class MainController {

	@Autowired
	private AlumnoServicio servicio;

	@Autowired
	private CursoServicio cursoServicio;

	/**
	 * Este método nos muestra una lista con los alumnos en la pagina index.
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
	 * Este método permite acceder a los cursos existentes en la base de datos. 
	 * @return
	 */
	@ModelAttribute("lista_cursos")
	public List<Curso> cursos() {
		return cursoServicio.findAll();
	}
	
	/**
	 * Este método permite acceder a una lista de tipos de cinturones existentes en la clase DatosCinturones,
	 * estos datos no pertenencen a una entidad , es una lista constante. 
	 * @return
	 */
	@ModelAttribute("lista_cinturones")
	public List<String> tiposCinturones() {
		return DatosCinturones.lista_cinturones();
	}
	
	/**
	 * Este método permite acceder a una lista de tipos de provincias existentes en la clase DatosProvincias,
	 * estos datos no pertenencen a una entidad , es una lista constante. 
	 * @return
	 */
	@ModelAttribute("lista_provincias")
	public List<String> tiposProvincias() {
		return DatosProvincias.lista_provincias();
	}

	/**
	 * Este método permite acceder a una lista de tipos de federaciones existentes en la clase DatosFederados,
	 * estos datos no pertenencen a una entidad , es una lista constante. 
	 * @return
	 */
	@ModelAttribute("lista_federados")
	public List<String> tiposFederados() {
		return DatosFederados.lista_federados();
	}
}
