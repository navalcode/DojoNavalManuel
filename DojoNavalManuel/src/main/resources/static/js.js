function comprobarDni() {
   let valor = document.getElementById("dni").value;
   let letras = ['T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E', 'T'];


   if (valor.charAt(8) != letras[(valor.substring(0, 8)) % 23]) {
      alert("La letra del DNI no se corresponden con su número, se guardarán los datos, podrá volver aquí para cambiarlo.");
      return false;
   }
}

function closeWin() {
   open(location, '_self').close();
}