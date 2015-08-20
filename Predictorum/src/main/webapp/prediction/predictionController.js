/**
 * 
 */
var predictionController = angular.module('predictorum.predictionController',
		[ 'predictorum.predictionService' ]);

predictionController.controller('predictionController', function($scope,
		$location, $timeout, $routeParams, $filter, $timeout, predictionService, sessionService) {

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
		
		if($location.path().includes('user')){
			predictionService.findUserPrediction($routeParams.predictionId).then(function(result){
				$scope.prediction = result.data;
				$scope.prediction.comments = [];
				$scope.switchData($scope.prediction.pSimpleResult);
				var principal = sessionService.getPrincipal();
				if($scope.prediction.username === principal.username){
					$scope.editPrediction = true;
				}
			});
		}else{
			predictionService.findSystemPrediction($routeParams.gameId).then(function(result){
				$scope.prediction = result.data;
				$scope.prediction.comments = [];
				$scope.switchData($scope.prediction.pSimpleResult);
				$scope.prediction.gameId= $routeParams.gameId;
			});
		}
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
		$scope.loadingComments = $scope.showComments;
		predictionService.findComments($scope.prediction.id).then(function(result){
			if(!result.data.errors){
				$timeout(function(){$scope.loadingComments = false},2000);
				$timeout(function(){$scope.prediction.comments = result.data},2000);
				$scope.showChildren = []; //sirve para guardar booleans para saber qué respuestas mostrar
				$scope.switchCommentOrder('MOST_POPULAR');
			}
		});
	}
	
	$scope.saveComment = function(){
		var commentForm ={};
		commentForm.text = $scope.myComment.text;
		if($scope.myComment.parent)
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
	
	//Form
	
	$scope.userPrediction = {};
	
	$scope.doubleResultCheck = function(resultClicked){
		if(resultClicked==='1'){
			$scope.userPrediction.doubleResult2 = !($scope.userPrediction.doubleResultX && $scope.userPrediction.doubleResult2)&&$scope.userPrediction.doubleResult2;
		}else if(resultClicked==='X'){
			$scope.userPrediction.doubleResult1 = !($scope.userPrediction.doubleResult1 && $scope.userPrediction.doubleResult2)&&$scope.userPrediction.doubleResult1;
		}else{
			$scope.userPrediction.doubleResult1 = !($scope.userPrediction.doubleResult1 && $scope.userPrediction.doubleResultX)&&$scope.userPrediction.doubleResult1;
		}
	};
	
	$scope.clearForm = function(input){
		switch(input){
		case 'simple':
			$scope.userPrediction.simpleResult = null;
			break;
		case 'double':
			$scope.userPrediction.doubleResult1=null;
			$scope.userPrediction.doubleResultX=null;
			$scope.userPrediction.doubleResult2=null;
			break;
		case 'result':
			$scope.userPrediction.homeGoals = null;
			$scope.userPrediction.awayGoals = null;
			break;
		case 'mt25':
			$scope.userPrediction.moreThan25 = null;
			break;
		}
	};
	
	if($location.path().includes('create')){
		predictionService.findPrincipalPrediction($routeParams.gameId).then(function(result){
			if(result.data.gameId !== null){
				$scope.userPrediction = result.data;
				if($scope.userPrediction.doubleResult){
					$scope.userPrediction.doubleResult1 = $scope.userPrediction.doubleResult.includes("1");
					$scope.userPrediction.doubleResultX = $scope.userPrediction.doubleResult.includes("X");
					$scope.userPrediction.doubleResult2 = $scope.userPrediction.doubleResult.includes("2");
				}
			}
			predictionService.findGame($routeParams.gameId).then(function(result){
				$scope.userPrediction.homeTeam = result.data.homeTeamName;
				$scope.userPrediction.awayTeam = result.data.awayTeamName;
			});
		});
	}
	
	$scope.savePrediction = function(){
		$scope.result = {};
		if($scope.userPrediction.homeGoals!==undefined || $scope.userPrediction.awayGoals!==undefined){ //si uno de los dos está cumplimentado
			if(!($scope.userPrediction.homeGoals!==undefined && $scope.userPrediction.awayGoals!==undefined)){ //ambos deben estarlo
				$scope.result.error = "MUST_FULFILL_BOTH_RESULT";
			}
		}
		if($scope.userPrediction.doubleResult1 || $scope.userPrediction.doubleResultX || $scope.userPrediction.doubleResult2){
			var pos1 = $scope.userPrediction.doubleResult1 && $scope.userPrediction.doubleResultX;
			var pos2 = $scope.userPrediction.doubleResult1 && $scope.userPrediction.doubleResult2;
			var pos3 = $scope.userPrediction.doubleResultX && $scope.userPrediction.doubleResult2;
			if(pos1){
				$scope.userPrediction.doubleResult = "1X";
			}else if(pos2){
				$scope.userPrediction.doubleResult = "12";
			}else if(pos3){
				$scope.userPrediction.doubleResult = "X2";
			}else{
				$scope.result.error = "MUST_FULFILL_BOTH_DOUBLE";
			}
		}
		if(!$scope.result.error){
			predictionService.savePrediction($scope.userPrediction, $routeParams.gameId).then(function(result){
				if(result.data.success){
					$location.path('/user/profile')
				}else{
					$scope.result.error = result.data.errors.fail;
				}
			});
		}
	}

});