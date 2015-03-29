'use strict';

angular.module('predictorumApp')
    .factory('Match', function ($resource) {
        return $resource('api/matchs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.date = new Date(data.date);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
