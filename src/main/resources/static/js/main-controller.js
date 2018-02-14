(function () {
    'use strict';
    myApp.controller('MainController', MainController);
    MainController.$inject = ['$scope', '$translate', 'API_URL','localStorageService'];

    function MainController($scope, $translate, API_URL, localStorageService) {
        $scope.changeLanguage = function (key) {
            $translate.use(key);
        };

        $scope.logout = function () {
            alert("Logout!");
        }
    }
})();