// Creación del módulo

var predictorum = angular.module('predictorum', [ 'ngRoute' ]);

// Configuración de las rutas

predictorum.config([ '$routeProvider', '$locationProvider',
		function($routeProvider, $locationProvider) {

			$routeProvider

			.when('/', {
				templateUrl : 'public/welcome.html',
				controller : 'indexController'
			})
			
			.otherwise({
				redirectTo : '/'
			});

		} ]);

predictorum.controller('indexController', [ '$scope', '$http',
		'$location', function($scope, $http, $location) {

	$scope.isWelcome = $location.path() === '/';

}]);