package com.dateunrepaso.dur.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dateunrepaso.dur.entidades.Usuario;

@Service
public class UsuarioService {

    @Autowired
    private AlumnoImp alumnoImp;

    @Autowired
    private ProfesorImp profesorImp;

    public List<Usuario> findAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.addAll(alumnoImp.findAll());
        usuarios.addAll(profesorImp.findAll());
        return usuarios;
    }
}
