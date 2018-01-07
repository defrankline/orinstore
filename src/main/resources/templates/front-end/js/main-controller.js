(function () {
    'use strict';
    myApp.controller('MainController', MainController);
    MainController.$inject = ['$scope','$translate','API_URL'];

    function MainController($scope,$translate,API_URL) {
        $scope.changeLanguage = function (key) {
            $translate.use(key);
        };
    }
})();