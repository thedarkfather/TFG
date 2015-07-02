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

});