'use strict';

angular.module('predictorumApp')
    .controller('LeagueController', function ($scope, League, ParseLinks) {
        $scope.leagues = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            League.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.leagues = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            League.update($scope.league,
                function () {
                    $scope.loadAll();
                    $('#saveLeagueModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            League.get({id: id}, function(result) {
                $scope.league = result;
                $('#saveLeagueModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            League.get({id: id}, function(result) {
                $scope.league = result;
                $('#deleteLeagueConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            League.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteLeagueConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.league = {name: null, season: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
