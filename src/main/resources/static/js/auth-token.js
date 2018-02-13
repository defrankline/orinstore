(function() {
  'use strict';

  angular.module('myApp').factory('AuthToken', ['$window', '$q','API_URL','TOKEN_AUTH_USERNAME','TOKEN_AUTH_PASSWORD','TOKEN_NAME', function($window, $q,API_URL,TOKEN_AUTH_USERNAME,TOKEN_AUTH_PASSWORD,TOKEN_NAME) {

    var store  =  $window.localStorage;
    var key    =  TOKEN_NAME;
    var user   =  'orin-user';
    var username =  'username-number';

    return {
      /**
       *
       * @param null
       * returns the value of the key `auth-token` from localStorage
       *
       */
      getToken: function() {
        return store.getItem(key);
      },

      /**
       *
       * @param null
       * returns the value of the key `orin-user` from localStorage
       *
       */
      getUser: function() {
        return JSON.parse(store.getItem(user));
      },

      /**
       *
       * @param loggedUser JWTToken
       * gets the user returned from the server and stores the
       * key, value pair 'orin-user': 'loggedUser' in localStorage
       *
       */
      setUser: function(loggedUser) {
        if (loggedUser) {
          store.setItem(user, loggedUser);
        } else {
          store.removeItem(user);
        }
      },

      /**
       *
       * @param token JWTToken
       * gets the jwt token returned from the server and stores the
       * key, value pair 'auth-token': 'thetoken' in localStorage
       *
       */
      setToken: function(token) {
        if (token) {
          store.setItem(key, token);
        } else {
          store.removeItem(key);
        }
      }
    };
  }]);
})();
