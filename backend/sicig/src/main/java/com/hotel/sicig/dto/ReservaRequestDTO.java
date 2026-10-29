package com.hotel.sicig.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ReservaRequestDTO {
    private String nombre;
    private String apellido;
    private String nacionalidad;
    private String tipoDocumento;
    private String numeroDocumento;

    private Long habitacionId;
    private LocalDate fechaIngreso;
    private LocalDate fechaSalida;
    private String estado;
    private Integer cantidadPersonas;
    private Double costoTotal;
}