document.addEventListener('DOMContentLoaded', function () {
  const idUsuario = document.getElementById("idUsuario").innerText;
  const rolUsuario = document.getElementById("rolUsuario").innerText;

  let eventosCalendario = [];

  function llamadaApi() {
    const urlAlumno = 'http://localhost:8080/api/reservas-alumno/' + idUsuario;
    const urlProfesor = 'http://localhost:8080/api/reservas-profesor/' + idUsuario + "?todos=s";

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
      events: eventosCalendario,
      headerToolbar: {
        center: 'dayGridMonth,dayGridWeek,timeGridDay' // botones para cambiar entre vistas
      },
      views: {
        dayGridMonth: {
          type: 'dayGrid',
          buttonText: 'Mes'
        },
        dayGridWeek: {
          type: 'dayGrid',
          buttonText: 'Semana'
        },
        timeGridDay: {
          type: 'timeGrid',
          buttonText: 'Día'
        }
      },
    });
    calendar.render();
  }

  llamadaApi();
});
