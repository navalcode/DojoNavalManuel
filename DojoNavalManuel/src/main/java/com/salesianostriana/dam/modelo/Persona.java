package com.salesianostriana.dam.modelo;

import java.time.LocalDate;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Esta clase genera una clase pojo que dará herencia a Alumno y Sensei.
 * @author Manuel Naval
 *
 */
@Data
@NoArgsConstructor
@MappedSuperclass
public class Persona {

	@Id
	@GeneratedValue
	private long id;

	private String nombre;
	private String apellido1;
	private String apellido2;
	private String email;
	private String dni;
	private String telefono;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaNacimiento;
	private String provincia;
	private int codigoPostal;
	private String municipio;
	private String direccion;
	private String imagen;
	private String cinturon;
	
	public Persona(String nombre, String apellido1, String apellido2, String email, String dni, String telefono,
			LocalDate fechaNacimiento, String provincia, int codigoPostal, String municipio, String direccion,
			String imagen, String cinturon) {
		super();
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.email = email;
		this.dni = dni;
		this.telefono = telefono;
		this.fechaNacimiento = fechaNacimiento;
		this.provincia = provincia;
		this.codigoPostal = codigoPostal;
		this.municipio = municipio;
		this.direccion = direccion;
		this.imagen = imagen;
		this.cinturon = cinturon;
	}



}
