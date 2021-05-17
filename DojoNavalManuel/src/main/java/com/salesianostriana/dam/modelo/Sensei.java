package com.salesianostriana.dam.modelo;

import java.time.LocalDate;

import javax.persistence.Entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
public class Sensei extends Persona {

	private String curso;
	private double sueldo;
	public Sensei(String nombre, String apellido1, String apellido2, String email,String dni, String telefono,
			LocalDate fechaNacimiento, String provincia, int codigoPostal, String municipio, String direccion,
			String curso, double sueldo) {
		super(nombre, apellido1, apellido2, email,dni, telefono, fechaNacimiento, provincia, codigoPostal, municipio,
				direccion);
		this.curso = curso;
		this.sueldo = sueldo;
	}
	
	
	
	
}
