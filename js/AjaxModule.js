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

  var internalGet = function(uri, successCallback, errorCallback){
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

    internalXHR.open("GET", uri);
    internalLog("HTTP Request\nURI:\t\t\t\t" + uri + "\nMethod:\t\tGET\n");
    internalXHR.send();
  };

  var internalPost = function(uri, data, successCallback, errorCallback){
    var internalXHR = new XMLHttpRequest();

    if(typeof successCallback === "function"){
      internalXHR.addEventListener("load", function(){
        internalLog("HTTP Response\nBody:\n" + internalXHR.responseText);
        if(internalXHR.getResponseHeader("Content-Type") === "application/json"){
          successCallback(JSON.parse(internalXHR.responseText));
        }
      });
    }

    if(typeof errorCallback === "function"){
      internalXHR.addEventListener("error", function(){
        if(internalXHR.getResponseHeader("Content-Type") === "application/json"){
          internalLog("HTTP Response\nBody:\n" + internalXHR.responseText);
          errorCallback(JSON.parse(internalXHR.responseText));
        }
      });
    }

    internalXHR.open("POST", uri);
    internalXHR.setRequestHeader("Content-Type", internalSettings.contentType);

    if(internalSettings.contentType === "application/json"){
      internalXHR.send(JSON.stringify(data));
      internalLog("HTTP Request\nURI:\t\t\t\t" + uri + "\nMethod:\t\tPOST\nBody:\n" + JSON.stringify(data, null, 4));
    }else if(internalSettings.contentType === "application/x-www-form-urlencoded"){
      internalXHR.send(data);
      internalLog("HTTP Request\nURI:\t\t\t\t" + uri + "\nMethod:\t\tPOST\nBody:\n" + data);
    }
  };

  var internalDelete = function(uri, data, successCallback, errorCallback){
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

    internalXHR.open("DELETE", uri);
    internalXHR.setRequestHeader("Content-Type", internalSettings.contentType);

    if(internalSettings.contentType === "application/json"){
      internalXHR.send(JSON.stringify(data));
      internalLog("HTTP Request\nURI:\t\t\t\t" + uri + "\nMethod:\t\tDELETE\nBody:\n" + JSON.stringify(data, null, 4));
    }else if(internalSettings.contentType === "application/x-www-form-urlencoded"){
      internalXHR.send(data);
      internalLog("HTTP Request\nURI:\t\t\t\t" + uri + "\nMethod:\t\tDELETE\nBody:\n" + data);
    }
  };

  var internalPut = function(uri, data, successCallback, errorCallback){
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

    internalXHR.open("PUT", uri);
    internalXHR.setRequestHeader("Content-Type", internalSettings.contentType);

    if(internalSettings.contentType === "application/json"){
      internalXHR.send(JSON.stringify(data));
      internalLog("HTTP Request\nURI:\t\t\t\t" + uri + "\nMethod:\t\tPUT\nBody:\n" + JSON.stringify(data, null, 4));
    }else if(internalSettings.contentType === "application/x-www-form-urlencoded"){
      internalXHR.send(data);
      internalLog("HTTP Request\nURI:\t\t\t\t" + uri + "\nMethod:\t\tPUT\nBody:\n" + data);
    }
  };

  return {

    //configures the ajax module according to the
    //settings object provided.
    configure:  function(settings){
      internalConfigure(settings);
    },

    //performs a HTTP POST.
    post: function(uri, data, successCallback, errorCallback){
      internalPost(uri, data, successCallback, errorCallback);
    },

    //performs a HTTP GET.
    get:  function(uri, successCallback, errorCallback){
      internalGet(uri, successCallback, errorCallback);
    },

    //performs a HTTP PUT.
    put: function(uri, data, successCallback, errorCallback){
      internalPut(uri, data, successCallback, errorCallback);
    },

    //performs a HTTP DELETE.
    delete: function(uri, data, successCallback, errorCallback){
      internalDelete(uri, data, successCallback, errorCallback);
    }

  };

})();
