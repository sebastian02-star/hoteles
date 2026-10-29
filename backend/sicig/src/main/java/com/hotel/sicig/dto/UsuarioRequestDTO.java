package com.hotel.sicig.dto;

import lombok.Data;
import java.util.Set;

@Data
public class UsuarioRequestDTO {
    
    private String username;
    private String password;
    private String nombres;
    private String apellidos;
    private String email;
    private Set<String> roles; 
}