/**
 * 
 */
var predictionService = angular.module('predictorum.predictionService', []);

predictionService
		.factory(
				'predictionService',
				function($http) {

					var predictionService = {};

					predictionService.findUpcoming = function() {
						var req = {
							method : 'GET',
							url : 'http://localhost:8080/Predictorum/api/game/listNextGames',
							withCredentials : true,
						}

						result = $http(req)

						return result;
					};

					predictionService.findSystemPrediction = function(gameId) {
						var req = {
							method : 'GET',
							url : 'http://localhost:8080/Predictorum/api/prediction/systemPrediction/'
									+ gameId,
							withCredentials : true,
						}

						result = $http(req)

						return result;
					};

					predictionService.findComments = function(predictionId) {
						var req = {
							method : 'GET',
							url : 'http://localhost:8080/Predictorum/api/comment/list/'
									+ predictionId,
							withCredentials : true,
						}

						result = $http(req)

						return result;
					}

					predictionService.saveComment = function(commentForm) {
						var req = {
							method : 'POST',
							url : 'http://localhost:8080/Predictorum/api/comment/save',
							withCredentials : true,
							data : commentForm
						};

						result = $http(req);

						return result;
					};

					predictionService.evaluateComment = function(commentId,
							boolean) {
						var evaluationForm = {
							commentId : commentId,
							type : boolean
						};
						var req = {
							method : 'POST',
							url : 'http://localhost:8080/Predictorum/api/comment/evaluate',
							withCredentials : true,
							data : evaluationForm
						};

						result = $http(req);

						return result;
					}

					predictionService.savePrediction = function(userPrediction,
							gameId) {
						var predictionForm = {
							gameId : gameId,
							simpleResult : userPrediction.simpleResult,
							doubleResult : userPrediction.doubleResult,
							homeGoals : userPrediction.homeGoals,
							awayGoals : userPrediction.awayGoals,
							moreThan25 : userPrediction.mt25
						};
						var req = {
								method : 'POST',
								url : 'http://localhost:8080/Predictorum/api/prediction/saveUserPredicion',
								withCredentials : true,
								data : predictionForm
							};

							result = $http(req);

							return result;
					}

					return predictionService;

				});