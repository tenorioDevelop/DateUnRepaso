package com.dateunrepaso.dur.security;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dateunrepaso.dur.entidades.Alumno;
import com.dateunrepaso.dur.entidades.Profesor;
import com.dateunrepaso.dur.repositorios.AlumnoRepo;
import com.dateunrepaso.dur.repositorios.ProfesorRepo;

@Service
public class UserDetail implements UserDetailsService {

	@Autowired
	private AlumnoRepo alumnoRepo;

	@Autowired
	private ProfesorRepo profesorRepo;

	private Set<GrantedAuthority> set = new HashSet<>();

	@Override
	public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {

		Optional<Alumno> alumno = alumnoRepo.findByCorreo(correo);
		Optional<Profesor> profesor = profesorRepo.findByCorreo(correo);

		if (alumno.isEmpty() && profesor.isEmpty()) {
			new UsernameNotFoundException("User not exists by Username");
		}

		if (!alumno.isEmpty()) {

			GrantedAuthority authorities = new SimpleGrantedAuthority(alumno.get().getRol().toString());
			set.add(authorities);

			return new org.springframework.security.core.userdetails.User(correo, alumno.get().getContrasena(), set);
		} else {

			GrantedAuthority authorities = new SimpleGrantedAuthority(profesor.get().getRol().toString());
			set.add(authorities);

			return new org.springframework.security.core.userdetails.User(correo, profesor.get().getContrasena(), set);

		}
	}

}
