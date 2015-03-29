'use strict';

angular.module('predictorumApp')
    .controller('PredictionController', function ($scope, Prediction, Match, Actor, ParseLinks) {
        $scope.predictions = [];
        $scope.matchs = Match.query();
        $scope.actors = Actor.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Prediction.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.predictions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Prediction.update($scope.prediction,
                function () {
                    $scope.loadAll();
                    $('#savePredictionModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Prediction.get({id: id}, function(result) {
                $scope.prediction = result;
                $('#savePredictionModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Prediction.get({id: id}, function(result) {
                $scope.prediction = result;
                $('#deletePredictionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Prediction.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePredictionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.prediction = {simpleResult: null, pSimpleResult: null, doubleResult: null, pDoubleResult: null, simpleHalfResult: null, pSimpleHalfResult: null, doubleHalfResult: null, pDoubleHalfResult: null, homeGoals: null, pHomeGoals: null, awayGoals: null, halfHomeGoals: null, pAwayGoals: null, pHalfHomeGoals: null, halfAwayGoals: null, pHalfAwayGoals: null, moreThan25: null, pMoreThan25: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
