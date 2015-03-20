angular.module("dashboard-front", [
    "ngRoute", "ngSanitize", "security-front","login-front", "search-back"  ])
    .run(["$rootScope", function ($rootScope) {
    }])
    .config(["$routeProvider", "$httpProvider", function ($routeProvider, $httpProvider) {


        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

        console.log("dashboard");
        //$routeProvider
        //    .when("/", {
        //        controller: "dashboard-controller",
        //        templateUrl: "../pages/dashboard/user-dashboard.html"
        //    })
        //    .otherwise({
        //        redirectTo: "/"
        //    });
    }]).controller("dashboard-controller", ["$scope", "$filter", "Search",
        function ($scope, $filter, Search) {

            console.log("dashboard-controller");
            $scope.person = {};
            $scope.persons = [];

            $scope.search = function(){
                var request = {
                    lastName : $scope.person.name
                };

                console.log("searching " + request.lastName);
                Search.findPersons(request, function(response){
                    console.log("find persons response", response);
                    $scope.persons = response.result;
                });
            }
        }
    ]);