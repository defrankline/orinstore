function AccountController ($scope,DataModel,AccountService,AccountSubTypeService,AccountTypeService,ConfirmDialogService,$timeout) {
    $scope.title = "Accounts";
    $scope.items = DataModel;

    $scope.alertSuccess = function () {
        $scope.showAlertSuccess = true;
        $timeout(function () {
            $scope.showAlertSuccess = false;
        }, 5000);
    };

    $scope.alertError = function () {
        $scope.showAlertError = true;
        $timeout(function () {
            $scope.showAlertError = false;
        }, 5000);
    };

    $scope.currentPage = 1;
    $scope.maxSize = 3;

    $scope.pageChanged = function () {
        AccountService.paginated({page: $scope.currentPage, perPage: $scope.perPage}, function (data) {
            $scope.items = data;
        });
    };

    $scope.showCreateForm = false;
    $scope.showList = true;
    $scope.showAddButton = true;

    $scope.create = function () {
        $scope.showCreateForm = true;
        $scope.showList = false;
        $scope.showAddButton = false;
        $scope.formDataModel = {};

        AccountTypeService.fetchAll(function (data) {
            $scope.accountTypes = data.all;
        });

        $scope.loadAccountSubTypes = function (id) {
            AccountTypeService.accountSubTypes({id: id}, function (data) {
                $scope.accountSubTypes = data.items;
            });
        };

        $scope.loadAccountGroups = function (id) {
            AccountSubTypeService.accountGroups({id: id}, function (data) {
                $scope.accountGroups = data.items;
            });
        };

        $scope.loadAccountCode = function (id) {
            AccountService.lastAccountCode({id: id}, function (data) {
                $scope.accountCode = data.code;
            });
        };

        $scope.createItem = "Add Item";

        $scope.store = function () {
            AccountService.save({perPage: $scope.perPage}, $scope.formDataModel,
                function (data) {
                    $scope.successMessage = data.successMessage;
                    $scope.items = data.items;
                    $scope.showCreateForm = false;
                    $scope.showList = true;
                    $scope.showAddButton = true;
                    $scope.errors = undefined;
                    $scope.alertSuccess();
                },
                function (error) {
                    $scope.errors = error.data.errors;
                    $scope.errorMessage = error.data.errorMessage;
                    $scope.alertError();
                }
            );
        };
    };

    $scope.edit = function (formDataModel, currentPage, perPage) {
        $scope.showEditForm = true;
        $scope.showList = false;
        $scope.showAddButton = false;

        AccountTypeService.fetchAll(function (data) {
            $scope.accountTypes = data.all;
            $scope.formDataModel = angular.copy(formDataModel);
        });

        $scope.loadAccountSubTypes = function (id) {
            AccountTypeService.accountSubTypes({id: id}, function (data) {
                $scope.accountSubTypes = data.items;
            });
        };

        $scope.loadAccountGroups = function (id) {
            AccountSubTypeService.accountGroups({id: id}, function (data) {
                $scope.accountGroups = data.items;
            });
        };

        $scope.updateItem = "Update Item";

        $scope.update = function () {
            AccountService.update({page: currentPage, perPage: perPage}, $scope.formDataModel,
                function (data) {
                    $scope.successMessage = data.successMessage;
                    $scope.items = data.items;
                    $scope.currentPage = $scope.items.current_page;
                    $scope.showEditForm = false;
                    $scope.showList = true;
                    $scope.showAddButton = true;
                    $scope.errors = undefined;
                    $scope.alertSuccess();
                },
                function (error) {
                    //console.log(error);
                    $scope.errors = error.data.errors;
                    //$scope.alertError();
                }
            );
        };
    };

    $scope.delete = function (item, currentPage, perPage) {
        ConfirmDialogService.showConfirmDialog('Confirm Delete!', 'Are sure you want to delete '+item.name).then(function () {
                AccountService.delete({id: item.id, page: currentPage, perPage: perPage},
                    function (data) {
                        $scope.successMessage = data.successMessage;
                        $scope.items = data.items;
                        $scope.currentPage = $scope.items.current_page;
                        $scope.alertSuccess();
                    }, function (error) {
                        $scope.errorMessage = error.data.errorMessage;
                        $scope.alertSuccess();
                    }
                );
            },
            function () {
                console.log("NO");
            });
    };

    $scope.close = function () {
        $scope.showCreateForm = false;
        $scope.showEditForm = false;
        $scope.showList = true;
        $scope.showAddButton = true;
    };

    $scope.toggleActive = function (id, active,account) {
        console.log(active);
        $scope.accountToActivate = {};
        $scope.accountToActivate.id = id;
        $scope.accountToActivate.active = active;
        $scope.accountToActivate.account = account;
        AccountService.toggleActive($scope.accountToActivate, function (data) {
            $scope.action = data.action;
            $scope.items = data.items;
            $scope.alertType = data.alertType;
        });
    };

};

AccountController .resolve = {
    DataModel: function (AccountService,$timeout, $q) {
        var deferred = $q.defer();
        $timeout(function () {
            AccountService.paginated({page: 1, perPage: 10},function (data) {
                deferred.resolve(data);
            });
        }, 900);
        return deferred.promise;
    }
};