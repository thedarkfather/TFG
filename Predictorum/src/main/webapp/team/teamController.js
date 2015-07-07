/**
 * 
 */
var teamController = angular.module('predictorum.teamController',['predictorum.teamService']);

teamController.controller('teamController',function($scope,teamService){
	
	$scope.tab = {current: ''};
	$scope.orderProp ="teamPosition";
	teamService.findAll().then(function(result){
		$scope.teams = result.data;
	});
	
	$scope.follow = function(team){
		teamService.switchFollow(team).then(function(result){
			if(result.success){
				team.isFollow = !team.isFollow;
			}
		});
	};

});