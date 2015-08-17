// Creación del módulo

var predictorum = angular.module('predictorum', [ 'ngAnimate','ngRoute','ngCookies','ipCookie',
		'pascalprecht.translate', 'smoothScroll','isteven-omni-bar','tc.chartjs',
		'predictorum.sessionService','predictorum.teamController','predictorum.actorController',
		'predictorum.predictionController']);

// Directivas propias

predictorum.directive('errSrc', function() {
	return {
		link : function(scope, element, attrs) {
			element.bind('error', function() {
				if (attrs.src != attrs.errSrc) {
					attrs.$set('src', attrs.errSrc);
				}
			});
		}
	}
});

// Configuración

predictorum.config(['$routeProvider','$locationProvider','$translateProvider','$httpProvider',function($routeProvider, $locationProvider,
		$translateProvider,$httpProvider) {
	
	//Llamadas de distintos dominios
	
	$httpProvider.defaults.useXDomain = true;

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
	
	.when('/team/favorites', {
		templateUrl : 'team/views/list.html',
		controller : 'teamController'
	})
	
	.when('/user/followers', {
		templateUrl : 'actor/views/list.html',
		controller : 'actorController'
	})
	
	.when('/user/following', {
		templateUrl : 'actor/views/list.html',
		controller : 'actorController'
	})
	
	.when('/user/ranking', {
		templateUrl : 'actor/views/list.html',
		controller : 'actorController'
	})
	
	.when('/user/profile', {
		templateUrl : 'actor/views/display.html',
		controller : 'actorController'
	})
	
	.when('/user/profile/:userId', {
		templateUrl : 'actor/views/display.html',
		controller : 'actorController'
	})
	
	.when('/prediction/upcoming', {
		templateUrl : 'prediction/views/list.html',
		controller : 'predictionController'
	})
	
	.when('/prediction/details/:gameId', {
		templateUrl : 'prediction/views/display.html',
		controller : 'predictionController'
	})
	
	.when('/prediction/user/details/:predictionId', {
		templateUrl : 'prediction/views/display.html',
		controller : 'predictionController'
	})
	
	.when('/prediction/create/:gameId', {
		templateUrl : 'prediction/views/form.html',
		controller : 'predictionController'
	})

	.otherwise({
		redirectTo : '/'
	});

}]);

predictorum.controller('indexController', function($scope, $location, $translate,$timeout, sessionService) {

	$scope.$on('$routeChangeStart', function(next, current) { 
		$scope.isWelcome = $location.path() === '/';
	 });
	
	//Login
	
	$scope.login={
			user: "",
			password: "",
			result: ""
	}
	
	$scope.notRegistered = true;

	$scope.goLogin = function(){
		sessionService.login($scope.login.user,$scope.login.password).success(function(data) {
			if (data!=="") {
				$scope.login.result = "ERROR";
			} else {
				$scope.login.result = "OK";
				$scope.showMenu = true;
				$scope.toggleMenu();
			};
		});
	}
	
	//Sign up
	
	$scope.goSignUp = function(){
		$scope.signUpSubmitted = true;
		$scope.blankError = false;
		$scope.passwordMatchError = false;
		$scope.loading = true;
		sessionService.signUp($scope.signUp).then(function(result){
			$scope.loading=false;
			if(!result.data.success){
				if(result.data.errors.username){
					$scope.blankError = result.data.errors.username.includes('empty')
				}
				if(result.data.errors.password){
					$scope.passwordMatchError = result.data.errors.password.includes('match');
					$scope.blankError = result.data.errors.password.includes('empty') || $scope.blankError;
				}
				if(result.data.errors.rpassword){ 
					$scope.blankError = result.data.errors.rpassword.includes('empty') || $scope.blankError;
				}
				if(result.data.errors.notUnique){
					$scope.notUniqueError = !$scope.passwordMatchError; //para que no se muestren ambos errores a la vez
				}
			}else{
				//se ha registrado 
				$scope.notRegistered = false;
			}
		});
	}
	
	//Logout
	
	$scope.logout = function(){
		sessionService.logout();
		showMenu=false;
		$scope.apply();
	}
	
	//Languages
	
	$scope.switchLanguage = function(lang){
		 $translate.use(lang);
	}
	
	//SVG Menu
	$scope.showMenu = typeof sessionService.getPrincipal()!=='undefined';
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
	
	if($scope.showMenu){
		$scope.toggleMenu();
	}

	$scope.predictionsNumber = 5650;

});