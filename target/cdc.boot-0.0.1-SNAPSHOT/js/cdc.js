var cdc = angular.module('cdc', [ 'ngRoute', 'ngResource' ]);

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
