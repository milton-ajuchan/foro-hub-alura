package com.alura.foro_api.infra.security;

import com.alura.foro_api.domain.usuario.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public AuthenticationService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Se intenta cargar el usuario desde el repositorio
        UserDetails user = usuarioRepository.findByUsername(username);

        // Si no se encuentra el usuario, se lanza una excepción
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con el nombre de usuario: " + username);
        }

        return user;
    }
}
