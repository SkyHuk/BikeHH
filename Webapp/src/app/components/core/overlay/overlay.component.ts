import { Component } from '@angular/core';
import { MatBottomSheet } from '@angular/material/bottom-sheet';
import { LayersheetComponent } from '../layersheet/layersheet.component';

@Component({
  selector: 'app-overlay',
  templateUrl: './overlay.component.html',
  styleUrls: ['./overlay.component.css']
})
export class OverlayComponent {

  constructor(private bottomSheet: MatBottomSheet) { }

  openLayerSelection(): void {
    this.bottomSheet.open(LayersheetComponent);
  }
}
