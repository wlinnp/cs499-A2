(function() {
    'use strict';

    angular
        .module('cs499P2App')
        .controller('ClassesDeleteController',ClassesDeleteController);

    ClassesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Classes'];

    function ClassesDeleteController($uibModalInstance, entity, Classes) {
        var vm = this;

        vm.classes = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Classes.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
