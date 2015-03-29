'use strict';

angular.module('predictorumApp')
    .controller('ResultController', function ($scope, Result, Match, ParseLinks) {
        $scope.results = [];
        $scope.matchs = Match.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Result.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.results = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Result.update($scope.result,
                function () {
                    $scope.loadAll();
                    $('#saveResultModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Result.get({id: id}, function(result) {
                $scope.result = result;
                $('#saveResultModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Result.get({id: id}, function(result) {
                $scope.result = result;
                $('#deleteResultConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Result.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteResultConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.result = {homeGoals: null, awayGoals: null, halfHomeGoals: null, halfAwayGoals: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
