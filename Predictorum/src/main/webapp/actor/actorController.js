/**
 * 
 */

var actorController = angular.module('predictorum.actorController',
		[ 'predictorum.actorService', 'predictorum.sessionService' ]);

actorController.controller("actorController", function($scope, $location,$interval, $routeParams, actorService, sessionService) {

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
	

	$scope.simpleValue = 0;
	$scope.doubleValue = 0;
	$scope.finalTimeHomeGoals = 0;
	$scope.finalTimeAwayGoals = 0;
	$scope.moreThan25 = 0;

	$scope.setImage = function(image){
		$scope.image = image.file;
	}
	
	$scope.loadPercentage = function(){
		$interval(function() {
	    	$scope.simpleValue+=$scope.profile.sRPointsPercentaje/100;
	    	$scope.doubleValue+=$scope.profile.dRPointsPercentaje/100;
	    	$scope.finalTimeHomeGoals+=$scope.profile.hGPointsPercentaje/100;
	    	$scope.finalTimeAwayGoals+=$scope.profile.aGPointsPercentaje/100;
	    	$scope.moreThan25+=$scope.profile.mT25PointsPercentaje/100;
		}, 30, 100);
	}
	
	if($routeParams.userId){
		actorService.getProfile($routeParams.userId).then(function(result){
			$scope.profile = result.data;
			$scope.loadPercentage();
		});
	}else{
		actorService.getPrincipalProfile().then(function(result){
			$scope.profile = result.data;
			$scope.loadPercentage();
			$scope.profile.following = null;
		});
		$scope.showEdit = true;
	}
	
	$scope.saveProfile = function(){
		/*actorService.saveProfile($scope.profile).then(function(result){
			
		});*/
		actorService.saveProfilePhoto($scope.image).then(function(result){
			if(result.data.success){
				$scope.profile.photo = $scope.image;
				$scope.editForm = false;
			}
		});
	}
	
});