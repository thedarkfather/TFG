/**
 * 
 */
var actorService = angular.module('predictorum.sessionService', []);


actorService
.factory('sessionService', function($http) {
	

	var login = function(username,password) {
		var req = {
			method : 'POST',
			url : 'http://localhost:8080/Predictorum/j_spring_security_check',
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			data : $.param({
				j_username : username,
				j_password : password
			}),
			withCredentials : false,
		}
		
		return $http(req);
	}
	
	return {
		login : login
	}
	
} );