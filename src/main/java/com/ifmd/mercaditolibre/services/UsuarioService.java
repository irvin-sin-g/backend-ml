package com.ifmd.mercaditolibre.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ifmd.mercaditolibre.dto.RegistroRequest;
import com.ifmd.mercaditolibre.model.ClienteEntity;
import com.ifmd.mercaditolibre.model.Rol;
import com.ifmd.mercaditolibre.model.Rol;
import com.ifmd.mercaditolibre.model.UsuarioEntity;
import com.ifmd.mercaditolibre.repository.ClienteRepository;
import com.ifmd.mercaditolibre.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clientesRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            ClienteRepository clientesRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.clientesRepository = clientesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
public UsuarioEntity saveUsuario(RegistroRequest request) {

    if (request.getUsername() == null || request.getUsername().isBlank()) {
        throw new IllegalArgumentException("El nombre de usuario o correo es obligatorio.");
    }

    if (request.getPassword() == null || request.getPassword().length() < 6) {
        throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
    }

    if (request.getNombre() == null || request.getNombre().isBlank()) {
        throw new IllegalArgumentException("El nombre es obligatorio.");
    }

    String username = request.getUsername().trim().toLowerCase();

    if (usuarioRepository.existsByUsername(username)) {
        throw new IllegalArgumentException("El nombre de usuario ya está en uso.");
    }

    Rol rol = Rol.ROLE_CLIENTE;
    if (request.getRol() != null && request.getRol().equalsIgnoreCase("ROLE_ADMIN")) {
        rol = Rol.ROLE_ADMIN;
    }

    // Crear usuario
    UsuarioEntity usuario = new UsuarioEntity();
    usuario.setUsername(username);
    usuario.setPassword(passwordEncoder.encode(request.getPassword()));
    usuario.setNombre(request.getNombre().trim());
    usuario.setRole(rol);

    // 👇 Estas dos líneas faltaban
    usuario.setTelefono(request.getTelefono());
    usuario.setDireccion(request.getDireccion());

    UsuarioEntity usuarioGuardado = usuarioRepository.save(usuario);

    // Si es cliente, también guardar en tabla cliente
    if (rol == Rol.ROLE_CLIENTE) {
        if (request.getTelefono() == null || request.getTelefono().isBlank()) {
            throw new IllegalArgumentException("El teléfono es obligatorio para el cliente.");
        }

        ClienteEntity cliente = new ClienteEntity();
        cliente.setNombre(request.getNombre().trim());
        cliente.setEmail(username);
        cliente.setDireccion(request.getDireccion());
        cliente.setTelefono(request.getTelefono());

        clientesRepository.save(cliente);
    }

    return usuarioGuardado;
}

}
