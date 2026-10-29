package com.hotel.sicig.controller;

import com.hotel.sicig.dto.UsuarioRequestDTO;
import com.hotel.sicig.dto.UsuarioResponseDTO;
import com.hotel.sicig.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200") 
public class UsuarioController {

    private final UsuarioService usuarioService;


    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    
    @GetMapping
    public ResponseEntity<?> listarUsuarios() {
        try {
            List<UsuarioResponseDTO> usuarios = usuarioService.listarUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al listar usuarios: " + e.getMessage());
        }
    }

   
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioRequestDTO dto) {
        try {
            UsuarioResponseDTO nuevoUsuario = usuarioService.registrarUsuario(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al registrar usuario: " + e.getMessage());
        }
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminacionLogica(@PathVariable Long id) {
        try {
            usuarioService.eliminacionLogica(id);
            return ResponseEntity.ok("Usuario desactivado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al desactivar usuario: " + e.getMessage());
        }
    }
}