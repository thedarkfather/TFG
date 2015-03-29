'use strict';

angular.module('predictorumApp')
    .controller('TeamDetailController', function ($scope, $stateParams, Team, League, TeamStatistics) {
        $scope.team = {};
        $scope.load = function (id) {
            Team.get({id: id}, function(result) {
              $scope.team = result;
            });
        };
        $scope.load($stateParams.id);
    });
