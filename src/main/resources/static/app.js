var myApp = angular.module('myApp', ['ui.router','services', 'ngResource', 'angular.filter', 'ngAria', 'ngAnimate','ui.select2','orin.localStorage',
    'ngMessages', 'ngLoadingSpinner','ngSanitize', 'ui.bootstrap','pascalprecht.translate', 'ngFileUpload','ngCookies','ngMaterial']);

myApp.config(function($stateProvider,$urlRouterProvider,$translateProvider) {

    $translateProvider.useSanitizeValueStrategy('sanitize');
    $translateProvider.translations('en', translationsEN);
    $translateProvider.translations('sw', translationsSW);
    $translateProvider.preferredLanguage('en');
    $translateProvider.useCookieStorage();

    $urlRouterProvider.otherwise('/home');

    var homeState = {
        name: 'home',
        url: '/home',
        templateUrl: '/templates/home.html',
        controller: HomeCtrl,
    };

    var aboutState = {
        name: 'about',
        url: '/about',
        templateUrl: '/templates/about.html',
        controller: AboutCtrl,
    };

    var brandState = {
        name: 'brands',
        url: '/brands',
        templateUrl: '/templates/setup/brands.html',
        controller: BrandCtrl,
        resolve: BrandCtrl.resolve,
    };

    var roleState = {
        name: 'roles',
        url: '/roles',
        templateUrl: '/templates/auth/roles.html',
        controller: RoleCtrl,
        resolve: RoleCtrl.resolve,
    };

    var productCategoryState = {
        name: 'product-categories',
        url: '/product-categories',
        templateUrl: '/templates/setup/product-categories.html',
        controller: ProductCategoryCtrl,
        resolve: ProductCategoryCtrl.resolve,
    };

    var productState = {
        name: 'products',
        url: '/products',
        templateUrl: '/templates/setup/products.html',
        controller: ProductCtrl,
        resolve: ProductCtrl.resolve,
    };

    var customerState = {
        name: 'customers',
        url: '/customers',
        templateUrl: '/templates/pos/customers.html',
        controller: CustomerCtrl,
        resolve: CustomerCtrl.resolve,
    };

    var saleState = {
        name: 'sales',
        url: '/sales',
        templateUrl: '/templates/pos/sales.html',
        controller: SaleCtrl,
        resolve: SaleCtrl.resolve,
    };

    $stateProvider.state(saleState);
    $stateProvider.state(customerState);
    $stateProvider.state(productState);
    $stateProvider.state(productCategoryState);
    $stateProvider.state(brandState);
    $stateProvider.state(homeState);
    $stateProvider.state(aboutState);
    $stateProvider.state(roleState);

    $stateProvider.state("otherwise", {url: '/home'});
});

myApp.constant("API_URL","http://127.0.0.1:9000");

myApp.run(function ($window, $rootScope, $interval, PingService) {
    var ping = function () {
        PingService.ping({noLoader: true}, function (status) {
            $rootScope.online = true;
        }, function (error) {
            $rootScope.online = false;
        });
    };
    $rootScope.pageLoaded = true;
    $rootScope.online = navigator.onLine;
    $window.addEventListener("offline", function () {
        $rootScope.$apply(function () {
            $rootScope.online = false;
        });
    }, false);

    $window.addEventListener("online", function () {
        $rootScope.$apply(function () {
            $rootScope.online = true;
        });
    }, false);
});

myApp.directive("perPage", function () {
    return {
        restrict: "E",
        templateUrl: '/templates/per-page.html',
        require: 'ngModel',
        link: function (scope, element, attrs, ctrl) {
            scope.perPageNumberChange = function (perPageNumber) {
                ctrl.$setViewValue(perPageNumber);
            };
            scope.perPageOptions = [10,25, 50, 100, 250, 500, 1000];
            scope.perPageNumber = scope.perPageOptions[0];
        }
    };
});

myApp.directive("totalPages", function () {
    return {
        restrict: "E",
        template: '<span class="total-pages">Page: {{currentPage}} of {{totalPages(totalItems/perPage)}}</span>',
        link: function (scope, element, attrs, ctrl) {
            scope.totalPages = function (totalPages) {
                pages = Math.ceil(totalPages);
                return pages;
            };
            scope.currentPage = attrs.currentpage;
            scope.totalItems = attrs.totalitems;
            scope.perPage = attrs.perpage;
        }
    };
});

myApp.factory("ConfirmDialogService", ["$q", "$uibModal", function ($q, $uibModal) {
    var _showConfirmDialog = function (title, message) {
        var defer = $q.defer();

        var modalInstance = $uibModal.open({
            animation: true,
            size: "lg",
            backdrop: false,
            templateUrl: '/templates/confirm-dialog.html',
            controller: function ($scope, $uibModalInstance) {
                $scope.title = title;
                $scope.message = message;

                $scope.ok = function () {
                    $uibModalInstance.close();
                    defer.resolve();
                };

                $scope.cancel = function () {
                    $uibModalInstance.dismiss();
                    defer.reject();
                };
            }
        });

        return defer.promise;
    };

    return {
        showConfirmDialog: _showConfirmDialog
    };

}]);

myApp.directive('pagetitle', function () {
    return {
        restrict: 'E',
        scope: {
            title: '@',
        },
        templateUrl: '/templates/pagetitle.html'
    };
});