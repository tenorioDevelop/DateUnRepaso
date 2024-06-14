package com.dateunrepaso.dur.controladores;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dateunrepaso.dur.email.EmailDTO;
import com.dateunrepaso.dur.email.EmailServiceImp;
import com.dateunrepaso.dur.entidades.Aula;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaProfesor;
import com.dateunrepaso.dur.entidades.Usuario;
import com.dateunrepaso.dur.repositorios.AulaRepo;
import com.dateunrepaso.dur.servicios.ProfesorImp;
import com.dateunrepaso.dur.servicios.ReservaProfesorImp;
import com.dateunrepaso.dur.servicios.UsuarioService;

@Controller
@RequestMapping("/reserva-profesor")
@PreAuthorize("hasRole('PROFESOR')")
public class ReservaProfesorControlador {

    @Autowired
    EmailServiceImp emailServiceImp;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private AulaRepo aulaRepo;

    @Autowired
    private ReservaProfesorImp reservaProfImp;

    @Autowired
    private ProfesorImp profesorImp;

    @GetMapping("")
    public String getMain(Model model) {
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
        model.addAttribute("usuario", usuario);

        List<Aula> aulas = new ArrayList<>();
        aulas = aulaRepo.findAll();
        model.addAttribute("listaAulas", aulas);

        return "ReservaProfesor";
    }

    @Transactional
    @PostMapping("")
    public String postReservar(@RequestParam(name = "resProfAula") Long idAula,
            @RequestParam(name = "resProfFecha") LocalDate fecha,
            @RequestParam(name = "resProfHoraI") int horaI,
            @RequestParam(name = "resProfHoraF") int horaF,
            Model model,
            RedirectAttributes atributos) {
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
        model.addAttribute("usuario", usuario);
        Profesor profesor = profesorImp.findById(usuario.getId()).get();

        int horaActual = LocalTime.now().getHour();

        boolean correcto = true;

        // Reatachar el profesor al contexto de persistencia
        profesor = profesorImp.findById(profesor.getId()).get();

        Aula aula = aulaRepo.findById(idAula).get();

        List<ReservaProfesor> reservas = reservaProfImp.findAll();

        // Comprobaciones basicas

        if (fecha.isBefore(LocalDate.now())) {
            atributos.addFlashAttribute("Error", "No puedes reservar en una fecha anterior a la fecha actual");
            correcto = false;
        } else if (horaI == horaF) {
            atributos.addFlashAttribute("Error", "La hora inicio y final no pueden ser la misma");
            correcto = false;
        } else if (horaI > horaF) {
            atributos.addFlashAttribute("Error", "La hora final no puede ser antes que la hora inicio");
            correcto = false;
        } else if ((fecha.isEqual(LocalDate.now())) && (horaI < horaActual)) {
            atributos.addFlashAttribute("Error", "La hora inicio ya ha pasado");
            correcto = false;
        }

        for (ReservaProfesor reserva : reservas) {
            boolean mismaFecha = reserva.getFechaReserva().equals(fecha);
            boolean mismoProfesor = reserva.getProfesor().equals(profesor);
            boolean mismaAula = reserva.getAula().equals(aula);

            // Conflicto exacto para el mismo profesor
            if (mismoProfesor && mismaFecha && reserva.getHoraInicio() <= horaI && reserva.getHoraFin() >= horaF) {
                atributos.addFlashAttribute("Error", "Ya tienes una reserva en ese horario");
                correcto = false;
                break;
            }

            // Conflicto exacto para el mismo aula
            if (mismaAula && mismaFecha && reserva.getHoraInicio() <= horaI && reserva.getHoraFin() >= horaF) {
                atributos.addFlashAttribute("Error", "Ya existe una reserva en ese horario");
                correcto = false;
                break;
            }

            // Solapamiento parcial para el mismo aula
            if (mismaAula && mismaFecha && ((horaI >= reserva.getHoraInicio() && horaI < reserva.getHoraFin())
                    || (horaF > reserva.getHoraInicio() && horaF <= reserva.getHoraFin())
                    || (horaI < reserva.getHoraInicio() && horaF > reserva.getHoraFin()))) {
                atributos.addFlashAttribute("Error", "No puedes elegir una hora que ya está ocupada por otra reserva");
                correcto = false;
                break;
            }
        }

        if (correcto) {
            ReservaProfesor reserva = new ReservaProfesor(null, profesor, aula, fecha, horaI, horaF);
            reservaProfImp.save(reserva);

            //Enviar correo
            EmailDTO emailDTO = new EmailDTO(profesor.getCorreo(), "Reserva realizada correctamente", "Has reservado el aula: " + aula.getNombre() + ", el dia: " + fecha);
            emailServiceImp.enviarCorreo(emailDTO);


            return "redirect:/clases";
        } else {
            return "redirect:/reserva-profesor";
        }
    }

}
