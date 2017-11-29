var wafepa = angular.module("firstModule", ['ngRoute']);

wafepa.controller("activitiesCtrl", function($scope, $http, $location){
	//Zbog visestrukog koriscenja, korisno je izdvojiti konstante poput ove.
	var baseUrl = "/api/activities";
	
	//Inicijalizujemo promenljive na $scope.
	$scope.activities = [];
	$scope.gotActivites = false;
	
	$scope.newActivity = {};
	$scope.newActivity.name = "";
	
	//Funkcija kojom dovlacimo aktivnosti. Mogli smo iskoristiti $http poziv i van funkcije,
	//ali kako se ova funkcionalnost ponavlja, korisno je izdvojiti u posebnu metodu.
	var getActivities = function(){
		
		//Koncept obecanja. Jedna od prosledjenih funkcija ce se izvrsiti.
		//Prosledjene funkcije su samo objekti, jer su funkcije u JavaScript-u objekti.
		//Redosledom funkcija naznacavamo koja se u kom slucaju pozove.
		var promise = $http.get(baseUrl);
		promise.then(
				function uspeh(data){
					//promenljiva data ne mora da se zove tako!
					$scope.activities = data.data;
					//Ovim smo samo prikazali tabelu, kada se uspesno dobave aktivnosti.
					$scope.gotActivities = true;
					//console.log(data);
				},
				function neuspeh(data){
					//Lepo je korisnika obavestiti o gresci!
					alert("Neuspesno dobavljanje podataka.");
				}
		);
	}
	
	//Na primeru pokazano kako je poziv ka bekendu asinhron, zbog cega mi prosledjujemo
	//funkcije koje ce se izvrsiti.
	getActivities();
	console.log("Hello!");
	
	$scope.addActivity = function(){
		//Post metoda $http servisa. $scope.newActivity je objekat.
		//Posedovace samo ime aktivnosti. Nece imati id.
		$http.post(baseUrl, $scope.newActivity).then(
			function success(data){
				//Ponovo pozivamo dobavljanje aktivnosti, jer je mozda u medjuvremenu
				//doslo do izmene.
				getActivities();
			},
			function error(data){
				alert("Neuspesno dodavanje aktivnosti.");
			}
		)
	}
	//Funkcija kojom reagujemo na klik iz view-a. Pomocu $location servisa se logicki
	//premestamo na editovanje pojedinacne aktivnosti. 
	//Ono sto je bitno, je da ce, zbog toga sto smo u konfiguraciji ruta stavili dvotacku (:)
	//ispred dela rute, angular staviti id u servis $routeParams.
	$scope.editActivity = function(id){
		$location.path("/activities/edit/" + id);
	}
	
	
	$scope.deleteActivity = function(id){
		
		var promise = $http.delete(baseUrl + "/" + id);
		promise.then(
			function uspeh(data){
				getActivities();
			},
			function neuspeh(data){
				alert("Nije uspelo brisanje aktivnosti!");
			}
		);
	}
	
});

//Poseban kontroler za edit aktivnosti. Jer tako obicno i biva, da uparujemo partial sa 
//zasebnim kontrolerom.
wafepa.controller("editActivityCtrl", function($scope, $http, $routeParams, $location){
	
	//Kako je prilikom konfiguracije ruta receno da je deo rute ka partialu u kojem se kreira
	//ovaj kontroler parametar, taj parametar preuzimamo koriscenjem $routeParams servisa.
	//Par linija ispod je zakomentarisano logovanje ovog servisa u konzolu, otkomentarisati
	//radi demonstracije.
	var id = $routeParams.aid;
	var baseUrl = "/api/activities";
	
	$scope.activity = {};
	
	//console.log($routeParams);
	
	//Dobavljamo aktivnost za koju je zahtevana izmena..
	var getActivity = function(){
		
		//dodajem i / znak, jer bi inace bilo /api/activitiesID, a to bekend ne prepoznaje.
		//Sa $http gadjam rutu za dobavljanje jednog, stoga se u data nece naci niz, vec objekat.
		var promise = $http.get(baseUrl + "/" + id);
		promise.then(
			function success(obj){
				$scope.activity = obj.data;
			},
			function error(obj){
				alert("Neuspesno dobavljanje aktivnosti.");
			}
		);
	}
	
	getActivity();
	
	$scope.edit = function(){
		
		//Put izgleda onako kako bekend zahteva od njega - da bude id menjane aktivnosti
		//i da se u podacima javi sama aktivnost, koja takodje poseduje id.
		$http.put(baseUrl + "/" + id, $scope.activity).then(
			function success(data){
				//I na kraju se pomocu $location servisa premestamo na partial u kome su
				//prikazane sve aktivnosti.
				$location.path("/activities");
			},
			function error(data){
				alert("Neuspela izmena aktivnosti.");
			}
		);		
	}
});


wafepa.controller("standoviCtrl", function($scope, $http){
	
	var baseUrl = "/api/standovi";
	var baseUrlSajmovi = "/api/sajmovi"
	
	$scope.standovi = [];
	$scope.sajmovi = [];
	
	$scope.noviStand = {};
	$scope.noviStand.zakupac = "";
	$scope.noviStand.povrsina = "";
	$scope.noviStand.sajamId = 0;
	
	
	var getStandovi = function(){
		
		$http.get(baseUrl).then(
			function success(x){
				$scope.standovi = x.data;
			},
			function error(data){
				alert("Nije uspelo dobavljanje standova!");
			}
		);
	}
	
	var getSajmovi = function(){
		
		$http.get(baseUrlSajmovi).then(
			function success(x){
				$scope.sajmovi = x.data;
			},
			function error(x){
				alert("Neuspesno dobavljanje sajmova.");
			}
		);		
	}
	
	getStandovi();
	getSajmovi();
	
	$scope.add = function(){
		//console.log($scope.noviStand);
		$http.post(baseUrl, $scope.noviStand).then(
			function uspeh(data){
				getStandovi();
			},
			function neuspeh(data){
				alert("Nije uspelo dodavanje novog standa.");
				console.log(data);
			}		
		);
	}
	
});


wafepa.config(['$routeProvider', function($routeProvider) {
	$routeProvider
		.when('/', {
			templateUrl : '/app/html/partial/home.html'
		})
		.when('/activities', {
			templateUrl : '/app/html/partial/activities.html'
		})
		.when('/standovi', {
			templateUrl : '/app/html/partial/standovi.html'
		})
		.when('/activities/edit/:aid', {//Ovakvo oznacavanje sa dvotackom je novina! Ovako oznacen deo rute ce se naci u $routeParams servisu.
			templateUrl : '/app/html/partial/edit_activity.html'
		})
		.otherwise({
			redirectTo: '/'
		});
}]);

