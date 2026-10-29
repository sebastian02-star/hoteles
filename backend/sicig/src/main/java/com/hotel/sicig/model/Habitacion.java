package com.hotel.sicig.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "habitaciones")
@Data
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String estado;

    private Double precio;
    private String descripcion;
}