import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { UsuarioService } from '../../services/usuario'; 

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.css']
})
export class UsuariosComponent implements OnInit {
  rolesDisponibles: any[] = [];  
  usuarios: any[] = [];
  mostrarFormulario: boolean = false;
  successMessage: string = '';
  errorMessage: string = '';

  nuevoUsuario = {
    username: '',
    password: '',
    nombres: '',
    apellidos: '',
    email: '',
    roles: []
  };

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit() {
  this.cargarUsuarios();
  this.cargarRoles();
}

cargarRoles() {
  this.usuarioService.listarRoles().subscribe({
    next: (data) => { this.rolesDisponibles = data; },
    error: (err) => { console.error('Error al cargar roles', err); }
  });
}


  cargarUsuarios() {
    this.usuarioService.listarUsuarios().subscribe({
      next: (data) => {
        this.usuarios = data;
      },
      error: (err) => {
        this.errorMessage = 'Error al cargar usuarios desde la base de datos.';
        console.error(err);
      }
    });
  }

  toggleFormulario() {
    this.mostrarFormulario = !this.mostrarFormulario;
  }


  registrar(form: NgForm) {
    if (form.valid) {
      
      const datosAEnviar = {
        ...this.nuevoUsuario,
        roles: Array.isArray(this.nuevoUsuario.roles) 
          ? this.nuevoUsuario.roles 
          : [this.nuevoUsuario.roles]
      };

      this.usuarioService.registrarUsuario(datosAEnviar).subscribe({
        next: (response) => {
          this.successMessage = 'Usuario registrado exitosamente en la base de datos.';
          this.mostrarFormulario = false;
          form.resetForm();
          this.cargarUsuarios(); 
        },
        error: (err) => {
          this.errorMessage = 'Error al registrar el usuario en el servidor.';
          console.error(err);
        }
      });
    }
  }

  eliminarLogico(id: number) {
    if (window.confirm('¿Estás seguro de eliminar lógicamente este usuario?')) {
      this.usuarioService.eliminarLogico(id).subscribe({
        next: (response) => {
          this.successMessage = 'Usuario eliminado lógicamente de forma correcta.';
          this.cargarUsuarios(); 
        },
        error: (err) => {
          this.errorMessage = 'Error al procesar la eliminación del usuario.';
          console.error(err);
        }
      });
    }
  }

  
  abrirModalEditar(usuario: any) {
    console.log('Editar usuario seleccionado:', usuario);

  }
}
