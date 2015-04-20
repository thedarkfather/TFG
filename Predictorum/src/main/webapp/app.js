// Creación del módulo

var predictorum = angular.module('predictorum', ['ngRoute']);

//Configuración de las rutas

routingApp.config([ '$routeProvider', '$locationProvider',
		function($routeProvider, $locationProvider) {
	
	$routeProvider
	
	
	.otherwise({
		redirectTo : '/'
	});
	
}]);