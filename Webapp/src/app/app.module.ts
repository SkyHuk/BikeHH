import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './modules/app-routing.module';
import { MaterialModule } from './modules/material.module';
import { MapComponent } from './components/map/map.component';
import { LayoutComponent } from './components/layout/layout.component';

@NgModule({
  declarations: [
    MapComponent,
    LayoutComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [LayoutComponent]
})
export class AppModule { }
