package com.hotel.sicig.service;

import com.hotel.sicig.dto.UsuarioRequestDTO;
import com.hotel.sicig.dto.UsuarioResponseDTO;
import com.hotel.sicig.model.Rol;
import com.hotel.sicig.model.Usuario;
import com.hotel.sicig.repository.RolRepository;
import com.hotel.sicig.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, 
                          RolRepository rolRepository, 
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 1. Listar todos los usuarios
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuario -> mapearADto(usuario))
                .collect(Collectors.toList());
    }

    // 2. Registrar usuario completo
    @Transactional
    public UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya se encuentra registrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setNombres(dto.getNombres());
        usuario.setApellidos(dto.getApellidos());
        usuario.setEmail(dto.getEmail());
        usuario.setActivo(true);

        Set<Rol> roles = dto.getRoles().stream()
                .map(nombreRol -> rolRepository.findByNombre(nombreRol)
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + nombreRol)))
                .collect(Collectors.toSet());
        usuario.setRoles(roles);

        Usuario guardado = usuarioRepository.save(usuario);
        return mapearADto(guardado);
    }

    // 3. Eliminación Lógica
    @Transactional
    public void eliminacionLogica(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    // Mapeo seguro sin advertencias de nulos
    private UsuarioResponseDTO mapearADto(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setNombres(usuario.getNombres());
        dto.setApellidos(usuario.getApellidos());
        dto.setEmail(usuario.getEmail());
        dto.setActivo(usuario.getActivo());
        
        Set<String> nombresRoles = usuario.getRoles().stream()
                .map(rol -> rol.getNombre())
                .collect(Collectors.toSet());
        dto.setRoles(nombresRoles);
        
        return dto;
    }
}