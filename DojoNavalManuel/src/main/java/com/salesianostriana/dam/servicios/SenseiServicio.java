package com.salesianostriana.dam.servicios;



import org.springframework.stereotype.Service;


import com.salesianostriana.dam.modelo.Sensei;
import com.salesianostriana.dam.repos.SenseiRepository;
import com.salesianostriana.dam.servicios.base.BaseService;

/**
 * Esta clase genera un servicio que hereda de BaseService.
 * 
 * @author Manuel Naval
 *
 */
@Service
public class SenseiServicio extends BaseService<Sensei, Long, SenseiRepository> {

	public SenseiServicio(SenseiRepository repo) {
		super(repo);
	}

	/**
	 * Este método sobreescribe a modo de nulo el método findByCursoId que se
	 * encuentra en BaseService, posiblemente no es la mejor solución, sería un buen
	 * punto a mejorar en el futuro.
	 */




}
