var authService = angular.module('auth-services', ['ngResource']);

authService.factory('AuthService', ['$resource','API_URL', function ($resource,API_URL) {
    return $resource(API_URL+'/api/ping/server', {}, {
        login: {
            method: 'POST',
            url: API_URL+'/api/users/login'
        }
    });
}]);
