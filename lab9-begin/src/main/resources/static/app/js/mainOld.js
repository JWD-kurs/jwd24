var wafepaApp = angular.module('wafepaApp', []);
wafepaApp.controller('myCtrl', function($scope){
	$scope.poruka="Hello world";
	$scope.pozdravi=function () {
		alert("Zdravo!!!");
	}
	$scope.izmena=function () {
		console.log($scope.poruka);
	}
	$scope.upaljeno=true;
	$scope.promeni=function(){
		$scope.upaljeno = !$scope.upaljeno;
	}
});
wafepaApp.controller('newCtrl', function(){
});