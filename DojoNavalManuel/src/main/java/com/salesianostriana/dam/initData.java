package com.salesianostriana.dam;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.salesianostriana.dam.modelo.Alumno;
import com.salesianostriana.dam.servicios.AlumnoServicio;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class initData {
	
	private final AlumnoServicio alumnoServicio;
	
	
	@PostConstruct
	public void init () {
		Alumno a =new Alumno ("Manuel","Naval","Rodríguez", null, "651666999", null, null, 0, null, null, 0, null, false, null, null);
		Alumno b =new Alumno ("Sergio","Perez","Peinado", null, null, null, null, 0, null, null, 0, null, false, null, null);
		Alumno c =new Alumno ("Sara","Clarke","Menos", null, null, null, null, 0, null, null, 0, null, false, null, null);
		Alumno d =new Alumno ("Cristina","Stromae","Ucchi", null, null, null, null, 0, null, null, 0, null, false, null, null);
		
		alumnoServicio.save(a);
		alumnoServicio.save(b);
		alumnoServicio.save(c);
		alumnoServicio.save(d);
		
	}
}
