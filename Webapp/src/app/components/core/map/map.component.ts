import { AfterViewInit, Component } from '@angular/core';
import Map from 'ol/Map';
import View from 'ol/View';
import { MapService } from 'src/app/services/core/map.service';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements AfterViewInit {

  private map: Map;

  constructor(private mapService: MapService) {
  }

  ngAfterViewInit(): void {
    this.map = new Map({
      target: 'map',
      layers: Array.from(this.mapService.getLayers.values()),
      view: new View({
        center: [1115000.0, 7090000.0],
        zoom: 13
      })
    });
  }

}
