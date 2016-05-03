
function setUpRsvpLinkEventHandler(){
	var rsvpLink = window.document.getElementById("side-nav-link-rsvp");
	rsvpLink.addEventListener("click", function(e){
		e.preventDefault();
		modalModule.configure({
			modalContentID: "rsvp-modal-content-code"
		});
		modalModule.show();
	});
}

function setUpRegistryLinkEventHandler(){
	var registryLink = window.document.getElementById("side-nav-link-registry");
	registryLink.addEventListener("click", function(e){
		e.preventDefault();
		modalModule.configure({
			modalContentID: "registry-modal-content"
		});
		modalModule.show();
	});
}

function setUpSideNavEventHandlers(){
	var menuButton = window.document.getElementById("side-nav-button");
	menuButton.addEventListener("click", function(){
		navigationModule.slideToggle();
	});
}

function setUpRsvpSubmitButtonHandler(){
	var submitButton = window.document.getElementById("form-enter-code");
	submitButton.addEventListener("submit", function(e){
		e.preventDefault();
		ajaxModule.configure({contentType: "application/x-www-form-urlencoded"})
		ajaxModule.post(
			"php/enterCode2.php",
			"rsvp-code=" + window.document.getElementById("rsvp-code-input").value,
			showRsvpDetail,
			submitRsvpErrorHandler
		);
	});
}

/*
	Sets up the event listener for the 'can make it'
	button. This in turn calls the ajax module.
*/
function setUpCanMakeItButtonHandlerRevised(){

	//grab the form.
	var detailForm = window.document.getElementById("form-rsvp-detail");

	//suppress the submit button.
	detailForm.addEventListener("submit", function(e){
		e.preventDefault();
	});

	//act on the 'can make it' button.
	var canMakeItButton = window.document.getElementById("rsvp-submit-yes");
	canMakeItButton.addEventListener("click", function(e){
		ajaxModule.configure({contentType: "application/x-www-form-urlencoded"})
		ajaxModule.post(
			"php/enterRsvp2.php",
			"guestId=" + window.document.getElementById("guest-id").value +
			"&guestDietaryRestrictions=" + window.document.getElementById("guest-dietary-restrictions").value +
			"&isAttending=1",
			submitRsvpSuccessHandler,
			submitRsvpErrorHandler);
	});
}

/*
	Sets up the event listener for the 'cannot make it'
	button. This in turn calls the ajax module.
*/
function setUpCannotMakeItButtonHandlerRevised(){

	//grab the form.
	var detailForm = window.document.getElementById("form-rsvp-detail");

	//suppress the submit button.
	detailForm.addEventListener("submit", function(e){
		e.preventDefault();
	});

	//act on the 'cannot make it' button.
	var cannotMakeIt = window.document.getElementById("rsvp-submit-no");
	cannotMakeIt.addEventListener("click", function(e){
		ajaxModule.configure({contentType: "application/x-www-form-urlencoded"})
		ajaxModule.post(
			"php/enterRsvp2.php",
			"guestId=" + window.document.getElementById("guest-id").value +
			"&guestDietaryRestrictions=" + window.document.getElementById("guest-dietary-restrictions").value +
			"&isAttending=0",
			submitRsvpSuccessHandler,
			submitRsvpErrorHandler);
	});
}

/*
	Given the response containing the detailed
	information about a particular guest, this
	function is responsible for showing the
	RSVP detail screen so that the user can submit
	their RSVP.
*/
function showRsvpDetail(response){

	if(response.data.length === 0){
		submitRsvpCodeError(response);
	}else{

		// TODO: currently just arbitrarily picking the first result that
		//comes back. ultimately need to have an additional step that shows
		//all of the guests that come back on API call, before performing this logic.
		var firstMatchingGuest = response.data[0];
		var contentDetail = window.document.getElementById("rsvp-modal-content-code-detail");
		var nameHeader = contentDetail.getElementsByTagName("h1")[0];
		var descriptionHeader = contentDetail.getElementsByTagName("h3")[0];
		var hiddenGuestIdInput = window.document.getElementById("guest-id");

		nameHeader.innerText = firstMatchingGuest.first_name + " " + firstMatchingGuest.last_name;
		descriptionHeader.innerText = firstMatchingGuest.guest_description;
		hiddenGuestIdInput.value = firstMatchingGuest.guest_id;

		if(!firstMatchingGuest.has_plus_one){
			var guestSection = window.document.getElementById("guest-info");
			guestSection.style.display = "none";
		}

		modalModule.configure({
			modalContentID: "rsvp-modal-content-code-detail"
		});
		modalModule.show();
	}
}

/*
	Responsible for showing the error state on the RSVP
	code entry page. Takes in an error message to be displayed
	to the user.
*/
function submitRsvpCodeError(response){
	var rsvpCodeInput = window.document.getElementById("rsvp-code-input");
	var errorMessageElement = window.document.getElementById("rsvp-modal-content-code-error-message");
	errorMessageElement.innerText = response.message;
	globalUtility.addClass("error", rsvpCodeInput);
	globalUtility.addClass("show", errorMessageElement);
}

/*
	Event handler that is called once a successful
	request has been made to submit an RSVP.
*/
function submitRsvpSuccessHandler(response){

	if(response.code == 0){
		//success.
		var rsvpSuccess = window.document.getElementById("rsvp-modal-success");
		var paragraph = rsvpSuccess.getElementsByTagName("p")[0];
		paragraph.innerText = response.message;

		modalModule.configure({
			modalContentID: "rsvp-modal-success"
		});

	}else{
		//error.
		submitRsvpErrorHandler(response);

	}

	modalModule.show();

}

/*
	Event handler that is called if an error is
	occured during the request to submit an RSVP.
*/
function submitRsvpErrorHandler(response){
	//error.
	var rsvpError = window.document.getElementById("rsvp-modal-error");
	var paragraph = rsvpError.getElementsByTagName("p")[0];
	paragraph.innerText = response.message;

	modalModule.configure({
		modalContentID: "rsvp-modal-error"
	});
}

function setUpEventHandlers(){
	setUpRsvpLinkEventHandler();
	setUpRegistryLinkEventHandler();
	setUpSideNavEventHandlers();
	setUpRsvpSubmitButtonHandler();
	setUpCanMakeItButtonHandlerRevised();
	setUpCannotMakeItButtonHandlerRevised();
}
