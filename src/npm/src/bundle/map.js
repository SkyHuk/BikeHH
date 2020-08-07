import Map from 'ol/Map';
import View from 'ol/View';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';

if (window.document.showMap) {
	var map = new Map({
		target: 'map',
		layers: [new TileLayer({
			source: new OSM()
		})],
		view: new View({
			center: [1115000.0, 7090000.0],
			zoom: 13
		})
	});
}
