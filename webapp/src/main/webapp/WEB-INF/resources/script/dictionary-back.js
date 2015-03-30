angular.module('dictionary-back', ['ngResource'])

    .factory('Dictionary', ['$resource', function ($resource) {

        return $resource('../data/:path', {}, {

            getCities: {
                method: 'GET',
                params: {
                    path: "getCities"
                }
            },
            getProviders: {
                method: 'GET',
                params: {
                    path: "getProviders"
                }
            }
        });
    }]);
