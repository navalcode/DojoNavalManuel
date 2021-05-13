package com.salesianostriana.dam;

import org.springframework.stereotype.Component;

import com.salesianostriana.dam.modelo.Alumno;
import com.salesianostriana.dam.servicios.AlumnoServicio;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class initData {
	
	private final AlumnoServicio alumnoServicio;
	
	
	
	public void init () {
		Alumno a =new Alumno ("Manuel","Naval","correo");
		Alumno b =new Alumno ("Manuel","Naval","correo");
		alumnoServicio.save(a);
		alumnoServicio.save(b);
		
	}
}
