/**
 * 
 */

var actorController = angular.module('predictorum.actorController',
		[ 'predictorum.actorService', 'predictorum.sessionService' ]);

actorController.controller("actorController", function($scope, $location,$interval, actorService, sessionService) {

	if($location.path().includes('followers')){
		$scope.tab = {
				current : 'followers'
		};
	}else if($location.path().includes('following')){
		$scope.tab = {
				current : 'following'
		};
	}else{
		$scope.tab = {
				current : 'ranking'
		};
	}
	
	$scope.switchTab = function(tab) {
		if (tab === 'followers') {
			$scope.getFollowers();
		} else if (tab === 'following') {
			$scope.getFollowing();
		} else if (tab === 'ranking') {
			$scope.getRanking();
		}
		$scope.tab.current = tab;
		$scope.query = '';
	};

	$scope.getFollowers = function() {
		actorService.getFollowers().then(function(result) {
			$scope.actors = result.data;
		});
	};

	$scope.getFollowing = function() {
		actorService.getFollowing().then(function(result) {
			$scope.actors = result.data;
		});
	}

	$scope.getRanking = function() {
		actorService.getRanking().then(function(result) {
			$scope.actors = result.data;
		});
	};

	$scope.find = function() {
		if ($scope.query !== undefined && $scope.query !== '') {
			$scope.tab.current = '';
			actorService.find($scope.query).then(function(result) {
				$scope.actors = result.data;
			});
		}
	}

	$scope.switchFollow = function(actor) {
		actorService.switchFollow(actor).then(function(result) {
			if (result.data.success) {
				actor.following = !actor.following;
			}
		});
	};

	$scope.switchTab($scope.tab.current);
	
	//Profile
	
	$scope.profile = sessionService.getPrincipal();
			
	
	$scope.predictions = [{homeTeam: 'R.Madrid', awayTeam: 'Barcelona', homeGoals: 2, awayGoals: 0, matchDate: '10/08/2015'},{homeTeam: 'At.Madrid', awayTeam: 'Getafe', homeGoals: 3, awayGoals: 1, matchDate: '11/08/2015'}];
	$scope.maxSimpleValue = 69;
	$scope.simpleValue = 0;
	$scope.maxDoubleValue = 35;
	$scope.doubleValue = 0;
	$scope.maxHalfTimeHomeGoals = 70;
	$scope.halfTimeHomeGoals = 0;
	$scope.maxHalfTimeAwayGoals = 85;
	$scope.halfTimeAwayGoals = 0;
	$scope.maxFinalTimeHomeGoals = 26;
	$scope.finalTimeHomeGoals = 0;
	$scope.maxFinalTimeAwayGoals = 90;
	$scope.finalTimeAwayGoals = 0;
	$scope.maxMoreThan25 = 46;
	$scope.moreThan25 = 0;

	$interval(function() {
	    $scope.simpleValue+=$scope.profile.sRPointsPercentaje/100;
	    $scope.doubleValue+=$scope.profile.dRPointsPercentaje/100;
	    $scope.finalTimeHomeGoals+=$scope.profile.hGPointsPercentaje/100;
	    $scope.finalTimeAwayGoals+=$scope.profile.aGPointsPercentaje/100;
	    $scope.moreThan25+=$scope.profile.mT25PointsPercentaje/100;
	}, 30, 100);
});