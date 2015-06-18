/**
 * 
 */

var actorController = angular.module('predictorum.actorController', ['predictorum.actorService']);

actorController.controller("actorController", function($scope,actorService){
	
	$scope.login = actorService.login($scope.user,$scope.password);
	
});