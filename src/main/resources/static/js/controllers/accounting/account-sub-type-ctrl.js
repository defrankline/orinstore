function AccountSubTypeController ($scope,DataModel,AccountSubTypeService,AccountTypeService,ConfirmDialogService,$timeout) {
    $scope.title = "Account Sub-Types";
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
        AccountSubTypeService.paginated({page: $scope.currentPage, perPage: $scope.perPage}, function (data) {
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

        $scope.createItem = "Add Item";

        $scope.store = function () {
            AccountSubTypeService.save({perPage: $scope.perPage}, $scope.formDataModel,
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

        $scope.updateItem = "Update Item";

        $scope.update = function () {
            AccountSubTypeService.update({page: currentPage, perPage: perPage}, $scope.formDataModel,
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
                    $scope.errors = error.data.errors;
                    $scope.errorMessage = error.data.errorMessage;
                    $scope.alertError();
                }
            );
        };
    };

    $scope.delete = function (item, currentPage, perPage) {
        ConfirmDialogService.showConfirmDialog('Confirm Delete!', 'Are sure you want to delete '+item.name).then(function () {
                AccountSubTypeService.delete({id: item.id, page: currentPage, perPage: perPage},
                    function (data) {
                        $scope.successMessage = data.successMessage;
                        $scope.items = data.items;
                        $scope.currentPage = $scope.items.current_page;
                        $scope.alertSuccess();
                    }, function (error) {
                        $scope.errors = error.data.errors;
                        $scope.errorMessage = error.data.errorMessage;
                        $scope.alertError();
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
};

AccountSubTypeController .resolve = {
    DataModel: function (AccountSubTypeService,$timeout, $q) {
        var deferred = $q.defer();
        $timeout(function () {
            AccountSubTypeService.paginated({page: 1, perPage: 10},function (data) {
                deferred.resolve(data);
            });
        }, 900);
        return deferred.promise;
    }
};