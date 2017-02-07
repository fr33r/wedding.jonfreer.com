/**
 * 
 */
var logModule = (function(){

	return {

		/**
		 *  Logs an HTTP request to console output.
		 *  @param {String} The HTTP method of the request.
		 *  @param {String} The URI of the HTTP request.
		 *  @param {Object} An array of header objects
		 *  indicating the headers and their values for the request.
		 *  @param {Object} The data to be serialized in
		 *  the body of the HTTP request.
		 */
		logRequest: function(request){
			
			var string = request.method + " " + request.uri + "\n";
			
			for(var header in request.headers){
				string += header + ": " + request.headers[header] + "\n";
			}
			
			if(request.headers["Content-Type"] === "application/json"){
				string += JSON.stringify(request.body, null, 4);
			}else if(request.body !== undefined && request.body !== null){
				string += request.body.toString();
			}

			console.log(string);
		},

		/**
		 *  Logs an HTTP response to console output.
		 *  @param {String} The HTTP status code of the response.
		 *  @param {String} The URI associated with the HTTP response.
		 *  @param {String} The HTTP headers of the response separated by CRLF.
		 *  @param {String} The body of the HTTP response in a string format.
		 */
		logResponse: function(response){

			var string = "HTTP/1.1 " + response.statusCode + " " + response.statusText + "\n";
			
			for(var header in response.headers){
				string += header + ": " + response.headers[header] + "\n";
			}
			
			if(response.body !== undefined && response.body !== null){
				switch(response.headers["Content-Type"]){
					case "application/json":
						string += JSON.stringify(response.body, null, 4);
						break;
					default:
						string += response.body.toString();		
				}
			}
			
			console.log(string);
		},
		
		log: function(message){
			console.log(message);
		}
	};
})();