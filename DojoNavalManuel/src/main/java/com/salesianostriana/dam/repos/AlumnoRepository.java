package com.salesianostriana.dam.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.modelo.Alumno;

public interface AlumnoRepository
extends JpaRepository<Alumno, Long> {

}
