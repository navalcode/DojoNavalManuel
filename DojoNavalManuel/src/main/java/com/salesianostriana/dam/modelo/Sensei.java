package com.salesianostriana.dam.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;


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
public class Sensei extends Persona {

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "sensei", fetch = FetchType.EAGER)
	private List<Curso> curso = new ArrayList<>();

	private double sueldo;

	public Sensei(String nombre, String apellido1, String apellido2, String email, String dni, String telefono,
			LocalDate fechaNacimiento, String provincia, int codigoPostal, String municipio, String direccion,
			String imagen, String cinturon, List<Curso> curso, double sueldo) {
		super(nombre, apellido1, apellido2, email, dni, telefono, fechaNacimiento, provincia, codigoPostal, municipio,
				direccion, imagen, cinturon);
		this.curso = curso;
		this.sueldo = sueldo;
	}

	public Sensei(String nombre, String apellido1, String apellido2, String email, String dni, String telefono,
			LocalDate fechaNacimiento, String provincia, int codigoPostal, String municipio, String direccion,
			String imagen, String cinturon, double sueldo) {
		super(nombre, apellido1, apellido2, email, dni, telefono, fechaNacimiento, provincia, codigoPostal, municipio,
				direccion, imagen, cinturon);
		this.sueldo = sueldo;
	}

	public void addCurso(Curso c) {
		this.curso.add(c);
		c.setSensei(this);
	}

	public void removeCurso(Curso c) {
		this.curso.remove(c);
		c.setSensei(null);
	}

}
