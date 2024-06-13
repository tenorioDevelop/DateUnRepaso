package com.dateunrepaso.dur.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dateunrepaso.dur.email.EmailDTO;
import com.dateunrepaso.dur.email.EmailServiceImp;
import com.dateunrepaso.dur.entidades.ReservaAlumno;
import com.dateunrepaso.dur.entidades.ReservaProfesor;
import com.dateunrepaso.dur.servicios.ReservaProfesorImp;

@Component
public class Tareas {

    @Autowired
    EmailServiceImp emailServiceImp;

    @Autowired
    ReservaProfesorImp reservaProfesorImp;

    @Transactional
    @Scheduled(cron = "0 0 19 * * *") // A las 19pm
    public void performTask() {

        List<ReservaProfesor> reservasProfesores = reservaProfesorImp.getReservasDeProfesorActuales();
        EmailDTO emailDTO = null;

        for (ReservaProfesor reservaProfesor : reservasProfesores) {
            if (reservaProfesor.getFechaReserva().equals(LocalDate.now().plusDays(1))) {
                // Enviar correo al profesor
                emailDTO = new EmailDTO(reservaProfesor.getProfesor().getCorreo(), "Recordatorio ⏰",
                        "Tienes clase mañana en el aula: " + reservaProfesor.getAula().getNombre() +
                                "<br>Numero de alumnos aputados: " + reservaProfesor.getLstReservaAlumno().size() +
                                "<br>Hora: " + reservaProfesor.getHoraInicio() + "pm");
                emailServiceImp.enviarCorreo(emailDTO);

                // Enviar correo a los alumnos
                for (ReservaAlumno reservaAlumno : reservaProfesor.getLstReservaAlumno()) {
                    emailDTO = new EmailDTO(reservaAlumno.getProfesor().getCorreo(), "Recordatorio ⏰",
                            "Tienes clase mañana en el aula: " + reservaAlumno.getAula().getNombre() +
                                    " <br>Con el profesor: " + reservaAlumno.getProfesor().getNomCompleto() +
                                    "<br>Asignatura: " + reservaAlumno.getProfesor().getAsignatura().getNombre());
                    emailServiceImp.enviarCorreo(emailDTO);
                }
            }
        }

    }
}
