angular.module("security-back", ["ngResource"])

    .factory("SecurityBack", ['$resource', function ($resource) {

        return $resource("../security/:path", {}, {

            "getUserDetails": {
                method: "GET",
                params: {
                    path: "getUserDetails"
                }
            },
            "getUsers": {
                method: "GET",
                params: {
                    path: "getUsers"
                }
            }
        })
    }]);