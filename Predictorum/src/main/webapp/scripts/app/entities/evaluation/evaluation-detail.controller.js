'use strict';

angular.module('predictorumApp')
    .controller('EvaluationDetailController', function ($scope, $stateParams, Evaluation, Actor, Comment) {
        $scope.evaluation = {};
        $scope.load = function (id) {
            Evaluation.get({id: id}, function(result) {
              $scope.evaluation = result;
            });
        };
        $scope.load($stateParams.id);
    });
