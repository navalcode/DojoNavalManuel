package com.salesianostriana.dam.web;

import com.salesianostriana.dam.modelo.*;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component("dojoView")
@RequiredArgsConstructor
public class DojoView {
  private final MessageSource messages;

  public String name(Persona person) {
    return java.util.stream.Stream.of(
            person.getNombre(), person.getApellido1(), person.getApellido2())
        .filter(value -> value != null && !value.isBlank())
        .collect(Collectors.joining(" "));
  }

  public String belt(String value, Locale locale) {
    if (value == null || value.isBlank()) return messages.getMessage("value.notSet", null, locale);
    int index = DatosCinturones.lista_cinturones().indexOf(value);
    return messages.getMessage("belt." + index, null, value, locale);
  }

  public Long age(Alumno student) {
    return student.getFechaNacimiento() == null ? null : student.calcularEdad();
  }
}
