package com.salesianostriana.dam.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.modelo.Alumno;
import com.salesianostriana.dam.modelo.Curso;
import com.salesianostriana.dam.repos.CursoRepository;
import com.salesianostriana.dam.servicios.base.BaseService;

@Service
public class CursoServicio extends BaseService<Curso, Long, CursoRepository> {

	public CursoServicio(CursoRepository repo) {
		super(repo);
	}

	@Override
	public List<Alumno> findByCursoId(Long cursoId) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
