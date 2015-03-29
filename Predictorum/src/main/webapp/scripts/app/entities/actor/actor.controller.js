'use strict';

angular.module('predictorumApp')
    .controller('ActorController', function ($scope, Actor, User, ParseLinks) {
        $scope.actors = [];
        $scope.users = User.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Actor.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.actors = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Actor.update($scope.actor,
                function () {
                    $scope.loadAll();
                    $('#saveActorModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Actor.get({id: id}, function(result) {
                $scope.actor = result;
                $('#saveActorModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Actor.get({id: id}, function(result) {
                $scope.actor = result;
                $('#deleteActorConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Actor.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteActorConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.actor = {srPoints: null, drPoints: null, sHRPoints: null, dHRPoints: null, hGPoints: null, aGPoints: null, hHGPoints: null, hAGPoints: null, mT25Points: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
