package com.salesianostriana.dam.servicios;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.modelo.Alumno;
import com.salesianostriana.dam.repos.AlumnoRepository;
import com.salesianostriana.dam.servicios.base.BaseService;

@Service
public class AlumnoServicio extends BaseService<Alumno, Long, AlumnoRepository>{

	public AlumnoServicio(AlumnoRepository repo) {
		super(repo);
		// TODO Auto-generated constructor stub
	}

	

	
}
