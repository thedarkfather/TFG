// Creación del módulo

var predictorum = angular.module('predictorum', [ 'ngRoute' ]);

// Configuración de las rutas

predictorum.config([ '$routeProvider', '$locationProvider',
		function($routeProvider, $locationProvider) {

			$routeProvider

			
			
			.otherwise({
				redirectTo : '/'
			});

		} ]);

predictorum.controller('indexController', function($scope, $location) {

	$scope.isWelcome = $location.path() === '/';
	$scope.predictionsNumber = 5650;

});