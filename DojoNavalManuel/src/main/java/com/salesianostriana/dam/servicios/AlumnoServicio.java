package com.salesianostriana.dam.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.modelo.Alumno;
import com.salesianostriana.dam.repos.AlumnoRepository;
import com.salesianostriana.dam.servicios.base.BaseService;

@Service
public class AlumnoServicio extends BaseService<Alumno, Long, AlumnoRepository>{
	@Autowired
	private AlumnoRepository repositorio;

	public AlumnoServicio(AlumnoRepository repo) {
		super(repo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Alumno> findByCursoId(Long cursoId) {
		// TODO Auto-generated method stub
		return repositorio.findByCursoId(cursoId);
	}

	
	
	}

	

	

