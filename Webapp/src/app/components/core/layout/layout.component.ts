import { Component } from '@angular/core';
import { MapService } from 'src/app/services/core/map.service';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css']
})
export class LayoutComponent {

  constructor(private mapService: MapService) { }
}
