/**
 * 
 */
var teamController = angular.module('predictorum.teamController',[]);

teamController.controller('teamController',function($scope){
	
	$scope.teams = [{name:'Real Madrid',league: 'BBVA'},{name:'Barcelona',league: 'BBVA'}];
	
	
});