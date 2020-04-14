import { AfterViewInit, Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import Map from 'ol/Map';
import View from 'ol/View';
import TileLayer from 'ol/layer/Tile';
import TileSource from 'ol/source/Tile';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements AfterViewInit, OnChanges {

  private map: Map;
  private layer: TileLayer;
  @Input() layerSource: TileSource;

  constructor() {
    this.layer = new TileLayer({ source: this.layerSource });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.layerSource) {
      this.layer.setSource(this.layerSource);
    }
  }

  ngAfterViewInit(): void {
    this.map = new Map({
      target: 'map',
      layers: [this.layer],
      view: new View({
        center: [1115000.0, 7090000.0],
        zoom: 13
      })
    });
  }

}
