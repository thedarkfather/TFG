'use strict';

angular.module('predictorumApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('result', {
                parent: 'entity',
                url: '/result',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'predictorumApp.result.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/result/results.html',
                        controller: 'ResultController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('result');
                        return $translate.refresh();
                    }]
                }
            })
            .state('resultDetail', {
                parent: 'entity',
                url: '/result/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'predictorumApp.result.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/result/result-detail.html',
                        controller: 'ResultDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('result');
                        return $translate.refresh();
                    }]
                }
            });
    });
