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
	
	teamService.switchFollow = function(team){
		var teamForm = {teamId: team.teamId};
		var req = {
				method: 'POST',
				url: 'http://localhost:8080/Predictorum/api/team/follow',
				withCredentials: true,
				data: teamForm
		};
		
		result = $http(req);
		
		return result;
	};
	
	teamService.getStatistics = function(teamId){
		var req = {
				method : 'GET',
				url : 'http://localhost:8080/Predictorum/api/teamStatistics/'+teamId,
				withCredentials : true,
			}

			result = $http(req)

			return result;
	}
	
	return teamService;

});