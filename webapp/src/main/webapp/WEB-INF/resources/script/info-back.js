angular.module('info-back', ['ngResource'])

    .factory('Info', ['$resource', function ($resource) {

        return $resource('../info/:path', {}, {

            findPersons: {
                method: 'POST',
                params: {
                    path: "addComment"
                }
            }
        });
    }]);
