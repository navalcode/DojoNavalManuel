package com.salesianostriana.dam.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.modelo.Alumno;
import com.salesianostriana.dam.repos.AlumnoRepository;
import com.salesianostriana.dam.servicios.base.BaseService;

/**
 * Esta clase genera un servicio que hereda de BaseService.
 * 
 * @author Manuel Naval
 *
 */
@Service
public class AlumnoServicio extends BaseService<Alumno, Long, AlumnoRepository> {
	@Autowired
	private AlumnoRepository repositorio;

	public AlumnoServicio(AlumnoRepository repo) {
		super(repo);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Este método realiza una consulta a la base de datos que devuelve una lista
	 * con todos los alumnos con el mismo id de curso.
	 */

	public List<Alumno> findByCursoId(Long cursoId) {
		// TODO Auto-generated method stub
		return repositorio.findByCursoId(cursoId);
	}

	/**
	 * Este método realiza una consulta a la base de datos que devuelve todos los
	 * alumnos ordenados alfabéticamente por nombre.
	 * 
	 * @return
	 */
	public List<Alumno> findAllSorted() {
		return repositorio.findAllSorted();
	}

	/**
	 * Este método realiza una consulta a la base de datos que devuelve todos los
	 * alumnos ordenados de menor a mayor edad.
	 * 
	 * @return
	 */
	public List<Alumno> findAllSortedEdad() {
		// TODO Auto-generated method stub
		return repositorio.findAllSortedEdad();
	}

}
