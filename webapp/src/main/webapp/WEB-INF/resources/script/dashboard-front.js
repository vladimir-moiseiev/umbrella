angular.module("dashboard-front", [
    "ngRoute", "ngSanitize", "security-front","login-front", "security-back", "search-back", "info-back", "dictionary-back","ui.bootstrap"  ])
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
    }])

    .controller("dashboard-controller", ["$scope", "$filter", "SecurityBack", "Search", "Dictionary", "Info", "Utils",
        function ($scope, $filter, SecurityBack, Search, Dictionary, Info, Utils) {

            console.log("dashboard-controller");
            $scope.person = {};
            $scope.persons = [];
            $scope.cities = [];
            $scope.providers = [];
            $scope.username = '';

            $scope.selectedCity = {};
            $scope.selectedProvider = {};
            $scope.selectedProvider.triolan = false;
            $scope.selectedProvider.ks = false;
            $scope.selectedProvider.volya = false;

            $scope.isSearching = false;

            Dictionary.getCities(function(response){
                console.log("getCities response", response);
                $scope.cities = response.result;
            });

            Dictionary.getProviders(function(response){
                console.log("getProviders response", response);
                $scope.providers = response.result;
            });

            SecurityBack.getUserDetails(function(response) {
                $scope.username = response.result.username;
            });

            $scope.search = function(){
                $scope.isSearching = true;
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
                    $scope.isSearching = false;
                });
            };

            $scope.validateSearch = function() {
                if($scope.person.name == null || $scope.person.name === "" ||
                    $scope.selectedCity.name == null || $scope.selectedCity.name === "" ) {
                    return false;
                }
                return true;
            };
            $scope.openAddCommentDialog = function(record, person) {
                Utils.openCommentDialog(function(result) {
                    var request = {
                        record : record,
                        text : result
                    };

                    console.log("adding comment to " + record);
                    Info.addComment(request, function(response) {
                        person.comments.push({
                            id : 0,
                            user : $scope.username,
                            date : new Date(),
                            text : result
                        })
                    });
                })
            }
        }
    ])
    .controller("admin-controller", ["$scope", "$rootScope", "SecurityBack", function ($scope, $rootScope, SecurityBack) {

        console.log("admin-controller");

        var users = [];

        $scope.isUserDetailsLoaded = false;

        $rootScope.getUserDetails = function () {
            return angular.copy(userDetails);
        };

        SecurityBack.getUsers({}, (function success(response) {
            users = response.result;

        }));

    }])
    .controller("comment-controller", ['$scope', '$modalInstance', 'params', function ($scope, $modalInstance, params) {
        $scope.newComment = '';

        $scope.submitButtonText = params.submitButtonText;
        $scope.modalTitle = params.title;

        $scope.submit = function () {
            var text = $scope.newComment;
            params.onSubmit(text);
            $modalInstance.close();

        };

        $scope.cancel = function () {
            $modalInstance.close();
        };
    }])
    .factory("Utils", ["$modal", function($modal) {
        var utils = {
            openDialog: function (options) {
                $modal.open({
                    templateUrl: options.templateUrl,
                    controller: options.controller,
                    resolve: {
                        params: function () {
                            return options.paramsObj;
                        }
                    },
                    backdrop: options.backdrop || "static",
                    size: options.size,
                    windowClass: options.windowClass || ""
                });
            },
            openCommentDialog: function (onSubmit) {
                utils.openDialog({
                    templateUrl: "../pages/dialogs/comment.html",
                    controller: "comment-controller",
                    paramsObj: {
                        title: "Новый комментарий",
                        submitButtonText: "Добавить",
                        onSubmit: function (text) {
                            onSubmit(text);
                        }
                    }
                });
            }
        };
        return utils;
    }])
    ;