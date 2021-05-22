package com.salesianostriana.dam.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.modelo.Curso;

/**
 * Esta interfaz hereda de JpaRepository ayudará a implementar un repositorio en
 * CursoServicio.
 * 
 * @author Manuel Naval
 *
 */
public interface CursoRepository extends JpaRepository<Curso, Long> {

}
