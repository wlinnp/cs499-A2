(function() {
    'use strict';
    angular
        .module('cs499P2App')
        .factory('Classes', Classes);

    Classes.$inject = ['$resource'];

    function Classes ($resource) {
        var resourceUrl =  'api/classes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
