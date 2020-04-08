import { Component, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';

@Component({
  selector: 'app-left-sidenav',
  templateUrl: './left-sidenav.component.html',
  styleUrls: ['./left-sidenav.component.css']
})
export class LeftSidenavComponent {

  @ViewChild(MatSidenav) sidenav: MatSidenav;

  public toggle(): void {
    this.sidenav.toggle();
  }

}
