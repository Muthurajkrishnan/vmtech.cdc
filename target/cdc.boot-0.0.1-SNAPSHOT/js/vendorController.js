cdc.controller('vendorController', function($scope, $location, $resource,
		$window, $http, cart) {
	
	$('.navbar-nav li a').click(function(event) {
		$(".navbar-collapse").collapse('hide');
	});

	$scope.loginCheck = 1;
	$scope.scroll = 1;
	$scope.products = 1;
	$scope.showelectronics = 0;
	$scope.showFurnitures = 0;
	$scope.showConsumerCart = 0;
	$scope.showServices = 0;
	$scope.userName = localStorage.getItem('userName');
	$scope.userEmail = localStorage.getItem('userEmail');
	$scope.vendorLocation = '';

	// *********************** GET VENDOR CART
	$scope.vendorList = [];
	cart.vendorCart(localStorage.getItem('userEmail')).then(function(response) {
		$.each(response.data, function(index, element) {
			$scope.vendorList.push(element);
		});
	})

	if (localStorage.getItem('userType') == 'vendor') {
		$scope.showVendorHome = 1
		$scope.products = 1;
	}

	$scope.vendorHome = function() {
		$scope.showVendorHome = 1
		$scope.scroll = 1;
		$scope.showVendorOrders = 0;
		$scope.showVendorProfile = 0;
	}

	// *********************** VENDOR LOGOUT
	$scope.logout = function() {
		$scope.userName = null;
		localStorage.removeItem('userName');
		localStorage.removeItem('userEmail');
		$scope.loginCheck = 0;
		$scope.cartCount = 0;
		localStorage.removeItem('userType');
		$location.path('/cdc');
	}

	// *********************** APPROVE QUOTE
	$scope.vendorList = [];
	$scope.approveQuote = function(consumerPriceId) {
		$scope.vendorList = [];
		var data = new FormData();
		data.append('consumerPriceId', consumerPriceId);
		$http.post('approveUserPrice', data, {
			withCredentials : false,
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			}
		}).success(
				function(resp) {
					cart.vendorCart(localStorage.getItem('userEmail')).then(
							function(response) {
								$.each(response.data, function(index, element) {
									$scope.vendorList.push(element);
								});
							});
				}).error(function(response) {
		});
		bootbox.alert('Thanks, user will be notified to initate the payment');
	}

	// *********************** VENDOR REJECTION
	$scope.vendorCartList = [];
	$scope.parent = {};
	$scope.vendorRejection = function(consumerPriceId) {
		$scope.vendorList = [];
		bootbox.alert('Thanks for your suggestion, we will inform the user');

		var data = new FormData();
		data.append('consumerPriceId', consumerPriceId);
		data.append('suggestedPrice', $scope.parent.suggestedPrice);
		data.append('rejectReason', $scope.parent.rejectReason);
		data.append('vendorReason', $scope.parent.vendorReason);
		$http.post('rejectUserPrice', data, {
			withCredentials : false,
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			}
		}).success(
				function(resp) {
					cart.vendorCart(localStorage.getItem('userEmail')).then(
							function(response) {
								$.each(response.data, function(index, element) {
									$scope.vendorList.push(element);
								});
							})
				}).error(function(response) {
		});
	}

	// ***************************** GET VENDOR ORDERS
	$scope.vendorOrderList = [];
	$scope.vendorOrders = function() {
		$scope.vendorOrderList = [];
		$scope.showVendorHome = 0;
		$scope.showVendorOrders = 1;
		$http.get('getVendorOrders', {
			params : {
				'userEmail' : localStorage.getItem('userEmail')
			}
		}).then(function(response) {
			$.each(response.data, function(index, element) {
				$scope.vendorOrderList.push(element);
			});
		});
	}

	// ************************ DISPATCH ORDER
	$scope.dispatchProduct = function(consumerPriceID) {
		$scope.vendorOrderList = [];
		$scope.showVendorHome = 0;
		$scope.showVendorOrders = 1;
		var data = new FormData();
		data.append('userEmail', localStorage.getItem('userEmail'));
		data.append('consumerPriceID', consumerPriceID);

		$http.post('dispatchOrder', data, {
			withCredentials : false,
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			}
		}).success(function() {
			bootbox.alert('User will be notified with the order status');
			$scope.vendorOrders();
		})
	}

	// **************************** GET VENDOR PROFILE
	$scope.getVendorProfile = function() {
		$scope.showVendorHome = 0;
		$scope.showVendorOrders = 0;
		$scope.showVendorProfile = 1;
		$scope.scroll = 0;
		$http.get('getProfile', {
			params : {
				'userEmail' : localStorage.getItem('userEmail')
			}
		}).then(function(response) {
			$scope.profileData = response.data;
		});
	}

});
