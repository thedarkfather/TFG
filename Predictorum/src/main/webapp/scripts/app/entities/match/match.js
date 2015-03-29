'use strict';

angular.module('predictorumApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('match', {
                parent: 'entity',
                url: '/match',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'predictorumApp.match.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/match/matchs.html',
                        controller: 'MatchController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('match');
                        return $translate.refresh();
                    }]
                }
            })
            .state('matchDetail', {
                parent: 'entity',
                url: '/match/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'predictorumApp.match.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/match/match-detail.html',
                        controller: 'MatchDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('match');
                        return $translate.refresh();
                    }]
                }
            });
    });
