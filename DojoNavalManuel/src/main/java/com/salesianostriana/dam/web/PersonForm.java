package com.salesianostriana.dam.web;

import java.time.LocalDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class PersonForm {
  private Long id;
  private String name = "",
      firstSurname = "",
      secondSurname = "",
      email = "",
      documentNumber = "",
      phone = "";

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate birthDate;

  private String province = "", postalCode = "", city = "", address = "", imageUrl = "", belt = "";

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate enrollmentDate;

  private String federation = "No", notes = "", salary = "0";
  private Long courseId;
}
