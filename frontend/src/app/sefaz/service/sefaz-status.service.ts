import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class SefazStatusService {

    private urlService: string = 'http://localhost:8080/v1/servico-status';

    constructor(private http: HttpClient) { }

    findAll(): Observable<Map<string, Object>> {
        return this.http.get<Map<string, Object>>(this.urlService);
    }

    findByAutorizador(autorizador: string): Observable<Map<string, Object>> {
        return this.http.get<Map<string, Object>>(`${this.urlService}/${autorizador}`);
    }

    topIndisponibilidade(): Observable<string> {
        return this.http.get<string>(`${this.urlService}/top/indisponibilidade`);
    }

}
