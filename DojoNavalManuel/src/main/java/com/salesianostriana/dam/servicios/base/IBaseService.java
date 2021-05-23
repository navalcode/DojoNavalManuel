package com.salesianostriana.dam.servicios.base;

import java.util.List;



/**
 * Esta interfaz permitirá implementar los métodos CRUD para las entidades del
 * proyecto.
 * 
 * @author Manuel Naval
 *
 * @param <T>
 * @param <ID>
 */
public interface IBaseService<T, ID> {

	/**
	 * Almacenamos una nueva entidad a través del repositorio
	 * 
	 * @param t
	 * @return
	 */
	T save(T t);

	/**
	 * Localizamos una entidad en base a su Id
	 * 
	 * @param id
	 * @return
	 */
	T findById(ID id);

	/**
	 * Obtenemos todas las entidades de un tipo de entidad
	 * 
	 * @return
	 */
	List<T> findAll();

	/**
	 * Editamos una instancia de una entidad (si no tiene Id, la insertamos).
	 * 
	 * @param t
	 * @return
	 */
	T edit(T t);

	/**
	 * Eliminamos una instancia de una entidad
	 * 
	 * @param t
	 */
	void delete(T t);

	/**
	 * Eliminamos una instancia en base a su ID
	 * 
	 * @param id
	 */
	void deleteById(ID id);

	/**
	 * Este método realiza una consulta que permite filtrar los alumnos por el id
	 * del curso que contienen, no debería estar presente en una interfaz, y debería
	 * solucionarse reestructurando los servicios, simplemente no he dado con la
	 * solución antes de la fecha de entrega.
	 * 
	 * @param cursoId
	 * @return
	 */

	
}