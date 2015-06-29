/**
 * 
 */
var sessionService = angular.module('predictorum.sessionService', []);


sessionService
.factory('sessionService', function($http) {
	
	var sessionService = {};
	
	sessionService.principal = {name: ''}
	
	sessionService.getPrincipal = function() { return sessionService.principal };
	

	sessionService.login = function(username,password) {
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
		
		result = $http(req).success(function(data) {
			if (data!=="") {
				sessionService.loginResult = "ERROR";
			} else {
				sessionService.loginResult= "OK";
			}
		});	
		
		return result;
	}
	
	
	return sessionService;
	
} );