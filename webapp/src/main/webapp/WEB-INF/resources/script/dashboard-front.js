angular.module("dashboard-front", [
    "ngRoute", "ngSanitize", "security-front","login-front", "search-back", "dictionary-back"  ])
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
    }]).controller("dashboard-controller", ["$scope", "$filter", "Search", "Dictionary",
        function ($scope, $filter, Search, Dictionary) {

            console.log("dashboard-controller");
            $scope.person = {};
            $scope.persons = [];
            $scope.cities = [];
            $scope.providers = [];

            $scope.selectedCity = {};
            $scope.selectedProvider = {};
            $scope.selectedProvider.triolan = false;
            $scope.selectedProvider.ks = false;
            $scope.selectedProvider.volya = false;

            Dictionary.getCities(function(response){
                console.log("getCities response", response);
                $scope.cities = response.result;
            });

            Dictionary.getProviders(function(response){
                console.log("getProviders response", response);
                $scope.providers = response.result;
            });

            $scope.search = function(){
                var request = {
                    lastName : $scope.person.name,
                    triolan : $scope.selectedProvider.triolan,
                    ks : $scope.selectedProvider.ks,
                    volya : $scope.selectedProvider.volya,
                    city : $scope.selectedCity.name
                };

                console.log("searching " + request.lastName);
                Search.findPersons(request, function(response){
                    console.log("find persons response", response);
                    $scope.persons = response.result;
                });
            };

            $scope.validateSearch = function() {
                if($scope.person.name == null || $scope.person.name === "" ||
                    $scope.selectedCity.name == null || $scope.selectedCity.name === "" ) {
                    return false;
                }
                return true;
            }
        }
    ]);