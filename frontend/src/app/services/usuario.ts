import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Usuario {
  id?: number;
  username: string;
  password?: string;
  nombres: string;
  apellidos: string;
  email: string;
  activo?: boolean;
  roles: string[];
}

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = 'http://localhost:8080/api/usuarios';

  constructor(private http: HttpClient) {}

  listarUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.apiUrl);
  }

  registrarUsuario(usuario: Usuario): Observable<any> {
    return this.http.post(`${this.apiUrl}/registro`, usuario);
  }

  listarRoles(): Observable<any[]> {
    return this.http.get<any[]>('http://localhost:8080/api/roles');
  }

  eliminarLogico(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

}