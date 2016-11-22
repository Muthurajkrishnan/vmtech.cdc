cdc.service('cart', function($http, $q) {
	this.vendorCart = function(userEmail) {
		return $http.get('getVendorCart?userEmail=' + userEmail);
	}
	this.vendorServiceCart = function(userEmail) {
		return $http.get('getVendorServiceCart?userEmail=' + userEmail);
	}

	this.camelize = function camelize(str) {
		return str.toLowerCase().replace( /\b\w/g, function (m) {
            return m.toUpperCase();
        });
	}
	
	this.capitalizeFirstLetter = function() {
	    return this.charAt(0).toUpperCase() + this.slice(1);
	}
});

// cdc.factory('cdcService', function($http) {
//
// var getProfileData = function(userEmail) {
//
// // Angular $http() and then() both return promises themselves
// var deferred = $q.defer();
// $http({
// method : "GET",
// url : "getProfile",
// params : {'userEmail' : userEmail}
// }).then(function(result) {
// console.log(result);
// // What we return here is the data that will be accessible
// // to us after the promise resolves
// //return result.data;
// });
// return deferred.promise;
// };
// return {
// getProfileData : getProfileData
// };
// });
