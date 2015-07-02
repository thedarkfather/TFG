/**
 * 
 */
var teamService = angular.module('predictorum.teamService', []);

teamService.factory('teamService', function($http) {

	var teamService = {};

	teamService.findAll = function() {
		var req = {
			method : 'GET',
			url : 'http://localhost:8080/Predictorum/api/team/list',
			withCredentials : true,
		}

		result = $http(req)

		return result;
	}
	
	return teamService;

});