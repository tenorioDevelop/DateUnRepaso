let seleccionableProf = document.getElementById("asignaturaProf");
let seleccionableAlum = document.getElementById("asignaturaAlumno");
let radioProf = document.getElementById("radioProf");
let radioAlumno = document.getElementById("radioAlumno");


radioProf.addEventListener("change", seleccionar);

radioAlumno.addEventListener("change", desSeleccionar);


function seleccionar(){
	seleccionableProf.hidden = false;
	seleccionableAlum.hidden = true;
}


function desSeleccionar(){
	seleccionableProf.hidden = true;
	seleccionableAlum.hidden = false;
}
