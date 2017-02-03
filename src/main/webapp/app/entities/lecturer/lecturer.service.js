(function() {
    'use strict';
    angular
        .module('cs499P2App')
        .factory('Lecturer', Lecturer);

    Lecturer.$inject = ['$resource'];

    function Lecturer ($resource) {
        var resourceUrl =  'api/lecturers/:id';

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
