var ajaxModule = (function(){

  var internalSettings = {
    contentType: "application/json",
    accept: "application/json"
  };

  return {

    //configures the ajax module according to the
    //settings object provided.
    configure:  function(settings){
      if(settings !== undefined && settings !== null){
        //overwrite.
        internalSettings.contentType = settings.contentType;
      }else{
        Console.error("The settings object provided to the ajax module is undefined or null.");
      }
    },

    //performs a HTTP POST.
    post: function(uri, data, successCallback, errorCallback){
      var internalXHR = new XMLHttpRequest();

      if(typeof successCallback === "function"){
        internalXHR.addEventListener("load", function(){
          if(internalSettings.accept === "application/json"){
            successCallback(JSON.parse(internalXHR.responseText));
          }
        });
      }

      if(typeof errorCallback === "function"){
        internalXHR.addEventListener("error", function(){
          if(internalSettings.accept === "application/json"){
            errorCallback(JSON.parse(internalXHR.responseText));
          }
        });
      }

      internalXHR.open("POST", uri);
      internalXHR.setRequestHeader("Content-Type", internalSettings.contentType);

      if(internalSettings.contentType === "application/json"){
        console.log("Sending HTTP POST request to: " + uri);
        internalXHR.send(JSON.stringify(data));
        console.log(JSON.stringify(data));
      }else if(internalSettings.contentType === "application/x-www-form-urlencoded"){
        console.log("Sending HTTP POST request to: " + uri);
        internalXHR.send(data);
        console.log(data);
      }
    },

    //performs a HTTP GET.
    get:  function(uri, successCallback, errorCallback){

      var internalXHR = new XMLHttpRequest();

      if(typeof successCallback === "function"){
        internalXHR.addEventListener("load", successCallback);
      }

      if(typeof errorCallback === "function"){
        internalXHR.addEventListener("error", errorCallback);
      }

      internalXHR.open("GET", uri);
      console.log("Sending HTTP GET request to: " + uri);
      internalXHR.send(JSON.stringify(data));
    },

    //performs a HTTP PUT.
    put: function(uri, data, successCallback, errorCallback){

      var internalXHR = new XMLHttpRequest();

      if(typeof successCallback === "function"){
        internalXHR.addEventListener("load", successCallback);
      }

      if(typeof errorCallback === "function"){
        internalXHR.addEventListener("error", errorCallback);
      }

      internalXHR.open("PUT", uri);
      internalXHR.setRequestHeader("Content-Type", internalSettings.contentType);

      if(internalSettings.contentType === "application/json"){
        console.log("Sending HTTP PUT request to: " + uri);
        internalXHR.send(JSON.stringify(data));
        console.log(JSON.stringify(data));
      }
    },

    //performs a HTTP DELETE.
    delete: function(uri, data, successCallback, errorCallback){

      var internalXHR = new XMLHttpRequest();

      if(typeof successCallback === "function"){
        internalXHR.addEventListener("load", successCallback);
      }

      if(typeof errorCallback === "function"){
        internalXHR.addEventListener("error", errorCallback);
      }

      internalXHR.open("DELETE", uri);
      internalXHR.setRequestHeader("Content-Type", internalSettings.contentType);

      if(internalSettings.contentType === "application/json"){
        console.log("Sending HTTP DELETE request to: " + uri);
        internalXHR.send(JSON.stringify(data));
        console.log(JSON.stringify(data));
      }
    }

  };

})();
