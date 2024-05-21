document.addEventListener('DOMContentLoaded', function() {

    var calendarEl = document.getElementById('calendar');
    
    var calendar = new FullCalendar.Calendar(calendarEl, {
      initialView: 'dayGridMonth',
      events:function(info, succesCallback, failureCallbak){
        console.log(info) //TODO seguir viendo el video y terminar api
      }
    });


    calendar.render();
  });