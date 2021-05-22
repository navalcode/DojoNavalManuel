package com.salesianostriana.dam.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.modelo.Alumno;
import com.salesianostriana.dam.modelo.Curso;
import com.salesianostriana.dam.modelo.DatosCinturones;
import com.salesianostriana.dam.modelo.DatosFederados;
import com.salesianostriana.dam.modelo.DatosProvincias;
import com.salesianostriana.dam.servicios.AlumnoServicio;
import com.salesianostriana.dam.servicios.CursoServicio;

import lombok.RequiredArgsConstructor;
/**
 * Esta clase se encarga de controlar las vistas para los alumnos
 * @author Manuel Naval
 *
 */
@Controller
@RequiredArgsConstructor
public class AlumnoController {

	@Autowired
	private AlumnoServicio servicio;

	@Autowired
	private CursoServicio cursoServicio;
	
	/**
	 * Este método redirige a la vista detalleAlumnos o a la pagina principal en caso de que el alumno sea nulo.
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/detalleAlumnos/{id}")
	public String verAlumno(@PathVariable("id") Long id, Model model) {

		Alumno alumno= servicio.findById(id);
		
		if (alumno != null) {
			model.addAttribute("alumno", alumno);
			model.addAttribute("cursos", cursoServicio.findAll());
			return "detalleAlumno";
		} else {
			return "redirect:/";
		}
		
	}

	/**
	 * Este método nos redirige a la pagina para crear un nuevo alumno
	 * 
	 * @param model
	 * @return agregar.html
	 */
	@GetMapping("/nuevo")
	public String formularioAlumno(Model model) {
		model.addAttribute("alumno", new Alumno());
		return "agregar";
	}

	/**
	 * Este método guarda el nuevo alumno y posteriormente nos redirige a la página
	 * principal.
	 * 
	 * @param alumno: El alumno que tomará del formulario.
	 * @param model
	 * @return redirect:/
	 */
	@PostMapping("/addAlumno")
	public String submit(@ModelAttribute("alumno") Alumno alumno, Model model) {

		
		servicio.save(alumno);
		return "redirect:/";
	}
	
	/**
	 * Este método permite editar un nuevo alumno, redirigiendonos a la pagina agregar con un alumno seleccionado por
	 * id.
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/editarAlumno/{id}")
	public String editarAlumno(@PathVariable("id") Long id, Model model) {
		
		Alumno alumno= servicio.findById(id);
	
		if (alumno != null) {
			model.addAttribute("alumno", alumno);
			model.addAttribute("cursos", cursoServicio.findAll());
			return "agregar";
		} else {
			return "redirect:/";
		}
		
	}
	 /**
	  * Este método edita el alumno con los nuevos datos tomados del formulario.
	  * @param a
	  * @return
	  */
	@PostMapping("/editarSubmit")
	public String guardarEdicionAlumno(@ModelAttribute("alumno") Alumno a) {
		servicio.edit(a);
		return "redirect:/";
	}
	
	/**
	 * Este método borra un alumno dado su id.
	 * @param id
	 * @param model
	 * @return
	 */
	
	@GetMapping("/borrarAlumno/{id}")
	public String borrarAlumno(@PathVariable("id") Long id, Model model) {

		Alumno alumno = servicio.findById(id);

		if (alumno != null) {
			servicio.delete(alumno);
		}

		return "redirect:/";

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
