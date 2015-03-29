'use strict';

angular.module('predictorumApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('actor', {
                parent: 'entity',
                url: '/actor',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'predictorumApp.actor.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/actor/actors.html',
                        controller: 'ActorController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('actor');
                        return $translate.refresh();
                    }]
                }
            })
            .state('actorDetail', {
                parent: 'entity',
                url: '/actor/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'predictorumApp.actor.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/actor/actor-detail.html',
                        controller: 'ActorDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('actor');
                        return $translate.refresh();
                    }]
                }
            });
    });
