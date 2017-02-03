(function() {
    'use strict';

    angular
        .module('cs499P2App')
        .controller('LecturerDetailController', LecturerDetailController);

    LecturerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Lecturer', 'Student', 'Classes'];

    function LecturerDetailController($scope, $rootScope, $stateParams, previousState, entity, Lecturer, Student, Classes) {
        var vm = this;

        vm.lecturer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cs499P2App:lecturerUpdate', function(event, result) {
            vm.lecturer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
