function AuthCtrl ($scope,$timeout, AuthService,localStorageService) {
    $scope.title = "Authentication";

    $scope.showAlertSuccess = false;
    $scope.showAlertError = false;

    $scope.alertSuccess = function () {
        $scope.showAlertSuccess = true;
        $timeout(function () {
            $scope.showAlertSuccess = false;
            window.location = '/#!/home'
        }, 500);
    };

    $scope.alertError = function () {
        $scope.showAlertError = true;
        $timeout(function () {
            $scope.showAlertError = false;
        }, 5000);
    };

    $scope.loginDataModel = {};

    $scope.login = function () {
        AuthService.login($scope.loginDataModel,
            function (data) {
                $scope.successMessage = data.successMessage;
                $scope.user = data.user;
                localStorageService.add(localStorageKeys.USER_ID, data.user.id);
                window.location = '/#!/home';
            },
            function (error) {
                $scope.errorMessage = error.data.errorMessage;
                $scope.alertError();
            }
        );
    };
}