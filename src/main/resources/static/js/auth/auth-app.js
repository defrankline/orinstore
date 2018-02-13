var authApp = angular.module('authApp', ['ui.router','ngResource','auth-services','ngStorage']);

authApp.config(function($stateProvider,$urlRouterProvider) {

    $urlRouterProvider.otherwise('/login');

    var loginState = {
        name: 'login',
        url: '/login',
        template: '<form class="form-signin">\n' +
        '    <h2 class="form-signin-heading">Login</h2>\n' +
        '    <label for="inputEmail" class="sr-only">Email address</label>\n' +
        '    <input type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus/>\n' +
        '    <label for="inputPassword" class="sr-only">Password</label>\n' +
        '    <input type="password" id="inputPassword" class="form-control" placeholder="Password" required />\n' +
        '    <div class="checkbox">\n' +
        '        <label>\n' +
        '            <input type="checkbox" value="remember-me"> Remember me\n' +
        '        </label>\n' +
        '    </div>\n' +
        '    <button class="btn btn-lg btn-primary btn-block" ng-click="login()" type="button">Sign in</button>\n' +
        '</form>',
        controller: AuthCtrl,
        resolve: AuthCtrl.resolve,
    };

    $stateProvider.state(loginState);

    $stateProvider.state("otherwise", {url: '/login'});
});

authApp.constant("API_URL","http://127.0.0.1:9000");