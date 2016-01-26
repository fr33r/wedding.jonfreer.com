var navigationModule = (function(utility){

	var sideNav = {};

	var internalSettings = {
		sideNavID: "side-nav",
		showClass: "show-side-nav"
	};

	var slideIn = function(){
		if(!utility.hasClass(internalSettings.showClass, sideNav)){
			utility.addClass(internalSettings.showClass, sideNav);
		}
	};

	var slideOut = function(){
		if(utility.hasClass(internalSettings.showClass, sideNav)){
			utility.removeClass(internalSettings.showClass, sideNav);
		}
	};

	//initialization.
	try{
		sideNav = window.document.getElementById(internalSettings.sideNavID);
		if(sideNav == undefined || sideNav == null){
			throw "No elements with the ID of '" + internalSettings.sideNavID + "' could be found.";
		}
	}catch(exception){
		window.alert("")
	}

	return {

		configure: function(settingsObj){
			try{
				//check to make sure that a settings object was provided.
				if(settingsObj !== undefined && settingsObj !== null){

					//update the internal settings.
					internalSettings.showClass = settingsObj.showClass !== undefined ? settingsObj.showClass : internalSettings.showClass;
					internalSettings.sideNavID = settingsObj.sideNavID !== undefined ? settingsObj.sideNavID : internalSettings.sideNavID;

				}else{
					throw new ArguementException("The settings object used to configure the navigationModule is invalid.");
				}
			}catch(exception){
				window.alert(exception.exceptionMessage);
			}
		},

		slideToggle: function(){
			if(!utility.hasClass(internalSettings.showClass, sideNav)){
				slideIn();
			}else{
				slideOut();
			}
		}
	}

})(globalUtility);