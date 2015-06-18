// Creación del módulo

var predictorum = angular.module('predictorum', [ 'ngRoute','ngCookies',
		'pascalprecht.translate', 'predictorum.actorService']);

// Configuración

predictorum.config(['$routeProvider','$locationProvider','$translateProvider',function($routeProvider, $locationProvider,
		$translateProvider) {

	//idiomas
	  
	$translateProvider.useStaticFilesLoader({
		prefix : 'i18n/',
		suffix : '.json'
	});
	$translateProvider.preferredLanguage('es');

	//rutas
	
	$routeProvider

	.otherwise({
		redirectTo : '/'
	});

}]);

predictorum.controller('indexController', function($scope, $location, $translate, actorService) {

	$scope.isWelcome = $location.path() === '/';
	
	$scope.login={
			user: "",
			password: "",
	}

	$scope.goLogin = function(){
		actorService.login($scope.login.user,$scope.login.password).success(function(data) {
			if (data.error) {
				$scope.result = "ERROR";
			} else {
				$scope.result = "OK"
			}

		});	
		
	}
	
	$scope.switchLanguage = function(lang){
		 $translate.use(lang);
	}

	$scope.predictionsNumber = 5650;

});