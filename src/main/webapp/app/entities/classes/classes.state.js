(function() {
    'use strict';

    angular
        .module('cs499P2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('classes', {
            parent: 'entity',
            url: '/classes',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Classes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/classes/classes.html',
                    controller: 'ClassesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('classes-detail', {
            parent: 'entity',
            url: '/classes/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Classes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/classes/classes-detail.html',
                    controller: 'ClassesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Classes', function($stateParams, Classes) {
                    return Classes.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'classes',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('classes-detail.edit', {
            parent: 'classes-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/classes/classes-dialog.html',
                    controller: 'ClassesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Classes', function(Classes) {
                            return Classes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('classes.new', {
            parent: 'classes',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/classes/classes-dialog.html',
                    controller: 'ClassesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                lecturer: null,
                                major: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('classes', null, { reload: 'classes' });
                }, function() {
                    $state.go('classes');
                });
            }]
        })
        .state('classes.edit', {
            parent: 'classes',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/classes/classes-dialog.html',
                    controller: 'ClassesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Classes', function(Classes) {
                            return Classes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('classes', null, { reload: 'classes' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('classes.delete', {
            parent: 'classes',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/classes/classes-delete-dialog.html',
                    controller: 'ClassesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Classes', function(Classes) {
                            return Classes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('classes', null, { reload: 'classes' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
