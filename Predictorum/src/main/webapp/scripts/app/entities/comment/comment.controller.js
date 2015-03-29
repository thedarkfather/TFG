'use strict';

angular.module('predictorumApp')
    .controller('CommentController', function ($scope, Comment, Prediction, Actor, ParseLinks) {
        $scope.comments = [];
        $scope.predictions = Prediction.query();
        $scope.actors = Actor.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Comment.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.comments = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Comment.update($scope.comment,
                function () {
                    $scope.loadAll();
                    $('#saveCommentModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Comment.get({id: id}, function(result) {
                $scope.comment = result;
                $('#saveCommentModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Comment.get({id: id}, function(result) {
                $scope.comment = result;
                $('#deleteCommentConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Comment.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCommentConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.comment = {text: null, posPoints: null, negPoints: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
