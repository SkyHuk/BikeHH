import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './modules/app-routing.module';
import { MaterialModule } from './modules/material.module';

import { MapComponent } from './components/map/map.component';
import { LayoutComponent } from './components/layout/layout.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { LeftSidenavComponent } from './components/left-sidenav/left-sidenav.component';

@NgModule({
  declarations: [
    MapComponent,
    LayoutComponent,
    NavbarComponent,
    LeftSidenavComponent
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
