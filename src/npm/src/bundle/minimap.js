import Map from 'ol/Map';
import View from 'ol/View';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';
import Draw from 'ol/interaction/Draw';
import VectorSource from 'ol/source/Vector';
import VectorLayer from 'ol/layer/Vector';
import {Circle, Fill, Style, Stroke, Text} from 'ol/style';
import Point from 'ol/geom/Point';
import Feature from 'ol/Feature';

/*
 * Default coordinate projection is EPSG:3857, positions are EPSG:4326 so we
 * need to convert
 */
if (window.document.showMiniMaps) {
	
	const anzahlMinimaps = window.document.anzahlMinimaps;
	
	for(let i = 0; i < anzahlMinimaps; i++) {
		const vectorSource = new VectorSource();
		const vectorLayer = new VectorLayer({
			source: vectorSource
		});
		const raster = new TileLayer({source: new OSM()});
		
		const map = new Map({
			layers: [raster, vectorLayer],
			target: 'map_' + i,
			interactions: [],
			controls: [],
			view: new View({
				center: [1115000.0, 7090000.0],
				zoom: 13,
				maxZoom: 16
			})
		});
		
		const coords = window.document.coords[i];
		const geometry = new Point(coords);
		geometry.transform('EPSG:4326', 'EPSG:3857');
		
		const feature = new Feature(geometry);
		const featureLabel = '' + (i + 1);
		feature.setStyle(new Style({
	        image: new Circle({
	            radius: 7,
	            fill: new Fill({color: 'blue'}),
	        	stroke: new Stroke({color: 'blue'})
	        }),
	        text: new Text({
	        	text: featureLabel,
	        	fill: new Fill({color: 'white'})
	        })
	    }));
		vectorSource.addFeature(feature);
		map.getView().fit(geometry.getExtent(), map.getSize());
	}
}
