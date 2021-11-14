import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-autorizadora-status-grid',
  templateUrl: './autorizadora-status-grid.component.html'
})
export class AutorizadoraStatusGridComponent implements OnChanges  {

    @Input()
    dados: object;

    @Input()
    listAutorizadora: string[] = [];

    listStatusAllHead: string[] = [];
    listStatusAllData: object[] = [];

    constructor() { }

    ngOnChanges(changes: SimpleChanges): void {
        const dados = changes && changes[`dados`];
        const listAutorizadora = changes && changes[`listAutorizadora`];

        if (dados && dados.currentValue && listAutorizadora && listAutorizadora.currentValue) {
            this.mountListStatusAllHead(dados.currentValue, listAutorizadora.currentValue);
            this.mountListStatusAll(dados.currentValue, listAutorizadora.currentValue);
        }
    }

    mountListStatusAll(map: object, listAutorizadora: string[] = []): void {

        this.listStatusAllData = [];

        listAutorizadora.forEach((autorizadora, index) => {

            const dataAutorizadora = map[autorizadora];
            const data = {};

            data[`col0`] = autorizadora;

            let cod = 1;

            this.listStatusAllHead.forEach((servico, statusAllHeadIndex) => {

                if (statusAllHeadIndex === 0) {
                    return;
                }

                let value: string = dataAutorizadora[servico];

                if (value === undefined || value === null) {

                    value = '';
                }

                data[`col${cod}`] = value;

                cod++;
            });

            this.listStatusAllData.push(data);
        });
    }

    mountListStatusAllHead(map: object, listAutorizadora: string[] = []): void {

        this.listStatusAllHead = [];
        this.listStatusAllHead.push('Autorizadoras');

        listAutorizadora.forEach((autorizadora) => {

            const dataAutorizadora = map[autorizadora];
            const properties: string[] = Object.getOwnPropertyNames(dataAutorizadora);

            properties.forEach((property) => {

                if (this.listStatusAllHead.indexOf(property) < 0) {
                    this.listStatusAllHead.push(property);
                }
            });
        });
    }

}
