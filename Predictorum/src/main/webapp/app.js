// Creación del módulo

var predictorum = angular.module('predictorum', [ 'ngAnimate','ngRoute','ngCookies',
		'pascalprecht.translate', 'smoothScroll', 'predictorum.sessionService', 'predictorum.teamController']);

// Configuración

predictorum.config(['$routeProvider','$locationProvider','$translateProvider',function($routeProvider, $locationProvider,
		$translateProvider) {

	//idiomas
	  
	$translateProvider.useStaticFilesLoader({
		prefix : 'i18n/',
		suffix : '.json'
	});
	$translateProvider.preferredLanguage('es');
	$translateProvider.useCookieStorage();
	 // Enable escaping of HTML
	  $translateProvider.useSanitizeValueStrategy('escaped');
	//rutas
	
	$routeProvider
	
	.when('/team/list', {
		templateUrl : 'team/views/list.html',
		controller : 'teamController'
	})

	.otherwise({
		redirectTo : '/'
	});

}]);

predictorum.controller('indexController', function($scope, $location, $translate, sessionService) {

	$scope.$on('$routeChangeStart', function(next, current) { 
		$scope.isWelcome = $location.path() === '/';
	 });
	
	//Login
	
	$scope.login={
			user: "",
			password: "",
			result: ""
	}

	$scope.goLogin = function(){
		sessionService.login($scope.login.user,$scope.login.password).success(function(data) {
			if (data!=="") {
				$scope.login.result = "ERROR";
			} else {
				$scope.login.result = "OK";
				$scope.toggleMenu();
			};
		});
	}
	
	//Languages
	
	$scope.switchLanguage = function(lang){
		 $translate.use(lang);
	}
	
	//SVG Menu
	var svg = document.getElementById('svg-menu'),
    items = svg.querySelectorAll('.item'),
    trigger = document.getElementById('trigger'),
    label = trigger.querySelectorAll('#label')[0],
    open = false;

    	//first scale the elements down
    TweenLite.set(items, {scale:0, visibility:"visible"});
    svg.style.pointerEvents = "none";
	
	$scope.toggleMenu = function(){
	    open = !open;
	    if (open) {
	        TweenMax.staggerTo(items, 0.7, {scale:1, ease:Elastic.easeOut}, 0.05);
	        label.innerHTML = "-";
	      svg.style.pointerEvents = "auto";
	    } else {
	        TweenMax.staggerTo(items, .3, {scale:0, ease:Back.easeIn}, 0.05);
	        label.innerHTML = "+";
	      svg.style.pointerEvents = "none";
	    }
	}

	$scope.predictionsNumber = 5650;

});