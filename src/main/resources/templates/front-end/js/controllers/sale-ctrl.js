function SaleCtrl($scope, DataModel, SaleService, CustomerService,SaleItemService, ProductService, $timeout, $state, ConfirmDialogService) {
    $scope.title = "SALES";
    $scope.items = DataModel;

    $scope.alertSuccess = function () {
        $scope.showAlertSuccess = true;
        $timeout(function () {
            $scope.showAlertSuccess = false;
        }, 10000);
        $state.reload();
    };

    $scope.alertError = function () {
        $scope.showAlertError = true;
        $timeout(function () {
            $scope.showAlertError = false;
        }, 10000);
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
        SaleService.paginated({page: pageNumber, perPage: $scope.perPage}, function (data) {
            $scope.items = data;
        });
    };

    $scope.create = function () {
        $scope.showCreateForm = true;
        $scope.showList = false;
        $scope.showAddButton = false;

        $scope.formDataModel = {};

        $scope.paymentModes = {
            'CASH': 'Cash',
            'CREDIT': 'On Credit',
        };

        CustomerService.query(function (data) {
            $scope.customers = data;
        });

        ProductService.query(function (data) {
            $scope.products = data;
        });

        $scope.invoice = {
            items: [{
                product: {},
                qty: 1
            }]
        };
        $scope.cart = function () {
            $scope.invoice.items.push({
                product: {},
                qty: 1
            });
        };
        $scope.decart = function (index) {
            $scope.invoice.items.splice(index, 1);
        };
        $scope.net = function () {
            var total = 0;
            angular.forEach($scope.invoice.items, function (item) {
                total += item.qty * item.product.price;
            });
            return total;
        };

        $scope.vat = function (net) {
            var percentage = 0.18;
            return Number(net * percentage);
        };

        $scope.total = function (net) {
            var percentage = 0.18;
            var vat = net * percentage;
            return Number(net + vat);
        };

        $scope.store = function () {
            $scope.sale = {
                'netAmount': $scope.net(),
                'tax': $scope.vat($scope.net()),
                'saleDate': $scope.formDataModel.saleDate,
                'customer': $scope.formDataModel.customer,
                'paid': $scope.formDataModel.paid,
            };
            //console.log($scope.postData);
            SaleService.save($scope.sale,
                function (data) {
                    angular.forEach($scope.invoice.items, function (item) {
                        $scope.saleItems = {
                            'price': item.product.price,
                            'qty': item.qty,
                            'product': item.product,
                            'sale': data,
                        };
                        SaleItemService.save($scope.saleItems,
                            function (data) {

                            }, function (error) {

                            }
                        )
                    });

                    $scope.successMessage = "Sale Recorded Successfully";
                    $scope.showCreateForm = false;
                    $scope.showList = true;
                    $scope.showAddButton = true;
                    $scope.errors = undefined;
                    $scope.alertSuccess();
                },
                function (error) {
                    $scope.errorMessage = "Sale could be recorded!";
                    $scope.alertError();
                }
            );
        };
    };


    $scope.edit = function (formDataModel) {
        $scope.showEditForm = true;
        $scope.showList = false;
        $scope.showAddButton = false;

        CustomerService.query(function (data) {
            $scope.customers = data;
            $scope.formDataModel = angular.copy(formDataModel);
        });

        $scope.update = function () {
            SaleService.update($scope.formDataModel,
                function (data) {
                    $scope.successMessage = "Sale Record updated successfully!";
                    $scope.alertSuccess();
                },
                function (error) {
                    $scope.errorMessage = "Sale Record Could not be Updated!";
                    $scope.alertError();
                }
            );
        };
    };


    $scope.delete = function (item) {
        ConfirmDialogService.showConfirmDialog('Confirm Delete!', 'Are sure you want to delete ' + item.title).then(function () {
                SaleService.delete({id: item.id}, function (data) {
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

SaleCtrl.resolve = {
    DataModel: function (SaleService, $timeout, $q) {
        var deferred = $q.defer();
        $timeout(function () {
            SaleService.paginated({page: 0, perPage: 5}, function (data) {
                deferred.resolve(data);
            });
        }, 900);
        return deferred.promise;
    }
};