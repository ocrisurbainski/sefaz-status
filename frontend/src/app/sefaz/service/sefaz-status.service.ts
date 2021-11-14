import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class SefazStatusService {

    private urlService = 'http://localhost:8080/v1/servico-status';

    constructor(private http: HttpClient) { }

    findAll(): Observable<Map<string, object>> {
        return this.http.get<Map<string, object>>(this.urlService);
    }

    findByAutorizador(autorizador: string): Observable<Map<string, object>> {
        return this.http.get<Map<string, object>>(`${this.urlService}/${autorizador}`);
    }

    topIndisponibilidade(): Observable<string> {
        return this.http.get<string>(`${this.urlService}/top/indisponibilidade`);
    }

}
