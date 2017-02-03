(function() {
    'use strict';

    angular
        .module('cs499P2App')
        .controller('LecturerController', LecturerController);

    LecturerController.$inject = ['$scope', '$state', 'Lecturer'];

    function LecturerController ($scope, $state, Lecturer) {
        var vm = this;

        vm.lecturers = [];

        loadAll();

        function loadAll() {
            Lecturer.query(function(result) {
                vm.lecturers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
