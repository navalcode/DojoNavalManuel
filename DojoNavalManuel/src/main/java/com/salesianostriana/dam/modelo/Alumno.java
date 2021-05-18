package com.salesianostriana.dam.modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
public class Alumno extends Persona {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaInscripcion;
	private boolean federado;
	private String cinturon;
	private String comentarios;

	@ManyToOne
	private Curso curso;

	public Alumno(String nombre, String apellido1, String apellido2, String email, String dni, String telefono,
			LocalDate fechaNacimiento, String provincia, int codigoPostal, String municipio, String direccion,
			String imagen, LocalDate fechaInscripcion, boolean federado, String cinturon, String comentarios) {
		super(nombre, apellido1, apellido2, email, dni, telefono, fechaNacimiento, provincia, codigoPostal, municipio,
				direccion, imagen);
		this.fechaInscripcion = fechaInscripcion;
		this.federado = federado;
		this.cinturon = cinturon;
		this.comentarios = comentarios;
	}

	public Alumno(String nombre, String apellido1, String apellido2, String email, String dni, String telefono,
			LocalDate fechaNacimiento, String provincia, int codigoPostal, String municipio, String direccion,
			String imagen, LocalDate fechaInscripcion, boolean federado, String cinturon, String comentarios,
			Curso curso) {
		super(nombre, apellido1, apellido2, email, dni, telefono, fechaNacimiento, provincia, codigoPostal, municipio,
				direccion, imagen);
		this.fechaInscripcion = fechaInscripcion;
		this.federado = federado;
		this.cinturon = cinturon;
		this.comentarios = comentarios;
		this.curso = curso;
	}

	public long calcularEdad() {
		return ChronoUnit.YEARS.between(this.getFechaNacimiento(), LocalDate.now());
	}

}
