/**
 * 
 */
var predictionService = angular.module('predictorum.predictionService', []);

predictionService.factory('predictionService', function($http) {

	var predictionService = {};
	
	predictionService.findUpcoming = function(){
		var req = {
				method : 'GET',
				url : 'http://localhost:8080/Predictorum/api/game/listNextGames',
				withCredentials : true,
			}

			result = $http(req)

			return result;
	};

	predictionService.findSystemPrediction = function(gameId){
		var req = {
				method : 'GET',
				url : 'http://localhost:8080/Predictorum/api/prediction/systemPrediction/'+gameId,
				withCredentials : true,
			}

			result = $http(req)

			return result;
	};
	
	return predictionService;

});