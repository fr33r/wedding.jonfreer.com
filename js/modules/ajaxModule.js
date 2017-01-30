var ajaxModule = (function(){

	/**
	 *  Sends an HTTP GET request.
	 *  @param {String} The URI of the HTTP request.
	 *  @param {Object} An array of header objects
	 *  indicating the headers and their values for the request.
	 *  @param {Function} The callback that is invoked upon success.
	 *  @param {Function} The callback that is invoked upon error.
	 */
	var internalGet = function(request, successCallback, errorCallback){

		if(typeof successCallback !== "function"){
			throw "The 'successCallback' parameter must be a function.";
		}

		if(typeof errorCallback !== "function"){
			throw "The 'errorCallback' parameter must be a function.";
		}

		//first check the cache to see if we already have this resource.
		if(cacheModule.get(request.uri) !== null){
			var cachedResponse = cacheModule.get(request.uri);

			//perform logic here on cache duration.
			//var ccHeader = cachedResponse.headers["Cache-Control"];
			//var dateHeader = cachedResponse.headers["Date"];

			//var ccHeaderValues = ccHeader.split(",");
			//for(var i = 0; i < ccHeaderValues.length; i++){
			//if(ccHeaderValues[i].trim().startsWith("max-age")){
			//var maxAge = ccHeaderValues[i].split("=")[1];
			//var expiration = new Date(dateHeader) + maxAge;
			//if(expiration > new Date()){
			// return cached response.
			//}else{
			// perform conditional request.
			//}
			//}
			//}

			logModule.log("Using cached resource " + request.uri);
			successCallback(cachedResponse.body);

			return;
		}

		var internalXHR = new XMLHttpRequest();

		internalXHR.addEventListener("load", function(){

			var response = new Response(
					internalXHR.status, 
					internalXHR.statusText, 
					request.uri, 
					internalXHR.getAllResponseHeaders(), 
					internalXHR.responseText);

			logModule.logResponse(response);

			if(response.statusCode >= 200 && response.statusCode < 300){
				cacheModule.add(response.uri, response);
				successCallback(response.body);
			}else{
				errorCallback("An error occurred: " + response.statusCode + " " + response.statusText);
			}
		});

		internalXHR.addEventListener("error", function(){

			var response = new Response(
					internalXHR.status, 
					internalXHR.statusText, 
					request.uri, 
					internalXHR.getAllResponseHeaders(), 
					internalXHR.responseText);

			logModule.logResponse(response);
			errorCallback("An error occurred: " + response.statusCode + " " + response.statusText);
		});

		internalXHR.open(request.method, request.uri);

		for(var header in request.headers){
			internalXHR.setRequestHeader(header, request.headers[header]);
		}

		logModule.logRequest(request);

		internalXHR.send();
	};

	/**
	 *  Sends an HTTP POST request.
	 *  @param {String} The URI of the HTTP request.
	 *  @param {Object} An array of header objects
	 *  indicating the headers and their values for the request.
	 *  @param {Object} The data to be serialized in
	 *  the body of the HTTP request.
	 *  @param {Function} The callback that is invoked upon success.
	 *  @param {Function} The callback that is invoked upon error.
	 */
	var internalPost = function(request, successCallback, errorCallback){
		if(typeof successCallback !== "function"){
			throw "The 'successCallback' parameter must be a function.";
		}

		if(typeof errorCallback !== "function"){
			throw "The 'errorCallback' parameter must be a function.";
		}

		var internalXHR = new XMLHttpRequest();

		internalXHR.addEventListener("load", function(){
			var response = new Response(
					internalXHR.status, 
					internalXHR.statusText, 
					request.uri, 
					internalXHR.getAllResponseHeaders(), 
					internalXHR.responseText);

			logModule.logResponse(response);

			if(response.statusCode >= 200 && (response.statusCode % 200) < 99){
				cacheModule.add(response.uri, response);
				successCallback(response.body);
			}else{
				errorCallback("An error occurred: " + response.statusCode + " " + response.statusText);
			}
		});

		internalXHR.addEventListener("error", function(){
			var response = new Response(
					internalXHR.status, 
					internalXHR.statusText, 
					request.uri, 
					internalXHR.getAllResponseHeaders(), 
					internalXHR.responseText);

			logModule.logResponse(response);
			errorCallback("An error occurred: " + response.statusCode + " " + response.statusText);
		});

		internalXHR.open(request.method, request.uri);

		for(var header in request.headers){
			internalXHR.setRequestHeader(header, request.headers[header]);
		}

		if(request.headers["Content-Type"].startsWith("application/x-www-form-urlencoded")){
			internalXHR.send(request.body);
		}else if(request.headers["Content-Type"].startsWith("application/json")){
			internalXHR.send(JSON.stringify(request.body));
		}

		logModule.logRequest(request);
	};

	/**
	 *  Sends an HTTP DELETE request.
	 *  @param {String} The URI of the HTTP request.
	 *  @param {Object} An array of header objects
	 *  indicating the headers and their values for the request.
	 *  @param {Function} The callback that is invoked upon success.
	 *  @param {Function} The callback that is invoked upon error.
	 */
	var internalDelete = function(request, successCallback, errorCallback){
		if(typeof successCallback !== "function"){
			throw "The 'successCallback' parameter must be a function.";
		}

		if(typeof errorCallback !== "function"){
			throw "The 'errorCallback' parameter must be a function.";
		}

		var internalXHR = new XMLHttpRequest();

		internalXHR.addEventListener("load", function(){

			var response = new Response(
					internalXHR.status, 
					internalXHR.statusText, 
					request.uri, 
					internalXHR.getAllResponseHeaders(), 
					internalXHR.responseText);

			logModule.logResponse(response);

			if(response.statusCode >= 200 && response.statusCode < 300){
				cacheModule.remove(response.uri);
				successCallback(response.body);
			}else{
				errorCallback("An error occurred: " + response.statusCode + " " + response.statusText);
			}
		});

		internalXHR.addEventListener("error", function(){

			var response = new Response(
					internalXHR.status, 
					internalXHR.statusText, 
					request.uri, 
					internalXHR.getAllResponseHeaders(), 
					internalXHR.responseText);

			logModule.logResponse(response);
			errorCallback("An error occurred: " + response.statusCode + " " + response.statusText);
		});

		internalXHR.open(request.method, request.uri);

		for(var header in request.headers){
			internalXHR.setRequestHeader(header, request.headers[header]);
		}

		logModule.logRequest(request);

		internalXHR.send();
	};

	/**
	 *  Sends an HTTP PUT request.
	 *  @param {String} The URI of the HTTP request.
	 *  @param {Object} An array of header objects
	 *  indicating the headers and their values for the request.
	 *  @param {Object} The data to be serialized in
	 *  the body of the HTTP request.
	 *  @param {Function} The callback that is invoked upon success.
	 *  @param {Function} The callback that is invoked upon error.
	 */
	var internalPut = function(request, successCallback, errorCallback){

		if(typeof successCallback !== "function"){
			throw "The 'successCallback' parameter must be a function.";
		}

		if(typeof errorCallback !== "function"){
			throw "The 'errorCallback' parameter must be a function.";
		}

		var internalXHR = new XMLHttpRequest();

		internalXHR.addEventListener("load", function(){
			var response = new Response(
					internalXHR.status, 
					internalXHR.statusText, 
					request.uri, 
					internalXHR.getAllResponseHeaders(), 
					internalXHR.responseText);

			logModule.logResponse(response);

			if(response.statusCode === 200 || response.statusCode === 204){
				cacheModule.add(response.uri, response);
				successCallback(response.body);
			}else{
				errorCallback("An error occurred: " + response.statusCode + " " + response.statusText);
			}
		});

		internalXHR.addEventListener("error", function(){
			var response = new Response(
					internalXHR.status, 
					internalXHR.statusText, 
					request.uri, 
					internalXHR.getAllResponseHeaders(), 
					internalXHR.responseText);

			logModule.logResponse(response);
			errorCallback("An error occurred: " + response.statusCode + " " + response.statusText);
		});

		internalXHR.open(request.method, request.uri);

		for(var header in request.headers){
			internalXHR.setRequestHeader(header, request.headers[header]);
		}

		if(request.headers["Content-Type"].startsWith("application/x-www-form-urlencoded")){
			internalXHR.send(request.body);
		}else if(request.headers["Content-Type"].startsWith("application/json")){
			internalXHR.send(JSON.stringify(request.body));
		}

		if(cacheModule.get(request.uri) !== null){
			cacheModule.remove(request.uri);
			logModule.log("Removed cached resource " + request.uri);
		}

		logModule.logRequest(request);
	};

	/**
	 *  Sends an HTTP OPTION request.
	 *  @param {String} The URI of the HTTP request.
	 *  @param {Object} An array of header objects
	 *  indicating the headers and their values for the request.
	 *  @param {Function} The callback that is invoked upon success.
	 *  @param {Function} The callback that is invoked upon error.
	 */
	var internalOption = function(request, successCallback, errorCallback){

		if(typeof successCallback !== "function"){
			throw "The 'successCallback' parameter must be a function.";
		}

		if(typeof errorCallback !== "function"){
			throw "The 'errorCallback' parameter must be a function.";
		}

		var internalXHR = new XMLHttpRequest();

		internalXHR.addEventListener("load", function(){

			var response = new Response(
					internalXHR.status, 
					internalXHR.statusText, 
					request.uri, 
					internalXHR.getAllResponseHeaders(), 
					internalXHR.responseText);

			logModule.logResponse(response);

			if(response.statusCode >= 200 && response.statusCode < 300){
				successCallback(response.body);
			}else{
				errorCallback("An error occurred: " + response.statusCode + " " + response.statusText);
			}
		});

		internalXHR.addEventListener("error", function(){

			var response = new Response(
					internalXHR.status, 
					internalXHR.statusText, 
					request.uri, 
					internalXHR.getAllResponseHeaders(), 
					internalXHR.responseText);

			logModule.logResponse(response);
			errorCallback("An error occurred: " + response.statusCode + " " + response.statusText);
		});

		internalXHR.open(request.method, request.uri);

		for(var header in request.headers){
			internalXHR.setRequestHeader(header, request.headers[header]);
		}

		logModule.logRequest(request);

		internalXHR.send();
	}

	/**
	 *  Sends an HTTP HEAD request.
	 *  @param {String} The URI of the HTTP request.
	 *  @param {Object} An array of header objects
	 *  indicating the headers and their values for the request.
	 *  @param {Function} The callback that is invoked upon success.
	 *  @param {Function} The callback that is invoked upon error.
	 */
	var internalHead = function(request, successCallback, errorCallback){

		if(typeof successCallback !== "function"){
			throw "The 'successCallback' parameter must be a function.";
		}

		if(typeof errorCallback !== "function"){
			throw "The 'errorCallback' parameter must be a function.";
		}

		var internalXHR = new XMLHttpRequest();

		internalXHR.addEventListener("load", function(){

			var response = new Response(
					internalXHR.status, 
					internalXHR.statusText, 
					request.uri, 
					internalXHR.getAllResponseHeaders(), 
					internalXHR.responseText);

			logModule.logResponse(response);

			if(response.statusCode >= 200 && response.statusCode < 300){
				successCallback(response.body);
			}else{
				errorCallback("An error occurred: " + response.statusCode + " " + response.statusText);
			}
		});

		internalXHR.addEventListener("error", function(){

			var response = new Response(
					internalXHR.status, 
					internalXHR.statusText, 
					request.uri, 
					internalXHR.getAllResponseHeaders(), 
					internalXHR.responseText);

			logModule.logResponse(response);
			errorCallback("An error occurred: " + response.statusCode + " " + response.statusText);
		});

		internalXHR.open(request.method, request.uri);

		for(var header in request.headers){
			internalXHR.setRequestHeader(header, request.headers[header]);
		}

		logModule.logRequest(request);

		internalXHR.send();
	}

	return {

		//configures the ajax module according to the
		//settings object provided.
		configure:  function(settings){
			internalConfigure(settings);
		},

		//performs an HTTP POST.
		post: function(uri, headers, data, successCallback, errorCallback){
			var request = new Request("POST", uri, headers, data);
			internalPost(request, successCallback, errorCallback);
		},

		//performs an HTTP GET.
		get:  function(uri, headers, successCallback, errorCallback){
			var request = new Request("GET", uri, headers, null);
			internalGet(request, successCallback, errorCallback);
		},

		//performs an HTTP PUT.
		put: function(uri, headers, data, successCallback, errorCallback){
			var request = new Request("PUT", uri, headers, data);
			internalPut(request, successCallback, errorCallback);
		},

		//performs an HTTP DELETE.
		delete: function(uri, headers, successCallback, errorCallback){
			var request = new Request("DELETE", uri, headers, null);
			internalDelete(request, successCallback, errorCallback);
		},

		//performs an HTTP OPTION.
		option: function(uri, headers, successCallback, errorCallback){
			var request = new Request("OPTION", uri, headers, null);
			internalOption(request, successCallback, errorCallback);
		},

		//performs an HTTP HEAD.
		head: function(uri, headers, successCallback, errorCallback){
			var request = new Request("HEAD", uri, headers, null);
			internalHead(request, successCallback, errorCallback);
		}

	};

})(cacheModule, logModule);
