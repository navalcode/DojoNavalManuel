package com.salesianostriana.dam.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesianostriana.dam.modelo.Alumno;

/**
 * Esta interfaz hereda de JpaRepository ayudará a implementar un repositorio en
 * AlumnoServicio
 * 
 * @author Manuel Naval
 *
 */
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

	/**
	 * Este método realiza una consulta que permite filtrar los alumnos por el id
	 * del curso que contienen.
	 * 
	 * @param cursoId
	 * @return
	 */
	@Query("select a from Alumno a where a.curso.id = ?1")
	public List<Alumno> findByCursoId(Long cursoId);

	/**
	 * Este método realiza una consulta a la base de datos que devuelve todos los
	 * alumnos ordenados alfabéticamente por nombre.
	 * 
	 * @return
	 */
	@Query("select a from Alumno a order by a.nombre")
	public List<Alumno> findAllSorted();

	/**
	 * Este método realiza una consulta a la base de datos que devuelve todos los
	 * alumnos ordenados de menor a mayor edad.
	 * 
	 * @return
	 */
	@Query("select a from Alumno a order by a.fechaNacimiento desc")
	public List<Alumno> findAllSortedEdad();

}
