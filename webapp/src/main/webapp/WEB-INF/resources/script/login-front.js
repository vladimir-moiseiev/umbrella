angular.module("login-front", ["login-back", "security-back"])
    .controller("login-controller", ["$scope", "$rootScope", "$location", "$http", "SecurityBack", function ($scope, $rootScope, $location, $http, SecurityBack) {

    }])
    .config(['$httpProvider', '$locationProvider', function ($httpProvider, $locationProvider) {
        $locationProvider.html5Mode(true).hashPrefix('!');
    }]);