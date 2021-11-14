import { Component, OnInit } from '@angular/core';
import { SefazStatusService } from '@sefaz/service/sefaz-status.service';

@Component({
    selector: 'app-autorizadora-top-indisponibilidade',
    templateUrl: './autorizadora-top-indisponibilidade.component.html'
})
export class AutorizadoraTopIndisponibilidadeComponent implements OnInit {

    autorizadoraTopDisponibilidade: string = undefined;

    constructor(private service: SefazStatusService) { }

    ngOnInit(): void {
        this.service.topIndisponibilidade().subscribe({
            next: (values) => {
                this.autorizadoraTopDisponibilidade = values && values[`autorizadora`];

                if (!this.autorizadoraTopDisponibilidade) {

                    this.autorizadoraTopDisponibilidade = 'Nenhuma autorizadora ficou indisponível hoje!';
                }
            }
        });
    }

}
