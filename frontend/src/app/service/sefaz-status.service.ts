import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class SefazStatusService {

  private urlService : string = 'http://localhost:8080/v1/servico-status';

  constructor(private http: HttpClient) { }

  findAll() {
    return this.http.get<Map<string, Object>>(this.urlService);
  }

  findByAutorizador(autorizador: string) {
    return this.http.get<Map<string, Object>>(this.urlService + '/' + autorizador);
  }

  topIndisponibilidade() {
    return this.http.get<string>(this.urlService + '/top/indisponibilidade')
  }

}
