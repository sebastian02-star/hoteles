package com.hotel.sicig.dto;


import lombok.Data;
import java.util.Set;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String username;
    private String nombres;
    private String apellidos;
    private String email;
    private Boolean activo;
    private Set<String> roles;
}