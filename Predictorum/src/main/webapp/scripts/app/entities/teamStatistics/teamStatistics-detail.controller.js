'use strict';

angular.module('predictorumApp')
    .controller('TeamStatisticsDetailController', function ($scope, $stateParams, TeamStatistics, Team) {
        $scope.teamStatistics = {};
        $scope.load = function (id) {
            TeamStatistics.get({id: id}, function(result) {
              $scope.teamStatistics = result;
            });
        };
        $scope.load($stateParams.id);
    });
