import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { SelectItem } from 'primeng/api';
import { SefazStatusService } from '@sefaz/service/sefaz-status.service';

@Component({
    selector: 'app-por-autorizador',
    templateUrl: './por-autorizador.component.html'
})
export class PorAutorizadorComponent implements OnChanges {

    @Input()
    listAutorizadora: string[];

    dropdownAutorizadora: SelectItem[] = [];
    autorizadoraSelecionada: string = undefined;
    listaAutorizadoraData: object[] = [];

    constructor(private service: SefazStatusService) { }

    ngOnChanges(changes: SimpleChanges): void {
        const listAutorizadora = changes && changes[`listAutorizadora`];

        if (listAutorizadora && listAutorizadora.currentValue) {
            this.mountDropdownAutorizador(listAutorizadora.currentValue);

            this.autorizadoraSelecionada = listAutorizadora.currentValue[0];

            this.onChangeAutorizador();
        }
    }

    mountDropdownAutorizador(listAutorizadora: string[] = []): void {

        listAutorizadora.forEach((autorizador) => {

            this.dropdownAutorizadora.push({
                label: autorizador,
                value: autorizador
            });
        });
    }

    onChangeAutorizador(): void {

        this.listaAutorizadoraData = [];

        if (!this.autorizadoraSelecionada) {
            return;
        }

        this.service.findByAutorizador(this.autorizadoraSelecionada).subscribe(map => {

            const properties = Object.getOwnPropertyNames(map[this.autorizadoraSelecionada]);

            properties.forEach((property) => {
                this.listaAutorizadoraData.push({
                    col0: property,
                    col1: map[this.autorizadoraSelecionada][property]
                });
            });
        });
    }

}
