let botones=document.querySelectorAll(".editar");

for (let boton of botones){
	boton.addEventListener("click", mostrarCampos);
} 

	
  	


function mostrarCampos(evento) {
	//el boton es evento.target. El formulario es evento.target.previousElementSibling
	
	
    let element = evento.target.previousElementSibling;
  
  
          element.classList.toggle('apagado');
   
          element.classList.toggle('encendido');
    
  
}