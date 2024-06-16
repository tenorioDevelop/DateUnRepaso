document.addEventListener('DOMContentLoaded', function () {
  const idUsuario = document.getElementById("idUsuario").innerText;
  const rolUsuario = document.getElementById("rolUsuario").innerText;

  let eventosCalendario = [];

  function llamadaApi() {
    const urlAlumno = 'https://dateunrepaso.up.railway.app/api/reservas-alumno/' + idUsuario;
    const urlProfesor = 'https://dateunrepaso.up.railway.app/api/reservas-profesor/' + idUsuario;

    let url;
    if (rolUsuario === "ALUMNO") {
      url = urlAlumno;
    } else if (rolUsuario === "PROFESOR") {
      url = urlProfesor;
    }

    if (url) {
      fetch(url)
        .then(response => {
          if (!response.ok) {
            throw new Error('Error en la respuesta de la red');
          }
          return response.json();
        })
        .then(data => {
          eventosCalendario = data;
          console.log(eventosCalendario);
          renderCalendar();
        })
        .catch(error => {
          console.error('Error al obtener los datos:', error);
        });
    }
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
