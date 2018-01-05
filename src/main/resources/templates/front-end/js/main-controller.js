(function () {
    'use strict';
    myApp.controller('MainController', MainController);
    MainController.$inject = ['$scope','$translate'];

    function MainController($scope,$translate) {
        $scope.changeLanguage = function (key) {
            $translate.use(key);
        };
    }
})();