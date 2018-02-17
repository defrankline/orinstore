function AccountGroupController($scope, DataModel, AccountGroupService, AccountReportService, AccountTypeService, ConfirmDialogService, $timeout) {
    $scope.title = "Account Groups";
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

    $scope.alertToggle = function () {
        $scope.showAlertToggle = true;
        $timeout(function () {
            $scope.showAlertToggle = false;
        }, 3000);
    };

    $scope.currentPage = 1;
    $scope.maxSize = 3;

    $scope.pageChanged = function () {
        AccountGroupService.paginated({page: $scope.currentPage, perPage: $scope.perPage}, function (data) {
            $scope.items = data;
        });
    };

    $scope.showCreateForm = false;
    $scope.showReportForm = false;
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

        $scope.loadAccountGroupCode = function (id) {
            AccountGroupService.lastAccountGroupCode({id: id}, function (data) {
                $scope.groupCode = data.code;
            });
        };

        $scope.createItem = "Add Item";

        $scope.store = function () {
            console.log("CONTRA: "+$scope.formDataModel.contra);
            AccountGroupService.save({perPage: $scope.perPage}, $scope.formDataModel,
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

    $scope.toggleContra = function (id, contra,account) {
        $scope.accountToActivate = {};
        $scope.accountToActivate.id = id;
        $scope.accountToActivate.contra = contra;
        $scope.accountToActivate.account = account;
        AccountGroupService.toggleContra($scope.accountToActivate, function (data) {
            $scope.action = data.action;
            $scope.items = data.items;
            $scope.alertType = data.alertType;
            $scope.alertToggle();
        });
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

        $scope.updateItem = "Update Item";

        $scope.update = function () {
            console.log("CONTRA: "+$scope.formDataModel.contra);
            AccountGroupService.update({page: currentPage, perPage: perPage}, $scope.formDataModel,
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
                    $scope.errorMessage = error.data.errorMessage;
                    $scope.alertError();
                }
            );
        };
    };

    $scope.accountGroupReports = function (formDataModel) {
        $scope.showReportForm = true;
        $scope.showList = false;
        $scope.showCreateForm = false;
        $scope.showEditForm = false;
        $scope.showAddButton = false;

        AccountGroupService.reports({id: formDataModel.id}, function (data) {
            $scope.reports = data.items;
        });

        $scope.account_group_id = formDataModel.id;
        $scope.account_group = formDataModel.name;

        AccountReportService.fetchAll(function (data) {
            $scope.accountReports = data.all;
        });

        $scope.accountGroupReportTile = "Reports";

        $scope.addReport = function () {
            AccountGroupService.addReport({account_group_id: $scope.account_group_id}, $scope.formDataModel, function (data) {
                    $scope.successMessage = data.successMessage;
                    $scope.reports = data.items;
                    $scope.errors = undefined;
                    $scope.alertSuccess();
                },
                function (error) {
                    //console.log(error);
                    $scope.errors = error.data.errors;
                    $scope.errorMessage = error.data.errorMessage;
                    $scope.alertError();
                }
            );
        };

        $scope.removeReport = function (item) {
            AccountGroupService.removeReport({account_group_id: $scope.account_group_id, item_id: item.id}, function (data) {
                    $scope.successMessage = data.successMessage;
                    $scope.reports = data.items;
                    $scope.errors = undefined;
                    $scope.alertSuccess();
                },
                function (error) {
                    //console.log(error);
                    $scope.errors = error.data.errors;
                    $scope.errorMessage = error.data.errorMessage;
                    $scope.alertError();
                }
            );
        };
    };

    $scope.delete = function (item, currentPage, perPage) {
        ConfirmDialogService.showConfirmDialog('Confirm Delete!', 'Are sure you want to delete ' + item.name).then(function () {
                AccountGroupService.delete({id: item.id, page: currentPage, perPage: perPage},
                    function (data) {
                        $scope.successMessage = data.successMessage;
                        $scope.items = data.items;
                        $scope.currentPage = $scope.items.current_page;
                        $scope.alertSuccess();
                    }, function (error) {
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
        $scope.showReportForm = false;
        $scope.showEditForm = false;
        $scope.showList = true;
        $scope.showAddButton = true;
    };
};

AccountGroupController.resolve = {
    DataModel: function (AccountGroupService,$timeout, $q) {
        var deferred = $q.defer();
        $timeout(function () {
            AccountGroupService.paginated({page: 1, perPage: 10}, function (data) {
                deferred.resolve(data);
            });
        }, 900);
        return deferred.promise;
    }
};