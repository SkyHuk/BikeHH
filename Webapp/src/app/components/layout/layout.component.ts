import { Component, ViewChild } from '@angular/core';
import { LeftSidenavComponent } from '../left-sidenav/left-sidenav.component';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css']
})
export class LayoutComponent {

  @ViewChild(LeftSidenavComponent) sidenav: LeftSidenavComponent;

  toggleSidenav(): void {
    this.sidenav.toggle();
  }

}
