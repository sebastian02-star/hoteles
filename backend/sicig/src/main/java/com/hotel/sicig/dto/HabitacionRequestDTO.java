package com.hotel.sicig.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class HabitacionRequestDTO {

    @NotBlank(message = "El número de habitación es obligatorio")
    private String numeroHabitacion;

    @NotBlank(message = "El tipo de habitación es obligatorio")
    private String tipo;

    @NotBlank(message = "El estado inicial es obligatorio")
    private String estado;

    @NotNull(message = "El precio por noche es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private Double precioPorNoche;

    private String descripcion;

   
    public String getNumeroHabitacion() { return numeroHabitacion; }
    public void setNumeroHabitacion(String numeroHabitacion) { this.numeroHabitacion = numeroHabitacion; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Double getPrecioPorNoche() { return precioPorNoche; }
    public void setPrecioPorNoche(Double precioPorNoche) { this.precioPorNoche = precioPorNoche; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
