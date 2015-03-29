'use strict';

angular.module('predictorumApp')
    .controller('ActorDetailController', function ($scope, $stateParams, Actor, User) {
        $scope.actor = {};
        $scope.load = function (id) {
            Actor.get({id: id}, function(result) {
              $scope.actor = result;
            });
        };
        $scope.load($stateParams.id);
    });
