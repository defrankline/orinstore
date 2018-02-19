function SaleCtrl($scope, DataModel, SaleService, CustomerService, $http, SaleItemService, ProductService, $timeout, $state, ConfirmDialogService) {
    $scope.title = "SALES";
    $scope.items = DataModel;


    $scope.totalTax = function () {
        var total = 0;
        angular.forEach($scope.items.content, function (value, key) {
            total += parseFloat(value.tax);
        });
        return total;
    };

    $scope.totalNet = function () {
        var total = 0;
        angular.forEach($scope.items.content, function (value, key) {
            total += parseFloat(value.netAmount);
        });
        return total;
    };

    $scope.grossSales = function () {
        var total = 0;
        angular.forEach($scope.items.content, function (value, key) {
            total += parseFloat(value.netAmount + value.tax);
        });
        return total;
    };

    $scope.getTotalGroupBy = function (values) {
        var total = 0;
        if (values !== undefined && values.length > 0) {
            for (var i = 0; i < values.length; i++) {
                var item = values[i];
                total += parseFloat(item.tax + item.netAmount);
            }
        }
        return total;
    };

    $scope.$watch('items', function () {
        $scope.totalTax();
        $scope.totalNet();
        $scope.grossSales();
    });

    $scope.alertSuccess = function () {
        $scope.showAlertSuccess = true;
        $timeout(function () {
            $scope.showAlertSuccess = false;
        }, 10000);
    };

    $scope.alertError = function () {
        $scope.showAlertError = true;
        $timeout(function () {
            $scope.showAlertError = false;
        }, 10000);
    };

    $scope.showCreateForm = false;
    $scope.showEditForm = false;
    $scope.showList = true;
    $scope.showAddButton = true;
    $scope.showItems = false;

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
            SaleService.save({perPage: $scope.perPage},$scope.sale,
                function (data) {
                    angular.forEach($scope.invoice.items, function (item) {
                        $scope.saleItems = {
                            'price': item.product.price,
                            'qty': item.qty,
                            'product': item.product,
                            'sale': data.sale,
                        };
                        SaleItemService.save($scope.saleItems,
                            function (data) {

                            }, function (error) {

                            }
                        )
                    });

                    $scope.successMessage = data.successMessage;
                    $scope.showCreateForm = false;
                    $scope.showList = true;
                    $scope.items = data.items;
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

    $scope.saleItems = function (sale) {
        $scope.showItems = true;
        $scope.showList = false;
        $scope.showEditForm = false;
        $scope.showAddButton = false;
        $scope.sale = sale;

        SaleService.items({saleId: sale.id}, function (data) {
            $scope.itms = data.items;
        }, function (error) {
            console.log(error);
        });

        $scope.saleTotal = function () {
            var total = 0;
            for (var i = 0; i < $scope.itms.length; i++) {
                var item = $scope.itms[i];
                var price = item.price;
                var qty = item.qty;
                total += parseFloat(price * qty);
            }
            return total;
        };

        $scope.$watch('itms', function () {
            $scope.saleTotal();
        });

    };


    $scope.edit = function (formDataModel,currentPage,perPage) {
        $scope.showEditForm = true;
        $scope.showList = false;
        $scope.showAddButton = false;

        CustomerService.query(function (data) {
            $scope.customers = data;
            $scope.formDataModel = angular.copy(formDataModel);
        });

        $scope.update = function () {
            var pageNumber = currentPage > 0 ? currentPage - 1 : 0;
            SaleService.update({page: pageNumber, perPage: perPage},$scope.formDataModel,
                function (data) {
                    $scope.successMessage = "Sale Record updated successfully!";
                    $scope.items = data;
                    $scope.currentPage = $scope.items.number;
                    $scope.close($scope.items.number, perPage);
                    $scope.alertSuccess();
                },
                function (error) {
                    $scope.errorMessage = "Sale Record Could not be Updated!";
                    $scope.alertError();
                }
            );
        };
    };


    $scope.delete = function (item, currentPage, perPage) {
        ConfirmDialogService.showConfirmDialog('Confirm Delete!', 'Are sure you want to delete ' + item.title).then(function () {
                var pageNumber = currentPage > 0 ? currentPage - 1 : 0;
                SaleService.delete({id: item.id, page: pageNumber, perPage: perPage}, function (data) {
                        $scope.items = data;
                        $scope.currentPage = $scope.items.number + 1;
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

    $scope.close = function (page, perPage) {
        $scope.showCreateForm = false;
        $scope.showEditForm = false;
        $scope.showItems = false;
        $scope.showList = true;
        $scope.showAddButton = true;

        $scope.currentPage = page + 1;
        SaleService.paginated({page: page, perPage: perPage}, function (data) {
            $scope.items = data;
        });
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