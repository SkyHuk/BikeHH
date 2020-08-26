if (window.document.showAntwortChart) {
	const fragenCountField = document.getElementById('fragenCount');
	if (fragenCountField.value) {
		const fragenCount = fragenCountField.value;
		for(let i = 0; i < fragenCount; i++) {
			const antworten = window.document.antworten[i];
			const werte = window.document.werte[i];
			
			var chartCanvas = document.getElementById('beantwortung_chart_' + i).getContext('2d');
			var chart = new Chart(chartCanvas, {
			    type: 'horizontalBar',
			    data: {
			        labels: antworten,
			        datasets: [{
			        	label: 'Anzahl Antworten',
			            data: werte,
			            backgroundColor: [
			                'rgba(255, 99, 132, 0.2)',
			                'rgba(54, 162, 235, 0.2)',
			                'rgba(255, 206, 86, 0.2)',
			                'rgba(75, 192, 192, 0.2)',
			                'rgba(153, 102, 255, 0.2)',
			                'rgba(255, 159, 64, 0.2)'
			            ],
			            borderColor: [
			                'rgba(255, 99, 132, 1)',
			                'rgba(54, 162, 235, 1)',
			                'rgba(255, 206, 86, 1)',
			                'rgba(75, 192, 192, 1)',
			                'rgba(153, 102, 255, 1)',
			                'rgba(255, 159, 64, 1)'
			            ],
			            borderWidth: 1
			        }]
			    },
			    options: {
			        scales: {
			            xAxes: [{
			                ticks: {
			                    beginAtZero: true
			                }
			            }]
			        }
			    }
			});
		}
	}
}
