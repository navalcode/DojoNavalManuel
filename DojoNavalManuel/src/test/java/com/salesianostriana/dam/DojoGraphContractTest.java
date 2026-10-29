package com.salesianostriana.dam;

import static org.junit.jupiter.api.Assertions.*;

import com.salesianostriana.dam.modelo.*;
import org.junit.jupiter.api.Test;

class DojoGraphContractTest {
  @Test
  void studentTransferAndRepeatedAttachmentKeepBothSides() {
    Curso first = new Curso(), second = new Curso();
    Alumno student = new Alumno();
    first.addAlumno(student);
    second.addAlumno(student);
    second.addAlumno(student);
    assertTrue(first.getAlumnos().isEmpty());
    assertEquals(1, second.getAlumnos().size());
    assertSame(second, student.getCurso());
  }

  @Test
  void wrongCourseRemovalKeepsActualOwner() {
    Curso owner = new Curso();
    Alumno student = new Alumno();
    owner.addAlumno(student);
    new Curso().removeAlumno(student);
    assertSame(owner, student.getCurso());
    assertEquals(1, owner.getAlumnos().size());
  }

  @Test
  void instructorTransferAndRemovalKeepBothSides() {
    Sensei first = new Sensei(), second = new Sensei();
    Curso course = new Curso();
    first.addCurso(course);
    second.addCurso(course);
    second.addCurso(course);
    first.removeCurso(course);
    assertTrue(first.getCurso().isEmpty());
    assertSame(second, course.getSensei());
    assertEquals(1, second.getCurso().size());
    second.removeCurso(course);
    assertNull(course.getSensei());
  }

  @Test
  void nullAttachmentFailsBeforeMutatingParent() {
    Curso course = new Curso();
    assertThrows(NullPointerException.class, () -> course.addAlumno(null));
    assertTrue(course.getAlumnos().isEmpty());
    Sensei person = new Sensei();
    assertThrows(NullPointerException.class, () -> person.addCurso(null));
    assertTrue(person.getCurso().isEmpty());
  }
}
