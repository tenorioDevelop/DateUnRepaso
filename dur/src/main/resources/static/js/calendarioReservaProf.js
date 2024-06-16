document.addEventListener('DOMContentLoaded', function () {
  const idUsuario = document.getElementById("idUsuario").innerText;
  const rolUsuario = document.getElementById("rolUsuario").innerText;

  let eventosCalendario = [];

  function llamadaApi() {
    const urlAlumno = 'https://dateunrepaso.up.railway.app/api/reservas-alumno/' + idUsuario;
    const urlProfesor = 'https://dateunrepaso.up.railway.app/api/reservas-profesor/' + idUsuario + "?todos=s";

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
      events: eventosCalendario,
      eventClick: function (info) {
        info.jsEvent.preventDefault(); // prevenir el comportamiento predeterminado
        document.getElementById('modalTitle').innerText = info.event.title;
        document.getElementById('modalBody').innerText =
          `Inicio: ${info.event.start.toLocaleString()}\nFin: ${info.event.end ? info.event.end.toLocaleString() : 'N/A'}`;
        document.getElementById('eventModal').style.display = 'block';
      }
    });

     // Código para cerrar el modal
  const modal = document.getElementById('eventModal');
  const span = document.getElementsByClassName('close')[0];

  span.onclick = function () {
    modal.style.display = 'none';
  }

  window.onclick = function (event) {
    if (event.target == modal) {
      modal.style.display = 'none';
    }
  }
    calendar.render();
  }

  llamadaApi();
});
