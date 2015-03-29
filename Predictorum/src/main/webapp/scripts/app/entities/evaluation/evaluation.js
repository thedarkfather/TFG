'use strict';

angular.module('predictorumApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('evaluation', {
                parent: 'entity',
                url: '/evaluation',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'predictorumApp.evaluation.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/evaluation/evaluations.html',
                        controller: 'EvaluationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('evaluation');
                        return $translate.refresh();
                    }]
                }
            })
            .state('evaluationDetail', {
                parent: 'entity',
                url: '/evaluation/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'predictorumApp.evaluation.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/evaluation/evaluation-detail.html',
                        controller: 'EvaluationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('evaluation');
                        return $translate.refresh();
                    }]
                }
            });
    });
