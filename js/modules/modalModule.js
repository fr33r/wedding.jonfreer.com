/*
	The modalModule.

	Takes on the responsibility of managing all modal functionality for a page.
*/
var modalModule = (function(utility){
	
	//current modal view that the modalModule is configured for.
	var currentModalContent 	= {};
	var currentModalCloseButton = {};
	var modalContainer 			= {};

	//current settings of the modalModule.
	//initialization is for setting defaults.s
	var internalSettings = {
		showClass: 				"show-modal",				//class used to show the modal view; one time.
		showContentClass: 		"show-content", 
		modalContentID: 		"rsvp-modal-content-code",		//the ID of the modal content to show/hide.
		modalContentClass: 		"modal-content",			//class used to identify an element as a particular section of modal content; one time.
		modalContainerClass: 	"modal-container"			//class used to identify the single element representing the container for all possible modal content; one time.
	};

	/*
		Grabs the modal container on the page.
	*/
	var findModalContainer = function(){
		var container = window.document.getElementsByClassName(internalSettings.modalContainerClass);

		//if we got more than one container.
		if(container.length > 1){
			throw new TooManyModalContainersException("You cannot have more than one modal container per page.");
		}else if(container.length < 1){
			throw new ModalContainerNotFoundException("You must have exactly one modal container on the page.");
		}

		//console.log("Found modal container.");

		modalContainer = container[0];
	};

	/*
		Grab the modal content to hide or show.
	*/
	var findModalContent = function(){

		var allModalContent = modalContainer.getElementsByClassName(internalSettings.modalContentClass);

		if(allModalContent.length < 1){
			throw new ModalContentNotFoundException("No modal content could be found.");
		}

		//set the current modal content to the first found modal content by default.
		currentModalContent = allModalContent[0];

		var desiredContent = window.document.getElementById(internalSettings.modalContentID);

		if(desiredContent === undefined && desiredContent === null){
			throw new ModalContentNotFoundException("The modal content with id='" + internalSettings.modalContentID + "' could not be found.");
		}

		//set the current modal content to the content that matches the content ID.
		currentModalContent = desiredContent;

		//console.log("Found modal content.");

	};

	//shows the current modal view with the configured content.
	var show = function(){

		//show the modal container if it is not already showing.
		if(!utility.hasClass(internalSettings.showClass, modalContainer)){

			//show the content that is currently specified in the settings.
			var modalContentToShow = window.document.getElementById(internalSettings.modalContentID);
			utility.addClass(internalSettings.showContentClass, modalContentToShow);
			
			utility.addClass(internalSettings.showClass, modalContainer);

		}else{
			//hide all of the current modal content showing.
			var modalContent = window.document.getElementsByClassName(internalSettings.modalContentClass);

			//hide all modal content.
			for(var i = 0; i < modalContent.length; i++){
				if(utility.hasClass(internalSettings.showContentClass, modalContent[i])){
					utility.removeClass(internalSettings.showContentClass, modalContent[i]);
				}
			}

			//show the content that is currently specified in the settings.
			var modalContentToShow = window.document.getElementById(internalSettings.modalContentID);
			utility.addClass(internalSettings.showContentClass, modalContentToShow);
		}
		//show the appropriate modal content.
	};

	//hides the current modal view.
	var hide = function(){

		//hide the modal container.
		utility.removeClass(internalSettings.showClass, modalContainer);

		//get all modal content.
		var modalContent = window.document.getElementsByClassName(internalSettings.modalContentClass);

		//hide all modal content.
		for(var i = 0; i < modalContent.length; i++){
			if(utility.hasClass(internalSettings.showContentClass, modalContent[i])){
				utility.removeClass(internalSettings.showContentClass, modalContent[i]);
			}
		}
	};

	//adds the event listener to the modal close button within
	//the current modal container.
	var attachCloseButtonClickEventHandler = function(){
		currentModalCloseButton = modalContainer.getElementsByClassName("modal-close-button-container")[0];
		currentModalCloseButton.addEventListener("click", function(){
			if(utility.hasClass(internalSettings.showClass, modalContainer)){
				modalModule.hide();
			}
		});
	};

	//removes the event listener attached to the modal close button
	//within the current modal container.
	var removeCloseButtonClickEventHandler = function(){
		currentModalCloseButton = modalContainer.getElementsByClassName("modal-close-button-container")[0];
		currentModalCloseButton.removeEventListener("click", function(){
			if(utility.hasClass(internalSettings.showClass, modalContainer)){
				modalModule.hide();
			}
		});
	};

	//initialization.
	try{
		findModalContainer();
		attachCloseButtonClickEventHandler();
	}catch(exception){
		window.alert(exceptionMessage);
	}

	return {

		/*
			Updates the settings of the module.

			Only updates the settings that are provided.
		*/
		configure: function(settingsObj){

			try{
				//check to make sure that a settings object was provided.
				if(settingsObj !== undefined && settingsObj !== null){

					//remove the modal close button event handler.
					removeCloseButtonClickEventHandler();

					//update the internal settings.
					internalSettings.showClass = settingsObj.showClass !== undefined ? settingsObj.showClass : internalSettings.showClass;
					internalSettings.showContentClass = settingsObj.showContentClass !== undefined ? settingsObj.showContentClass : internalSettings.showContentClass;
					internalSettings.modalContentID = settingsObj.modalContentID !== undefined ? settingsObj.modalContentID : internalSettings.modalContentID;
					internalSettings.modalContentClass = settingsObj.modalContentClass !== undefined ? settingsObj.modalContentClass : internalSettings.modalContentClass;
					internalSettings.modalContainerClass = settingsObj.modalContainerClass !== undefined ? settingsObj.modalContainerClass : internalSettings.modalContainerClass;

					findModalContent();

					//add the modal close button event handler.
					attachCloseButtonClickEventHandler();

				}else{
					throw new ArguementException("The settings object used to configure the modalModule is invalid.");
				}
			}catch(exception){
				window.alert(exception.exceptionMessage);
			}
		},

		/*
			Shows the currently configured modal view.
		*/
		show: function(){
			show();
		},

		/*
			Hides the currently configured modal view.
		*/
		hide: function(){
			hide();
		}

	};

})(globalUtility);