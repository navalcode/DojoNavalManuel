package com.salesianostriana.dam.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.modelo.Alumno;
import com.salesianostriana.dam.modelo.Curso;
import com.salesianostriana.dam.repos.CursoRepository;
import com.salesianostriana.dam.servicios.base.BaseService;

/**
 * Esta clase genera un servicio que hereda de BaseService.
 * 
 * @author Manuel Naval
 *
 */
@Service
public class CursoServicio extends BaseService<Curso, Long, CursoRepository> {

	public CursoServicio(CursoRepository repo) {
		super(repo);
	}

	/**
	 * Este método sobreescribe a modo de nulo el método findByCursoId que se
	 * encuentra en BaseService, posiblemente no es la mejor solución, sería un buen
	 * punto a mejorar en el futuro.
	 */
	@Override
	public List<Alumno> findByCursoId(Long cursoId) {
		// TODO Auto-generated method stub
		return null;
	}

}
