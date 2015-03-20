angular.module('search-back', ['ngResource'])

    .factory('Search', ['$resource', function ($resource) {

        return $resource('../search/:path', {}, {

            findPersons: {
                method: 'POST',
                params: {
                    path: "findPersons"
                }
            }
        });
    }]);
