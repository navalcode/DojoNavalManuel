package com.salesianostriana.dam.servicios;

import org.springframework.stereotype.Service;


import com.salesianostriana.dam.modelo.Sensei;
import com.salesianostriana.dam.repos.SenseiRepository;
import com.salesianostriana.dam.servicios.base.BaseService;

@Service
public class SenseiServicio extends BaseService<Sensei, Long, SenseiRepository> {

	public SenseiServicio(SenseiRepository repo) {
		super(repo);
	}

	
}
