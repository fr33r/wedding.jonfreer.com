function Response(statusCode, statusText, uri, headerString, body){
	
	function parseHeaders(headers){
		var headerObj = {};
		var headersArray = headers.split("\r\n");

		for(var i = 0; i < headersArray.length; i++){
			if(headersArray[i] !== ""){
				var currentHeader = headersArray[i].substring(0, headersArray[i].indexOf(":")).trim();
				var currentHeaderValue = headersArray[i].substring(headersArray[i].indexOf(":")+1).trim();
				headerObj[currentHeader] = currentHeaderValue;
			}	
		}

		return headerObj;
	}

	function parseBody(contentType, bodyText){
		
		if(contentType === null || bodyText === null ||
				contentType === undefined || bodyText === undefined ||
				bodyText === ""){
			return null;
		}
		
		switch(contentType){
		case "application/json":
			return JSON.parse(bodyText);
			break;
		default:
			return JSON.parse(bodyText);
		}
	}
	
	var internalHeaders = parseHeaders(headerString);
	
	this.statusCode = statusCode;
	this.statusText = statusText;
	this.uri = uri;
	this.headers = internalHeaders;
	this.body = parseBody(internalHeaders["Content-Type"], body);
}