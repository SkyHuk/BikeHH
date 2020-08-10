import Map from 'ol/Map';
import View from 'ol/View';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';
import Draw from 'ol/interaction/Draw';
import VectorSource from 'ol/source/Vector';
import VectorLayer from 'ol/layer/Vector';
import {Circle, Fill, Style} from 'ol/style';
import Point from 'ol/geom/Point';
import Feature from 'ol/Feature';

/*
 * Default coordinate projection is EPSG:3857
 */

if (window.document.showMap) {
	const longitudeField = document.getElementById('longitude');
	const latitudeField = document.getElementById('latitude');
	
	const addOrtBtn = document.getElementById('mapAddOrtBtn');
	const resetOrtBtn = document.getElementById('resetOrtBtn');
	
	const raster = new TileLayer({source: new OSM()});
	const vectorSource = new VectorSource({wrapX: false});
	const vectorLayer = new VectorLayer({
		source: vectorSource,
		style: new Style({
	        image: new Circle({
	          radius: 5,
	          fill: new Fill({color: 'blue'}),
	        }),
	    }),
	});
	
	const draw = new Draw({
		source : vectorSource,
		type: 'Point'
	});
	
	const map = new Map({
		layers: [raster, vectorLayer],
		target: 'map',
		view: new View({
			center: [1115000.0, 7090000.0],
			zoom: 13,
			maxZoom: 19
		})
	});
	
	const addInteraction = function(map) {
		map.addInteraction(draw);
	}
	
	const removeInteraction = function(map) {
		map.removeInteraction(draw);
	}
	
	const clearOrt = function() {
		vectorSource.clear();
		longitudeField.value = 0;
		latitudeField.value = 0;
	}
	
	if(addOrtBtn) {
		// Ist der Ort hinzufügen Button vorhanden, sollte die Map auf
		// Interaktionen reagieren
		
		addOrtBtn.onclick = function() {
			clearOrt();
			addInteraction(map);
		};
		
		vectorSource.on('addfeature', function(event){
			const geometry = event.feature.getGeometry();
			const coordinates = geometry.getCoordinates();
			
			longitudeField.value = coordinates[0].toFixed(2);
			latitudeField.value = coordinates[1].toFixed(2);
			
			// Zoom map
			map.getView().fit(geometry.getExtent(), map.getSize());
			
			removeInteraction(map);
		});
	}
	
	if(resetOrtBtn) {
		resetOrtBtn.onclick = function() {
			clearOrt();
			removeInteraction(map);
		};
	}
	
	if(longitudeField.value) {
		const longitude = longitudeField.value;
		const latitude = latitudeField.value;
		const feature = new Feature(new Point([longitude, latitude]));
		vectorSource.addFeature(feature);
		map.getView().fit(feature.getGeometry().getExtent(), map.getSize());
	}
	
}

