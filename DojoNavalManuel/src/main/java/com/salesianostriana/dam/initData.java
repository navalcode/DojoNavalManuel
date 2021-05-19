package com.salesianostriana.dam;

import java.time.LocalDate;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.salesianostriana.dam.modelo.Alumno;
import com.salesianostriana.dam.modelo.Curso;
import com.salesianostriana.dam.modelo.Sensei;
import com.salesianostriana.dam.servicios.AlumnoServicio;
import com.salesianostriana.dam.servicios.CursoServicio;
import com.salesianostriana.dam.servicios.SenseiServicio;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class initData {
	
	private final AlumnoServicio alumnoServicio;
	private final CursoServicio cursoServicio;
	private final SenseiServicio senseiServicio;
	
	
	

	
	@PostConstruct
	public void init () {
		Alumno a =new Alumno ("Manuel","Naval","Rodríguez","navalrod@gmail.com","20072517A","651666999",LocalDate.of(1997, 04, 02),"Cádiz",11550, "Chipiona","Avd/Almería 74",null,  LocalDate.now(), "Si", "Marrón-Negro","Alergia estacional");
		Alumno b =new Alumno ("Sara","Clarke","Swuch","clarkswuch@gmail.com","00055521J","000555444",LocalDate.of(1999, 05, 19),"Cádiz",12550, "Jerez","Calle fino",null,  LocalDate.now(), "Si", "Azul","");
		Alumno c =new Alumno ("Antonio","Peinado","Rodríguez","navalrod@gmail.com","01247857H","651666999",LocalDate.of(1998, 05, 19),"Cádiz",11550, "Chipiona","calle girasol,20",null,  null, "No", "Blanco","Asma");
		Alumno d =new Alumno ("Marina","Figueroa","Flores","navalrod@gmail.com","57489624G","651666999",LocalDate.of(1994, 05, 19),"Cádiz",11550, "Chipiona","AVD/Rosaleda sn",null,  null, "No", "Marrón","");
		Alumno e =new Alumno ("Daniel","Villanueva","Garcia","navalrod@gmail.com","3333333E","651666999",LocalDate.of(1988, 05, 19),"Cádiz",11550, "Chipiona","Avd/Miguel Cervantes, 52",null,  null, "Si", "Negro","");
		Alumno f =new Alumno ("Lorena","Cespedes","Roman","navalrod@gmail.com","2222222D","651666999",LocalDate.of(1989, 05, 19),"Cádiz",11550, "Chipiona","Avd/Andalucía,15",null,  null, "Si", "Amarillo","");
		Alumno g =new Alumno ("Sergio","Arques","Tubio","navalrod@gmail.com","1111111C","651666999",LocalDate.of(1996, 05, 19),"Cádiz",11550, "Chipiona","calle/ Castaño, 10",null,  null, "No", "Verde","");
		Alumno h =new Alumno ("Elon","Musk","Ramirez","navalrod@gmail.com","0000000B","651666999",LocalDate.of(2004, 05, 19),"Cádiz",11550, "Chipiona","Avd/Neptuno, 7",null,  null, "No", "Blanco-Amarillo","");
		
		
		alumnoServicio.save(a);
		alumnoServicio.save(b);
		alumnoServicio.save(c);
		alumnoServicio.save(d);
		alumnoServicio.save(e);
		alumnoServicio.save(f);
		alumnoServicio.save(g);
		alumnoServicio.save(h);
		
		Curso nuevoA =new Curso (2021,"Juveniles",25.00);
		Curso nuevoB =new Curso (2021,"Alevines",25.00);
		Curso nuevoC =new Curso(2021, "Adultos", 30.00);
		
		cursoServicio.save(nuevoA);
		cursoServicio.save(nuevoB);
		cursoServicio.save(nuevoC);
		
		Sensei sa =new Sensei("Jose María", null, null, null, null, null, null, null, 0, null, null, null, 1150.00);
		
		senseiServicio.save(sa);
		
		nuevoC.addSensei(sa);
		senseiServicio.edit(sa);
		
		nuevoC.addAlumno(a);
		alumnoServicio.edit(a);
		
		
		
	}
}
