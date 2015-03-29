'use strict';

angular.module('predictorumApp')
    .controller('MatchDetailController', function ($scope, $stateParams, Match, Team, Result) {
        $scope.match = {};
        $scope.load = function (id) {
            Match.get({id: id}, function(result) {
              $scope.match = result;
            });
        };
        $scope.load($stateParams.id);
    });
