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

/**
 * Esta clase genera una entidad Sensei con asociación OneToMany hacia curso.
 *
 * @author naval
 */
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

  public Sensei(
      String nombre,
      String apellido1,
      String apellido2,
      String email,
      String dni,
      String telefono,
      LocalDate fechaNacimiento,
      String provincia,
      int codigoPostal,
      String municipio,
      String direccion,
      String imagen,
      String cinturon,
      List<Curso> curso,
      double sueldo) {
    super(
        nombre,
        apellido1,
        apellido2,
        email,
        dni,
        telefono,
        fechaNacimiento,
        provincia,
        codigoPostal,
        municipio,
        direccion,
        imagen,
        cinturon);
    this.curso = curso;
    this.sueldo = sueldo;
  }

  public Sensei(
      String nombre,
      String apellido1,
      String apellido2,
      String email,
      String dni,
      String telefono,
      LocalDate fechaNacimiento,
      String provincia,
      int codigoPostal,
      String municipio,
      String direccion,
      String imagen,
      String cinturon,
      double sueldo) {
    super(
        nombre,
        apellido1,
        apellido2,
        email,
        dni,
        telefono,
        fechaNacimiento,
        provincia,
        codigoPostal,
        municipio,
        direccion,
        imagen,
        cinturon);
    this.sueldo = sueldo;
  }

  /**
   * Este método agrega un curso a la lista de cursos de un sensei.
   *
   * @param c
   */
  public void addCurso(Curso item) {
    java.util.Objects.requireNonNull(item, "item");
    Sensei previous = item.getSensei();
    if (previous != null && previous != this) previous.getCurso().removeIf(value -> value == item);
    item.setSensei(this);
    if (curso.stream().noneMatch(value -> value == item)) curso.add(item);
  }

  public void removeCurso(Curso item) {
    java.util.Objects.requireNonNull(item, "item");
    curso.removeIf(value -> value == item);
    if (item.getSensei() == this) item.setSensei(null);
  }
}
