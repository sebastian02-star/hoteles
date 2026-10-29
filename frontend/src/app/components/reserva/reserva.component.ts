import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HabitacionService } from '../../services/habitacion.service';
import { ReservaService } from '../../services/reserva.service';

@Component({
  selector: 'app-reserva',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reserva.component.html',
  styleUrls: ['./reserva.component.css']
})
export class ReservaComponent implements OnInit {
  habitaciones: any[] = [];
  reservasLista: any[] = []; 
  filtroHabitacion: string = '';
  fechaMinima: string = new Date().toISOString().split('T')[0];
  
  mensajeExito: string | null = null;
  habitacionSeleccionada: any = null;

  cliente = { 
    nombre: '', 
    apellido: '', 
    nacionalidad: 'Peruano', 
    tipoDocumento: 'DNI', 
    numeroDocumento: '' 
  };
  
  reserva = { 
    habitacionId: null as number | null, 
    fechaIngreso: '', 
    fechaSalida: '', 
    cantidadPersonas: 1,
    estado: 'Reservada' 
  };

  constructor(
    private habitacionService: HabitacionService, 
    private reservaService: ReservaService
  ) {}

  ngOnInit(): void {
    this.cargarHabitaciones();
    this.cargarReservas(); 
  }

  cargarHabitaciones() {
    this.habitacionService.listarHabitaciones().subscribe({
      next: (data) => { 
        
        this.habitaciones = data.filter(h => {
          const estado = h.estado ? h.estado.toLowerCase().trim() : 'disponible';
          return estado === 'disponible';
        }); 
      },
      error: (err) => { console.error('Error al cargar habitaciones', err); }
    });
  }

  cargarReservas() {
    this.reservaService.listarReservas().subscribe({
      next: (data) => {
        this.reservasLista = data;
      },
      error: (err) => { console.error('Error al listar reservas', err); }
    });
  }

  onHabitacionChange() {
    this.habitacionSeleccionada = this.habitaciones.find(h => String(h.id) === String(this.reserva.habitacionId));
    console.log('Habitación seleccionada:', this.habitacionSeleccionada);
  }

  get calcularSubtotal(): number {
    if (!this.habitacionSeleccionada || !this.reserva.fechaIngreso || !this.reserva.fechaSalida) {
      return 0;
    }
    const ingreso = new Date(this.reserva.fechaIngreso);
    const salida = new Date(this.reserva.fechaSalida);
    const diffTime = salida.getTime() - ingreso.getTime();
    
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

    const dias = (isNaN(diffDays) || diffDays <= 0) ? 1 : diffDays;
    const personas = Number(this.reserva.cantidadPersonas) || 1;
    const precioBase = Number(this.habitacionSeleccionada.precio) || 0;

    return precioBase * personas * dias;
  }

  get habitacionesFiltradas() {
    if (!this.filtroHabitacion) {
      return this.habitaciones;
    }
    const filtro = this.filtroHabitacion.toLowerCase();
    return this.habitaciones.filter(h => 
      h.tipo.toLowerCase().includes(filtro) || 
      h.numero.toString().includes(filtro) ||
      (h.descripcion && h.descripcion.toLowerCase().includes(filtro))
    );
  }

  validarEntradaDocumento(event: KeyboardEvent) {
    if (this.cliente.tipoDocumento === 'DNI') {
      const charCode = event.which ? event.which : event.keyCode;
      if (charCode < 48 || charCode > 57) {
        event.preventDefault();
      }
    }
  }

  limpiarDocumento() {
    this.cliente.numeroDocumento = '';
  }

  guardarReserva() {
    if (!this.reserva.habitacionId) {
      alert('Por favor, selecciona una habitación disponible válida.');
      return;
    }

    const payload = {
      ...this.cliente,
      habitacionId: this.reserva.habitacionId,
      fechaIngreso: this.reserva.fechaIngreso,
      fechaSalida: this.reserva.fechaSalida,
      cantidadPersonas: Number(this.reserva.cantidadPersonas) || 1,
      costoTotal: this.calcularSubtotal,
      estado: 'Reservada'
    };

    this.reservaService.crearReserva(payload).subscribe({
      next: () => {
        this.mensajeExito = '¡Reserva registrada con éxito y estado de habitación actualizado a Reservada!';
        setTimeout(() => { this.mensajeExito = null; }, 4000); 

        this.cliente = { nombre: '', apellido: '', nacionalidad: 'Peruano', tipoDocumento: 'DNI', numeroDocumento: '' };
        this.reserva = { habitacionId: null, fechaIngreso: '', fechaSalida: '', cantidadPersonas: 1, estado: 'Reservada' };
        this.habitacionSeleccionada = null;
        this.filtroHabitacion = '';
        
        this.cargarHabitaciones();
        this.cargarReservas(); 
      },
      error: (err) => {
        alert('Error al registrar la reserva: ' + (err.error?.message || err.error || err.message));
      }
    });
  }
}