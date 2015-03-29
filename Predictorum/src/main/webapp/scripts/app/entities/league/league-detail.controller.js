'use strict';

angular.module('predictorumApp')
    .controller('LeagueDetailController', function ($scope, $stateParams, League) {
        $scope.league = {};
        $scope.load = function (id) {
            League.get({id: id}, function(result) {
              $scope.league = result;
            });
        };
        $scope.load($stateParams.id);
    });
