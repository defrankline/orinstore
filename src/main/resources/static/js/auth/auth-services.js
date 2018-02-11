var authService = angular.module('auth-services', ['ngResource']);

var API_URL = "http://127.0.0.1:9000";

authService.factory('AuthService', ['$resource', function ($resource) {
    return $resource(API_URL+'/api/ping/server', {}, {
        login: {method: 'POST', url: API_URL+'/api/users/login'},
        signup: {method: 'POST', url: API_URL+'/api/users/signup'},
    });
}]);
