package com.salesianostriana.dam.repos;





import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesianostriana.dam.modelo.Alumno;


public interface AlumnoRepository
extends JpaRepository<Alumno, Long> {

	@Query("select a from Alumno a where a.curso.id = ?1")
	public List<Alumno> findByCursoId(Long cursoId);
	

}
