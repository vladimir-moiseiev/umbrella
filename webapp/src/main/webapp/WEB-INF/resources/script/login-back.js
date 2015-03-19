angular.module('login-back', ['ngResource'])

    .factory('User', ['$resource', function ($resource) {

        return $resource('../security/:path', {}, {

            query: {
                method: 'GET',
                isArray: true
            },
            register: {
                method: 'POST'
            }
        });
    }]);