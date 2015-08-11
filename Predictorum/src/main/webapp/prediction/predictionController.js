/**
 * 
 */
var predictionController = angular.module('predictorum.predictionController',
		[ 'predictorum.predictionService' ]);

predictionController.controller('predictionController', function($scope,
		$location, $timeout, $routeParams, predictionService) {

	// List
	

	$scope.switchTab = function(tab) {
		$scope.tab.current = tab;

	};

	$scope.orderProp = "predictionPosition";

	if($location.path().includes('upcoming')){
		predictionService.findUpcoming().then(function(result) {
			$scope.predictions = result.data;
			$scope.switchTab('');
		});
	}

	// Display
	
	$scope.prediction = {};
	
	/*$scope.prediction = {
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
		comments = [ {
			id : 1,
			user : 'david',
			text : 'Ronaldo is going to score',
			parent : null,
			evaluated : 1,
			children : [ {
				id : 3,
				user : 'andresin',
				text : 'He may not play',
				parent : 1,
				evaluated : 2,
			}, {
				id : 4,
				user : 'david',
				text : 'He said he will yesterday',
				parent : 1,
				evaluated : 3,
			} ]
		}, {
			id : 2,
			user : 'miguelin',
			text : 'R. Madrid is in a spree',
			parent : null,
			children : [],
			evaluated : 1,
		}, {
			id : 3,
			user : 'andresin',
			text : 'He may not play',
			parent : 1,
			evaluated : 2,
			children : []
		}, {
			id : 4,
			user : 'david',
			text : 'He said he will yesterday',
			parent : 1,
			evaluated : 3,
			children : []
		} ]
	};*/

	$scope.showChildren = [];

	$scope.tab = {
		current : 'SIMPLE'
	};

	$scope.loading = false;

	$scope.switchTab = function(tab) {
		$scope.tab.current = tab;
		$scope.dataAway = [];
		$scope.dataHome = [];
		switch (tab) {
		case 'SIMPLE':
			$scope.switchData($scope.prediction.pSimpleResult);
			break;
		case 'DOUBLE':
			$scope.switchData($scope.prediction.pDoubleResult);
			break;
		case 'MT25':
			$scope.switchData($scope.prediction.pmoreThan25);
			break;
		case 'RESULT':
			$scope.dataAway = [ {
				value : 100 - $scope.prediction.pAwayGoals,
				color : '#F7464A',
				highlight : '#FF5A5E',
			}, {
				value : $scope.prediction.pAwayGoals,
				color : '#82dc23',
				highlight : '#B2E67A',
			}, ];

			$scope.dataHome = [ {
				value : 100 - $scope.prediction.pHomeGoals,
				color : '#F7464A',
				highlight : '#FF5A5E',
			}, {
				value : $scope.prediction.pHomeGoals,
				color : '#82dc23',
				highlight : '#B2E67A',
			}, ];
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
	
	if($location.path().includes('details')){
		predictionService.findSystemPrediction($routeParams.gameId).then(function(result){
			$scope.prediction = result.data;
			$scope.switchData($scope.prediction.pSimpleResult);
			
		});
	}

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