import { Component } from '@angular/core';
import { MatBottomSheetRef } from '@angular/material/bottom-sheet';
import { MapService } from 'src/app/services/core/map.service';


@Component({
  selector: 'app-layersheet',
  templateUrl: './layersheet.component.html',
  styleUrls: ['./layersheet.component.css']
})
export class LayersheetComponent {

  selectedLayerName: string;

  constructor(private bottomSheetRef: MatBottomSheetRef<LayersheetComponent>, private mapService: MapService) {
    this.selectedLayerName = 'default';
  }

  selectLayer(selectedLayer: string): void {
    this.selectedLayerName = selectedLayer;
    this.mapService.selectLayerSource(selectedLayer);
  }
}
