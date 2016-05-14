function setUpRsvpLinkEventHandler(){
	var rsvpLink = window.document.getElementById("side-nav-link-rsvp");
	rsvpLink.addEventListener("click", function(e){
		e.preventDefault();
		modalModule.configure({
			modalContentID: "rsvp-modal-content-code"
			//modalContentID: "rsvp-model-matching-guest-list"
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
			submitRsvpCodeSuccess,
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
function showRsvpDetail(guest){

	var contentDetail = window.document.getElementById("rsvp-modal-content-code-detail");
	var nameHeader = contentDetail.getElementsByTagName("h1")[0];
	var descriptionHeader = contentDetail.getElementsByTagName("h3")[0];
	var hiddenGuestIdInput = window.document.getElementById("guest-id");
	var dietaryRestrictions = window.document.getElementById("guest-dietary-restrictions");

	nameHeader.innerText = guest.first_name + " " + guest.last_name;
	descriptionHeader.innerText = guest.description;
	hiddenGuestIdInput.value = guest.guest_id;
	dietaryRestrictions.value = guest.dietary_restrictions;

	modalModule.configure({
		modalContentID: "rsvp-modal-content-code-detail"
	});
	modalModule.show();

}

/*

*/
function submitRsvpCodeSuccess(response){
	if(response.data.length === 0){
		submitRsvpCodeError(response);
	}else{
		var guestsWithoutReservations = [];

		for(var i =0; i < response.data.length; i++){
			if(response.data[i].reservation == null){
				guestsWithoutReservations.push(response.data[i]);
			}
		}

		if(guestsWithoutReservations.length > 0){
			clearMatchingGuestList();
			showMatchingGuestsWithoutReservation(guestsWithoutReservations);
		}else{
			showThatAllMatchingGuestsHaveReservation();
		}

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

/*
	Dynamically constructs the list of the guests that match the
	entered invite code that have not made a reservation yet.
*/
function showMatchingGuestsWithoutReservation(guestsWithoutReservation){

	var fragment = window.document.createDocumentFragment();
	var centeredDiv = window.document.createElement("div");
	centeredDiv.className = "vert-horiz-centered";

	var numOfGuestWithoutReservation = guestsWithoutReservation.length;

	for(var i = 0; i < numOfGuestWithoutReservation; i++){
		var div = window.document.createElement("div");
		div.addEventListener("click", clickMatchingGuestCardHandler, false);
		var header2 = window.document.createElement("h2");
		var header4 = window.document.createElement("h4");
		var horizRule = window.document.createElement("hr");

		div.className = "matching-guest-card";
		div.setAttribute("data-guest-id", guestsWithoutReservation[i].guest_id);
		header2.innerHTML = guestsWithoutReservation[i].first_name +
		" " + guestsWithoutReservation[i].last_name;
		header4.innerHTML = guestsWithoutReservation[i].description;
		horizRule.className = "section-header-bar spaced";

		div.appendChild(header2);
		div.appendChild(header4);
		centeredDiv.appendChild(div);

		if(i != numOfGuestWithoutReservation - 1){
			centeredDiv.appendChild(horizRule);
		}
	}

	var modalContent = window.document.getElementById("rsvp-model-matching-guest-list");
	fragment.appendChild(centeredDiv);
	modalContent.appendChild(fragment);

	modalModule.configure({
		modalContentID: "rsvp-model-matching-guest-list"
	});

	modalModule.show();
}

function clearMatchingGuestList(){

	var modalContent = window.document.getElementById("rsvp-model-matching-guest-list");
	var centeredDivs = modalContent.getElementsByClassName("vert-horiz-centered");

	if(centeredDivs != null && centeredDivs.length > 0){
		var centeredDiv = centeredDivs[0];
		modalContent.removeChild(centeredDiv);
	}

}


/*
	Handles when the click event fires for the
	matching guest cards.
*/
function clickMatchingGuestCardHandler(e){
	var guestId = this.dataset.guestId;

	ajaxModule.configure({contentType: "application/x-www-form-urlencoded"})
	ajaxModule.get(
		"php/lookupGuestById.php?guestId="+ guestId,
		clickMatchingGuestCardSuccessHandler,
		null);
}

function clickMatchingGuestCardSuccessHandler(response){
	if(response.data.length === 0){
		window.alert("someting went wrong!");
	}else{
		var guest = response.data;
		showRsvpDetail(guest);
	}
}

function showThatAllMatchingGuestsHaveReservation(){

	modalModule.configure({
		modalContentID: "rsvp-all-guests-have-reservation"
	});

	modalModule.show();

}

function setUpEventHandlers(){
	setUpRsvpLinkEventHandler();
	setUpRegistryLinkEventHandler();
	setUpSideNavEventHandlers();
	setUpRsvpSubmitButtonHandler();
	setUpCanMakeItButtonHandlerRevised();
	setUpCannotMakeItButtonHandlerRevised();
}
