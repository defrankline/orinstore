(function () {
    'use strict';
    myApp.controller('MainController', MainController);
    MainController.$inject = ['$scope', '$translate', 'API_URL', '$localStorage', '$sessionStorage'];

    function MainController($scope, $translate, API_URL, $localStorage, $sessionStorage) {
        $scope.changeLanguage = function (key) {
            $translate.use(key);
        };

        $scope.logout = function () {
            alert("Logout!");
        }
    }
})();