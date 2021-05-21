package com.salesianostriana.dam.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.modelo.Alumno;
import com.salesianostriana.dam.modelo.Sensei;
import com.salesianostriana.dam.repos.SenseiRepository;
import com.salesianostriana.dam.servicios.base.BaseService;

@Service
public class SenseiServicio extends BaseService<Sensei, Long, SenseiRepository> {

	public SenseiServicio(SenseiRepository repo) {
		super(repo);
	}

	@Override
	public List<Alumno> findByCursoId(Long cursoId) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
