var ajaxModule = (function(){

	//consider putting this in the cacheModule?
	var isExpired = function(cachedResponse){
		
		//perform logic here on cache duration.
		var ccHeader = cachedResponse.headers["Cache-Control"];
		var dateHeader = cachedResponse.headers["Date"];

		//if no cache control information can be found, always invalidate.
		if(ccHeader === null || ccHeader === undefined || dateHeader === null || dateHeader === undefined){
			return true;
		}
		
		var ccHeaderValues = ccHeader.split(",");
		
		for(var i = 0; i < ccHeaderValues.length; i++){
			
			if(ccHeaderValues[i].trim().startsWith("max-age")){
				
				var maxAge = ccHeaderValues[i].split("=")[1];
				var expiration = new Date(dateHeader).getTime() + (maxAge * 1000);
				
				logModule.log("Cached Response Expiration: " + new Date(expiration).toLocaleString());
				
				if(expiration >= new Date().getTime()){				
					return false;			
				}	
			}
		}
		
		return true;
	};
	
	var isCachable = function(response){
		
		if(
			response.headers["Cache-Control"] === undefined ||
			response.headers["Cache-Control"] === null
		){
			return false;
		}
		
		if(
			response.headers["Date"] === undefined ||
			response.headers["Date"] === null
		){
			return false;
		}
		
		if(
			response.headers["ETag"] === undefined ||
			response.headers["ETag"] === null
		){
			return false;
		}
		
		if(
			response.headers["Last-Modified"] === undefined ||
			response.headers["Last-Modified"] === null
		){
			return false;
		}
		
		return true;
	}
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

		var cachedResponse = cacheModule.get(request.uri);
		
		if(cachedResponse !== null){
			if(isExpired(cachedResponse)){
				request.headers["If-None-Match"] = cachedResponse.headers["ETag"];
				request.headers["If-Modified-Since"] = cachedResponse.headers["Last-Modified"];
			}else{
				successCallback(cachedResponse.body);
				return;
			}
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

			if(response.statusCode === 304){
				cacheModule.get(response.uri).headers["Date"] = response.headers["Date"];
				cacheModule.get(response.uri).headers["ETag"] = response.headers["ETag"];
				cacheModule.get(response.uri).headers["Last-Modified"] = response.headers["Last-Modified"];
				successCallback(cacheModule.get(response.uri));
			}else if(response.statusCode >= 200 && response.statusCode < 300){
				if(isCachable(response)){
					cacheModule.add(response.uri, response);
				}
				successCallback(response.body);
			}else{
				errorCallback("you break it, you buy it! not really, but now we have to fix something.");
			}
		});

		internalXHR.addEventListener("error", function(){

			if(internalXHR.status !== 0){
				var response = new Response(
						internalXHR.status, 
						internalXHR.statusText, 
						request.uri, 
						internalXHR.getAllResponseHeaders(), 
						internalXHR.responseText);

				logModule.logResponse(response);
			}else{
				logModule.log("Could not connect to server.");
			}

			errorCallback("you break it, you buy it! not really, but now we have to fix something.");
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
				errorCallback("you break it, you buy it! not really, but now we have to fix something.");
			}
		});

		internalXHR.addEventListener("error", function(){
			
			if(internalXHR.status !== 0){
				var response = new Response(
						internalXHR.status, 
						internalXHR.statusText, 
						request.uri, 
						internalXHR.getAllResponseHeaders(), 
						internalXHR.responseText);

				logModule.logResponse(response);
			}else{
				logModule.log("Could not connect to server.");
			}
			
			errorCallback("you break it, you buy it! not really, but now we have to fix something.");
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
				errorCallback("you break it, you buy it! not really, but now we have to fix something.");
			}
		});

		internalXHR.addEventListener("error", function(){

			if(internalXHR.status !== 0){
				var response = new Response(
						internalXHR.status, 
						internalXHR.statusText, 
						request.uri, 
						internalXHR.getAllResponseHeaders(), 
						internalXHR.responseText);

				logModule.logResponse(response);
			}else{
				logModule.log("Could not connect to server.");
			}
			
			errorCallback("you break it, you buy it! not really, but now we have to fix something.");
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
				errorCallback("you break it, you buy it! not really, but now we have to fix something.");
			}
		});

		internalXHR.addEventListener("error", function(){
			
			if(internalXHR.status !== 0){
				var response = new Response(
						internalXHR.status, 
						internalXHR.statusText, 
						request.uri, 
						internalXHR.getAllResponseHeaders(), 
						internalXHR.responseText);

				logModule.logResponse(response);
			}else{
				logModule.log("Could not connect to server.");
			}
			
			errorCallback("you break it, you buy it! not really, but now we have to fix something.");
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
				errorCallback("you break it, you buy it! not really, but now we have to fix something.");
			}
		});

		internalXHR.addEventListener("error", function(){

			if(internalXHR.status !== 0){
				var response = new Response(
						internalXHR.status, 
						internalXHR.statusText, 
						request.uri, 
						internalXHR.getAllResponseHeaders(), 
						internalXHR.responseText);

				logModule.logResponse(response);
			}else{
				logModule.log("Could not connect to server.");
			}
			
			errorCallback("you break it, you buy it! not really, but now we have to fix something.");
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
				errorCallback("you break it, you buy it! not really, but now we have to fix something.");
			}
		});

		internalXHR.addEventListener("error", function(){

			if(internalXHR.status !== 0){
				var response = new Response(
						internalXHR.status, 
						internalXHR.statusText, 
						request.uri, 
						internalXHR.getAllResponseHeaders(), 
						internalXHR.responseText);

				logModule.logResponse(response);
			}else{
				logModule.log("Could not connect to server.");
			}
			
			errorCallback("you break it, you buy it! not really, but now we have to fix something.");
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
