let seleccionable = document.getElementById("asignaturaProf");
let radioProf = document.getElementById("radioProf");
let radioAlumno = document.getElementById("radioAlumno");


radioProf.addEventListener("change", seleccionar);

radioAlumno.addEventListener("change", desSeleccionar);


function seleccionar(){
	seleccionable.disabled = false;
}


function desSeleccionar(){
	seleccionable.disabled = true;
}
