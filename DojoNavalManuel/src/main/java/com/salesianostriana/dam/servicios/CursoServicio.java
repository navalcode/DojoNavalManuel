package com.salesianostriana.dam.servicios;



import org.springframework.stereotype.Service;


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





}
