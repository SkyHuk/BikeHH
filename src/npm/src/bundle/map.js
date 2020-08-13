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
 * Default coordinate projection is EPSG:3857
 */

if (window.document.showMap) {
	let currentInteraction;
	
	const vectorSource = new VectorSource();
	const vectorLayer = new VectorLayer({
		source: vectorSource
	});
	const raster = new TileLayer({source: new OSM()});
	
	const map = new Map({
		layers: [raster, vectorLayer],
		target: 'map',
		view: new View({
			center: [1115000.0, 7090000.0],
			zoom: 13,
			maxZoom: 19
		})
	});
	
	window.document.removeBefragungsOrt = function(befragungsIndex) {
		// TODO Ort von Befragung entfernen
		if(currentInteraction) { removeInteraction(map, currentInteraction); }
		currentInteraction = null;
	}
	
	window.document.addBefragungsOrt = function(befragungsIndex) {
		window.document.removeBefragungsOrt(befragungsIndex);
		if(currentInteraction) { removeInteraction(map, currentInteraction); }
		
		currentInteraction = new Draw({
			source: vectorSource,
			type: 'Point',
			style: new Style({
		        image: new Circle({
		            radius: 7,
		            fill: new Fill({color: 'blue'}),
		            stroke: new Stroke({color: 'blue'})
		        }),
		    })
		});
		
		currentInteraction.on('drawend', function(event) {
			const feature = event.feature;
			
			const featureLabel = '' + (befragungsIndex + 1);
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
			
			const geometry = feature.getGeometry();
			const coordinates = geometry.getCoordinates();
			
			const longitudeField = document.getElementById('longitude_' + befragungsIndex);
			const latitudeField = document.getElementById('latitude_' + befragungsIndex);
			longitudeField.value = coordinates[0].toFixed(2);
			latitudeField.value = coordinates[1].toFixed(2);
			
			// Zoom map
			map.getView().fit(geometry.getExtent(), map.getSize());
			
			removeInteraction(map, currentInteraction);
		});
		
		addInteraction(map, currentInteraction);
	}
	
	const befragungenCountField = document.getElementById('befragungen_count');
	if(befragungenCountField.value) {
		// Beim Laden der Seite existieren bereits Positionen.
		// Features müssen erstellt werden
		vectorSource.clear();
		const befragungenCount = befragungenCountField.value;
		for(let i = 0; i < befragungenCount; i++) {
			const longitude = document.getElementById('longitude_' + i).value;
			const latitude = document.getElementById('latitude_' + i).value;
			
			if(!longitude) { continue; }
			
			const feature = new Feature(new Point([longitude, latitude]));
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
		}
	}
}

function addInteraction(map, interaction) {
	map.addInteraction(interaction);
}

function removeInteraction(map, interaction) {
	map.removeInteraction(interaction);
}
