(function() {
    'use strict';

    angular
        .module('cs499P2App')
        .controller('ClassesDetailController', ClassesDetailController);

    ClassesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Classes'];

    function ClassesDetailController($scope, $rootScope, $stateParams, previousState, entity, Classes) {
        var vm = this;

        vm.classes = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cs499P2App:classesUpdate', function(event, result) {
            vm.classes = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
