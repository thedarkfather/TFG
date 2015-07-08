/**
 * 
 */
var teamController = angular.module('predictorum.teamController',['predictorum.teamService']);

teamController.controller('teamController',function($scope,$location,teamService){
	
	if($location.path().includes('favorites')){
		$scope.tab = {current: ''};
		$scope.tab.favorites = true;
	}else{
		$scope.tab = {current: ''};
		$scope.tab.all = true;
	}
	
	$scope.orderProp ="teamPosition";
	teamService.findAll().then(function(result){
		$scope.teams = result.data;
	});
	
	$scope.follow = function(team){
		teamService.switchFollow(team).then(function(result){
			if(result.data.success){
				team.isFollow = !team.isFollow;
			}
		});
	};
	
	$scope.switchTab = function(tab){
		
		if(tab==='Favorites'){
			$scope.tab.favorites=true;
			$scope.tab.all = false;
			$scope.tab.current = '';
		}else{
			$scope.tab.current = tab;
			$scope.tab.favorites=undefined;
			if(tab===''){
				$scope.tab.all = true;
			}else{
				$scope.tab.all = false;
			}
		}
	};

});