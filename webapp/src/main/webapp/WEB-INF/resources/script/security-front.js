angular.module("security-front", ["security-back"])
    .controller("security-controller", ["$scope", "$rootScope", "SecurityBack", function ($scope, $rootScope, SecurityBack) {

        var userDetails = null;

        $scope.isUserDetailsLoaded = false;

        $rootScope.getUserDetails = function () {
            return angular.copy(userDetails);
        };

        SecurityBack.getUserDetails({}, (function success(response) {

            userDetails = response.result;

            console.log("getUserDetails", userDetails);

            if (userDetails) {

                $scope.userEmail = userDetails.email;

                $rootScope.isUserLoggedIn = true;
                $rootScope.isUserAdmin = userDetails.admin;

                userDetails.username = userDetails.firstName + " " + userDetails.lastName;

                $scope.isUserDetailsLoaded = true;
            }
        }));

    }]);