package com.dateunrepaso.dur.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.servicios.AlumnoImp;
import com.dateunrepaso.dur.servicios.ProfesorImp;
import com.dateunrepaso.dur.utilidades.UtilidadesString;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dateunrepaso.dur.enums.Roles;

@Controller
public class GestionUsuarioControlador {

    @Autowired
    AlumnoImp alumnoImp;

    @Autowired
    ProfesorImp profesorImp;

    @GetMapping("/gestion-usuario")
    public String getGestionUsuario(HttpSession sesion, Model model) {
        if (sesion.getAttribute("usuarioLogeado").getClass() == Alumno.class) {
            Alumno alumno = (Alumno) sesion.getAttribute("usuarioLogeado");
            model.addAttribute("usuario", alumno);
            model.addAttribute("tipoUsuario", "alumno");
        } else {
            Profesor profesor = (Profesor) sesion.getAttribute("usuarioLogeado");
            model.addAttribute("usuario", profesor);
            model.addAttribute("tipoUsuario", "profesor");
        }

        return "GestionUsuario";
    }

    @PostMapping("/gestion-usuario/editar")
    public String postEditarGestionUsuario(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "nombre") String nombre,
            @RequestParam(name = "dni") String dni,
            @RequestParam(name = "fechaNac") String fechaNac,
            @RequestParam(name = "correo") String correo,
            RedirectAttributes atributos,
            @RequestParam(name = "contrasena") String contrasena, HttpSession sesion, Model model) {

        if (sesion.getAttribute("usuarioLogeado").getClass() == Alumno.class) {
            boolean correcto = false;
            Alumno alumno = alumnoImp.findById(id).get();

            if ((!profesorImp.findByCorreo(correo).isEmpty() || !alumnoImp.findByCorreo(correo).isEmpty())
                    && !correo.equals(alumno.getCorreo())) {
                atributos.addFlashAttribute("Error", "Ya existe un usuario con ese correo electrónico");

            } else if ((profesorImp.findByDni(dni) != null || alumnoImp.findByDni(dni) != null)
                    && !dni.equals(alumno.getDni())) {
                atributos.addFlashAttribute("Error", "Ya existe un usuario con ese DNI");

            } else if (!UtilidadesString.esMayorEdad(fechaNac, 8)) {
                atributos.addFlashAttribute("Error", "El alumno no puede ser menor de 8 años");
            } else {
                correcto = true;
            }

            if (correcto) {
                alumnoImp.actualizarAlumno(id, nombre, alumno.getNomUsuario(), dni, correo, contrasena, fechaNac);
                alumno = new Alumno(id, dni, nombre, alumno.getNomUsuario(), correo, contrasena, fechaNac,
                        Roles.ALUMNO);
                sesion.setAttribute("usuarioLogeado", alumno);
            }

        } else {
            boolean correcto = false;
            Profesor profesor = profesorImp.findById(id).get();

            if ((!profesorImp.findByCorreo(correo).isEmpty() || !alumnoImp.findByCorreo(correo).isEmpty())
                    && !correo.equals(profesor.getCorreo())) {
                atributos.addFlashAttribute("Error", "Ya existe un usuario con ese correo electrónico");

            } else if ((profesorImp.findByDni(dni) != null || alumnoImp.findByDni(dni) != null)
                    && !dni.equals(profesor.getDni())) {
                atributos.addFlashAttribute("Error", "Ya existe un usuario con ese DNI");

            } else if (!UtilidadesString.esMayorEdad(fechaNac, 8)) {
                atributos.addFlashAttribute("Error", "El alumno no puede ser menor de 8 años");
            } else {
                correcto = true;
            }

            if (correcto) {
                profesorImp.actualizarProfesor(id, nombre, profesor.getNomUsuario(), correo, contrasena, fechaNac, dni,
                        profesor.getAsignatura());
                profesor = new Profesor(id, dni, nombre, profesor.getNomUsuario(), correo, contrasena, fechaNac,
                        Roles.PROFESOR, profesor.getAsignatura());
                sesion.setAttribute("usuarioLogeado", profesor);

            }

        }

        return "redirect:/gestion-usuario";
    }

}
