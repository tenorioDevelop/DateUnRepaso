package com.dateunrepaso.dur.controladores;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.entidades.ReservaAlumno;
import com.dateunrepaso.dur.entidades.ReservaProfesor;
import com.dateunrepaso.dur.entidades.Usuario;
import com.dateunrepaso.dur.enums.Roles;
import com.dateunrepaso.dur.servicios.AlumnoImp;
import com.dateunrepaso.dur.servicios.ReservaAlumnoImp;
import com.dateunrepaso.dur.servicios.ReservaProfesorImp;
import com.dateunrepaso.dur.servicios.UsuarioService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reserva-alumno")
@PreAuthorize("hasRole('ALUMNO')")
public class ReservaAlumnoControlador {

    @Autowired
    ReservaAlumnoImp reservaAlumnoImp;

    @Autowired
    ReservaProfesorImp reservaProfeImp;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    AlumnoImp alumnoImp; // Añadir el repositorio de Alumno

    @GetMapping("")
    public String getReservaAlumno(Model model) {
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
        Roles rol = usuario.getRol();
        model.addAttribute("usuario", usuario);
        model.addAttribute("tipoUsuario", rol.name().toLowerCase());

        Alumno alumno = alumnoImp.findById(usuario.getId()).get();


        List<ReservaProfesor> reservaP = reservaProfeImp.getReservasDeProfesorActuales();
        reservaP.removeIf(p -> !p.getLstReservaAlumno().stream().allMatch(a -> a.getId() == alumno.getId()));
        model.addAttribute("listaReservasP", reservaP);
        return "ReservaAlumno";
    }

    @Transactional
    @PostMapping("")
    public String postReservaAlumno(Model model,
            RedirectAttributes atributos,
            @RequestParam(name = "idReserva") Long idReserva) {

        boolean esValido = true;

        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.findByUsername(nombreUsuario).get();
        Roles rol = usuario.getRol();
        model.addAttribute("usuario", usuario);
        model.addAttribute("tipoUsuario", rol.name().toLowerCase());

        Alumno alumno = alumnoImp.findById(usuario.getId()).get();

        ReservaProfesor reservaP = reservaProfeImp.findById(idReserva).get();

        ReservaAlumno reservaA = new ReservaAlumno(null, alumno, reservaP.getProfesor(), reservaP.getAula(),
                reservaP.getFechaReserva(), reservaP.getHoraInicio(), reservaP.getHoraFin(), reservaP);

        // Busca si la reserva que se quiere crear ya existe
        if (reservaAlumnoImp.findByAulaAndProfesorAndAlumnoAndFechaReservaAndHoraInicio(reservaP.getAula(),
                reservaP.getProfesor(), alumno, reservaP.getFechaReserva(), reservaP.getHoraInicio()).isPresent()) {
            atributos.addFlashAttribute("Error", "Ya existe una reserva suya para ese dia a esa hora");
            esValido = false;
        } else if (reservaAlumnoImp.findByAulaAndFechaReservaAndProfesor(reservaP.getAula(), reservaP.getFechaReserva(),
                reservaP.getProfesor()).get().size() >= reservaP.getAula().getCantidadMaxAlumnos()) {
            atributos.addFlashAttribute("Error", "Se ha superado el numero de alumnos en este aula");
            esValido = false;
        }

        if (esValido) {
            reservaAlumnoImp.save(reservaA);
            return "redirect:/clases";
        } else {
            return "redirect:/reserva-alumno";
        }
    }
}
