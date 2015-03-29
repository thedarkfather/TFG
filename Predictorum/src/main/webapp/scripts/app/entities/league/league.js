'use strict';

angular.module('predictorumApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('league', {
                parent: 'entity',
                url: '/league',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'predictorumApp.league.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/league/leagues.html',
                        controller: 'LeagueController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('league');
                        return $translate.refresh();
                    }]
                }
            })
            .state('leagueDetail', {
                parent: 'entity',
                url: '/league/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'predictorumApp.league.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/league/league-detail.html',
                        controller: 'LeagueDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('league');
                        return $translate.refresh();
                    }]
                }
            });
    });
