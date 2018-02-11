function AuthCtrl ($scope,$timeout, AuthService,localStorageService) {
    $scope.title = "Authentication";

    $scope.login = function () {
        alert("login");
    }
}