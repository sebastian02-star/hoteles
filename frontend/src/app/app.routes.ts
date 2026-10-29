import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login';
import { DashboardComponent } from './components/dashboard/dashboard';
import { InicioComponent } from './components/inicio/inicio.component';
import { UsuariosComponent } from './components/usuarios/usuarios.component';
import { HabitacionesComponent } from './components/habitaciones/habitaciones';
import { ReservaComponent } from './components/reserva/reserva.component';


export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  
  { 
    path: 'dashboard', 
    component: DashboardComponent,
    children: [
      { path: 'inicio', component: InicioComponent },
      { path: 'usuarios', component: UsuariosComponent },
      { path: 'reservas', component: ReservaComponent },
      { path: '', redirectTo: 'inicio', pathMatch: 'full' }

    ]
  },
  
  { path: 'habitaciones', component: HabitacionesComponent },

  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' }
];