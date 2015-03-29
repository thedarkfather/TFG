'use strict';

angular.module('predictorumApp')
    .controller('EvaluationController', function ($scope, Evaluation, Actor, Comment, ParseLinks) {
        $scope.evaluations = [];
        $scope.actors = Actor.query();
        $scope.comments = Comment.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Evaluation.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.evaluations = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Evaluation.update($scope.evaluation,
                function () {
                    $scope.loadAll();
                    $('#saveEvaluationModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Evaluation.get({id: id}, function(result) {
                $scope.evaluation = result;
                $('#saveEvaluationModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Evaluation.get({id: id}, function(result) {
                $scope.evaluation = result;
                $('#deleteEvaluationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Evaluation.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEvaluationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.evaluation = {type: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
