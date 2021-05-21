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

@Controller
@RequiredArgsConstructor
public class SenseiController {

	@Autowired
	private SenseiServicio servicio;

	@Autowired
	private CursoServicio cursoServicio;

	@Autowired
	private AlumnoServicio alumnoServicio;

	@GetMapping("/senseis")
	public String todosLosCursos(Model model) {
		model.addAttribute("senseis", servicio.findAll());
		model.addAttribute("cursos", cursoServicio.findAll());
		model.addAttribute("alumnos", alumnoServicio.findAll());
		return "senseis";
	}

	@GetMapping("/nuevoSensei")
	public String formularioSensei(Model model) {
		model.addAttribute("sensei", new Sensei());
		model.addAttribute("cursos", cursoServicio.findAll());
		return "agregarSensei";
	}

	@PostMapping("/addSensei")
	public String submitSensei(@ModelAttribute("sensei") Sensei sensei, Model model) {
		servicio.save(sensei);
		return "redirect:/senseis";
	}
	
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
	
	@PostMapping("/editarSenseiSubmit")
	public String guardarEdicionSensei(@ModelAttribute("sensei") Sensei s) {
		servicio.edit(s);
		return "redirect:/senseis";
	}
	
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

	@ModelAttribute("lista_cursos")
	public List<Curso> tiposCursos() {
		return cursoServicio.findAll();
	}

	@ModelAttribute("lista_cinturones")
	public List<String> Cinturones() {
		return DatosCinturones.lista_cinturones();
	}

	@ModelAttribute("provincias")
	public List<String> Provincias() {
		return DatosProvincias.lista_provincias();
	}

}
