document.addEventListener('DOMContentLoaded', function () {
  const idUsuario = document.getElementById("idUsuario").innerText;
  const rolUsuario = document.getElementById("rolUsuario").innerText;

  let eventosCalendario = [];

  const alertDiv = document.getElementById("alert");
  alertDiv.style.display = "none";

  function llamadaApi() {
    const urlAlumno = 'http://localhost:8080/api/reservas-alumno/' + idUsuario;
    const urlProfesor = 'http://localhost:8080/api/reservas-profesor/' + idUsuario;


    const conexion = new XMLHttpRequest();
    conexion.onreadystatechange = function () {
      if (conexion.readyState == 4) {
        alertDiv.style.display = "none";
        if (conexion.status == 200) {
          const json = conexion.responseText;
          eventosCalendario = JSON.parse(json);
          console.log(eventosCalendario);
          renderCalendar();
        } else {
          alertDiv.style.display = "inherit";
          alertDiv.children[0].innerHTML = 'Error al cargar los datos';
        }
      } else {
        alertDiv.style.display = "inherit";
        alertDiv.children[0].innerHTML = 'cargando...';
      }
    };

    if (rolUsuario == "ROL_ALUMNO") {
      conexion.open('GET', urlAlumno, true); // true para solicitud asíncrona
      conexion.send();
    }

    if (rolUsuario == "ROL_Profesor") {
      conexion.open('GET', urlProfesor, true); // true para solicitud asíncrona
      conexion.send();

    }
  }

  function renderCalendar() {
    const calendarEl = document.getElementById('calendar');

    const calendar = new FullCalendar.Calendar(calendarEl, {
      locale: 'es',
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

    calendar.render();
  }

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

  llamadaApi();
});
