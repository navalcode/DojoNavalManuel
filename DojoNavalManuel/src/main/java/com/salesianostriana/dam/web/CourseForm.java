package com.salesianostriana.dam.web;

import lombok.Data;

@Data
public class CourseForm {
  private Long id;
  private String name = "", price = "0";
  private Integer year = java.time.LocalDate.now().getYear();
  private Long instructorId;
}
