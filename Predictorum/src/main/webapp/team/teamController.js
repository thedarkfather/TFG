/**
 * 
 */
var teamController = angular.module('predictorum.teamController',['predictorum.teamService']);

teamController.controller('teamController',function($scope,$location,$routeParams,$translate,teamService){
	
	//LIST
	
	if($location.path().includes('favorites')){
		$scope.tab = {current: ''};
		$scope.tab.favorites = true;
	}else{
		$scope.tab = {current: ''};
		$scope.tab.all = true;
	}
	
	if($location.path().includes('list')){
		$scope.orderProp ="teamPosition";
		teamService.findAll().then(function(result){
			$scope.teams = result.data;
		});
	}
	
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
	
	//STATISTICS
	
	if($location.path().includes('statistics')){
		teamService.getStatistics($routeParams.teamId).then(function(result){
			$scope.team = result.data;
			$scope.dataResults = [ 
			{
				value : $scope.team.lostMatchPercentage,
				color : '#F7464A',
				highlight : '#FF5A5E',
			},
			{
				value : $scope.team.drawMatchPercentage,
				color : '#DAA520',
				highlight : '#F0C861',
			},
			{
				value : $scope.team.wonMatchPercentage,
				color : '#82dc23',
				highlight : '#B2E67A',
			}];
			$scope.labelGoals = $translate.instant('GOALS');
			$scope.labelWonMatches = $translate.instant('WON_MATCHES');
			$scope.labelLostMatches = $translate.instant('LOST_MATCHES');
			$scope.dataBar =  {
				      labels: [$scope.labelGoals,$scope.labelWonMatches,$scope.labelLostMatches],
				      datasets: [
				        {
				          fillColor: '#8ABBEE',
					      strokeColor: '#548DCA',
					      highlightFill: '#ABCDF1',
					      highlightStroke: '#548DCA',
				          data: [$scope.team.homeGoals,$scope.team.homeWonMatches,$scope.team.homeLostMatches]
				        },
				        {
				          fillColor: '#4FDCBB',
				          strokeColor: '#32BB9B',
				          highlightFill: '#7FEAD1',
				          highlightStroke: '#548DCA',
				          data: [$scope.team.awayGoals,$scope.team.awayWonMatches,$scope.team.awayLostMatches]
				        }
				      ]
				    };
		});
	}
	
	$scope.getBadgeColor = function (charIndex) {
		var result = {};
		if($scope.team.streak.charAt(charIndex)==='d'){
			result = {'background-color' : '#d12d32'};
		}else if($scope.team.streak.charAt(charIndex)==='x'){
			result = {'background-color': '#DAA520'};
		}
		return result;
	}
	
	// Chart.js Doughnut Graph Options
	$scope.doughnut_options = {

			// Sets the chart to be responsive
			responsive : true,

			// Boolean - Whether we should show a stroke on each segment
			segmentShowStroke : true,

			// String - The colour of each segment stroke
			segmentStrokeColor : '#fff',

			// Number - The width of each segment stroke
			segmentStrokeWidth : 2,

			// Number - The percentage of the chart that we cut out of the middle
			percentageInnerCutout : 40, // This is 0 for Pie charts

			// Number - Amount of animation steps
			animationSteps : 100,

			// String - Animation easing effect
			animationEasing : 'easeOutBounce',

			// Boolean - Whether we animate the rotation of the Doughnut
			animateRotate : true,

			// Boolean - Whether we animate scaling the Doughnut from the centre
			animateScale : false,

		};
	
	// Chart.js Bar Graph Options
    $scope.bar_options =  {

      // Sets the chart to be responsive
      responsive: true,

      //Boolean - Whether the scale should start at zero, or an order of magnitude down from the lowest value
      scaleBeginAtZero : true,

      //Boolean - Whether grid lines are shown across the chart
      scaleShowGridLines : true,

      //String - Colour of the grid lines
      scaleGridLineColor : "rgba(0,0,0,.05)",

      //Number - Width of the grid lines
      scaleGridLineWidth : 1,

      //Boolean - If there is a stroke on each bar
      barShowStroke : true,

      //Number - Pixel width of the bar stroke
      barStrokeWidth : 2,

      //Number - Spacing between each of the X value sets
      barValueSpacing : 5,

      //Number - Spacing between data sets within X values
      barDatasetSpacing : 1,

       };
	

});