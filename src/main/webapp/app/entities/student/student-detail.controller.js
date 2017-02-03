(function() {
    'use strict';

    angular
        .module('cs499P2App')
        .controller('StudentDetailController', StudentDetailController);

    StudentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Student', 'Classes', 'Lecturer'];

    function StudentDetailController($scope, $rootScope, $stateParams, previousState, entity, Student, Classes, Lecturer) {
        var vm = this;

        vm.student = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cs499P2App:studentUpdate', function(event, result) {
            vm.student = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
