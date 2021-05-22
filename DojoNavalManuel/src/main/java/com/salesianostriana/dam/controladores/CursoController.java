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

/**
 * Este método controla las vistas relacionadas con los cursos.
 * @author naval
 *
 */
@Controller
@RequiredArgsConstructor
public class CursoController {
	
	@Autowired
	private CursoServicio servicio;
	
	@Autowired
	private SenseiServicio senseiServicio;
	
	@Autowired
	private AlumnoServicio alumnoServicio;

	/**
	 * Este método permite visualizar todos los cursos existentes
	 * @param model
	 * @return
	 */
	@GetMapping("/cursos")
	public String todosLosCursos(Model model) {
		model.addAttribute("cursos", servicio.findAll());
		model.addAttribute("senseis", senseiServicio.findAll());
		model.addAttribute("alumnos",alumnoServicio.findAll());
		return "cursos";
	}
	
	/**
	 * Este método nos redirige a un formulario para crear un nuevo curso.
	 * @param model
	 * @return
	 */
	@GetMapping("/nuevoCurso")
	public String formularioCurso(Model model) {
		model.addAttribute("curso", new Curso());
		model.addAttribute("senseis",senseiServicio.findAll());
		return "agregarCurso";
	}
	
	/**
	 * Este método guarda en la base de datos un curso con los datos proporcionados en el formulario
	 * @param curso
	 * @param model
	 * @return
	 */
	@PostMapping("/addCurso")
	public String submitCurso(@ModelAttribute("curso") Curso curso, Model model) {
		servicio.save(curso);
		return "redirect:/cursos";
	}
	
	/**
	 * Este método nos redirige a un formulario proporcionando el id de un curso, de forma que dicho formulario
	 * se rellene total o parcialmente con los datos del curso con dicho id.
	 * @param id
	 * @param model
	 * @return
	 */
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
	
	/**
	 * Este método se encarga de cuardar los cambios realizados sobre un formualrio de edición correspondiente a un curso.
	 * @param c
	 * @return
	 */
	@PostMapping("/editarCursoSubmit")
	public String guardarEdicionCurso(@ModelAttribute("curso") Curso c) {
		servicio.edit(c);
		return "redirect:/cursos";
	}
	
	/**
	 * Este método comprueba si un curso con un determinado id es nulo y si está vacío, si es así lo borra, en caso contrario
	 * muestra una vista de error advirtiendo de que el curso contiene alumnos, y primero debemos eliminarlos.
	 * @param id
	 * @param model
	 * @return
	 */
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
