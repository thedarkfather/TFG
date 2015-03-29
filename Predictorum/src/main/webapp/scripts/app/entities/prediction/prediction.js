'use strict';

angular.module('predictorumApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('prediction', {
                parent: 'entity',
                url: '/prediction',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'predictorumApp.prediction.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/prediction/predictions.html',
                        controller: 'PredictionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prediction');
                        return $translate.refresh();
                    }]
                }
            })
            .state('predictionDetail', {
                parent: 'entity',
                url: '/prediction/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'predictorumApp.prediction.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/prediction/prediction-detail.html',
                        controller: 'PredictionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prediction');
                        return $translate.refresh();
                    }]
                }
            });
    });
