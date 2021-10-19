import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'status'
})
export class StatusPipe implements PipeTransform {

    transform(value: unknown, ...args: unknown[]): unknown {
        if (value === 'VERDE') {
            return 'Ok';
        } else if (value === 'AMARELO') {
            return 'Atenção';
        } else if (value === 'VERMELHO') {
            return 'Problema';
        }
        return value;
    }

}
