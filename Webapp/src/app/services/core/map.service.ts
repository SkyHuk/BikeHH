import { Injectable } from '@angular/core';
import OSM from 'ol/source/OSM';
import TileSource from 'ol/source/Tile';
import XYZ from 'ol/source/XYZ';


@Injectable({
  providedIn: 'root'
})
export class MapService {
  private layers: Map<string, TileSource>;
  private selectedLayer: TileSource;

  public get getLayers(): Map<string, TileSource> {
    return this.layers;
  }

  public get getSelectedLayerSource(): TileSource {
    return this.selectedLayer;
  }

  constructor() {
    this.initialize();
  }

  public selectLayerSource(layerName: string): void {
    this.selectedLayer = this.layers.has(layerName) ? this.layers.get(layerName) : this.layers.get('default');
  }

  private initialize(): void {
    this.layers = new Map<string, TileSource>([
      ['default', new OSM()],
      ['opnv', new XYZ({
        url: 'http://tile.memomaps.de/tilegen/{z}/{x}/{y}.png'
      })],
      ['satellite', new XYZ({
        attributions: ['Powered by Esri'],
        attributionsCollapsible: false,
        url: 'https://services.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}',
        maxZoom: 23
      })]
    ]);
    this.selectedLayer = this.layers.get('default');
  }

}
