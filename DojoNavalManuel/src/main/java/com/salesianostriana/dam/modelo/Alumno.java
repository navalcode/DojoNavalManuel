package com.salesianostriana.dam.modelo;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Alumno {
	
	@Id
	@GeneratedValue
private long id;
	
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String email;
	private String telefono;
	private LocalDate fechaNacimiento;
	private String provincia;
	private int codigoPostal;
	private String municipio;
	private String direccion;
	private int curso;
	
	private LocalDate fechaInscripcion;
	private boolean federado;
	private String cinturon;
	private String comentarios;
	
	public Alumno(String nombre, String apellido1, String apellido2, String email, String telefono,
			LocalDate fechaNacimiento, String provincia, int codigoPostal, String municipio, String direccion,
			int curso, LocalDate fechaInscripcion, boolean federado, String cinturon, String comentarios) {
		super();
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.email = email;
		this.telefono = telefono;
		this.fechaNacimiento = fechaNacimiento;
		this.provincia = provincia;
		this.codigoPostal = codigoPostal;
		this.municipio = municipio;
		this.direccion = direccion;
		this.curso = curso;
		this.fechaInscripcion = fechaInscripcion;
		this.federado = federado;
		this.cinturon = cinturon;
		this.comentarios = comentarios;
	}


	
	
	
	

}
