import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { Routes, RouterModule } from '@angular/router';

import { PrimengModule } from '@shared/primeng/primeng.module';

import { DashboardComponent } from '@sefaz/view/dashboard/dashboard.component';
import { StatusPipe } from '@sefaz/pipe/status.pipe';
import { AutorizadoraStatusGridComponent } from '@sefaz/view/dashboard/autorizadora-status-grid/autorizadora-status-grid.component';
import { AutorizadoraTopIndisponibilidadeComponent } from '@sefaz/view/dashboard/autorizadora-top-indisponibilidade/autorizadora-top-indisponibilidade.component';
import { PorAutorizadorComponent } from '@sefaz/view/dashboard/por-autorizador/por-autorizador.component';

const routes: Routes = [
    {
        path: 'dashboard',
        component: DashboardComponent
    }
];

@NgModule({
    declarations: [
        DashboardComponent,
        StatusPipe,
        AutorizadoraStatusGridComponent,
        AutorizadoraTopIndisponibilidadeComponent,
        PorAutorizadorComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        HttpClientModule,
        BrowserAnimationsModule,
        ScrollingModule,
        PrimengModule,
        RouterModule.forChild(routes)
    ],
})
export class SefazModule { }
