package com.salesianostriana.dam.modelo;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
public class Alumno extends Persona {
	

	
	private String imagen;
	private LocalDate fechaInscripcion;
	private boolean federado;
	private String cinturon;
	private String comentarios;
	
	@ManyToOne
	private Curso curso;

	public Alumno(String nombre, String apellido1, String apellido2, String email, String telefono,
			LocalDate fechaNacimiento, String provincia, int codigoPostal, String municipio, String direccion,
			String imagen, LocalDate fechaInscripcion, boolean federado, String cinturon, String comentarios) {
		super(nombre, apellido1, apellido2, email, telefono, fechaNacimiento, provincia, codigoPostal, municipio,
				direccion);
		this.imagen = imagen;
		this.fechaInscripcion = fechaInscripcion;
		this.federado = federado;
		this.cinturon = cinturon;
		this.comentarios = comentarios;
	}

	public Alumno(String nombre, String apellido1, String apellido2, String email, String telefono,
			LocalDate fechaNacimiento, String provincia, int codigoPostal, String municipio, String direccion,
			String imagen, LocalDate fechaInscripcion, boolean federado, String cinturon, String comentarios,
			Curso curso) {
		super(nombre, apellido1, apellido2, email, telefono, fechaNacimiento, provincia, codigoPostal, municipio,
				direccion);
		this.imagen = imagen;
		this.fechaInscripcion = fechaInscripcion;
		this.federado = federado;
		this.cinturon = cinturon;
		this.comentarios = comentarios;
		this.curso = curso;
	}
	




	
	
	
	

}
