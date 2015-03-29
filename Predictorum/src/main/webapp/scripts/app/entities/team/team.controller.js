'use strict';

angular.module('predictorumApp')
    .controller('TeamController', function ($scope, Team, League, TeamStatistics, ParseLinks) {
        $scope.teams = [];
        $scope.leagues = League.query();
        $scope.teamstatisticss = TeamStatistics.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Team.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.teams = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Team.update($scope.team,
                function () {
                    $scope.loadAll();
                    $('#saveTeamModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Team.get({id: id}, function(result) {
                $scope.team = result;
                $('#saveTeamModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Team.get({id: id}, function(result) {
                $scope.team = result;
                $('#deleteTeamConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Team.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTeamConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.team = {name: null, logo: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
