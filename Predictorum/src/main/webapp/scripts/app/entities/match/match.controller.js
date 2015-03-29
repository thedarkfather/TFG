'use strict';

angular.module('predictorumApp')
    .controller('MatchController', function ($scope, Match, Team, Result, ParseLinks) {
        $scope.matchs = [];
        $scope.teams = Team.query();
        $scope.results = Result.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Match.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.matchs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Match.update($scope.match,
                function () {
                    $scope.loadAll();
                    $('#saveMatchModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Match.get({id: id}, function(result) {
                $scope.match = result;
                $('#saveMatchModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Match.get({id: id}, function(result) {
                $scope.match = result;
                $('#deleteMatchConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Match.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMatchConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.match = {date: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
