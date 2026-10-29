import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { Habitacion } from '../../services/habitacion';
import { HabitacionService } from '../../services/habitacion.service';

@Component({
  selector: 'app-habitaciones',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './habitaciones.html',
  styleUrls: ['./habitaciones.css']
})
export class HabitacionesComponent implements OnInit {
  habitaciones: Habitacion[] = [];
  
  nuevaHabitacion: Habitacion = {
    numero: '',
    tipo: '',
    estado: 'Disponible',
    precio: 0,
    descripcion: ''
  };

  mostrarModal = false;
  successMessage = '';
  errorMessage = '';

  estadosHabitacion: string[] = [
    'Disponible',
    'Ocupada',
    'Limpieza',
    'Mantenimiento',
    'Reservada',
    'Fuera de Servicio'
  ];

  constructor(private habitacionService: HabitacionService) {}

  ngOnInit(): void {
    this.cargarHabitaciones();
  }

  cargarHabitaciones() {
    this.habitacionService.listarHabitaciones().subscribe({
      next: (data) => {
        this.habitaciones = data;
      },
      error: (err) => {
        this.errorMessage = 'Error al cargar las habitaciones desde el servidor.';
        console.error(err);
      }
    });
  }

  abrirModal() {
    this.nuevaHabitacion = { numero: '', tipo: '', estado: 'Disponible', precio: 0, descripcion: '' };
    this.mostrarModal = true;
    this.successMessage = '';
    this.errorMessage = '';
  }

  cerrarModal() {
    this.mostrarModal = false;
  }

  guardarHabitacion(form: NgForm) {
    if (form.valid) {
      this.habitacionService.guardarHabitacion(this.nuevaHabitacion).subscribe({
        next: (response) => {
          this.successMessage = '¡Habitación registrada exitosamente!';
          this.cargarHabitaciones();
          setTimeout(() => {
            this.cerrarModal();
          }, 1200);
        },
        error: (err) => {
          this.errorMessage = typeof err.error === 'string' ? err.error : 'Error al registrar la habitación.';
          console.error(err);
        }
      });
    }
  }

  eliminar(id?: number) {
    if (!id) return;
    if (confirm('¿Estás seguro de eliminar esta habitación del sistema?')) {
      this.habitacionService.eliminarHabitacion(id).subscribe({
        next: () => {
          this.successMessage = 'Habitación eliminada correctamente.';
          this.cargarHabitaciones();
        },
        error: (err) => {
          this.errorMessage = 'No se pudo eliminar la habitación.';
          console.error(err);
        }
      });
    }
  }
}