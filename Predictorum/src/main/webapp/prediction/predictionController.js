/**
 * 
 */
var predictionController = angular.module('predictorum.predictionController',
		[ 'predictorum.predictionService' ]);

predictionController.controller('predictionController', function($scope,
		$location, $timeout, predictionService) {

	// List

	$scope.orderProp = "predictionPosition";

	$scope.predictions = [ {
		homeTeam : 'R.Madrid',
		awayTeam : 'Barcelona',
		homeGoals : 2,
		awayGoals : 0,
		matchDate : '10/08/2015'
	}, {
		homeTeam : 'At.Madrid',
		awayTeam : 'Getafe',
		homeGoals : 3,
		awayGoals : 1,
		matchDate : '11/08/2015'
	} ];

	// Display

	$scope.prediction = {
		homeTeam : 'R.Madrid',
		awayTeam : 'Barcelona',
		matchDate : '10/08/2015',
		simple : '1',
		pSimple : 78,
		double : '1X',
		pDouble : 97,
		mt25 : true,
		pMT25 : 42,
		homeGoals : 2,
		pHome : 67,
		awayGoals : 0,
		pAway : 45,
		comments : [ {
			id : 1,
			user : 'david',
			text : 'Ronaldo is going to score',
			parent : null,
			children : [ {
				id : 3,
				user : 'andresin',
				text : 'He may not play',
				parent : 1
			}, {
				id : 4,
				user : 'david',
				text : 'He said he will yesterday',
				parent : 1
			} ]
		}, {
			id : 2,
			user : 'miguelin',
			text : 'R. Madrid is in a spree',
			parent : null,
			children: []
		}, {
			id : 3,
			user : 'andresin',
			text : 'He may not play',
			parent : 1,
			children: []
		}, {
			id : 4,
			user : 'david',
			text : 'He said he will yesterday',
			parent : 1,
			children: []
		} ]
	};

	$scope.showChildren = [];
	
	$scope.tab = {
		current : 'SIMPLE'
	};

	$scope.loading = false;

	$scope.switchTab = function(tab) {
		$scope.tab.current = tab;
		switch (tab) {
		case 'SIMPLE':
			$scope.switchData($scope.prediction.pSimple);
			break;
		case 'DOUBLE':
			$scope.switchData($scope.prediction.pDouble);
			break;
		case 'MT25':
			$scope.switchData($scope.prediction.pMT25);
			break;
		}
	}

	$scope.switchData = function(prob) {
		var probneg = 100 - prob;
		var data = [ {
			value : probneg,
			color : '#F7464A',
			highlight : '#FF5A5E',
		}, {
			value : prob,
			color : '#82dc23',
			highlight : '#B2E67A',
		}, ]
		$scope.data = data;
	}

	$scope.switchData($scope.prediction.pSimple);

	$scope.dataAway = [ {
		value : 100 - $scope.prediction.pAway,
		color : '#F7464A',
		highlight : '#FF5A5E',
	}, {
		value : $scope.prediction.pAway,
		color : '#82dc23',
		highlight : '#B2E67A',
	}, ];

	$scope.dataHome = [ {
		value : 100 - $scope.prediction.pHome,
		color : '#F7464A',
		highlight : '#FF5A5E',
	}, {
		value : $scope.prediction.pHome,
		color : '#82dc23',
		highlight : '#B2E67A',
	}, ];

	// Chart.js Options
	$scope.options = {

		// Sets the chart to be responsive
		responsive : true,

		// Boolean - Whether we should show a stroke on each segment
		segmentShowStroke : true,

		// String - The colour of each segment stroke
		segmentStrokeColor : '#fff',

		// Number - The width of each segment stroke
		segmentStrokeWidth : 2,

		// Number - The percentage of the chart that we cut out of the middle
		percentageInnerCutout : 40, // This is 0 for Pie charts

		// Number - Amount of animation steps
		animationSteps : 100,

		// String - Animation easing effect
		animationEasing : 'easeOutBounce',

		// Boolean - Whether we animate the rotation of the Doughnut
		animateRotate : true,

		// Boolean - Whether we animate scaling the Doughnut from the centre
		animateScale : false,

	};

});