var cdc = angular.module('cdc', [ 'ngRoute', 'ngResource', 'autocomplete']);

cdc.config(function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : "index.html",
		controller : "productController"
	}).when('/cdc', {
		templateUrl : "views/home.html",
		controller : "productController"
	}).when('/ecom', {
		templateUrl : "views/welcome.html",
		controller : "productController"
	}).when('/vendorHome', {
		templateUrl : "views/vendorHome.html",
		controller : "vendorController"
	}).when('/vendorServiceHome', {
		templateUrl : "views/vendorServiceHome.html",
		controller : "vendorController"
	}).when('/signup', {
		templateUrl : "views/home.html",
		controller : "productController"
	}).when('/electronics', {
		templateUrl : "views/electronics.html",
		controller : "productController"
	}).when('/reviews', {
		templateUrl : "views/reviews.html",
		controller : "reviewsController"
	}).otherwise({
		redirectTo : '/cdc'
	});
});

cdc.factory('AutoRetriever', function($http, $q, $timeout) {
	var AutoRetriever = new Object();

	AutoRetriever.getmovies = function(i, category) {
		var suggestedData = $q.defer();
		var dataString;
		var suggestedString = [];
		$http.get('getSuggestion').then(function(response) {
			$.each(response.data, function(
					index, elements) {
				suggestedString.push(elements.suggestionString);
			});	
		})

		if (i && i.indexOf(suggestedString) != -1) {
			dataString = suggestedString;
		}
		else {
			dataString = suggestedString;
		}

		$timeout(function() {
			suggestedData.resolve(dataString);
		}, 1000);

		return suggestedData.promise
	}

	return AutoRetriever;
});