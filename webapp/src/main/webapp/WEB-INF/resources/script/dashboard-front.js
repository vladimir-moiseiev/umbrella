angular.module("dashboard-front", [
    "ngRoute", "ngSanitize", "security-front"  ])
    .run(["$rootScope", function ($rootScope) {
    }])
    .config(["$routeProvider", "$httpProvider", function ($routeProvider, $httpProvider) {

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

        $routeProvider
            .when("/dashboard", {
                controller: "dashboard-controller",
                templateUrl: "../pages/dashboard/dashboard.html"
            })
            .when("/login", {
                controller: "login-controller",
                templateUrl: "../pages/login/login.html",
            })
            .otherwise({
                redirectTo: "/dashboard"
            });
    }]);