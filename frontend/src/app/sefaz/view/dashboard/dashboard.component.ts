import { Component, OnInit } from '@angular/core';
import { SefazStatusService } from '@sefaz/service/sefaz-status.service';

@Component({
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.sass']
})
export class DashboardComponent implements OnInit {

    dados: object;

    listAutorizadora: string[] = [];

    constructor(private service: SefazStatusService) { }

    ngOnInit(): void {
        this.service.findAll().subscribe({
            next: (dados) => {
                this.mountListAutorizador(dados);

                this.dados = dados;
            }
        });
    }

    mountListAutorizador(dados: object): void {

        this.listAutorizadora = Object.getOwnPropertyNames(dados);

        const indexOfData = this.listAutorizadora.indexOf(`data`);

        if (indexOfData >= 0) {

            this.listAutorizadora.splice(indexOfData, 1);
        }
    }

}
