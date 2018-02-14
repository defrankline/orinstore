var authService = angular.module('auth-services', ['ngResource']);

authService.factory('AuthService', ['$resource','API_URL','TOKEN_AUTH_USERNAME','TOKEN_AUTH_PASSWORD', function ($resource,API_URL,TOKEN_AUTH_USERNAME,TOKEN_AUTH_PASSWORD) {
    return $resource(API_URL+'/api/ping/server', {}, {
        login: {
            method: 'POST',
            url: API_URL+'/oauth/token',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'Authorization': 'Basic ' + btoa(TOKEN_AUTH_USERNAME + ':' + TOKEN_AUTH_PASSWORD),
            }
        }
    });
}]);
