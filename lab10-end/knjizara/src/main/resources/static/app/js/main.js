var knjizaraApp = angular.module("knjizaraApp", ['ngRoute']);

knjizaraApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/',{
        templateUrl: '/app/html/partial/knjige.html'
    }).when('/knjige/edit/:id',{
        templateUrl: '/app/html/partial/edit-knjiga.html'
    }).otherwise({
        redirectTo: '/'
    });
}]);

knjizaraApp.controller("knjigeCtrl", function($scope, $http, $location){

	var baseUrlIzdavaci = "/api/izdavaci";
    var baseUrlKnjige = "/api/knjige";
    

    $scope.promeniRezim = function(){
        $scope.rezimDodavanja = !$scope.rezimDodavanja;
    };
    $scope.rezimDodavanja = true;

    $scope.pageNum = 0;
    $scope.totalPages = 0;

    $scope.izdavaci = [];
    $scope.knjige = [];

    $scope.novaKnjiga = {};
    $scope.novaKnjiga.naziv = "";
    $scope.novaKnjiga.pisac = "";
    $scope.novaKnjiga.isbn = "";
    $scope.novaKnjiga.kolicina = "";
    $scope.novaKnjiga.cena = "";
    $scope.novaKnjiga.izdavacId = "";


    $scope.trazenaKnjiga = {};
    $scope.trazenaKnjiga.naziv = "";
    $scope.trazenaKnjiga.pisac = "";
    $scope.trazenaKnjiga.maxKolicina = "";

    var getKnjige = function(){

        var config = {params: {}};

        config.params.pageNum = $scope.pageNum;

        if($scope.trazenaKnjiga.naziv != ""){
            config.params.naziv = $scope.trazenaKnjiga.naziv;
        }

        if($scope.trazenaKnjiga.pisac != ""){
            config.params.pisac = $scope.trazenaKnjiga.pisac;
        }

        if($scope.trazenaKnjiga.maxKolicina != ""){
            config.params.maxKolicina = $scope.trazenaKnjiga.maxKolicina;
        }


        $http.get(baseUrlKnjige, config)
            .then(function success(data){
                $scope.knjige = data.data;
                $scope.totalPages = data.headers('totalPages');

            });
    };

    var getIzdavaci = function(){

        $http.get(baseUrlIzdavaci)
            .then(function success(data){
                $scope.izdavaci = data.data;
            });

    };

    getIzdavaci();
    getKnjige();
   

    $scope.nazad = function(){
        if($scope.pageNum > 0) {
            $scope.pageNum = $scope.pageNum - 1;
            getKnjige();
        }
    };

    $scope.napred = function(){
        if($scope.pageNum < $scope.totalPages - 1){
            $scope.pageNum = $scope.pageNum + 1;
            getKnjige();
        }
    };

    $scope.dodaj = function(){
        $http.post(baseUrlKnjige, $scope.novaKnjiga)
            .then(function success(data){
            	getKnjige();

                $scope.novaKnjiga.naziv = "";
                $scope.novaKnjiga.pisac = "";
                $scope.novaKnjiga.isbn = "";
                $scope.novaKnjiga.kolicina = "";
                $scope.novaKnjiga.cena = "";
                $scope.novaKnjiga.izdavacId = "";
            });
    };

    $scope.trazi = function () {
        $scope.pageNum = 0;
        getKnjige();
    }

    $scope.izmeni = function(id){
        $location.path('/knjige/edit/' + id);
    }

    $scope.obrisi = function(id){
        $http.delete(baseUrlKnjige + "/" + id).then(
            function success(data){
            	getKnjige();
            },
            function error(data){
                alert("Neuspesno brisanje!");
            }
        );
    }
    
    $scope.kupi = function(id){
    	$http.post(baseUrlKnjige + "/" + id + "/kupovina").then(
    		function success(data){
    			alert("Knjiga je uspesno kupljena.");
    			getKnjige();
    		},
    		function error(data){
    			alert("Nije uspela kupovina knjige.")
    		}
    	)
    }
});

knjizaraApp.controller("editKnjigaCtrl", function($scope, $http, $routeParams, $location){

    var baseUrlKnjige = "/api/knjige";

    $scope.staraKnjiga = null;

    var getStaraKnjiga = function(){

        $http.get(baseUrlKnjige + "/" + $routeParams.id)
            .then(
            	function success(data){
            		$scope.staraKnjiga = data.data;
            	},
            	function error(data){
            		alert("Neušpesno dobavljanje knjige.");
            	}
            );

    }
    getStaraKnjiga();
    
    $scope.izmeni = function(){
        $http.put(baseUrlKnjige + "/" + $scope.staraKnjiga.id, $scope.staraKnjiga)
            .then(
        		function success(data){
        			alert("Uspešno izmenjen objekat!");
        			$location.path("/");
        		},
        		function error(data){
        			alert("Neuspešna izmena knjige.");
        		}
            );
    }
});












