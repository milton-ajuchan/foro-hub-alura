package com.alura.foro_api.controller;

import com.alura.foro_api.domain.usuario.AutenticarUsuarioDTO;
import com.alura.foro_api.domain.usuario.Usuario;
import com.alura.foro_api.infra.security.JWTtokenDTO;
import com.alura.foro_api.infra.security.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticación", description = "Permite la autenticación del usuario mediante sus credenciales para obtener un token JWT, el cual es necesario para acceder a los recursos protegidos de la API.")

public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    // Inyección de dependencias por constructor
    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    /**
     * Realiza la autenticación del usuario y genera el token JWT correspondiente.
     *
     * @param autenticacionUsuario Datos de usuario para autenticación.
     * @return ResponseEntity con el token JWT.
     */
    @PostMapping
    public ResponseEntity<JWTtokenDTO> autenticar(@RequestBody @Valid AutenticarUsuarioDTO autenticacionUsuario) {
        try {
            // Crear un token de autenticación con las credenciales proporcionadas.
            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    autenticacionUsuario.usuario(),
                    autenticacionUsuario.password()
            );

            // Autenticar al usuario a través del AuthenticationManager
            Authentication usuarioAutenticado = authenticationManager.authenticate(authToken);

            // Generar el token JWT
            var jwtToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

            // Retornar el token en el cuerpo de la respuesta
            return ResponseEntity.ok(new JWTtokenDTO(jwtToken));
        } catch (Exception e) {
            // En caso de error, puedes agregar un manejo de excepciones más específico
            return ResponseEntity.status(401).build();  // Unauthorized
        }
    }
}
