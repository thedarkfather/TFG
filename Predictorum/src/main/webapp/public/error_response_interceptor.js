(function() {
	var httpInterceptor = function($provide, $httpProvider) {
		$provide.factory('httpInterceptor', function($q, $location, ipCookie) {
			return {
				response : function(response) {
					return response || $q.when(response);
				},
				responseError : function(rejection) {
					if (rejection.status === 401) {
						$location.path("/#welcome");
						ipCookie.remove('PRINCIPAL');
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