'use strict';

angular.module('predictorumApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


