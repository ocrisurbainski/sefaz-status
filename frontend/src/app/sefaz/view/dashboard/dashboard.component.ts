import { Component, OnInit } from '@angular/core';
import {SefazStatusService} from '@sefaz/service/sefaz-status.service';
import { SelectItem } from 'primeng/api';

@Component({
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.sass']
})
export class DashboardComponent implements OnInit {

    listAutorizadora : string[] = [];
    listStatusAllHead : string[] = [];
    listStatusAllData : Object[] = [];
    autorizadoraTopDisponibilidade : string = undefined;

    dropdownAutorizadora : SelectItem[] = [];
    autorizadoraSelecionada : string = undefined;
    listaAutorizadoraData : Object[] = [];

    constructor(private service : SefazStatusService) { }

    ngOnInit(): void {
        this.service.findAll().subscribe({
            next: (map) => {
                this.mountListAutorizador(map);
                this.mountListStatusAll(map);
                this.mountDropdownAutorizador();

                this.autorizadoraSelecionada = this.listAutorizadora[0];

                this.onChangeAutorizador(undefined);
            }
        });

        this.service.topIndisponibilidade().subscribe({
            next: (values) => {
                this.autorizadoraTopDisponibilidade = values && values['autorizadora'];

                if (!this.autorizadoraTopDisponibilidade) {

                    this.autorizadoraTopDisponibilidade = 'Nenhuma autorizadora ficou indisponível hoje!';
                }
            }
        });
    }

    mountListAutorizador(map : Object) {

        this.listAutorizadora = Object.getOwnPropertyNames(map);

        var indexOfData = this.listAutorizadora.indexOf("data");

        if (indexOfData >= 0) {

            this.listAutorizadora.splice(indexOfData, 1);
        }
    }

    mountListStatusAll(map : Object) {

        this.mountListStatusAllHead(map);

        for (var i = 0; i < this.listAutorizadora.length; i++) {

            var autorizadora : string = this.listAutorizadora[i];
            var object = map[autorizadora];

            var data = {};
            data['col0'] = autorizadora;

            var cod = 1;

            for (var k = 1; k < this.listStatusAllHead.length; k++) {

                var servico : string = this.listStatusAllHead[k];

                var value : string = object[servico];

                if (value === undefined || value === null) {

                    value = "";
                }

                data['col' + cod] = value;

                cod++;
            }

            this.listStatusAllData.push(data);
        }
    }

    mountDropdownAutorizador() {

        for (var i = 0; i < this.listAutorizadora.length; i++) {

            var autorizador = this.listAutorizadora[i];

            this.dropdownAutorizadora.push({
                'label': autorizador,
                'value': autorizador
            });
        }
    }

    mountListStatusAllHead(map : Object) {

        this.listStatusAllHead.push('Autorizadoras');

        for (var i = 0; i < this.listAutorizadora.length; i++) {

            var autorizadora : string = this.listAutorizadora[i];
            var object = map[autorizadora];

            var properties : string[] = Object.getOwnPropertyNames(object);

            for (var k = 0; k < properties.length; k++) {

                var property : string = properties[k];

                if (this.listStatusAllHead.indexOf(property) >= 0) {
                    continue;
                }

                this.listStatusAllHead.push(property);
            }
        }
    }

    onChangeAutorizador(event) {

        this.listaAutorizadoraData = [];

        if (!this.autorizadoraSelecionada) {
            return;
        }

        this.service.findByAutorizador(this.autorizadoraSelecionada).subscribe(map => {

            var properties = Object.getOwnPropertyNames(map[this.autorizadoraSelecionada]);

            for (var i = 0; i < properties.length; i++) {

                this.listaAutorizadoraData.push({
                    'col0': properties[i],
                    'col1': map[this.autorizadoraSelecionada][properties[i]]
                })
            }
        })
    }

}
