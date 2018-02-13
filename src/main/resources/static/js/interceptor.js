(function() {
    'use strict';

    angular.module('myApp').factory('AuthInterceptor', ['$rootScope', '$window', 'AuthToken','API_URL','TOKEN_AUTH_USERNAME','TOKEN_AUTH_PASSWORD','TOKEN_NAME', function($rootScope, $window, AuthToken,API_URL,TOKEN_AUTH_USERNAME,TOKEN_AUTH_PASSWORD,TOKEN_NAME){
        return {
            /**
             * @param config Object
             * hijacks all the http requests and set the config.hearders.Authorization to 'Bearer ' + token
             * and returns the modified config object
             */
            request: function(config) {

                var token = AuthToken.getToken();

                if (token) {
                    config.headers = config.headers || {};
                    config.headers.Authorization = 'Basic ' + btoa(TOKEN_AUTH_USERNAME + ':' + TOKEN_AUTH_PASSWORD);
                }
                return config;
            },

            responseError: function(response) {

                var access_token = response.access_token;

                if (access_token === '' || access_token === null || access_token === undefined) {
                    $rootScope.$broadcast('Unauthorized');
                } else {
                    $rootScope.$broadcast('Authorized');
                }
                return response;
            }
        };
    }]);
})();
