function AuthCtrl($scope, localStorageService, AuthService, $http) {

    $scope.user = {};

    $scope.login = function () {

        $scope.formData = {
            'username': $scope.user.username,
            'password': $scope.user.password
        };

        AuthService.login($scope.formData,
            function (response) {
                console.log(response);
                /*$scope.successMessage = data.successMessage;
                $scope.user = data.user;
                localStorageService.add(localStorageKeys.USER, data.user);
                localStorageService.add(localStorageKeys.ACCESS_TOKEN, data.user.id);
                window.location = '/#!/home';*/
            },
            function (error) {
                console.log(error);
            }
        );
    };
}