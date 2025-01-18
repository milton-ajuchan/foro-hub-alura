package com.alura.foro_api.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
@Entity(name = "Usuario")
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String nombre;
    private String apellido;
    private String email;
    private Boolean enabled;

    // Constructor con DTO y password hasheado
    public Usuario(CrearUsuarioDTO crearUsuarioDTO, String hashedPassword) {
        this.username = crearUsuarioDTO.usuario();
        this.password = hashedPassword;
        this.role = Role.USUARIO; // Rol predeterminado
        this.nombre = capitalize(crearUsuarioDTO.nombre());
        this.apellido = capitalize(crearUsuarioDTO.apellido());
        this.email = crearUsuarioDTO.email();
        this.enabled = true;
    }

    // Metodo que actualiza el usuario con nuevo password o solo con datos
    public void actualizarUsuario(ActualizarUsuarioDTO actualizarUsuarioDTO, String hashedPassword) {
        if (actualizarUsuarioDTO.password() != null) this.password = hashedPassword;
        updateFields(actualizarUsuarioDTO);
    }

    // Metodo sin password, solo datos
    public void actualizarUsuario(ActualizarUsuarioDTO actualizarUsuarioDTO) {
        updateFields(actualizarUsuarioDTO);
    }

    // Metodo auxiliar para actualizar campos comunes
    private void updateFields(ActualizarUsuarioDTO actualizarUsuarioDTO) {
        if (actualizarUsuarioDTO.role() != null) this.role = actualizarUsuarioDTO.role();
        if (actualizarUsuarioDTO.nombre() != null) this.nombre = capitalize(actualizarUsuarioDTO.nombre());
        if (actualizarUsuarioDTO.apellido() != null) this.apellido = capitalize(actualizarUsuarioDTO.apellido());
        if (actualizarUsuarioDTO.email() != null) this.email = actualizarUsuarioDTO.email();
        if (actualizarUsuarioDTO.enabled() != null) this.enabled = actualizarUsuarioDTO.enabled();
    }

    // Desactiva el usuario
    public void eliminarUsuario() {
        this.enabled = false;
    }

    // Metodo auxiliar para capitalizar nombres
    private String capitalize(String string) {
        if (string == null || string.isEmpty()) return string;
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    // Metodos de UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
