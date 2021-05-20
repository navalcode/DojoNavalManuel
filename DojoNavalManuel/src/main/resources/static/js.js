function comprobarDni() {
   let valor = document.getElementById("dni").value;
    let letras = ['T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E', 'T'];


    if (valor.charAt(8) != letras[(valor.substring(0, 8)) % 23]) {
       alert("El dni no es válido");
       return false;
    }
}