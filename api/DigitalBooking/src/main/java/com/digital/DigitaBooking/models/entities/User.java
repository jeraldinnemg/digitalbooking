package com.digital.DigitaBooking.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class User implements UserDetails {

    // La interfaz UserDetails es parte del framework de seguridad de Spring (Spring
    // Security) y se utiliza para representar los detalles de un usuario en el contexto
    // de autenticación y autorización.
    // La interfaz UserDetails define una serie de métodos que deben ser implementados para
    // proporcionar información sobre el usuario, como su nombre de usuario, contraseña,
    // roles, permisos y otros detalles relevantes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @Column
    @NotNull
    private String userName;

    @Column
    @NotNull
    private String userLastName;

    @Column
    @NotNull
    private String userFirstName;

    @Column
    @NotNull
    private Double latitude;

    @Column
    @NotNull
    private Double longitude;

    @Column
    @NotNull
    private String password;

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Reservation> reservations = new HashSet<>();

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch =FetchType.LAZY)
    private Set<Favorite> favorites = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    // El método getAuthorities() devuelve una colección de objetos GrantedAuthority,
    // que representan los roles o permisos del usuario. El propósito de este método
    // es proporcionar los roles o permisos del usuario al sistema de seguridad de
    // Spring para su uso en la autenticación y autorización. Cada objeto GrantedAuthority
    // representa un rol o permiso específico y se utiliza para tomar decisiones sobre los
    // accesos permitidos o restringidos a diferentes partes de la aplicación.
    // Por ejemplo, como enum Role tiene dos constantes: USER y ADMIN, y el usuario
    // tiene asignado el rol USER, entonces el método getAuthorities() devolverá una lista
    // con un único objeto SimpleGrantedAuthority que tiene el nombre "USER".

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // El método devuelve true, lo que implica que la cuenta del usuario nunca
    // se considera expirada.

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // El método devuelve true, lo que implica que la cuenta del usuario nunca se considera
    // bloqueada, diferente sería si implementamos una funcionalidad de bloqueo.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // El método simplemente devuelve true, lo que indica que las credenciales del usuario
    // nunca se consideran expiradas.
    @Override
    public boolean isEnabled() {
        return true;
    }

    // El método isEnabled() indica si la cuenta de usuario está habilitada o no. En nuestro
    // caso el método siempre devuelve true, lo que implica que la cuenta del usuario se
    // considera habilitada.
}
