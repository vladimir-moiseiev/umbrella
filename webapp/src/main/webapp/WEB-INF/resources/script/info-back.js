angular.module('info-back', ['ngResource'])

    .factory('Info', ['$resource', function ($resource) {

        return $resource('../info/:path', {}, {

            addComment: {
                method: 'POST',
                params: {
                    path: "addComment"
                }
            }
        });
    }]);
