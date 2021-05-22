package com.salesianostriana.dam.modelo;

import java.util.List;
/**
 * Esta se trata de una clase auxiliar para proporcionar datos inmutables, en este caso una lista de tipos de federación. 
 * 
 * @author Manuel Naval
 *
 */
public class DatosFederados {

	public static List<String> lista_federados() {
		return List.of("Si","No");
	}
}
