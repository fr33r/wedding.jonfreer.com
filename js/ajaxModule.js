var ajaxModule = (function(){

  var internalSettings = {
    contentType: "application/json",
    accept: "application/json",
    logging: true
  };

  var internalLog = function(message){
    if(internalSettings.logging === true){
      console.log(message);
    }
  };

/**
 *  Logs an HTTP request to console output.
 *  @param {String} The HTTP method of the request.
 *  @param {String} The URI of the HTTP request.
 *  @param {Object} An array of header objects
 *  indicating the headers and their values for the request.
 *  @param {Object} The data to be serialized in
 *  the body of the HTTP request.
 */
  var logRequest = function(method, uri, headers, data){
    var contentType = "application/json";
    var string = method + " " + uri + "\n";

    for(var i = 0; i < headers.length; i++){
        string += headers[i].header + ": " + headers[i].value + "\n";
        if(headers[i].header === "Content-Type"){ contentType = headers[i].value; }
    }

    if(data != null){
        if(contentType === "application/json"){
            string += JSON.stringify(data, null, 4);
        }else{
            string += data;
        }
    }

    console.log(string);
  };

 /**
  *  Logs an HTTP response to console output.
  *  @param {String} The HTTP status code of the response.
  *  @param {String} The URI associated with the HTTP response.
  *  @param {String} The HTTP headers of the response separated by CRLF.
  *  @param {String} The body of the HTTP response in a string format.
  */
  var logResponse = function(statusCode, uri, headers, data){
    var contentType = "application/json";
    var string = "HTTP/1.1 " + statusCode + "\n";
    var headersArray = headers.split("\r\n");

    for(var i = 0; i < headersArray.length; i++){
        string += headersArray[i] + "\n";
        if(headers[i].startsWith("Content-Type")){
            contentType = headers[i].substring(headers[i].indexOf(":")).trim();
        }
    }

    if(data != null){
        if(contentType === "application/json"){
            string += JSON.stringify(JSON.parse(data), null, 4);
        }else{
            string += data;
        }
    }

    console.log(string);
  };

  var internalConfigure = function(settings){
    if(settings !== undefined && settings !== null){
      //overwrite.
      internalSettings.contentType =
        settings.contentType !== undefined && settings.contentType !== null
          ? settings.contentType : internalSettings.contentType;
      internalSettings.accept =
        settings.accept !== undefined && settings.accept !== null
          ? settings.accept : internalSettings.accept;
      internalSettings.logging =
        settings.logging !== undefined && settings.logging !== null
          ? settings.logging : internalSettings.logging;
    }else{
      console.error("The settings object provided to the ajax module is undefined or null.");
    }
  };

 /**
  *  Sends an HTTP GET request.
  *  @param {String} The URI of the HTTP request.
  *  @param {Object} An array of header objects
  *  indicating the headers and their values for the request.
  *  @param {Function} The callback that is invoked upon success.
  *  @param {Function} The callback that is invoked upon error.
  */
  var internalGet = function(uri, headers, successCallback, errorCallback){
    var internalXHR = new XMLHttpRequest();

    if(typeof successCallback === "function"){
      internalXHR.addEventListener("load", function(){
        if(internalSettings.accept === "application/json"){ //TODO: check for actual response content type instead.
          internalLog("HTTP Response\nBody:\n" + internalXHR.responseText);
          successCallback(JSON.parse(internalXHR.responseText));
        }
      });
    }

    if(typeof errorCallback === "function"){
      internalXHR.addEventListener("error", function(){
        if(internalSettings.accept === "application/json"){ //TODO: check for actual response content type instead.
          internalLog("HTTP Response\nBody:\n" + internalXHR.responseText);
          errorCallback(JSON.parse(internalXHR.responseText));
        }
      });
    }

    var contentType = "application/json";

    if(typeof headers === "object"){
        for(var i = 0; i < headers.length; i++){
          internalXHR.setRequestHeader(headers[i].header, headers[i].value);
          if(headers[i].header === "Content-Type"){ contentType = headers[i].value; }
        }
    }

    internalXHR.open("GET", uri);
    logRequest("GET", uri, headers, null);
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
  var internalPost = function(uri, headers, data, successCallback, errorCallback){
    var internalXHR = new XMLHttpRequest();

    if(typeof successCallback === "function"){
      internalXHR.addEventListener("load", function(){
        logResponse(internalXHR.statusText, uri, internalXHR.getAllResponseHeaders(), internalXHR.responseText);
        if(internalXHR.getResponseHeader("Content-Type") === "application/json"){
          successCallback(JSON.parse(internalXHR.responseText));
        }
      });
    }

    if(typeof errorCallback === "function"){
      internalXHR.addEventListener("error", function(){
        logResponse(internalXHR.statusText, uri, internalXHR.getAllResponseHeaders(), internalXHR.responseText);
        if(internalXHR.getResponseHeader("Content-Type") === "application/json"){
          errorCallback(JSON.parse(internalXHR.responseText));
        }
      });
    }

    internalXHR.open("POST", uri);

    var contentType = "application/json";

    if(typeof headers === "object"){
        for(var i = 0; i < headers.length; i++){
          internalXHR.setRequestHeader(headers[i].header, headers[i].value);
          if(headers[i].header === "Content-Type"){ contentType = headers[i].value; }
        }
    }

    if(contentType === "application/x-www-form-urlencoded"){
        internalXHR.send(data);
    }else{ //default from JSON.
        internalXHR.send(JSON.stringify(data));
    }
    logRequest("POST", uri, headers, data);
  };

 /**
  *  Sends an HTTP DELETE request.
  *  @param {String} The URI of the HTTP request.
  *  @param {Object} An array of header objects
  *  indicating the headers and their values for the request.
  *  @param {Function} The callback that is invoked upon success.
  *  @param {Function} The callback that is invoked upon error.
  */
  var internalDelete = function(uri, headers, successCallback, errorCallback){
    var internalXHR = new XMLHttpRequest();

    if(typeof successCallback === "function"){
      internalXHR.addEventListener("load", function(){
        if(internalXHR.getResponseHeader("Content-Type") === "application/json"){
          logResponse(internalXHR.statusText, uri, internalXHR.getAllResponseHeaders(), internalXHR.responseText);
          successCallback(JSON.parse(internalXHR.responseText));
        }
      });
    }

    if(typeof errorCallback === "function"){
      internalXHR.addEventListener("error", function(){
        if(internalXHR.getResponseHeader("Content-Type") === "application/json"){
          logResponse(internalXHR.statusText, uri, internalXHR.getAllResponseHeaders(), internalXHR.responseText);
          errorCallback(JSON.parse(internalXHR.responseText));
        }
      });
    }

    var contentType = "application/json";

    if(typeof headers === "object"){
        for(var i = 0; i < headers.length; i++){
          internalXHR.setRequestHeader(headers[i].header, headers[i].value);
          if(headers[i].header === "Content-Type"){ contentType = headers[i].value; }
        }
    }

    internalXHR.open("DELETE", uri);
    internalXHR.send();
    logRequest("DELETE", uri, headers, null);
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
  var internalPut = function(uri, headers, data, successCallback, errorCallback){
    var internalXHR = new XMLHttpRequest();

    if(typeof successCallback === "function"){
      internalXHR.addEventListener("load", function(){
        if(internalXHR.getResponseHeader("Content-Type") === "application/json"){
          logResponse(internalXHR.statusText, uri, internalXHR.getAllResponseHeaders(), internalXHR.responseText);
          successCallback(JSON.parse(internalXHR.responseText));
        }
      });
    }

    if(typeof errorCallback === "function"){
      internalXHR.addEventListener("error", function(){
        if(internalXHR.getResponseHeader("Content-Type") === "application/json"){
          logResponse(internalXHR.statusText, uri, internalXHR.getAllResponseHeaders(), internalXHR.responseText);
          errorCallback(JSON.parse(internalXHR.responseText));
        }
      });
    }

    internalXHR.open("PUT", uri);

    var contentType = "application/json";

    if(typeof headers === "object"){
        for(var i = 0; i < headers.length; i++){
          internalXHR.setRequestHeader(headers[i].header, headers[i].value);
          if(headers[i].header === "Content-Type"){ contentType = headers[i].value; }
        }
    }

    if(contentType === "application/x-www-form-urlencoded"){
        internalXHR.send(data);
    }else{ //default from JSON.
        internalXHR.send(JSON.stringify(data));
    }
    logRequest("PUT", uri, headers, data);
  };

 /**
  *  Sends an HTTP OPTION request.
  *  @param {String} The URI of the HTTP request.
  *  @param {Object} An array of header objects
  *  indicating the headers and their values for the request.
  *  @param {Function} The callback that is invoked upon success.
  *  @param {Function} The callback that is invoked upon error.
  */
  var internalOption = function(uri, headers, successCallback, errorCallback){
    var internalXHR = new XMLHttpRequest();

    if(typeof successCallback === "function"){
      internalXHR.addEventListener("load", function(){
        if(internalXHR.getResponseHeader("Content-Type") === "application/json"){
          logResponse(internalXHR.statusText, uri, internalXHR.getAllResponseHeaders(), internalXHR.responseText);
          successCallback(JSON.parse(internalXHR.responseText));
        }
      });
    }

    if(typeof errorCallback === "function"){
      internalXHR.addEventListener("error", function(){
        if(internalXHR.getResponseHeader("Content-Type") === "application/json"){
          logResponse(internalXHR.statusText, uri, internalXHR.getAllResponseHeaders(), internalXHR.responseText);
          errorCallback(JSON.parse(internalXHR.responseText));
        }
      });
    }

    internalXHR.open("OPTION", uri);

    if(typeof headers === "object"){
        for(var i = 0; i < headers.length; i++){
          internalXHR.setRequestHeader(headers[i].header, headers[i].value);
        }
    }

    internalXHR.send();
    logRequest("OPTION", uri, headers, null);
  }

 /**
  *  Sends an HTTP HEAD request.
  *  @param {String} The URI of the HTTP request.
  *  @param {Object} An array of header objects
  *  indicating the headers and their values for the request.
  *  @param {Function} The callback that is invoked upon success.
  *  @param {Function} The callback that is invoked upon error.
  */
  var internalHead = function(uri, headers, successCallback, errorCallback){
    var internalXHR = new XMLHttpRequest();

    if(typeof successCallback === "function"){
      internalXHR.addEventListener("load", function(){
        if(internalXHR.getResponseHeader("Content-Type") === "application/json"){
          logResponse(internalXHR.statusText, uri, internalXHR.getAllResponseHeaders(), internalXHR.responseText);
          successCallback(JSON.parse(internalXHR.responseText));
        }
      });
    }

    if(typeof errorCallback === "function"){
      internalXHR.addEventListener("error", function(){
        if(internalXHR.getResponseHeader("Content-Type") === "application/json"){
          logResponse(internalXHR.statusText, uri, internalXHR.getAllResponseHeaders(), internalXHR.responseText);
          errorCallback(JSON.parse(internalXHR.responseText));
        }
      });
    }

    internalXHR.open("HEAD", uri);

    if(typeof headers === "object"){
        for(var i = 0; i < headers.length; i++){
          internalXHR.setRequestHeader(headers[i].header, headers[i].value);
        }
    }

    internalXHR.send();
    logRequest("HEAD", uri, headers, null);
  }

  return {

    //configures the ajax module according to the
    //settings object provided.
    configure:  function(settings){
      internalConfigure(settings);
    },

    //performs an HTTP POST.
    post: function(uri, data, successCallback, errorCallback){
      internalPost(uri, data, successCallback, errorCallback);
    },

    //performs an HTTP GET.
    get:  function(uri, successCallback, errorCallback){
      internalGet(uri, successCallback, errorCallback);
    },

    //performs an HTTP PUT.
    put: function(uri, data, successCallback, errorCallback){
      internalPut(uri, data, successCallback, errorCallback);
    },

    //performs an HTTP DELETE.
    delete: function(uri, data, successCallback, errorCallback){
      internalDelete(uri, data, successCallback, errorCallback);
    },

    //performs an HTTP OPTION.
    option: function(uri, successCallback, errorCallback){
      internalOption(uri, successCallback, errorCallback);
    },

    //performs an HTTP HEAD.
    head: function(uri, successCallback, errorCallback){
      internalHead(uri, successCallback, errorCallback);
    }

  };

})();
