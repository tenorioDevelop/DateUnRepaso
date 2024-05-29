document.addEventListener('DOMContentLoaded', function() {
  const idUsuario = document.getElementById("idUsuario").innerText;
  const rolUsuario = document.getElementById("rolUsuario").innerText;

  let eventosCalendario = [];

  function llamadaApi() {
    const urlAlumno = 'http://localhost:8080/api/reservas-alumno/' + idUsuario;
    const urlProfesor = 'http://localhost:8080/api/reservas-profesor/' + idUsuario;


    const conexion = new XMLHttpRequest();
    conexion.onreadystatechange = function () {
      if (conexion.readyState == 4) {
        if (conexion.status == 200) {
          const json = conexion.responseText;
          eventosCalendario = JSON.parse(json);
          console.log(eventosCalendario);
          renderCalendar();
        }
      }
    };
    if (rolUsuario == "ROL_ALUMNO") {
      conexion.open('GET', urlAlumno, true); // true para solicitud asíncrona
    }
    if (rolUsuario == "ROL_PROFESOR") {
      conexion.open('GET', urlProfesor, true); // true para solicitud asíncrona
    }
    conexion.send();
  }
  function renderCalendar() {
    const calendarEl = document.getElementById('calendar');

    const calendar = new FullCalendar.Calendar(calendarEl, {
      locale: 'es',
      events: eventosCalendario
    });
    calendar.render();
  }
  llamadaApi();
  });