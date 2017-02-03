function Request(method, uri, headers, body){
	
	//this currently isn't used...
	var serializeBody = function(contentType, bodyText){
		
		if(contentType === null || bodyText === null ||
				contentType === undefined || bodyText === undefined){
			return null;
		}
		
		switch(contentType){
		case "application/json":
			return JSON.stringify(bodyText, null, 4);
			break;
		default:
			return JSON.stringify(bodyText, null, 4);
		}
	}
	
	this.method = method,
	this.uri = uri,
	this.headers = headers,
	this.body = body;
}