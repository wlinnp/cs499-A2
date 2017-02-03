(function() {
    'use strict';

    angular
        .module('cs499P2App')
        .controller('ClassesDialogController', ClassesDialogController);

    ClassesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Classes'];

    function ClassesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Classes) {
        var vm = this;

        vm.classes = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.classes.id !== null) {
                Classes.update(vm.classes, onSaveSuccess, onSaveError);
            } else {
                Classes.save(vm.classes, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cs499P2App:classesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
