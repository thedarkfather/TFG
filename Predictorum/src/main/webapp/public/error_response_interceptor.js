(function() {
	var httpInterceptor = function($provide, $httpProvider) {
		$provide.factory('httpInterceptor', function($q, $location, sessionService) {
			return {
				response : function(response) {
					return response || $q.when(response);
				},
				responseError : function(rejection) {
					if (rejection.status === 401) {
						$location.path("/#welcome");
						sessionService.logout();
					} else {
						$location.path("/public/" + rejection.status + ".html");
					}
					return $q.reject(rejection);
				}
			};
		});
		$httpProvider.interceptors.push('httpInterceptor');
	};
	angular.module("predictorum").config(httpInterceptor);
}());