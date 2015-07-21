/**
 * 
 */
var actorService = angular.module('predictorum.actorService', []);


actorService
.factory('actorService', function($http) {
	
	actorService = {};
	
	actorService.getProfile = function() {
		var req = {
				method : 'GET',
				url : 'http://localhost:8080/Predictorum/api/user/profile',
				withCredentials : true,
			}
			
			return $http(req);
	};
	
	actorService.getFollowers = function() {
		var req = {
			method : 'GET',
			url : 'http://localhost:8080/Predictorum/api/user/listFollowers',
			withCredentials : true,
		}
		
		return $http(req);
	};
	
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
	
	actorService.switchFollow = function(actor){
		var userForm = {userId: actor.id};
		var req = {
				method: 'POST',
				url: 'http://localhost:8080/Predictorum/api/user/follow',
				withCredentials: true,
				data: userForm
		};
		
		result = $http(req);
		
		return result;
	};
	
	actorService.find = function(query){
		var req = {
				method : 'GET',
				url : 'http://localhost:8080/Predictorum/api/user/find/'+query,
				withCredentials : true,
			}
			
			return $http(req);
	}
	
	return actorService;
	
} );