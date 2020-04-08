import { Injectable } from '@angular/core';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';
import XYZ from 'ol/source/XYZ';

@Injectable({
  providedIn: 'root'
})
export class MapService {

  private layers: Map<string, TileLayer>;

  public get getLayers(): Map<string, TileLayer> {
    return this.layers;
  }

  constructor() {
    this.initialize();
  }

  private initialize(): void {
    this.layers = new Map<string, TileLayer>([
      ['Standardkarte', new TileLayer({
        source: new OSM()
      })],
      // ['Öffentliche Verkehrsmittel', new TileLayer({
      //   source: new XYZ({
      //     url: 'http://tile.memomaps.de/tilegen/{z}/{x}/{y}.png'
      //   })
      // })],
      // ['Dark Theme', new TileLayer({
      //   source: new XYZ({
      //     url: 'https://cartodb-basemaps-{s}.global.ssl.fastly.net/dark_all/{z}/{x}/{y}.png'
      //   })
      // })]
    ]);
  }
}
