cdc
		.controller(
				'productController',
				function($scope, $location, $resource, $window, $http, cart) {
					//
					// $('#categories').click(function(event) {
					// $(".navbar-collapse").collapse('show');
					// })
									
					$('#homeMenu').click(function(event) {
						$(".navbar-collapse").collapse('hide');
					});
					
					$('.navbar-nav li a').click(function(evt) {
							$(".navbar-collapse").collapse('hide');
						});


					$scope.homeClicked = 1;
					$scope.loginCheck = 0;
					$scope.scroll = 1;
					$scope.products = 0;
					$scope.showelectronics = 0;
					$scope.showFurnitures = 0;
					$scope.showConsumerCart = 0;
					$scope.showUserQuotes = 0;
					$scope.showMyProfile = 0;
					$scope.showAcceptedQuotes = 0;
					$scope.showOrderedItems = 0;
					$scope.showServices = 0;
					$scope.productDisplay = 0;
					$scope.serviceDisplay = 0;
					$scope.homeDisplay = 0;
					$scope.userName = localStorage.getItem('userName');
					$scope.userEmail = localStorage.getItem('userEmail');
					// $scope.vendorCartList =
					// localStorage.getItem('vendorCart');
					$scope.vendorLocation = '';
					$scope.cartCount = 0;
					$scope.userEmailCheck = 0;
					$scope.cartByUser = [];
					$scope.phoneNumber;
					$scope.searchMessage = ''

					// MOVE THIS TO DB LATER
					var productString = [ 'GADGETS', 'FURNITURES', 'CAR',
							'BIKE', 'BABY', 'BEAUTY', 'CLOTHING',
							'ACCESSORIES', 'PRODUCT' ];
					var serviceString = [ 'LAUNDRY', 'CLEANING', 'HOUSE',
							'SERVICES' ];

					// Build the auto suggestion, later pull this from DB
					var suggestedList = {};

					// PAGINATION
					$scope.curPage = 0;
					$scope.pageSize = 4;

					$scope.numberOfPages = function(list) {
						if (list) {
							return Math.ceil(list.length / $scope.pageSize);
						}
					};

					// Sets the price edit as readonly
					$scope.editPrice = false;
					if (localStorage.getItem('userType') == 'consumer') {
						$scope.showVendorHome = 0
						$scope.products = 0;
						$scope.cartCount = localStorage.getItem('cartLength');
					} else {
						$scope.showVendorHome = 1
						$scope.products = 1;
					}

					if (localStorage.getItem('userName')) {
						$scope.loginCheck = 1;
					}

					// HOME PAGE CALL TO DECIDE THE ROUTING OF THE USER
					$scope.shopIt = function() {
						$scope.searchMessage = '';
						var selectedText = document
								.getElementById('input.suggest').value;
						var selectedTextList = selectedText.split(' ');
						if (selectedTextList) {
							for (i = 0; i < selectedTextList.length; i++) {
								if (productString.indexOf(selectedTextList[i]
										.toUpperCase()) > -1) {
									$scope.productDisplay = 1;
									$scope.serviceDisplay = 0;
									$scope.homeDisplay = 1;
									break;
								} else if (serviceString
										.indexOf(selectedTextList[i]
												.toUpperCase()) > -1) {
									$scope.serviceDisplay = 1;
									$scope.productDisplay = 0;
									$scope.homeDisplay = 1;
									break;
								} else {
									$scope.searchMessage = 'Please refine your search';
								}
							}
						}
					}

					$scope.home = function() {
						$scope.consumerCart();
						$scope.scroll = 1;
						$scope.products = 0;
						$scope.showelectronics = 0;
						$scope.showFurnitures = 0;
						$scope.products = 0;
						$scope.services = 0;
						$scope.homeClicked = 1;
						$scope.showReviews = 0;
						$scope.showServices = 0;
						$scope.showConsumerCart = 0;
						$scope.showReviewItems = 0;
						$scope.productDisplay = 0;
						$scope.serviceDisplay = 0;
						$scope.homeDisplay = 0;
						$scope.searchMessage = '';
						if (localStorage.getItem('userType') == 'vendor')
							$location.path('/vendorHome');
						else
							$location.path('/cdc');
					}

					// **************** REVIEWS
					$scope.reviews = function() {
						$window.scrollTo(0, 0);
						// $scope.consumerCart();
						$scope.scroll = 0;
						$scope.products = 0;
						$scope.showelectronics = 0;
						$scope.showFurnitures = 0;
						$scope.showOrderedItems = 0;
						$scope.showAcceptedQuotes = 0;
						$scope.showUserQuotes = 0;
						$scope.showServices = 0;
						$scope.showMyProfile = 0;
						$scope.showConsumerCart = 0;
						$scope.showReviews = 1;
						$scope.showReviewItems = 0;

						/*
						 * if (localStorage.getItem('userType') == 'vendor')
						 * $location.path('/vendorHome'); else
						 * $location.path('/cdc');
						 */
					}

					// *************** GET DETAIL FOR THE SELECTED ORDERS
					$scope.getOrderDetails = function(reviewId) {
						$window.scrollTo(0, 0);
						$scope.showOrders = 0;
						$scope.showReviews = 1;
						$scope.orderDetails = [];
						$scope.showReviewItems = 0;
						$http.get(
								'/getOrderDetails',
								{
									params : {
										'userEmail' : localStorage
												.getItem('userEmail'),
										'reviewId' : reviewId
									}
								}).then(
								function(response) {
									$.each(response.data, function(index,
											elements) {
										elements.rating = '';
										$scope.orderDetails.push(elements);
									});

									// $scope.consumerCartList = response.data;
									$scope.orderLegth = response.data.length;
									localStorage.setItem('orderLength',
											$scope.orderLegth);
									$scope.orderCount2 = $scope.orderLegth;
									// $scope.orderReview.rating = '';
								});
					}

					// ******************** UDPATE REVIEWS AND DISPLAY IT IN
					// REVIEW PAGE
					$scope.parent = {};
					$scope.updateReviews = function(reviewId, rating) {
						$scope.rating = 5;
						$window.scrollTo(0, 0);
						$scope.showOrders = 0;
						$scope.showReviews = 1;
						$scope.orderReviewDetails = [];
						$scope.showReviewItems = 0;
						var data = new FormData();
						data.append('userEmail', localStorage
								.getItem('userEmail'));
						data.append('reviewId', reviewId);
						data.append('rating', rating);
						data.append('comments', $scope.parent.reviewcomments);
						data.append('reviewtitle', $scope.parent.reviewtitle); 
						$http.post('updateReviews', data, {
							withCredentials : false,
							transformRequest : angular.identity,
							headers : {
								'Content-Type' : undefined
							}
						}).success(function(response) {
							// console.log(response.data);
							// $.each(response.data,
							// function(index, element) {
							// console.log(element);
							// $scope.orderReviewDetails
							// .push(element);
							// });
							// // $scope.consumerCartList = response.data;
							// console.log($scope.orderReviewDetails);
							// $scope.orderLegth = response.data.length;
							// localStorage.setItem('orderLength',
							// $scope.orderLegth);
							// $scope.orderCount1 = $scope.orderLegth;
							$scope.getOrders();

						});
					}

					// ******************** PICK ALL ORDERS AND DISPLAY FOR
					// REVIEW
					// $window.onload
					$scope.getOrders = function() {
						// $window.onload = function() {
						$window.scrollTo(0, 0);
						$scope.showOrders = 0;
						$scope.showReviews = 1;
						$scope.orderAllList = [];
						$scope.showReviewItems = 0;
						$http.get('getOrders', {
							params : {
								'userEmail' : localStorage.getItem('userEmail')
							}
						}).then(
								function(response) {
									$.each(response.data, function(index,
											element) {
										$scope.orderAllList.push(element);
									});

									// $scope.consumerCartList = response.data;
									$scope.orderLegth = response.data.length;
									localStorage.setItem('orderLength',
											$scope.orderLegth);
									$scope.orderCount = $scope.orderLegth;
								});
					}

					// ***************************** DISPLAY REVIEWS AGAINST
					// PRODUCT
					// ab19000
					$scope.ratings = [ {
						current : 5,
						max : 5
					} ];

					$scope.avgPseudoRatingList = [];
					$scope.showReviewItems = 0;
					// ***************************** GET ELECTRONICS PRODUCTS
					// REVIEWS
					$scope.shopElectronicsReview = function(product_id) {
						$window.scrollTo(0, 0);
						$scope.showelectronics = 0;
						$scope.showConsumerCart = 0;
						$scope.showFurnitures = 0;
						$scope.showReviews = 0;
						$scope.showOrderedItems = 0;
						$scope.showServices = 0;
						$scope.scroll = 0;
						$scope.products = 1;
						$scope.showReviewItems = 1;
						$scope.electronicProductReviewList = [];
						$http
								.get('getProductReviews', {
									params : {
										'productid' : product_id
									}
								})
								.then(
										function(response) {
											$scope.electronicProductReviewListLength = response.data.length;
											$scope.electronicProductReviewList = response.data;
											console
													.log($scope.electronicProductReviewList);
											$scope.avgPseudoRatingList = [ {
												avgrate : $scope.electronicProductReviewList[0].product.avgrating
											} ];
										});
					}
					// ends

					// ********************************8 VENDOR HOME
					$scope.vendorHome = function() {
						$scope.vendorCart();
						// $scope.vendorCart();
						// $scope.vendorCartList =
						// localStorage.getItem('vendorCart');
						$scope.showVendorHome = 1;
						$scope.showVendorOrders = 0;
						$scope.showVendorProducts = 0;
						$scope.showReviewItems = 0;
						if (localStorage.getItem('userType') == 'consumer')
							$location.path('/cdc');
					}

					$scope.successRateCheck = function() {
						$scope.successRatePer = 82;
					}

					// ****************************** Populating the product
					// list for electronics
					$scope.productList = {
						'type' : 'select',
						'name' : 'product',
						'value' : 'Electronics',
						'values' : [ 'Tablet', 'Computer', 'Laptop' ]
					};

					// *****************************LOGIN
					$scope.vendorCartList = [];
					$scope.login = function() {
						var data = new FormData();
						data.append('userEmail', $scope.email);
						data.append('userPassword', $scope.password);
						$http
								.post('login', data, {
									withCredentials : false,
									transformRequest : angular.identity,
									headers : {
										'Content-Type' : undefined
									}
								})
								.success(
										function(resp) {
											if (resp.length > 0) {
												$scope.userName = resp[0].userName;
												$scope.userID = resp[0].userID;
												localStorage.setItem(
														'userName',
														$scope.userName);
												localStorage.setItem('userID',
														$scope.userID);
												localStorage.setItem(
														'userEmail',
														$scope.email);
												$scope.userType = resp[0].userType;
												localStorage.setItem(
														'userType',
														$scope.userType);
												$scope.loginCheck = 1;
												$('.modal-backdrop').remove();
												$('.in').remove();
												$scope.homeClicked = 0;
												$scope.userEmailCheck = 1;
												if (resp[0].userType == 'vendor') {
													// $scope.vendorCart()
													// localStorage.setItem('vendorCartList',
													// JSON.stringify($scope.vendorCartList));
													// $scope.vendorCartList =
													// localStorage.getItem('vendorCart');
													cart
															.vendorCart(
																	localStorage
																			.getItem('userEmail'))
															.then(
																	function(
																			response) {
																		$
																				.each(
																						response.data,
																						function(
																								index,
																								element) {
																							$scope.vendorCartList
																									.push(element);
																						});
																	})
													$location
															.path('/vendorHome');
												} else {
													$scope.consumerCart();
													$scope.cartCount = localStorage
															.getItem('cartLength');
													$scope.scroll = 1;
													$scope.products = 1;
													$scope.showelectronics = 0;
													$scope.showFurnitures = 0;
													$scope.showReviews = 0;
													$scope.showServices = 0;
													$scope.showConsumerCart = 0;

													$location.path('/cdc');
												}
												$window.scrollTo(0, 0);
											} else {
												$scope.errorMessage = 'Invalid Login';
											}
										}).error(function() {
									$scope.errorMessage = 'Invalid Login';
								});
					}

					// ***************************** SIGN UP
					$scope.signup = function() {
						var data = new FormData();
						data.append('userName', $scope.signupFullname);
						data.append('userEmail', $scope.signupEmail);
						data.append('userPassword', $scope.signupPassword);
						data.append('vendorLocation', $scope.vendorLocation);
						data.append('serviceType', $scope.serviceType);
						data.append('proser', $scope.proser);
						if ($scope.signupEmail != null
								&& $scope.signupPassword != null) {
							$http(
									{
										url : 'getProfile',
										method : "GET",
										dataType : "json",
										headers : {
											'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
										},
										params : {
											'userEmail' : $scope.signupEmail
										}
									})
									.then(
											function(response) {
												if (response.data.userID == 0) {
													$http
															.post(
																	'signup',
																	data,
																	{
																		withCredentials : false,
																		transformRequest : angular.identity,
																		headers : {
																			'Content-Type' : undefined
																		}
																	})
															.success(
																	function(
																			resp) {
																		$(
																				'.modal-backdrop')
																				.remove();
																		bootbox
																				.alert('Please check your email to authenticate/activate your Email ID');
																		$location
																				.path('/cdc');
																		// $('#signupConfirmation').modal('show');
																	})
															.error(
																	function() {
																		console
																				.log('My Error');
																		$location
																				.path('/cdc');
																	});
												} else {
													$scope.errorMessage = 'User already exists. Please try again'
												}
											})
						}
					}

					// ***************************** LOGOUT
					$scope.logout = function() {
						$scope.userName = null;
						localStorage.removeItem('userName');
						localStorage.removeItem('userEmail');
						$scope.loginCheck = 0;
						$scope.cartCount = 0;
						if (localStorage.getItem('userType') == 'consumer') {
							localStorage.removeItem('userType')
							localStorage.removeItem('CartLength');
							$scope.home();
							$location.path('/cdc');
						}

						else {
							localStorage.removeItem('userType');
							$location.path('/cdc');
						}

					}

					$scope.parent = {};
					// ***************************** GET ELECTRONICS PRODUCTS
					$scope.shopElectronics = function() {
						$window.scrollTo(0, 0);
						$scope.showelectronics = 1;
						$scope.showConsumerCart = 0;
						$scope.showFurnitures = 0;
						$scope.showOrderedItems = 0;
						$scope.showReviews = 0;
						$scope.showServices = 0;
						$scope.scroll = 0;
						$scope.products = 1;
						$scope.showReviewItems = 0;
						$scope.electronicProductList = [];
						$http
								.get('getProducts', {
									params : {
										'category' : 'PRODUCTS',
										'subcategory' : 'ELECTRONICS'
									}
								})
								.then(
										function(response) {
											$scope.electronicProductListLength = response.data.length;
											$scope.electronicProductList = response.data;
										});
					}

					// **************************** GET FURNITURES PRODUCTS
					$scope.shopFurnitures = function() {
						$window.scrollTo(0, 0);
						$scope.showFurnitures = 1;
						$scope.showConsumerCart = 0;
						$scope.showelectronics = 0;
						$scope.showOrderedItems = 0;
						$scope.showReviews = 0;
						$scope.showServices = 0;
						$scope.scroll = 0;
						$scope.products = 1;
						$scope.showReviewItems = 0;
						$scope.furnitureProductList = [];
						$http
								.get('getProducts', {
									params : {
										'category' : 'PRODUCTS',
										'subcategory' : 'FURNITURES'
									}
								})
								.then(
										function(response) {
											$scope.furnitureProductListLength = response.data.length;
											$scope.furnitureProductList = response.data;
										});
					}

					// ***************************** GET SERVICES CART
					$scope.shopServices = function() {
						$window.scrollTo(0, 0);
						$scope.showConsumerCart = 0;
						$scope.showFurnitures = 0;
						$scope.showelectronics = 0;
						$scope.showOrderedItems = 0;
						$scope.showReviews = 0;
						$scope.showServices = 1;
						$scope.scroll = 0;
						$scope.products = 1;
						$scope.showReviewItems = 0;
						$scope.servicesProductList = [];
						$http
								.get('getProducts', {
									params : {
										'category' : 'SERVICES',
										'subcategory' : '%'
									}
								})
								.then(
										function(response) {
											$scope.servicesProductListLength = response.data.length;
											$scope.servicesProductList = response.data;
										});
					}

					// ***************************** DISPLAY CONSUMER CART
					$scope.consumerCart = function() {
						$window.scrollTo(0, 0);
						$scope.showConsumerCart = 1;
						$scope.showFurnitures = 0;
						$scope.showelectronics = 0;
						$scope.showUserQuotes = 0;
						$scope.showMyProfile = 0;
						$scope.showAcceptedQuotes = 0;
						$scope.showOrderedItems = 0;
						$scope.showReviews = 0;
						$scope.showServices = 0;
						$scope.scroll = 0;
						$scope.products = 1;
						$scope.showReviewItems = 0;
						$scope.consumerCartList = [];
						$http.get('getConsumerCart', {
							params : {
								'userEmail' : localStorage.getItem('userEmail')
							}
						}).then(
								function(response) {
									$.each(response.data, function(index,
											element) {
										$scope.consumerCartList.push(element);
									});

									// $scope.consumerCartList = response.data;
									$scope.cartLegth = response.data.length;
									localStorage.setItem('cartLength',
											$scope.cartLegth);
									$scope.cartCount = $scope.cartLegth;
								});
					}

					// ***************************** VENDOR ORDERS
					$scope.vendorOrders = function() {
						$scope.showVendorHome = 0;
						$scope.showVendorOrders = 1;
						$scope.showVendorProducts = 0;
					}

					// Func to check if the user is signed in
					$scope.productPrice = function() {
						if (localStorage.getItem('userEmail') == null) {
							$('.modal-backdrop').remove();
							bootbox
									.alert('Please check your email to authenticate/activate your Email ID');
						}

					}

					// ************************ SUBMITTING THE ITEMS TO CART
					$scope.parent = {};
					$scope.submitQuote = function(productID) {
						if (localStorage.getItem('userEmail')) {
							var data = new FormData();
							data.append('userName', localStorage
									.getItem('userName'));
							data.append('userEmail', localStorage
									.getItem('userEmail'));
							data.append('productID', productID);
							data.append('userPrice', $scope.parent.priceQuote);
							data.append('quantity', $scope.parent.quantity);
							$http
									.post('submitUserQuote', data, {
										withCredentials : false,
										transformRequest : angular.identity,
										headers : {
											'Content-Type' : undefined
										}
									})
									.success(
											function(resp) {
												console
														.log('submitQuote Success'
																+ resp);
												$('.modal-backdrop').remove();
												$scope.cartCount = Number($scope.cartCount) + 1;
												$scope.quoteOrdered = true;
												$scope.priceQuote = '';
												bootbox
														.alert('Your purchase is added to the cart.');
												// $scope.parent.userMessage =
												// "Thanks for your Quote, we
												// will
												// notify you through
												// email/mobile.";

											})
									.error(
											function() {
												$scope.userMessage = "There is some issue with the quote submission. Please try again."
											});
						} else {
							bootbox.alert('Please login to proceed');
						}
					}

					// ************************ SUBMITTING THE ITEMS FROM CART
					// TO ORDER
					$scope.placeQuote = function() {
						$scope.showConsumerCart = 0;
						$scope.scroll = 1;
						// $scope.cartCount = 0;
						// $location.path('/cdc');
						$scope.formData = [];
						var listFinalItems = [];
						// $scope.consumerCart();
						for (i = 0; i < $scope.consumerCartList.length; i++) {
							listFinalItems.push($scope.consumerCartList[i]);
						}
						console.log(listFinalItems);
						$scope.formData = $scope.consumerCartList;
						$http
								.post('placeQuote', $scope.formData)
								.success(
										function(resp) {
											$scope.cartCount = 0;
											bootbox
													.alert('Thanks for your Quote, we will keep you posted via email/mobile');
										})
								.error(
										function() {
											$scope.userQuoteMessage = "There is some issue with the quote submission. Please try again."
										});

					}

					// ********************* GET USER PROFILE
					$scope.getProfile = function() {
						$window.scrollTo(0, 0);
						$scope.showConsumerCart = 0;
						$scope.showUserQuotes = 0;
						$scope.showMyProfile = 1;
						$scope.showAcceptedQuotes = 0;
						$scope.showFurnitures = 0;
						$scope.showelectronics = 0;
						$scope.showOrderedItems = 0;
						$scope.showServices = 0;
						$scope.showReviews = 0;
						$scope.scroll = 0;
						$scope.products = 1;
						$scope.showReviewItems = 0;
						var data = new FormData();
						data.append('userName', $scope.userEmail);
						$http.get('getProfile', {
							params : {
								'userEmail' : localStorage.getItem('userEmail')
							}
						}).then(function(response) {
							$scope.profileData = response.data;
							// $.each(response.data, function(index,
							// element) {
							// console.log(element);
							// $scope.profileData = element;
							// });
						});
					}

					// ********************* UPDATE USER PROFILE
					$scope.profileData = {};
					$scope.updateProfile = function() {
						$window.scrollTo(0, 0);
						$scope.showConsumerCart = 0;
						$scope.showUserQuotes = 0;
						$scope.showMyProfile = 1;
						$scope.showAcceptedQuotes = 0;
						$scope.showFurnitures = 0;
						$scope.showelectronics = 0;
						$scope.showOrderedItems = 0;
						$scope.showServices = 0;
						$scope.showReviews = 0;
						$scope.scroll = 0;
						$scope.showReviewItems = 0;
						$scope.products = 1;

						var data = new FormData();
						data.append('userEmail', $scope.userEmail);
						data.append('userName', $scope.userName);
						data.append('phoneNumber',
								$scope.profileData.userPhonenumber);
						data.append('addressOne',
								$scope.profileData.userAddressOne);
						data.append('addressTwo',
								$scope.profileData.userAddressTwo);
						data.append('city', $scope.profileData.userCity);
						data.append('state', $scope.profileData.userState);
						data.append('country', $scope.profileData.userCountry);
						data.append('zipcode', $scope.profileData.zipCode);
						$http.post('updateProfile', data, {
							withCredentials : false,
							transformRequest : angular.identity,
							headers : {
								'Content-Type' : undefined
							}
						}).success(function() {
							bootbox.alert('Profile updated successfully');
						})
					}

					// ********************* GET USER QUOTES BY EMAIL
					$scope.consumerQuoteList = [];
					$scope.userQuotes = function() {
						$scope.consumerQuoteList = [];
						$window.scrollTo(0, 0);
						$scope.showConsumerCart = 0;
						$scope.showUserQuotes = 1;
						$scope.showMyProfile = 0;
						$scope.showAcceptedQuotes = 0;
						$scope.showFurnitures = 0;
						$scope.showelectronics = 0;
						$scope.showServices = 0;
						$scope.showOrderedItems = 0;
						$scope.showReviews = 0;
						$scope.scroll = 0;
						$scope.products = 1;
						$scope.showReviewItems = 0;
						$http.get('getUserQuotes', {
							params : {
								'userEmail' : localStorage.getItem('userEmail')
							}
						}).then(function(response) {
							$.each(response.data, function(index, element) {
								$scope.consumerQuoteList.push(element);
							});

						});
					}

					// ********************* GET VENDOR ACCEPTED QUOTES BY EMAIL
					$scope.vendorAcceptedQuotesList = [];
					$scope.acceptedQuotes = function() {
						$scope.vendorAcceptedQuotesList = [];
						$window.scrollTo(0, 0);
						$scope.showConsumerCart = 0;
						$scope.showUserQuotes = 0;
						$scope.showMyProfile = 0;
						$scope.showAcceptedQuotes = 1;
						$scope.showFurnitures = 0;
						$scope.showelectronics = 0;
						$scope.showOrderedItems = 0;
						$scope.showServices = 0;
						$scope.showReviews = 0;
						$scope.scroll = 0;
						$scope.products = 1;
						$scope.showReviewItems = 0;
						$http.get('getVendorAcceptedQuotes', {
							params : {
								'userEmail' : localStorage.getItem('userEmail')
							}
						}).then(function(response) {
							$.each(response.data, function(index, element) {
								$scope.vendorAcceptedQuotesList.push(element);
							});

						});
					}

					// *********************** EDIT QUOTE
					$scope.vendorList = [];
					$scope.updateQuote = function() {
						$scope.formData = [];
						var listUpdatedQuotes = [];
						for (i = 0; i < $scope.consumerQuoteList.length; i++) {
							listUpdatedQuotes.push($scope.consumerQuoteList[i]);
						}
						$scope.formData = $scope.consumerQuoteList;
						$http.post('updateQuotes', $scope.formData).success(
								function(response) {
									$scope.userQuotes();
								});
					}

					// ************************* DELETE QUOTE
					$scope.deleteItemInQuote = function(consumerPriceID) {
						var data = new FormData();
						data.append('consumerPrice', consumerPriceID);
						data.append('userEmail', localStorage
								.getItem('userEmail'));
						$http.post('deleteQuote', data, {
							withCredentials : false,
							transformRequest : angular.identity,
							headers : {
								'Content-Type' : undefined
							}
						}).success(function() {
							$scope.userQuotes();
						});

					}

					// ************************* DELETE ITEM FROM CART
					$scope.deleteItemInCart = function(consumerPriceID) {
						var data = new FormData();
						data.append('consumerPrice', consumerPriceID);
						data.append('userEmail', localStorage
								.getItem('userEmail'));
						$http.post('deleteCart', data, {
							withCredentials : false,
							transformRequest : angular.identity,
							headers : {
								'Content-Type' : undefined
							}
						}).success(function() {
							$scope.consumerCart();
						});

					}

					// ******************* CHECKOUT PRODUCT
					$scope.checkoutProduct = function(element) {
						$scope.getProfile();
						$window.scrollTo(0, 0);
						$scope.showConsumerCart = 0;
						$scope.showUserQuotes = 0;
						$scope.showMyProfile = 0;
						$scope.showAcceptedQuotes = 0;
						$scope.showCheckout = 1;
						$scope.showOrderedItems = 0;
						$scope.showFurnitures = 0;
						$scope.showelectronics = 0;
						$scope.showServices = 0;
						$scope.showReviews = 0;
						$scope.scroll = 0;
						$scope.products = 1;
						$scope.showReviewItems = 0;
						$scope.productInfo = element;
					}

					// ******************** PLACE THE ORDER
					$scope.placeOrder = function(productInfo) {
						var data = new FormData();
						data.append('consumerPriceID',
								productInfo.consumerPriceID);
						$http.post('placeOrder', data, {
							withCredentials : false,
							transformRequest : angular.identity,
							headers : {
								'Content-Type' : undefined
							}
						}).success(function() {
							bootbox.alert('Order Placed successfully!');
							$scope.home();
						})

					}

					// *********************** ORDERED ITEMS
					$scope.orderList = [];
					$scope.orderedItems = function() {
						$scope.orderList = [];
						$window.scrollTo(0, 0);
						$scope.showConsumerCart = 0;
						$scope.showUserQuotes = 0;
						$scope.showMyProfile = 0;
						$scope.showAcceptedQuotes = 0;
						$scope.showOrderedItems = 1;
						$scope.showCheckout = 0;
						$scope.showFurnitures = 0;
						$scope.showelectronics = 0;
						$scope.showServices = 0;
						$scope.showReviews = 0;
						$scope.scroll = 0;
						$scope.showReviewItems = 0;
						$scope.products = 1;
						$http.get('getOrderByUser', {
							params : {
								'userEmail' : localStorage.getItem('userEmail')
							}
						}).then(function(response) {
							$.each(response.data, function(index, element) {
								$scope.orderList.push(element);
							});
						});
					}

					// THis is to divide the HTML DIV into 2 elements per row
					Array.prototype.divideIt = function(d) {
						if (this.length <= d)
							return this;
						var arr = this;
						$scope.hold = [], ref = -1;
						for (var i = 0; i < arr.length; i++) {
							if (i % d === 0) {
								ref++;
							}
							if (typeof $scope.hold[ref] === 'undefined') {
								$scope.hold[ref] = [];
							}
							$scope.hold[ref].push(arr[i]);
						}

						return $scope.hold;
					};

				});

cdc.directive('scrollSpy', function($timeout) {
	return {
		restrict : 'A',
		link : function(scope, elem, attr) {
			var offset = parseInt(attr.scrollOffset, 10)
			if (!offset)
				offset = 10;
			// elem.scrollSpy({ "offset" : offset});
			scope.$watch(attr.scrollSpy, function(value) {
				$timeout(function() {
					// elem.scrollSpy('refresh', { "offset" : offset})
				}, 1);
			}, true);
		}
	}
});

cdc.directive('preventDefault', function() {
	return function(scope, element, attrs) {
		jQuery(element).click(function(event) {
			event.preventDefault();
		});
	}
});

cdc.directive("scrollTo", [ "$window", function($window) {
	return {
		restrict : "AC",
		compile : function() {

			function scrollInto(elementId) {
				if (!elementId)
					$window.scrollTo(0, 0);
				// check if an element can be found with id attribute
				var el = document.getElementById(elementId);
				if (el)
					el.scrollIntoView();
			}

			return function(scope, element, attr) {
				element.bind("click", function(event) {
					scrollInto(attr.scrollTo);
				});
			};
		}
	};
} ]);

cdc.filter('pagination', function() {
	return function(input, start) {
		start = +start;
		return input.slice(start);
	};
});

// ************** DISPLAY STARS FOR REVIEWS
cdc
		.directive(
				'starRating',
				function() {
					return {
						restrict : 'A',
						template : '<ul class="rating">'
								+ '	<li ng-repeat="star in stars" ng-class="star" ng-click="toggle($index)">'
								+ '\u2605' + '</li>' + '</ul>',
						scope : {
							ratingValue : '=',
							max : '=',
							onRatingSelected : '&'
						},
						link : function(scope, elem, attrs) {
							var updateStars = function() {
								scope.stars = [];
								for (var i = 0; i < scope.max; i++) {
									scope.stars.push({
										filled : i < scope.ratingValue
									});
								}
							};

							scope.toggle = function(index) {
								scope.ratingValue = index + 1;
								scope.onRatingSelected({
									rating : index + 1
								});
							};

							scope.$watch('ratingValue',
									function(oldVal, newVal) {
										if (newVal) {
											updateStars();
										}
									});
						}
					};
				});