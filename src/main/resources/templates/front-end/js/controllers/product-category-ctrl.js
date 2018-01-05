function ProductCategoryCtrl($scope, DataModel,ProductCategoryService, $timeout, $state, ConfirmDialogService) {
    $scope.title = "Product Categories";
    $scope.items = DataModel;

    $scope.alertSuccess = function () {
        $scope.showAlertSuccess = true;
        $timeout(function () {
            $scope.showAlertSuccess = false;
        }, 6000);
        $state.reload();
    };

    $scope.alertError = function () {
        $scope.showAlertError = true;
        $timeout(function () {
            $scope.showAlertError = false;
        }, 6000);
        $state.reload();
    };

    $scope.showCreateForm = false;
    $scope.showEditForm = false;
    $scope.showList = true;
    $scope.showAddButton = true;

    $scope.currentPage = 0;
    $scope.maxSize = 3;

    $scope.pageChanged = function () {
        var pageNumber = $scope.currentPage > 0 ? $scope.currentPage - 1 : 0;
        ProductCategoryService.paginated({page: pageNumber, perPage: $scope.perPage}, function (data) {
            $scope.items = data;
        });
    };

    $scope.create = function () {
        $scope.showCreateForm = true;
        $scope.showList = false;
        $scope.showAddButton = false;

        $scope.formDataModel = {};

        $scope.store = function () {
            ProductCategoryService.save($scope.formDataModel,
                function (data) {
                    $scope.successMessage = "Item Added Successfully";
                    $scope.showCreateForm = false;
                    $scope.showList = true;
                    $scope.showAddButton = true;
                    $scope.errors = undefined;
                    $scope.alertSuccess();
                },
                function (error) {
                    $scope.errorMessage = "Item could be added!";
                    $scope.alertError();
                }
            );
        };
    };


    $scope.edit = function (formDataModel) {
        $scope.showEditForm = true;
        $scope.showList = false;
        $scope.showAddButton = false;
        $scope.formDataModel = angular.copy(formDataModel);

        $scope.update = function () {
            ProductCategoryService.update($scope.formDataModel,
                function (data) {
                    $scope.successMessage = "Item updated successfully!";
                    $scope.alertSuccess();
                },
                function (error) {
                    $scope.errorMessage = "Item Could not be deleted!";
                    $scope.alertError();
                }
            );
        };
    };


    $scope.delete = function (item) {
        ConfirmDialogService.showConfirmDialog('Confirm Delete!', 'Are sure you want to delete ' + item.title).then(function () {
                ProductCategoryService.delete({id: item.id}, function (data) {
                        $scope.successMessage = "Item Deleted Successfully";
                        $scope.alertSuccess();

                    }, function (error) {
                        $scope.errorMessage = "Item could be deleted!";
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
        /*$state.reload();*/
    };
};

ProductCategoryCtrl.resolve = {
    DataModel: function (ProductCategoryService, $timeout, $q) {
        var deferred = $q.defer();
        $timeout(function () {
            ProductCategoryService.paginated({page: 0, perPage: 5}, function (data) {
                deferred.resolve(data);
            });
        }, 900);
        return deferred.promise;
    }
};