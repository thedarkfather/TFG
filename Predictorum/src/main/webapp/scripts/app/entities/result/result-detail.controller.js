'use strict';

angular.module('predictorumApp')
    .controller('ResultDetailController', function ($scope, $stateParams, Result, Match) {
        $scope.result = {};
        $scope.load = function (id) {
            Result.get({id: id}, function(result) {
              $scope.result = result;
            });
        };
        $scope.load($stateParams.id);
    });
