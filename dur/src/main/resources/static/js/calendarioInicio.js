document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendarioInicio');
    var calendar = new FullCalendar.Calendar(calendarEl, {
      initialView: 'dayGridMonth'
    });
    calendar.render();
  });