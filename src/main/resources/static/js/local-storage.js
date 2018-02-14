var angularLocalStorage = angular.module('orin.localStorage', []);
angularLocalStorage.constant('prefix', 'orin');
angularLocalStorage.constant('cookie', {expiry: 30, path: '/'});
angularLocalStorage.service('localStorageService', [
    '$rootScope',
    'prefix',
    'cookie',
    function ($rootScope, prefix, cookie) {
        if (prefix.substr(-1) !== '.') {
            prefix = !!prefix ? prefix + '.' : '';
        }

        var browserSupportsLocalStorage = function () {
            try {
                return ('localStorage' in window && window['localStorage'] !== null);
            } catch (e) {
                $rootScope.$broadcast('LocalStorageModule.notification.error', e.Description);
                return false;
            }
        };

        var addToLocalStorage = function (key, value) {

            if (!browserSupportsLocalStorage()) {
                $rootScope.$broadcast('LocalStorageModule.notification.warning', 'LOCAL_STORAGE_NOT_SUPPORTED');
                return false;
            }

            if (!value && value !== 0 && value !== "") return false;

            try {
                localStorage.setItem(prefix + key, value);
            } catch (e) {
                $rootScope.$broadcast('LocalStorageModule.notification.error', e.Description);
                return false;
            }
            return true;
        };

        var getFromLocalStorage = function (key) {
            if (!browserSupportsLocalStorage()) {
                $rootScope.$broadcast('LocalStorageModule.notification.warning', 'LOCAL_STORAGE_NOT_SUPPORTED');
                return false;
            }

            var item = localStorage.getItem(prefix + key);
            if (!item) return null;
            return item;
        };

        var removeFromLocalStorage = function (key) {
            if (!browserSupportsLocalStorage()) {
                $rootScope.$broadcast('LocalStorageModule.notification.warning', 'LOCAL_STORAGE_NOT_SUPPORTED');
                return false;
            }

            try {
                localStorage.removeItem(prefix + key);
            } catch (e) {
                $rootScope.$broadcast('LocalStorageModule.notification.error', e.Description);
                return false;
            }
            return true;
        };

        var clearAllFromLocalStorage = function () {

            if (!browserSupportsLocalStorage()) {
                $rootScope.$broadcast('LocalStorageModule.notification.warning', 'LOCAL_STORAGE_NOT_SUPPORTED');
                return false;
            }

            var prefixLength = prefix.length;

            for (var key in localStorage) {
                if (key.substr(0, prefixLength) === prefix) {
                    try {
                        removeFromLocalStorage(key.substr(prefixLength));
                    } catch (e) {
                        $rootScope.$broadcast('LocalStorageModule.notification.error', e.Description);
                        return false;
                    }
                }
            }
            return true;
        };

        // Checks the browser to see if cookies are supported
        var browserSupportsCookies = function () {
            try {
                return navigator.cookieEnabled ||
                    ("cookie" in document && (document.cookie.length > 0 ||
                        (document.cookie = "test").indexOf.call(document.cookie, "test") > -1));
            } catch (e) {
                $rootScope.$broadcast('LocalStorageModule.notification.error', e.Description);
                return false;
            }
        };

        var addToCookies = function (key, value) {

            if (typeof value == "undefined") return false;

            if (!browserSupportsCookies()) {
                $rootScope.$broadcast('LocalStorageModule.notification.error', 'COOKIES_NOT_SUPPORTED');
                return false;
            }

            try {
                var expiry = '', expiryDate = new Date();
                if (value === null) {
                    cookie.expiry = -1;
                    value = '';
                }
                if (cookie.expiry !== 0) {
                    expiryDate.setTime(expiryDate.getTime() + (cookie.expiry * 24 * 60 * 60 * 1000));
                    expiry = "; expires=" + expiryDate.toGMTString();
                }
                document.cookie = prefix + key + "=" + encodeURIComponent(value) + expiry + "; path=" + cookie.path;
            } catch (e) {
                $rootScope.$broadcast('LocalStorageModule.notification.error', e.Description);
                return false;
            }
            return true;
        };

        var getFromCookies = function (key) {
            if (!browserSupportsCookies()) {
                $rootScope.$broadcast('LocalStorageModule.notification.error', 'COOKIES_NOT_SUPPORTED');
                return false;
            }

            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var thisCookie = cookies[i];
                while (thisCookie.charAt(0) == ' ') {
                    thisCookie = thisCookie.substring(1, thisCookie.length);
                }
                if (thisCookie.indexOf(prefix + key + '=') === 0) {
                    return decodeURIComponent(thisCookie.substring(prefix.length + key.length + 1, thisCookie.length));
                }
            }
            return null;
        };

        var removeFromCookies = function (key) {
            addToCookies(key, null);
        };

        var clearAllFromCookies = function () {

            var thisCookie = null, thisKey = null;
            var prefixLength = prefix.length;
            var cookies = document.cookie.split(';');

            for (var i = 0; i < cookies.length; i++) {
                thisCookie = cookies[i];
                while (thisCookie.charAt(0) == ' ') {
                    thisCookie = thisCookie.substring(1, thisCookie.length);
                }
                key = thisCookie.substring(prefixLength, thisCookie.indexOf('='));
                removeFromCookies(key);
            }
        };

        return {
            isSupported: browserSupportsLocalStorage,
            add: addToLocalStorage,
            get: getFromLocalStorage,
            remove: removeFromLocalStorage,
            clearAll: clearAllFromLocalStorage,
            cookie: {
                add: addToCookies,
                get: getFromCookies,
                remove: removeFromCookies,
                clearAll: clearAllFromCookies
            }
        };

    }]);
