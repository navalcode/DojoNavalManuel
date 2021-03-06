package com.salesianostriana.dam.modelo;

import java.util.List;

/**
 * Esta se trata de una clase auxiliar para proporcionar datos inmutables, en
 * este caso una lista con las provincias españolas.
 * 
 * @author Manuel Naval
 *
 */
public class DatosProvincias {

	public static List<String> lista_provincias() {
		return List.of("Álava/Araba", "Albacete", "Alicante", "Almería", "Asturias", "Ávila", "Badajoz", "Baleares",
				"Barcelona", "Burgos", "Cáceres", "Cádiz", "Cantabria", "Castellón", "Ceuta", "Ciudad Real", "Córdoba",
				"Cuenca", "Gerona/Girona", "Granada", "Guadalajara", "Guipúzcoa", "Huelva", "Huesca", "Jaén",
				"La Coruña", "La Rioja", "Las Palmas", "León", "Lérida", "Lugo", "Madrid", "Málaga", "Melilla",
				"Murcia", "Navarra", "Orense", "Palencia", "Pontevedra", "Salamanca", "Segovia", "Sevilla", "Soria",
				"Tarragona", "Tenerife", "Teruel", "Toledo", "Valencia", "Valladolid", "Vizcaya", "Zamora", "Zaragoza");
	}
}
