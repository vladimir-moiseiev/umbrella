angular.module("dashboard-front", [
    "ngRoute", "ngSanitize", "security-front","login-front", "security-back", "search-back", "info-back", "dictionary-back","ui.bootstrap"  ])
    .run(["$rootScope", function ($rootScope) {
    }])
    .config(["$routeProvider", "$httpProvider", "$locationProvider", function ($routeProvider, $httpProvider, $locationProvider) {

        $locationProvider.html5Mode(false);
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

        console.log("dashboard");
        $routeProvider
            .when("/dashboard", {
                controller: "dashboard-controller",
                templateUrl: "dashboard/user-dashboard.html"
            })
            .when("/admin", {
                controller: "admin-controller",
                templateUrl: "dashboard/admin-dashboard.html"
            })
            .otherwise({
                redirectTo: "/dashboard"
            });
        console.log("dashboard config done");
    }])

    .controller("dashboard-controller", ["$scope", "$rootScope", "$filter", "SecurityBack", "Search", "Dictionary", "Info", "Utils",
        function ($scope, $rootScope, $filter, SecurityBack, Search, Dictionary, Info, Utils) {

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
                $rootScope.username = response.result.username;
                $rootScope.isUserAdmin = response.result.admin;
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
    .controller("admin-controller", ["$scope", "$rootScope", "SecurityBack", "Utils", function ($scope, $rootScope, SecurityBack, Utils) {

        console.log("admin-controller");

        $scope.users = [];
        $scope.today = new Date();

        $scope.isUserDetailsLoaded = false;

        $rootScope.getUserDetails = function () {
            return angular.copy(userDetails);
        };
        var updateUsers = function() {
            SecurityBack.getUsers({}, (function success(response) {
                $scope.users = response.result;

            }));
        };

        updateUsers();


        $scope.isUserValid = function(user) {
            if(  (!!user.validUntil && user.validUntil < new Date()) && !user.admin)
                return "text-muted";
            return "dt";
        };

        $scope.openAddUserDialog = function(record, person) {
            Utils.openAddUserDialog(function(username, password, admin, validUntil) {
                var request = {
                    userId : 0,
                    username : username,
                    password : password,
                    admin : admin,
                    validUntil : validUntil
                };

                console.log("adding user");
                SecurityBack.setUser(request, function(response) {
                    updateUsers();
                });
            })
        };

        $scope.openSetValidUntilDialog = function(user) {
            Utils.openSetValidUntilDialog(user.username, function(validUntil) {
                var request = {
                    userId : user.id,
                    username : '',
                    password : '',
                    admin : false,
                    validUntil : validUntil
                };

                console.log("setting date");
                SecurityBack.setUser(request, function(response) {
                    user.validUntil = validUntil;
                });
            })
        };

        $scope.openSetPasswordDialog = function(user) {
            Utils.openSetPasswordDialog(user.username, function(password) {
                var request = {
                    userId : user.id,
                    username : '',
                    password : password,
                    admin : false,
                    validUntil : ''
                };

                console.log("setting password");
                SecurityBack.setUser(request, function(response) {
                });
            })
        };

        $scope.removeUser = function(user) {
            Utils.openConfirmDialog("Удалить пользователя " + user.username + "?", function() {
                console.log("removing user");
                SecurityBack.removeUser({id: user.id}, function(response) {
                    var i = $scope.users.indexOf(user);
                    $scope.users.splice(i,1);
                });
            })
        };

    }])
    .controller("user-dialog-controller", ['$scope', '$modalInstance', 'params', function ($scope, $modalInstance, params) {
        $scope.username = params.username;
        $scope.password = '';
        $scope.repeatPassword = '';
        $scope.validUntil = new Date();
        $scope.admin = false;

        $scope.changingPassword = params.changingPassword;
        $scope.changingValidUntil = params.changingValidUntil;
        $scope.newUser = params.newUser;

        $scope.submitButtonText = params.submitButtonText;
        $scope.modalTitle = params.title;

        $scope.isInputValid = function () {
            return $scope.password.localeCompare($scope.repeatPassword);
        };

        $scope.submit = function () {
            params.onSubmit($scope.username, $scope.password, $scope.admin, $scope.validUntil);
            $modalInstance.close();

        };

        $scope.cancel = function () {
            $modalInstance.close();
        };
    }])
    .controller("confirm-controller", ['$scope', '$modalInstance', 'params', function ($scope, $modalInstance, params) {
        $scope.submitButtonText = params.submitButtonText;
        $scope.messageText = params.messageText;
        $scope.modalTitle = params.title;

        $scope.submit = function () {
            params.onOk();
            $modalInstance.close();

        };

        $scope.cancel = function () {
            $modalInstance.close();
        };
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
            openConfirmDialog: function (messageText, onOk) {
                utils.openDialog({
                    templateUrl: "../pages/dialogs/confirm.html",
                    controller: "confirm-controller",
                    paramsObj: {
                        title: "Подтверждение",
                        submitButtonText: "OK",
                        messageText: messageText,
                        onOk: function () {
                            onOk();
                        }
                    }
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
            },
            openAddUserDialog: function (onSubmit) {
                utils.openDialog({
                    templateUrl: "../pages/dialogs/add-user.html",
                    controller: "user-dialog-controller",
                    paramsObj: {
                        title: "Новый пользователь",
                        submitButtonText: "Добавить",
                        newUser: true,
                        changingPassword: true,
                        changingValidUntil: true,
                        onSubmit: function (username, password, admin, validUntil) {
                            onSubmit(username, password, admin, validUntil);
                        }
                    }
                });
            },
            openSetValidUntilDialog: function (username, onSubmit) {
                utils.openDialog({
                    templateUrl: "../pages/dialogs/add-user.html",
                    controller: "user-dialog-controller",
                    paramsObj: {
                        title: "Установить дату окончания доступа",
                        submitButtonText: "Установить",
                        newUser: false,
                        changingPassword: false,
                        changingValidUntil: true,
                        username: username,
                        onSubmit: function (username, password, admin, validUntil) {
                            onSubmit(validUntil);
                        }
                    }
                });
            },
            openSetPasswordDialog: function (username, onSubmit) {
                utils.openDialog({
                    templateUrl: "../pages/dialogs/add-user.html",
                    controller: "user-dialog-controller",
                    paramsObj: {
                        title: "Установить пароль",
                        submitButtonText: "Установить",
                        newUser: false,
                        changingPassword: true,
                        changingValidUntil: false,
                        username: username,
                        onSubmit: function (username, password, admin, validUntil) {
                            onSubmit(password);
                        }
                    }
                });
            }
        };
        return utils;
    }])
    ;