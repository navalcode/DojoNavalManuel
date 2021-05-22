package com.salesianostriana.dam.repos;

import org.springframework.data.jpa.repository.JpaRepository;


import com.salesianostriana.dam.modelo.Sensei;
/**
 * Esta interfaz hereda de JpaRepository ayudará a implementar un repositorio en SenseiServicio.
 * @author Manuel Naval
 *
 */
public interface SenseiRepository extends JpaRepository<Sensei, Long>{

}