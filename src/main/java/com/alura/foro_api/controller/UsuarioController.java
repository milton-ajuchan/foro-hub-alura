package com.alura.foro_api.controller;

import com.alura.foro_api.domain.usuario.*;
import com.alura.foro_api.domain.usuario.validaciones.actualizar.ValidarActualizarUsuario;
import com.alura.foro_api.domain.usuario.validaciones.crear.ValidarCrearUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Usuario", description = "Gestiona la creación de usuarios, sus datos y sus interacciones en el foro.")

public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private List<ValidarCrearUsuario> crearValidador;

    @Autowired
    private List<ValidarActualizarUsuario> actualizarValidador;

    // Metodo privado para evitar duplicación al crear el DTO de respuesta
    private DetallesUsuarioDTO convertirADetallesUsuarioDTO(Usuario usuario) {
        return new DetallesUsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getEnabled()
        );
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Crea un nuevo usuario y lo registra en la base de datos.")

    public ResponseEntity<DetallesUsuarioDTO> crearUsuario(@RequestBody @Valid CrearUsuarioDTO crearUsuarioDTO, UriComponentsBuilder uriBuilder) {
        crearValidador.forEach(v -> v.validate(crearUsuarioDTO));

        String hashedPassword = passwordEncoder.encode(crearUsuarioDTO.password());
        Usuario usuario = new Usuario(crearUsuarioDTO, hashedPassword);

        repository.save(usuario);
        var uri = uriBuilder.path("/usuarios/{username}").buildAndExpand(usuario.getUsername()).toUri();
        return ResponseEntity.created(uri).body(convertirADetallesUsuarioDTO(usuario));
    }

    @GetMapping("/all")
    @Operation(summary = "Lista todos los usuarios, sin importar su estado de habilitación.")

    public ResponseEntity<Page<DetallesUsuarioDTO>> leerTodosUsuarios(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        var pagina = repository.findAll(pageable).map(this::convertirADetallesUsuarioDTO);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping
    @Operation(summary = "Lista únicamente los usuarios habilitados.")

    public ResponseEntity<Page<DetallesUsuarioDTO>> leerUsuariosActivos(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        var pagina = repository.findAllByEnabledTrue(pageable).map(this::convertirADetallesUsuarioDTO);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Obtiene los detalles de un único usuario a través de su nombre de usuario.")

    public ResponseEntity<DetallesUsuarioDTO> leerUnUsuario(@PathVariable String username) {
        Optional<Usuario> usuarioOpt = Optional.ofNullable((Usuario) repository.findByUsername(username));
        return usuarioOpt.map(usuario -> ResponseEntity.ok(convertirADetallesUsuarioDTO(usuario)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Obtiene los detalles de un único usuario a través de su ID.")

    public ResponseEntity<DetallesUsuarioDTO> leerUnUsuario(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = Optional.ofNullable(repository.findById(id).orElse(null));
        return usuarioOpt.map(usuario -> ResponseEntity.ok(convertirADetallesUsuarioDTO(usuario)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{username}")
    @Transactional
    @Operation(summary = "Actualiza la contraseña, rol, nombre, apellido, correo electrónico o estado habilitado de un usuario.")

    public ResponseEntity<DetallesUsuarioDTO> actualizarUsuario(@RequestBody @Valid ActualizarUsuarioDTO actualizarUsuarioDTO, @PathVariable String username) {
        actualizarValidador.forEach(v -> v.validate(actualizarUsuarioDTO));

        Usuario usuario = (Usuario) repository.findByUsername(username);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        if (actualizarUsuarioDTO.password() != null) {
            String hashedPassword = passwordEncoder.encode(actualizarUsuarioDTO.password());
            usuario.actualizarUsuarioConPassword(actualizarUsuarioDTO, hashedPassword);
        } else {
            usuario.actualizarUsuario(actualizarUsuarioDTO);
        }

        return ResponseEntity.ok(convertirADetallesUsuarioDTO(usuario));
    }

    @DeleteMapping("/{username}")
    @Transactional
    @Operation(summary = "Deshabilita o elimina la cuenta de un usuario en el sistema.")

    public ResponseEntity<Void> eliminarUsuario(@PathVariable String username) {
        Optional<Usuario> usuarioOpt = Optional.ofNullable((Usuario) repository.findByUsername(username));
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = usuarioOpt.get();
        usuario.eliminarUsuario();
        return ResponseEntity.noContent().build();
    }
}
