package com.salesianostriana.dam;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.salesianostriana.dam.modelo.*;
import com.salesianostriana.dam.repos.*;
import java.time.LocalDate;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.*;

@SpringBootTest(
    properties = {
      "spring.datasource.url=jdbc:h2:mem:dojo_http;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
      "spring.jpa.hibernate.ddl-auto=create-drop",
      "dojo.demo.seed=false"
    })
@AutoConfigureMockMvc
class DojoHttpJourneyTest {
  @Autowired MockMvc mvc;
  @Autowired AlumnoRepository students;
  @Autowired CursoRepository courses;
  @Autowired SenseiRepository instructors;
  MockHttpSession session;
  String token;
  Curso course;
  Alumno student;

  @BeforeEach
  void fixture() throws Exception {
    students.deleteAll();
    courses.deleteAll();
    instructors.deleteAll();
    course = courses.save(new Curso(2026, "Sample course", 25));
    student = new Alumno();
    student.setNombre("Ada");
    student.setApellido1("Demo");
    student.setFechaNacimiento(LocalDate.of(2004, 1, 1));
    student.setCurso(course);
    student = students.save(student);
    session = new MockHttpSession();
    mvc.perform(get("/").session(session)).andExpect(status().isOk());
    token = (String) session.getAttribute("formToken");
    if (token == null) token = "baseline-token";
  }

  @Test
  void ageSortRendersItsCaseSensitiveTemplate() throws Exception {
    mvc.perform(get("/ordenarEdad"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Ada")));
  }

  @Test
  void missingStudentDetailIs404() throws Exception {
    mvc.perform(get("/detalleAlumnos/9223372036854775807")).andExpect(status().isNotFound());
  }

  @Test
  void missingStudentEditIs404() throws Exception {
    mvc.perform(get("/editarAlumno/9223372036854775807")).andExpect(status().isNotFound());
  }

  @Test
  void deleteGetIsReadOnlyConfirmation() throws Exception {
    mvc.perform(get("/borrarAlumno/{id}", student.getId()).session(session))
        .andExpect(status().isOk());
    assertTrue(students.existsById(student.getId()));
  }

  @Test
  void emptyStudentSubmissionDoesNotWrite() throws Exception {
    long before = students.count();
    mvc.perform(post("/addAlumno").session(session).param("csrfToken", token))
        .andExpect(status().isBadRequest());
    assertEquals(before, students.count());
  }

  @Test
  void studentCreationAndEditPersistAcrossRequests() throws Exception {
    mvc.perform(
            post("/addAlumno")
                .session(session)
                .param("csrfToken", token)
                .param("name", "Lucia")
                .param("firstSurname", "Sample")
                .param("courseId", String.valueOf(course.getId()))
                .param("birthDate", "2005-03-04"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/"));
    Alumno created =
        students.findAll().stream()
            .filter(x -> "Lucia".equals(x.getNombre()))
            .findFirst()
            .orElseThrow();
    assertEquals(course.getId(), created.getCurso().getId());
    mvc.perform(
            post("/editarSubmit")
                .session(session)
                .param("csrfToken", token)
                .param("id", String.valueOf(created.getId()))
                .param("name", "Luz")
                .param("firstSurname", "Sample"))
        .andExpect(status().is3xxRedirection());
    assertEquals("Luz", students.findById(created.getId()).orElseThrow().getNombre());
    assertEquals(2, students.count());
  }

  @Test
  void suppliedIdOnCreationCannotOverwrite() throws Exception {
    mvc.perform(
            post("/addAlumno")
                .session(session)
                .param("csrfToken", token)
                .param("id", String.valueOf(student.getId()))
                .param("name", "Changed")
                .param("firstSurname", "Sample"))
        .andExpect(status().isBadRequest());
    assertEquals("Ada", students.findById(student.getId()).orElseThrow().getNombre());
  }

  @Test
  void unknownCourseDoesNotWrite() throws Exception {
    mvc.perform(
            post("/addAlumno")
                .session(session)
                .param("csrfToken", token)
                .param("name", "Unknown")
                .param("firstSurname", "Sample")
                .param("courseId", "9223372036854775807"))
        .andExpect(status().isBadRequest());
    assertEquals(1, students.count());
  }

  @Test
  void invalidDatesAndNegativeMoneyAreReportedWithoutWrites() throws Exception {
    mvc.perform(
            post("/addAlumno")
                .session(session)
                .param("csrfToken", token)
                .param("name", "Future")
                .param("firstSurname", "Sample")
                .param("birthDate", LocalDate.now().plusDays(1).toString()))
        .andExpect(status().isBadRequest());
    assertEquals(1, students.count());
    mvc.perform(
            post("/addCurso")
                .session(session)
                .param("csrfToken", token)
                .param("name", "Negative")
                .param("year", "2026")
                .param("price", "-1"))
        .andExpect(status().isBadRequest());
    assertEquals(1, courses.count());
  }

  @Test
  void deletionRequiresPostAndValidToken() throws Exception {
    mvc.perform(post("/borrarAlumno/{id}", student.getId()).session(session))
        .andExpect(status().isForbidden());
    assertTrue(students.existsById(student.getId()));
    mvc.perform(
            post("/borrarAlumno/{id}", student.getId()).session(session).param("csrfToken", token))
        .andExpect(status().is3xxRedirection());
    assertFalse(students.existsById(student.getId()));
    assertTrue(courses.existsById(course.getId()));
  }

  @Test
  void referencedCourseCannotBeDeleted() throws Exception {
    mvc.perform(
            post("/borrarCurso/{id}", course.getId()).session(session).param("csrfToken", token))
        .andExpect(status().isConflict());
    assertTrue(courses.existsById(course.getId()));
    assertTrue(students.existsById(student.getId()));
  }

  @Test
  void referencedInstructorCannotBeDeleted() throws Exception {
    Sensei person = new Sensei();
    person.setNombre("Sample");
    person.setApellido1("Instructor");
    person = instructors.save(person);
    course.setSensei(person);
    courses.save(course);
    mvc.perform(
            post("/borrarSensei/{id}", person.getId()).session(session).param("csrfToken", token))
        .andExpect(status().isConflict());
    assertTrue(instructors.existsById(person.getId()));
  }

  @Test
  void filterAndSortRemainCombined() throws Exception {
    Curso other = courses.save(new Curso(2026, "Other", 30));
    Alumno another = new Alumno();
    another.setNombre("Aaron");
    another.setApellido1("Example");
    another.setCurso(other);
    students.save(another);
    mvc.perform(get("/").param("idCurso", String.valueOf(course.getId())).param("sort", "name"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Ada")))
        .andExpect(content().string(not(containsString("Aaron"))));
  }

  @Test
  void nullBirthDateStillRendersDetail() throws Exception {
    student.setFechaNacimiento(null);
    students.save(student);
    mvc.perform(get("/detalleAlumnos/{id}", student.getId())).andExpect(status().isOk());
  }

  @Test
  void spanishAndEnglishRenderLocalResources() throws Exception {
    mvc.perform(get("/").param("lang", "en"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Students")))
        .andExpect(content().string(not(containsString("cdn.jsdelivr.net"))));
    mvc.perform(get("/").param("lang", "es"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Alumnos")));
  }

  @Test
  void instructorAndCourseCreationEditAndUnreferencedRemoval() throws Exception {
    mvc.perform(
            post("/addSensei")
                .session(session)
                .param("csrfToken", token)
                .param("name", "Fictional")
                .param("firstSurname", "Instructor")
                .param("salary", "120,50")
                .param("email", "teacher@example.invalid"))
        .andExpect(status().is3xxRedirection());
    Sensei person = instructors.findAll().get(0);
    assertEquals(120.5, person.getSueldo());
    mvc.perform(
            post("/addCurso")
                .session(session)
                .param("csrfToken", token)
                .param("name", "New course")
                .param("year", "2026")
                .param("price", "35.50")
                .param("instructorId", String.valueOf(person.getId())))
        .andExpect(status().is3xxRedirection());
    Curso created =
        courses.findAll().stream()
            .filter(c -> "New course".equals(c.getNombre()))
            .findFirst()
            .orElseThrow();
    assertEquals(person.getId(), created.getSensei().getId());
    mvc.perform(
            post("/editarCursoSubmit")
                .session(session)
                .param("csrfToken", token)
                .param("id", String.valueOf(created.getId()))
                .param("name", "Edited course")
                .param("year", "2027")
                .param("price", "40"))
        .andExpect(status().is3xxRedirection());
    assertNull(courses.findById(created.getId()).orElseThrow().getSensei());
    mvc.perform(
            post("/borrarCurso/{id}", created.getId()).session(session).param("csrfToken", token))
        .andExpect(status().is3xxRedirection());
    assertFalse(courses.existsById(created.getId()));
    mvc.perform(
            post("/borrarSensei/{id}", person.getId()).session(session).param("csrfToken", token))
        .andExpect(status().is3xxRedirection());
    assertFalse(instructors.existsById(person.getId()));
  }

  @Test
  void everyEditorAndListRendersInBothLanguages() throws Exception {
    for (String language : new String[] {"es", "en"})
      for (String path :
          new String[] {
            "/nuevo",
            "/nuevoSensei",
            "/nuevoCurso",
            "/editarAlumno/" + student.getId(),
            "/editarCurso/" + course.getId(),
            "/senseis",
            "/cursos"
          })
        mvc.perform(get(path).param("lang", language))
            .andExpect(status().isOk())
            .andExpect(content().string(not(containsString("??"))));
  }

  @Test
  void malformedAndInvalidValuesKeepNamesAndDoNotWrite() throws Exception {
    mvc.perform(
            post("/addAlumno")
                .session(session)
                .param("csrfToken", token)
                .param("name", "Retained")
                .param("firstSurname", "Sample")
                .param("birthDate", "not-a-date")
                .param("email", "invalid")
                .param("postalCode", "12"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("Retained")))
        .andExpect(content().string(containsString("error-summary")));
    assertEquals(1, students.count());
    mvc.perform(
            post("/addCurso")
                .session(session)
                .param("csrfToken", token)
                .param("name", "NaN")
                .param("year", "2026")
                .param("price", "NaN"))
        .andExpect(status().isBadRequest());
    assertEquals(1, courses.count());
  }

  @Test
  void validOptionalImageAndEscapedNotesStayData() throws Exception {
    mvc.perform(
            post("/addAlumno")
                .session(session)
                .param("csrfToken", token)
                .param("name", "Image sample")
                .param("firstSurname", "Demo")
                .param("imageUrl", "https://images.example.invalid/sample.png")
                .param("notes", "<script>alert(1)</script>"))
        .andExpect(status().is3xxRedirection());
    Alumno created =
        students.findAll().stream()
            .filter(a -> "Image sample".equals(a.getNombre()))
            .findFirst()
            .orElseThrow();
    mvc.perform(get("/detalleAlumnos/{id}", created.getId()))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("&lt;script&gt;")))
        .andExpect(content().string(not(containsString("<script>alert(1)</script>"))));
  }

  @Test
  void courseAndInstructorIdsCannotBeMergedOnCreate() throws Exception {
    mvc.perform(
            post("/addCurso")
                .session(session)
                .param("csrfToken", token)
                .param("id", String.valueOf(course.getId()))
                .param("name", "Overwrite")
                .param("year", "2026")
                .param("price", "10"))
        .andExpect(status().isBadRequest());
    assertEquals("Sample course", courses.findById(course.getId()).orElseThrow().getNombre());
    Sensei person = new Sensei();
    person.setNombre("Original");
    person = instructors.save(person);
    mvc.perform(
            post("/addSensei")
                .session(session)
                .param("csrfToken", token)
                .param("id", String.valueOf(person.getId()))
                .param("name", "Overwrite")
                .param("firstSurname", "Demo")
                .param("salary", "1"))
        .andExpect(status().isBadRequest());
    assertEquals("Original", instructors.findById(person.getId()).orElseThrow().getNombre());
  }

  @Test
  void studentCanBeReassignedAndUnenrolledWithoutStaleInverseData() throws Exception {
    Curso other = courses.save(new Curso(2026, "Other", 20));
    mvc.perform(
            post("/editarSubmit")
                .session(session)
                .param("csrfToken", token)
                .param("id", String.valueOf(student.getId()))
                .param("name", "Ada")
                .param("firstSurname", "Demo")
                .param("courseId", String.valueOf(other.getId())))
        .andExpect(status().is3xxRedirection());
    assertTrue(courses.findById(course.getId()).orElseThrow().getAlumnos().isEmpty());
    assertEquals(1, courses.findById(other.getId()).orElseThrow().getAlumnos().size());
    mvc.perform(
            post("/editarSubmit")
                .session(session)
                .param("csrfToken", token)
                .param("id", String.valueOf(student.getId()))
                .param("name", "Ada")
                .param("firstSurname", "Demo"))
        .andExpect(status().is3xxRedirection());
    assertNull(students.findById(student.getId()).orElseThrow().getCurso());
    assertTrue(courses.findById(other.getId()).orElseThrow().getAlumnos().isEmpty());
  }

  @Test
  void switchingEditorLanguagePreservesDraftWithoutWriting() throws Exception {
    mvc.perform(
            post("/addAlumno")
                .session(session)
                .param("csrfToken", token)
                .param("name", "Unsaved draft")
                .param("lang", "en"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Unsaved draft")))
        .andExpect(content().string(containsString("Save student")));
    assertEquals(1, students.count());
  }

  @Test
  void forbiddenErrorDispatchRendersTheRecoveryPage() throws Exception {
    mvc.perform(
            post("/error")
                .with(
                    request -> {
                      request.setDispatcherType(javax.servlet.DispatcherType.ERROR);
                      return request;
                    })
                .requestAttr(javax.servlet.RequestDispatcher.ERROR_STATUS_CODE, 403)
                .accept("text/html"))
        .andExpect(status().isForbidden())
        .andExpect(content().string(containsString("dojo.css")))
        .andExpect(content().string(containsString("Dojo")));
  }
}
