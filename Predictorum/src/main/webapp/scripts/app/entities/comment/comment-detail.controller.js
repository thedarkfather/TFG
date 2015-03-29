'use strict';

angular.module('predictorumApp')
    .controller('CommentDetailController', function ($scope, $stateParams, Comment, Prediction, Actor) {
        $scope.comment = {};
        $scope.load = function (id) {
            Comment.get({id: id}, function(result) {
              $scope.comment = result;
            });
        };
        $scope.load($stateParams.id);
    });
