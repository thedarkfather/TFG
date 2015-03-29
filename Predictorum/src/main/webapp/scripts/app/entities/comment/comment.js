'use strict';

angular.module('predictorumApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('comment', {
                parent: 'entity',
                url: '/comment',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'predictorumApp.comment.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/comment/comments.html',
                        controller: 'CommentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('comment');
                        return $translate.refresh();
                    }]
                }
            })
            .state('commentDetail', {
                parent: 'entity',
                url: '/comment/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'predictorumApp.comment.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/comment/comment-detail.html',
                        controller: 'CommentDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('comment');
                        return $translate.refresh();
                    }]
                }
            });
    });
