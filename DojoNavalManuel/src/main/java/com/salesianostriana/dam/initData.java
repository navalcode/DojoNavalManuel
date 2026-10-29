package com.salesianostriana.dam;

import com.salesianostriana.dam.modelo.*;
import com.salesianostriana.dam.repos.*;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/** Original coursework initializer by Manuel Naval; new fixtures are entirely fictional. */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "dojo.demo.seed", havingValue = "true", matchIfMissing = true)
public class initData implements CommandLineRunner {
  private final AlumnoRepository students;
  private final CursoRepository courses;
  private final SenseiRepository instructors;

  @Override
  public void run(String... args) {
    if (students.count() != 0 || courses.count() != 0 || instructors.count() != 0) return;
    Sensei first = new Sensei();
    first.setNombre("Ari");
    first.setApellido1("Demo");
    first.setEmail("ari@example.invalid");
    first.setCinturon("Negro 1 DAN");
    first = instructors.save(first);
    Sensei second = new Sensei();
    second.setNombre("Noa");
    second.setApellido1("Ejemplo");
    second.setEmail("noa@example.invalid");
    second.setCinturon("Negro 2 DAN");
    second = instructors.save(second);
    Curso intro = new Curso(2026, "Iniciación 2026", 25);
    intro.setSensei(first);
    intro = courses.save(intro);
    Curso advanced = new Curso(2026, "Técnica 2026", 35);
    advanced.setSensei(second);
    advanced = courses.save(advanced);
    String[] names = {"Lucía", "Mateo", "Alba", "Leo", "Nora", "Hugo"};
    String[] surnames = {"Demo", "Ejemplo", "Prueba", "Modelo", "Muestra", "Ensayo"};
    String[] belts = {"Blanco", "Amarillo", "Verde"};
    for (int i = 0; i < names.length; i++) {
      Alumno a = new Alumno();
      a.setNombre(names[i]);
      a.setApellido1(surnames[i]);
      a.setEmail("student" + (i + 1) + "@example.invalid");
      a.setFechaNacimiento(LocalDate.of(2003 + i, i + 1, 10));
      a.setFechaInscripcion(LocalDate.of(2026, 9, 1));
      a.setCinturon(belts[i % 3]);
      a.setFederado("No");
      a.setCurso(i % 2 == 0 ? advanced : intro);
      students.save(a);
    }
  }
}
