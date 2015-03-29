'use strict';

angular.module('predictorumApp')
    .factory('TeamStatistics', function ($resource) {
        return $resource('api/teamStatisticss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
