package com.salesianostriana.dam.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.modelo.Curso;
import com.salesianostriana.dam.modelo.DatosCinturones;
import com.salesianostriana.dam.modelo.DatosProvincias;
import com.salesianostriana.dam.modelo.Sensei;
import com.salesianostriana.dam.servicios.AlumnoServicio;
import com.salesianostriana.dam.servicios.CursoServicio;
import com.salesianostriana.dam.servicios.SenseiServicio;

import lombok.RequiredArgsConstructor;
/**
 * Este método controla las vistas relacionadas con los senseis.
 * @author Manuel Naval
 *
 */
@Controller
@RequiredArgsConstructor
public class SenseiController {

	@Autowired
	private SenseiServicio servicio;

	@Autowired
	private CursoServicio cursoServicio;

	@Autowired
	private AlumnoServicio alumnoServicio;

	/**
	 *  Este método permite visualizar todos los cursos existentes
	 * @param model
	 * @return
	 */
	@GetMapping("/senseis")
	public String todosLosCursos(Model model) {
		model.addAttribute("senseis", servicio.findAll());
		model.addAttribute("cursos", cursoServicio.findAll());
		model.addAttribute("alumnos", alumnoServicio.findAll());
		return "senseis";
	}
/**
 * Este método nos redirige a un formulario para crear un nuevo sensei.
 * @param model
 * @return
 */
	@GetMapping("/nuevoSensei")
	public String formularioSensei(Model model) {
		model.addAttribute("sensei", new Sensei());
		model.addAttribute("cursos", cursoServicio.findAll());
		return "agregarSensei";
	}

	/**
	 * Este método guarda en la base de datos un sensei con los datos proporcionados en el formulario
	 * @param sensei
	 * @param model
	 * @return
	 */
	@PostMapping("/addSensei")
	public String submitSensei(@ModelAttribute("sensei") Sensei sensei, Model model) {
		servicio.save(sensei);
		return "redirect:/senseis";
	}
	
	/**
	 *  Este método nos redirige a un formulario proporcionando el id de un sensei, de forma que dicho formulario
	 * se rellene total o parcialmente con los datos del sensei con dicho id.
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/editarSensei/{id}")
	public String editarSensei(@PathVariable("id") Long id, Model model) {
		
		Sensei sensei= servicio.findById(id);
	
		if (sensei != null) {
			model.addAttribute("sensei", sensei);
			model.addAttribute("cursos", cursoServicio.findAll());
			return "agregarSensei";
		} else {
			return "redirect:/senseis";
		}
		
	}
	
	/**
	 * Este método se encarga de cuardar los cambios realizados sobre un formualrio de edición correspondiente a un sensei.
	 * @param s
	 * @return
	 */
	@PostMapping("/editarSenseiSubmit")
	public String guardarEdicionSensei(@ModelAttribute("sensei") Sensei s) {
		servicio.edit(s);
		return "redirect:/senseis";
	}
	
	/**
	 *  Este método comprueba si un sensei con un determinado id es nulo y si no tiene cursos asignados, si es así lo borra, en caso contrario
	 * muestra una vista de error advirtiendo de que el sensei contiene cursos, y primero debemos eliminarlos.
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/borrarSensei/{id}")
public String borrarSensei(@PathVariable("id") Long id, Model model) {
		
		String direccion="redirect:/senseis";
		
		Sensei sensei =servicio.findById(id);
		
		
		if(sensei!=null && sensei.getCurso().isEmpty()) {
			servicio.delete(sensei);
			
		} else {
			direccion="errorReferencialSenseis";
		}
		return direccion;
	}

	/**
	 * Este método permite acceder a los cursos existentes en la base de datos. 
	 * @return
	 */
	@ModelAttribute("lista_cursos")
	public List<Curso> tiposCursos() {
		return cursoServicio.findAll();
	}
	/**
	 * Este método permite acceder a una lista de tipos de cinturones existentes en la clase DatosCinturones,
	 * estos datos no pertenencen a una entidad , es una lista constante. 
	 * @return
	 */
	@ModelAttribute("lista_cinturones")
	public List<String> Cinturones() {
		return DatosCinturones.lista_cinturones();
	}
	
	/**
	 * Este método permite acceder a una lista de tipos de provincias existentes en la clase DatosProvincias,
	 * estos datos no pertenencen a una entidad , es una lista constante. 
	 * @return
	 */
	@ModelAttribute("provincias")
	public List<String> Provincias() {
		return DatosProvincias.lista_provincias();
	}

}
