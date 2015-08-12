/**
 * 
 */
var predictionController = angular.module('predictorum.predictionController',
		[ 'predictorum.predictionService' ]);

predictionController.controller('predictionController', function($scope,
		$location, $timeout, $routeParams, $filter, predictionService) {

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
	$scope.myComment = {};
	
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
			$scope.prediction.comments = [];
			$scope.switchData($scope.prediction.pSimpleResult);
			
		});
	}
	
	
	$scope.switchCommentOrder = function(tab){
		$scope.commentTab = tab;
		if(tab==='MOST_RECENT'){
			$scope.commentOrder = '-date';
		}else{
			$scope.commentOrder = '-posPoints';
		}
	}
	
	$scope.findComments = function(){
		$scope.showComments = !$scope.showComments;
		predictionService.findComments($scope.prediction.id).then(function(result){
			if(!result.data.errors){
				$scope.prediction.comments = result.data;
				$scope.showChildren = []; //sirve para guardar booleans para saber qué respuestas mostrar
				$scope.switchCommentOrder('MOST_RECENT');
			}
		});
	}
	
	$scope.saveComment = function(){
		var commentForm ={};
		commentForm.text = $scope.myComment.text;
		commentForm.parentId = $scope.myComment.parent.id;
		commentForm.predictionId = $scope.prediction.id;
		predictionService.saveComment(commentForm).then(function(result){
			if(result.data.id){
				$scope.myComment = {}; //vaciamos lo escrito
				$scope.prediction.comments.push(result.data);
				$scope.prediction.commentSize+=1;
			}
		});
	}
	
	$scope.evaluateComment = function(comment, boolean){
		predictionService.evaluateComment(comment.id,boolean).then(function(result){
			if(result.data.success){
				if(boolean){
					comment.evaluated = true;
					comment.posPoints+=1;
				}else{
					comment.evaluated = false;
					comment.negPoints+=1;
				}
			}
		});
	};

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