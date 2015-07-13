/**
 * 
 */

var actorController = angular.module('predictorum.actorController',
		[ 'predictorum.actorService' ]);

actorController.controller("actorController", function($scope, actorService) {

	$scope.tab = {
		current : 'followers'
	};

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

	$scope.getFollowers()
});