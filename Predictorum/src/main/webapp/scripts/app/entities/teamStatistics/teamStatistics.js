'use strict';

angular.module('predictorumApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('teamStatistics', {
                parent: 'entity',
                url: '/teamStatistics',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'predictorumApp.teamStatistics.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/teamStatistics/teamStatisticss.html',
                        controller: 'TeamStatisticsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('teamStatistics');
                        return $translate.refresh();
                    }]
                }
            })
            .state('teamStatisticsDetail', {
                parent: 'entity',
                url: '/teamStatistics/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'predictorumApp.teamStatistics.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/teamStatistics/teamStatistics-detail.html',
                        controller: 'TeamStatisticsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('teamStatistics');
                        return $translate.refresh();
                    }]
                }
            });
    });
