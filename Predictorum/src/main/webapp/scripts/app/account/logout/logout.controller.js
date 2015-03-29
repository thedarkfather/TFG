'use strict';

angular.module('predictorumApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
