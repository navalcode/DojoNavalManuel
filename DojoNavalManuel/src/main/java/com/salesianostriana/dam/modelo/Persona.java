package com.salesianostriana.dam.modelo;

import java.time.LocalDate;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.NoArgsConstructor;

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
	private String telefono;
	private LocalDate fechaNacimiento;
	private String provincia;
	private int codigoPostal;
	private String municipio;
	private String direccion;
	
	
	public Persona(String nombre, String apellido1, String apellido2, String email, String telefono,
			LocalDate fechaNacimiento, String provincia, int codigoPostal, String municipio, String direccion) {
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
	}
	
	
}
