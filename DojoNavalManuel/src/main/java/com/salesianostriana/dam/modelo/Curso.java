package com.salesianostriana.dam.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * Esta clase genera una entidad Curso con asociación ManyToOne hacia sensei y OneToMany hacia Alumno.
 * @author Manuel Naval
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Curso {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	private int anio;
	private String nombre;
	private double precio;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	    @ManyToOne(fetch = FetchType.EAGER)
	    private Sensei sensei;

	public Curso(int anio, String nombre, double precio) {
		super();
		this.anio = anio;
		this.nombre = nombre;
		this.precio = precio;
	}

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "curso", fetch = FetchType.EAGER)
	private List<Alumno> alumnos = new ArrayList<>();

	/**
	 * Método auxiliar para el tratamiento bidireccional de la asociación. Añade a
	 * un alumno a la colección de alumnos de un curso, y asigna a dicho alumno este
	 * curso como el suyo.
	 * 
	 * @param a
	 */
	public void addAlumno(Alumno a) {
		this.alumnos.add(a);
		a.setCurso(this);
	}

	/**
	 * Método auxiliar para el tratamiento bidireccional de la asociación. Elimina
	 * un alumno de la colección de alumnos de un curdso, y desasigna a dicho alumno
	 * el curso, dejándolo como nulo.
	 * 
	 * @param a
	 */
	public void removeAlumno(Alumno a) {
		this.alumnos.remove(a);
		a.setCurso(null);
	}
	
	

}
