angular.module("dashboard-front", [
    "ngRoute", "ngSanitize", "security-front","login-front"  ])
    .run(["$rootScope", function ($rootScope) {
    }])
    .config(["$routeProvider", "$httpProvider", function ($routeProvider, $httpProvider) {

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

        console.log("dashboard");
        $routeProvider
            .when("/dashboard", {
                controller: "dashboard-controller",
                templateUrl: "../pages/dashboard/dashboard.html"
            })
            .when("/login", {
                controller: "login-controller",
                templateUrl: "../pages/login/login.html"
            })
            .otherwise({
                redirectTo: "/dashboard"
            });
    }]).controller("dashboard-controller", ["$scope", "$filter",
        function ($scope, $filter) {

            console.log("dashboard-controller");
        }
    ]);