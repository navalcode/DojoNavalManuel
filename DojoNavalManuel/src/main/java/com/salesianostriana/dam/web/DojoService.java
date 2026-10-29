package com.salesianostriana.dam.web;

import com.salesianostriana.dam.modelo.*;
import com.salesianostriana.dam.repos.*;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@RequiredArgsConstructor
public class DojoService {
  private final AlumnoRepository students;
  private final CursoRepository courses;
  private final SenseiRepository instructors;

  public List<Curso> courses() {
    return courses.findAll();
  }

  public List<Sensei> instructors() {
    return instructors.findAll();
  }

  public Alumno student(long id) {
    return students
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  public Curso course(long id) {
    return courses
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  public Sensei instructor(long id) {
    return instructors
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  public List<Alumno> students(Long courseId, String sort) {
    if (courseId != null) course(courseId);
    List<Alumno> result = courseId == null ? students.findAll() : students.findByCursoId(courseId);
    Comparator<Alumno> byName =
        Comparator.comparing(
            a -> Objects.toString(a.getNombre(), ""), String.CASE_INSENSITIVE_ORDER);
    if ("age".equals(sort))
      result.sort(
          Comparator.comparing(
                  Alumno::getFechaNacimiento, Comparator.nullsLast(Comparator.reverseOrder()))
              .thenComparing(byName));
    else result.sort(byName.thenComparingLong(Alumno::getId));
    return result;
  }

  private void text(Errors errors, String field, String value, int max, boolean required) {
    if (required && (value == null || value.isBlank())) errors.rejectValue(field, "form.required");
    else if (value != null && value.length() > max)
      errors.rejectValue(field, "form.tooLong", new Object[] {max}, null);
  }

  private BigDecimal money(String value) {
    if (value == null || !value.matches("[0-9]{1,7}([.,][0-9]{1,2})?")) return null;
    BigDecimal amount = new BigDecimal(value.replace(',', '.'));
    return amount.compareTo(new BigDecimal("1000000")) <= 0 ? amount : null;
  }

  public void validate(PersonForm f, Errors errors, boolean staff, boolean create) {
    if (create && f.getId() != null && f.getId() != 0) errors.reject("form.createId");
    if (!create && (f.getId() == null || f.getId() <= 0)) errors.reject("form.editId");
    text(errors, "name", f.getName(), 80, true);
    text(errors, "firstSurname", f.getFirstSurname(), 100, true);
    text(errors, "secondSurname", f.getSecondSurname(), 100, false);
    text(errors, "email", f.getEmail(), 200, false);
    text(errors, "documentNumber", f.getDocumentNumber(), 32, false);
    text(errors, "phone", f.getPhone(), 32, false);
    text(errors, "province", f.getProvince(), 100, false);
    text(errors, "city", f.getCity(), 100, false);
    text(errors, "address", f.getAddress(), 200, false);
    text(errors, "notes", f.getNotes(), 2000, false);
    if (f.getEmail() != null
        && !f.getEmail().isBlank()
        && !f.getEmail().matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+"))
      errors.rejectValue("email", "form.email");
    if (f.getBirthDate() != null && f.getBirthDate().isAfter(LocalDate.now()))
      errors.rejectValue("birthDate", "form.birthDate");
    if (f.getPostalCode() != null
        && !f.getPostalCode().isBlank()
        && !f.getPostalCode().matches("[0-9]{5}"))
      errors.rejectValue("postalCode", "form.postalCode");
    if (f.getBelt() != null
        && !f.getBelt().isBlank()
        && !DatosCinturones.lista_cinturones().contains(f.getBelt()))
      errors.rejectValue("belt", "form.selection");
    if (f.getImageUrl() != null && !f.getImageUrl().isBlank()) {
      try {
        URI u = URI.create(f.getImageUrl());
        if (f.getImageUrl().length() > 1000
            || u.getHost() == null
            || !("https".equals(u.getScheme()) || "http".equals(u.getScheme())))
          errors.rejectValue("imageUrl", "form.url");
      } catch (IllegalArgumentException ex) {
        errors.rejectValue("imageUrl", "form.url");
      }
    }
    if (staff) {
      if (money(f.getSalary()) == null) errors.rejectValue("salary", "form.money");
    } else {
      if (f.getCourseId() != null && !courses.existsById(f.getCourseId()))
        errors.rejectValue("courseId", "form.selection");
      if (!DatosFederados.lista_federados().contains(f.getFederation()))
        errors.rejectValue("federation", "form.selection");
    }
  }

  public void validate(CourseForm f, Errors errors, boolean create) {
    if (create && f.getId() != null && f.getId() != 0) errors.reject("form.createId");
    if (!create && (f.getId() == null || f.getId() <= 0)) errors.reject("form.editId");
    text(errors, "name", f.getName(), 120, true);
    if (f.getYear() == null || f.getYear() < 1900 || f.getYear() > 2100)
      errors.rejectValue("year", "form.year");
    if (money(f.getPrice()) == null) errors.rejectValue("price", "form.money");
    if (f.getInstructorId() != null && !instructors.existsById(f.getInstructorId()))
      errors.rejectValue("instructorId", "form.selection");
  }

  private String clean(String value) {
    return value == null ? "" : value.strip();
  }

  private void apply(PersonForm f, Persona p) {
    p.setNombre(clean(f.getName()));
    p.setApellido1(clean(f.getFirstSurname()));
    p.setApellido2(clean(f.getSecondSurname()));
    p.setEmail(clean(f.getEmail()));
    p.setDni(clean(f.getDocumentNumber()));
    p.setTelefono(clean(f.getPhone()));
    p.setFechaNacimiento(f.getBirthDate());
    p.setProvincia(clean(f.getProvince()));
    p.setCodigoPostal(
        f.getPostalCode() == null || f.getPostalCode().isBlank()
            ? 0
            : Integer.parseInt(f.getPostalCode()));
    p.setMunicipio(clean(f.getCity()));
    p.setDireccion(clean(f.getAddress()));
    p.setImagen(clean(f.getImageUrl()));
    p.setCinturon(clean(f.getBelt()));
  }

  public void saveStudent(PersonForm f, boolean create) {
    Alumno a = create ? new Alumno() : student(f.getId());
    apply(f, a);
    a.setFechaInscripcion(f.getEnrollmentDate());
    a.setFederado(f.getFederation());
    a.setComentarios(clean(f.getNotes()));
    Curso next = f.getCourseId() == null ? null : course(f.getCourseId());
    if (a.getCurso() != null && a.getCurso() != next) a.getCurso().removeAlumno(a);
    if (next != null) next.addAlumno(a);
    students.save(a);
  }

  public void saveInstructor(PersonForm f, boolean create) {
    Sensei a = create ? new Sensei() : instructor(f.getId());
    apply(f, a);
    a.setSueldo(money(f.getSalary()).doubleValue());
    instructors.save(a);
  }

  public void saveCourse(CourseForm f, boolean create) {
    Curso c = create ? new Curso() : course(f.getId());
    c.setNombre(clean(f.getName()));
    c.setAnio(f.getYear());
    c.setPrecio(money(f.getPrice()).doubleValue());
    Sensei next = f.getInstructorId() == null ? null : instructor(f.getInstructorId());
    if (c.getSensei() != null && c.getSensei() != next) c.getSensei().removeCurso(c);
    if (next != null) next.addCurso(c);
    courses.save(c);
  }

  public PersonForm personForm(Persona p) {
    PersonForm f = new PersonForm();
    f.setId(p.getId());
    f.setName(p.getNombre());
    f.setFirstSurname(p.getApellido1());
    f.setSecondSurname(p.getApellido2());
    f.setEmail(p.getEmail());
    f.setDocumentNumber(p.getDni());
    f.setPhone(p.getTelefono());
    f.setBirthDate(p.getFechaNacimiento());
    f.setProvince(p.getProvincia());
    f.setPostalCode(p.getCodigoPostal() == 0 ? "" : String.format("%05d", p.getCodigoPostal()));
    f.setCity(p.getMunicipio());
    f.setAddress(p.getDireccion());
    f.setImageUrl(p.getImagen());
    f.setBelt(p.getCinturon());
    if (p instanceof Alumno) {
      Alumno a = (Alumno) p;
      f.setEnrollmentDate(a.getFechaInscripcion());
      f.setFederation(a.getFederado() == null ? "No" : a.getFederado());
      f.setNotes(a.getComentarios());
      f.setCourseId(a.getCurso() == null ? null : a.getCurso().getId());
    } else f.setSalary(BigDecimal.valueOf(((Sensei) p).getSueldo()).toPlainString());
    return f;
  }

  public CourseForm courseForm(Curso c) {
    CourseForm f = new CourseForm();
    f.setId(c.getId());
    f.setName(c.getNombre());
    f.setYear(c.getAnio());
    f.setPrice(BigDecimal.valueOf(c.getPrecio()).toPlainString());
    f.setInstructorId(c.getSensei() == null ? null : c.getSensei().getId());
    return f;
  }

  public boolean deleteStudent(long id) {
    Alumno a = student(id);
    if (a.getCurso() != null) a.getCurso().removeAlumno(a);
    students.delete(a);
    return true;
  }

  public boolean deleteCourse(long id) {
    Curso c = course(id);
    if (!c.getAlumnos().isEmpty()) return false;
    if (c.getSensei() != null) c.getSensei().removeCurso(c);
    courses.delete(c);
    return true;
  }

  public boolean deleteInstructor(long id) {
    Sensei a = instructor(id);
    if (!a.getCurso().isEmpty()) return false;
    instructors.delete(a);
    return true;
  }
}
