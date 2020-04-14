import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './modules/app-routing.module';
import { MaterialModule } from './modules/material.module';

import { MapComponent } from './components/core/map/map.component';
import { LayoutComponent } from './components/core/layout/layout.component';
import { NavbarComponent } from './components/core/navbar/navbar.component';
import { LeftSidenavComponent } from './components/core/left-sidenav/left-sidenav.component';
import { OverlayComponent } from './components/core/overlay/overlay.component';
import { LayersheetComponent } from './components/core/layersheet/layersheet.component';

@NgModule({
  declarations: [
    MapComponent,
    LayoutComponent,
    NavbarComponent,
    LeftSidenavComponent,
    OverlayComponent,
    LayersheetComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [LayoutComponent]
})
export class AppModule { }