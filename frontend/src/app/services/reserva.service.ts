
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReservaService {
  private apiUrl = 'http://localhost:8080/api/reservas';

  constructor(private http: HttpClient) {}

  listarReservas(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  crearReserva(reservaData: any): Observable<any> {
    return this.http.post(this.apiUrl, reservaData);
  }
}