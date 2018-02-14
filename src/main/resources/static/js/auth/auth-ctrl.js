function AuthCtrl($scope, localStorageService, AuthService, $http) {

    $scope.user = {};

    $scope.login = function () {

        var formData = {
            'username': $scope.user.username,
            'password': $scope.user.password,
            'grant_type': 'password',
        };

        var API_URL = 'http://127.0.0.1:9000/oauth/token';
        var TOKEN_AUTH_USERNAME = 'frankkachinga';
        var TOKEN_AUTH_PASSWORD = 'frnakkachinga';


        var config = {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;',
                'Authorization': 'Basic ' + btoa(TOKEN_AUTH_USERNAME + ':' + TOKEN_AUTH_PASSWORD),
            }
        };

        $http.post(API_URL, formData, config).then(function (response) {
            //success
                console.log(response);
        }, function (response) {
            //error
        });

        /*AuthService.login($scope.formData,
            function (response) {
                console.log(response);
                /!*$scope.successMessage = data.successMessage;
                $scope.user = data.user;
                localStorageService.add(localStorageKeys.USER, data.user);
                localStorageService.add(localStorageKeys.ACCESS_TOKEN, data.user.id);
                window.location = '/#!/home';*!/
            },
            function (error) {
                console.log(error);
            }
        );*/
    };
}