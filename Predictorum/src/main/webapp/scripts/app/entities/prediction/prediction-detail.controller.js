'use strict';

angular.module('predictorumApp')
    .controller('PredictionDetailController', function ($scope, $stateParams, Prediction, Match, Actor) {
        $scope.prediction = {};
        $scope.load = function (id) {
            Prediction.get({id: id}, function(result) {
              $scope.prediction = result;
            });
        };
        $scope.load($stateParams.id);
    });
