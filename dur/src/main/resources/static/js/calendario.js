document.addEventListener('DOMContentLoaded', function () {

  var calendarEl = document.getElementById('calendar');

  var calendar = new FullCalendar.Calendar(calendarEl, {
    locale: 'es',
    headerToolbar: {
      center: 'dayGridMonth,dayGridWeek,timeGridDay' // buttons for switching between views
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
        buttonText: 'Dia'
      }
    },
    events: [{
      title: 'Mehndi',
      start: '2024-05-20T17:00',
      end: '2024-05-20T19:00',
      color: 'yellow',
      textColor: 'black'
    }],
  }
  );


  calendar.render();
});