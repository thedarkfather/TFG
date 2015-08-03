/**
 * 
 */
var sessionService = angular.module('predictorum.sessionService', ['ipCookie','predictorum.actorService']);


sessionService
.factory('sessionService', function($http,$location,ipCookie,actorService) {
	
	var sessionService = {};
	
	sessionService.getPrincipal = function() {
		sessionService.principal = ipCookie('PRINCIPAL');
		if(typeof sessionService.principal === 'undefined'){
			actorService.getPrincipalProfile().then(function(result){
				if(result.status=='200'){
					sessionService.principal = result.data;
					ipCookie('PRINCIPAL', result.data,{expires: 3, expirationUnit: 'hours'});
				}
			});
		}
		return sessionService.principal 
	};
	

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
				sessionService.getPrincipal();
			}
		});
		
		return result
	}
	
	sessionService.signUp = function(signUpForm){
		if(signUpForm===undefined){
			signUpForm = {username: null,password: null,rpassword:null};
		}
		var req = {
				method : 'POST',
				url : 'http://localhost:8080/Predictorum/api/join',
				data : signUpForm,
				withCredentials : false,
			}
			
		result = $http(req);
	
	
	return result;
	
	}
	
	sessionService.logout = function(){
		ipCookie.remove('PRINCIPAL');
	}
	
	
	return sessionService;
	
} );