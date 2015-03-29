'use strict';

angular.module('predictorumApp')
    .controller('TeamStatisticsController', function ($scope, TeamStatistics, Team, ParseLinks) {
        $scope.teamStatisticss = [];
        $scope.teams = Team.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            TeamStatistics.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.teamStatisticss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            TeamStatistics.update($scope.teamStatistics,
                function () {
                    $scope.loadAll();
                    $('#saveTeamStatisticsModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            TeamStatistics.get({id: id}, function(result) {
                $scope.teamStatistics = result;
                $('#saveTeamStatisticsModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            TeamStatistics.get({id: id}, function(result) {
                $scope.teamStatistics = result;
                $('#deleteTeamStatisticsConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            TeamStatistics.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTeamStatisticsConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.teamStatistics = {streak: null, wonMatchPercentage: null, lostMatchPercentage: null, drawnMatchPercentage: null, moreThan25Percentage: null, wonHalfMatchPercentage: null, lostHalfMatchPercentage: null, drawHalfMatchPercentage: null, homeWonMatches: null, awayWonMatches: null, leaguePosition: null, leaguePoints: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
