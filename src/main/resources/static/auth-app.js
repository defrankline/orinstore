var authApp = angular.module('authApp', ['ui.router','ngResource','auth-services','orin.localStorage']);

authApp.config(function($stateProvider,$urlRouterProvider) {

    $urlRouterProvider.otherwise('/login');

    var loginState = {
        name: 'login',
        url: '/login',
        templateUrl: '/templates/login.html',
        controller: AuthCtrl,
        resolve: AuthCtrl.resolve,
    };

    $stateProvider.state(loginState);

    $stateProvider.state("otherwise", {url: '/login'});
});

authApp.constant("API_URL","http://127.0.0.1:9000");
authApp.constant("TOKEN_AUTH_USERNAME","frankkachinga");
authApp.constant("TOKEN_AUTH_PASSWORD","fankkachinga");
authApp.constant("TOKEN_NAME","access_token");
authApp.constant("GET_TOKEN_URL","http://127.0.0.1:9000/oauth/token");