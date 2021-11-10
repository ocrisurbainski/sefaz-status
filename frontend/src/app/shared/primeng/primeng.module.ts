import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CardModule } from 'primeng/card';
import { TableModule } from 'primeng/table';
import { DropdownModule } from 'primeng/dropdown';

@NgModule({
    declarations: [],
    imports: [
        CommonModule,
        CardModule,
        TableModule,
        DropdownModule,
    ],
    exports: [
        CardModule,
        TableModule,
        DropdownModule,
    ]
})
export class PrimengModule { }
