
/**Este bloque genera los eventos para los botones editar de todas las tarjetas de alumno */
let botones = document.querySelectorAll(".editar");
/**Con este bucle se añaden los eventos */
for (let boton of botones) {
	boton.addEventListener("click", mostrarCampos);
}



/**Esta función maneja el evento para editar cada alumno, realiza varios cambios en el documento, se desglosa abajo
con comentarios en línea */
function mostrarCampos(evento) {

	//Este elemento es el formulario en sí.
	let element = evento.currentTarget.parentNode.previousElementSibling;
	//Este elemento es el div padre, es decir la tarjeta de cada alumno, servirá para modificar el tamaño de dicha tarjeta.
	let tarjetaAlumno = element.parentNode.parentNode;

	//Con estos toggle los formularios para cada alumno aparecen y desaparecen, al cambiar la clase el css cambia la
	//propiedad display none a display inherit
	element.classList.toggle('apagado');

	element.classList.toggle('encendido');

	//Este if se encarga de modificar las clases de bootstrap y por tanto el tamaño de la tarjeta.
	if (tarjetaAlumno.className === "mx-auto col-11 col-sm-11 col-md-11 col-lg-3 col-xl-3 mt-4 mb-4") {
		tarjetaAlumno.className = "mx-auto col-11 col-sm-11 col-md-11 col-lg-11 col-xl-11 mt-4 mb-4";
		//Se genera un ancla para poder redirigir al usuario al formulario en cuestión, esto evita mareos
		//puesto que al cambiar el tamaño el formulario puede abrirse en algún sitio incómodo, evitamos al usuario
		//teener que hacer scroll.
		tarjetaAlumno.id = "ancla";
		location.href = "#ancla";

	} else {
		//En otro caso devolvemos a la tarjeta a su tamaño original
		tarjetaAlumno.className = "mx-auto col-11 col-sm-11 col-md-11 col-lg-3 col-xl-3 mt-4 mb-4";
		//También eliminamos el id del ancla para evitar duplicados.
		tarjetaAlumno.id = "";
	}
}