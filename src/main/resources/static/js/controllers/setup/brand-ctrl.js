function BrandCtrl($scope, DataModel, BrandService, $timeout, $state, ConfirmDialogService) {
    $scope.title = "BRANDS";
    $scope.items = DataModel;

    $scope.alertSuccess = function () {
        $scope.showAlertSuccess = true;
        $timeout(function () {
            $scope.showAlertSuccess = false;
        }, 6000);
    };

    $scope.alertError = function () {
        $scope.showAlertError = true;
        $timeout(function () {
            $scope.showAlertError = false;
        }, 6000);
    };

    $scope.showCreateForm = false;
    $scope.showEditForm = false;
    $scope.showList = true;
    $scope.showAddButton = true;

    $scope.currentPage = 0;
    $scope.maxSize = 3;

    $scope.pageChanged = function () {
        var pageNumber = $scope.currentPage > 0 ? $scope.currentPage - 1 : 0;
        BrandService.paginated({page: pageNumber, perPage: $scope.perPage}, function (data) {
            $scope.items = data;
        });
    };

    $scope.create = function () {
        $scope.showCreateForm = true;
        $scope.showList = false;
        $scope.showAddButton = false;

        $scope.formDataModel = {};

        $scope.store = function () {
            BrandService.save({perPage: $scope.perPage}, $scope.formDataModel,
                function (data) {
                    $scope.successMessage = "Item Added Successfully";
                    $scope.showCreateForm = false;
                    $scope.showList = true;
                    $scope.items = data;
                    $scope.currentPage = 0;
                    $scope.showAddButton = true;
                    $scope.errors = undefined;
                    $scope.alertSuccess();
                },
                function (error) {
                    console.log(error);
                    $scope.errorMessage = "Item could be added!";
                    $scope.alertError();
                }
            );
        };
    };


    $scope.edit = function (formDataModel, currentPage, perPage) {
        $scope.showEditForm = true;
        $scope.showList = false;
        $scope.showAddButton = false;
        $scope.formDataModel = angular.copy(formDataModel);

        $scope.update = function () {
            var pageNumber = currentPage > 0 ? currentPage - 1 : 0;
            BrandService.update({page: pageNumber, perPage: perPage}, $scope.formDataModel,
                function (data) {
                    $scope.successMessage = "Item updated successfully!";
                    $scope.items = data;
                    $scope.currentPage = $scope.items.number;
                    $scope.close($scope.items.number, perPage);
                    $scope.alertSuccess();
                },
                function (error) {
                    console.log(error);
                    $scope.errorMessage = "Item Could not be deleted!";
                    $scope.alertError();
                }
            );
        };
    };


    $scope.delete = function (item, currentPage, perPage) {
        ConfirmDialogService.showConfirmDialog('Confirm Delete!', 'Are sure you want to delete ' + item.title).then(function () {
                var pageNumber = currentPage > 0 ? currentPage - 1 : 0;
                BrandService.delete({id: item.id, page: pageNumber, perPage: perPage}, function (data) {
                        $scope.successMessage = "Item Deleted Successfully";
                        $scope.items = data;
                        $scope.currentPage = $scope.items.number + 1;
                        $scope.alertSuccess();

                    }, function (error) {
                        console.log(error)
                        $scope.errorMessage = "Item could be deleted!";
                        $scope.alertError();
                    }
                );
            },
            function () {
                console.log("NO");
            });

    };

    $scope.close = function (page, perPage) {
        $scope.showCreateForm = false;
        $scope.showEditForm = false;
        $scope.showList = true;
        $scope.showAddButton = true;

        $scope.currentPage = page + 1;
        BrandService.paginated({page: page, perPage: perPage}, function (data) {
            $scope.items = data;
        });
    };
};

BrandCtrl.resolve = {
    DataModel: function (BrandService, $timeout, $q) {
        var deferred = $q.defer();
        $timeout(function () {
            BrandService.paginated({page: 0, perPage: 5}, function (data) {
                deferred.resolve(data);
            });
        }, 900);
        return deferred.promise;
    }
};