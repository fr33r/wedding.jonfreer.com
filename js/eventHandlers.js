
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
		var xhr = new XMLHttpRequest();
		xhr.addEventListener("load", function(){
			//window.alert(xhr.responseText);

			//show error message if the code does not match any guest.
			var matchingGuests = JSON.parse(xhr.responseText);
			if(matchingGuests.length === 0){
				var rsvpCodeInput = window.document.getElementById("rsvp-code-input");
				var errorMessage = window.document.getElementById("rsvp-modal-content-code-error-message");
				globalUtility.addClass("error", rsvpCodeInput);
				globalUtility.addClass("show", errorMessage);
			}
			var firstMatchingGuest = matchingGuests[0];
			//load up the page that shows all users that match that code.
			var contentDetail = window.document.getElementById("rsvp-modal-content-code-detail");
			var nameHeader = contentDetail.getElementsByTagName("h1")[0];
			//nameHeader.innerText = firstMatchingGuest.first_name + " " + firstMatchingGuest.last_name;
			var descriptionHeader = contentDetail.getElementsByTagName("h3")[0];
			descriptionHeader.innerText = firstMatchingGuest.guest_description;
			var hiddenGuestIdInput = window.document.getElementById("guest-id");
			hiddenGuestIdInput.value = firstMatchingGuest.guest_id;

			if(!firstMatchingGuest.has_reservation){
				var guestSection = window.document.getElementById("guest-info");
				guestSection.style.display = "none";
			}

			modalModule.configure({
				modalContentID: "rsvp-modal-content-code-detail"
			});
			modalModule.show();

		});
		xhr.open("POST", "php/enterCode.php");
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		xhr.send("rsvp-code=" + window.document.getElementById("rsvp-code-input").value);
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
			"php/enterRsvp.php",
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
			"php/enterRsvp.php",
			"guestId=" + window.document.getElementById("guest-id").value +
			"&guestDietaryRestrictions=" + window.document.getElementById("guest-dietary-restrictions").value +
			"&isAttending=0",
			submitRsvpSuccessHandler,
			submitRsvpErrorHandler);
	});
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

/*
	DEPRECATED.
*/
function setUpCanMakeItButtonHandler(){
	var detailForm = window.document.getElementById("form-rsvp-detail");
	detailForm.addEventListener("submit", function(e){
		e.preventDefault();
	});
	var canMakeItButton = window.document.getElementById("rsvp-submit-yes");
	canMakeItButton.addEventListener("click", function(e){
		var xhr = new XMLHttpRequest();
		xhr.addEventListener("load", function(){
			//window.alert(xhr.responseText);
			var response = JSON.parse(xhr.responseText);
			if(response.code == 0){
				//success.
				//window.alert(xhr.responseText);
				var rsvpSuccess = window.document.getElementById("rsvp-modal-success");
				var paragraph = rsvpSuccess.getElementsByTagName("p")[0];
				paragraph.innerText = response.message;

				modalModule.configure({
					modalContentID: "rsvp-modal-success"
				});
				modalModule.show();
			}else{
				//error.
				//window.alert(xhr.responseText);
				var rsvpError = window.document.getElementById("rsvp-modal-error");
				var paragraph = rsvpError.getElementsByTagName("p")[0];
				paragraph.innerText = response.message;

				modalModule.configure({
					modalContentID: "rsvp-modal-error"
				});
				modalModule.show();
			}
		});
		xhr.open("POST", "php/enterRsvp.php");
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		xhr.send(
			"guestId=" + window.document.getElementById("guest-id").value +
			"&guestDietaryRestrictions=" + window.document.getElementById("guest-dietary-restrictions").value +
			"&isAttending=1");
	});
}

/*
	DEPRECATED.
*/
function setUpCannotMakeItButtonHandler(){
	var detailForm = window.document.getElementById("form-rsvp-detail");
	detailForm.addEventListener("submit", function(e){
		e.preventDefault();
	});
	var canMakeItButton = window.document.getElementById("rsvp-submit-no");
	canMakeItButton.addEventListener("click", function(e){
		var xhr = new XMLHttpRequest();
		xhr.addEventListener("load", function(){
			//window.alert(xhr.responseText);
			var response = JSON.parse(xhr.responseText);
			if(response.code == 0){
				//success.

				//window.alert(xhr.responseText);
				var rsvpSuccess = window.document.getElementById("rsvp-modal-success");
				var paragraph = rsvpSuccess.getElementsByTagName("p")[0];
				paragraph.innerText = response.message;

				modalModule.configure({
					modalContentID: "rsvp-modal-success"
				});
				modalModule.show();
			}else{
				//error.

				//window.alert(xhr.responseText);
				var rsvpError = window.document.getElementById("rsvp-modal-error");
				var paragraph = rsvpError.getElementsByTagName("p")[0];
				paragraph.innerText = response.message;

				modalModule.configure({
					modalContentID: "rsvp-modal-error"
				});
				modalModule.show();
			}
		});
		xhr.open("POST", "php/enterRsvp.php");
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		xhr.send(
			"guestId=" + window.document.getElementById("guest-id").value +
			"&guestDietaryRestrictions=" + window.document.getElementById("guest-dietary-restrictions").value +
			"&isAttending=0");
	});
}

function setUpEventHandlers(){
	setUpRsvpLinkEventHandler();
	setUpRegistryLinkEventHandler();
	setUpSideNavEventHandlers();
	setUpRsvpSubmitButtonHandler();
	//setUpCanMakeItButtonHandler();
	//setUpCannotMakeItButtonHandler();
	setUpCanMakeItButtonHandlerRevised();
	setUpCannotMakeItButtonHandlerRevised();
}
