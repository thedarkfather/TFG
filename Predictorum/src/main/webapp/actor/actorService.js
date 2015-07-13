/**
 * 
 */
var actorService = angular.module('predictorum.actorService', []);


actorService
.factory('actorService', function($http) {
	
	actorService = {};
	
	actorService.getFollowers = function() {
		var req = {
			method : 'GET',
			url : 'http://localhost:8080/Predictorum/api/user/listFollowers',
			withCredentials : true,
		}
		
		return $http(req);
	}
	
	actorService.getFollowing = function() {
		var req = {
			method : 'GET',
			url : 'http://localhost:8080/Predictorum/api/user/listFollowing',
			withCredentials : true,
		}
		
		return $http(req);
	}
	
	actorService.getRanking = function() {
		var req = {
			method : 'GET',
			url : 'http://localhost:8080/Predictorum/api/user/ranking',
			withCredentials : true,
		}
		
		return $http(req);
	}
	
	return actorService;
	
} );