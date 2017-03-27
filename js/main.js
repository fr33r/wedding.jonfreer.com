eventModule.add("rsvp-link", window.document.getElementById("side-nav-link-rsvp"));
eventModule.add("registry-link", window.document.getElementById("side-nav-link-registry"));
eventModule.add("nav-button", window.document.getElementById("side-nav-button"));
eventModule.add("enter-code-button", window.document.getElementById("rsvp-code-submit-button"));
eventModule.add("enter-code-form", window.document.getElementById("form-enter-code"));
eventModule.add("rsvp-detail-form", window.document.getElementById("form-rsvp-detail"));
eventModule.add("coming-button", window.document.getElementById("rsvp-submit-yes"));
eventModule.add("not-coming-button", window.document.getElementById("rsvp-submit-no"));

eventModule.attach("rsvp-link", "click", function(e){
	e.preventDefault();
	modalModule.configure({
		modalContentID: "rsvp-modal-content-code"
	});
	modalModule.show();
});

eventModule.attach("registry-link", "click", function(e){
	e.preventDefault();
	modalModule.configure({
		modalContentID: "registry-modal-content"
	});
	modalModule.show();
});

eventModule.attach("nav-button", "click", function(){
	navigationModule.slideToggle();
});

eventModule.attach("enter-code-button", "click", function(e){
	e.preventDefault();
	var headers = {};
	headers["Accept"] = "application/json";
	
	ajaxModule.get(
        "http://freer.ddns.net:8080/api/wedding/guests/?inviteCode=" +
            window.document.getElementById("rsvp-code-input").value,
        headers,
        submitRsvpCodeSuccess,
        submitRsvpErrorHandler
    );
});

eventModule.attach("enter-code-form", "submit", function(e){e.preventDefault();});
eventModule.attach("rsvp-detail-form", "submit", function(e){e.preventDefault();});

eventModule.attach("coming-button", "click", function(e){
	e.preventDefault();
	var guestId = window.document.getElementById("guest-id").value;
	
	getGuest(guestId, function(guest){
		guest.reservation = new Reservation(null, true, null);
		updateGuest(guest, submitRsvpSuccessHandler, submitRsvpErrorHandler);
	}, submitRsvpErrorHandler);
});

eventModule.attach("not-coming-button", "click", function(e){
	e.preventDefault();
	var guestId = window.document.getElementById("guest-id").value;
	
	getGuest(guestId, function(guest){
		guest.reservation = new Reservation(null, false, null);
		guest.dietaryRestrictions = window.document.getElementById("guest-dietary-restrictions").value;
		updateGuest(guest, submitRsvpSuccessHandler, submitRsvpErrorHandler);
	}, submitRsvpErrorHandler);
});

function updateGuest(guest, success, fail){
	var guestUri = "http://freer.ddns.net:8080/api/wedding/guests/" + guest.id + "/";
	
	var headers = {};
	headers["Accept"] = "application/json";
	headers["Content-Type"] = "application/json";
	
	ajaxModule.put(
        guestUri,
        headers,
        guest,
        success,
        fail
    );
}

function getGuest(guestId, success, fail){
	var guestUri = "http://freer.ddns.net:8080/api/wedding/guests/" + guestId + "/";
	
	var headers = {};
	headers["Accept"] = "application/json";

	ajaxModule.get(
        guestUri,
        headers,
        success,
        fail
    );
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

	nameHeader.innerText = guest.givenName + " " + guest.surName;
	descriptionHeader.innerText = guest.description;
	hiddenGuestIdInput.value = guest.id;
	dietaryRestrictions.value = guest.dietaryRestrictions;

	modalModule.configure({
		modalContentID: "rsvp-modal-content-code-detail"
	});
	modalModule.show();

}

function submitRsvpCodeSuccess(matchingGuests){
	if(matchingGuests.length === 0){
		submitRsvpCodeError(matchingGuests);
	}else{
		var guestsWithoutReservations = [];

		for(var i =0; i < matchingGuests.length; i++){
			if(matchingGuests[i].reservation == null){
				guestsWithoutReservations.push(matchingGuests[i]);
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
function submitRsvpCodeError(){
	var rsvpCodeInput = window.document.getElementById("rsvp-code-input");
	var errorMessageElement = window.document.getElementById("rsvp-modal-content-code-error-message");
	errorMessageElement.innerText = "no guests found.";
	globalUtility.addClass("error", rsvpCodeInput);
	globalUtility.addClass("show", errorMessageElement);
}

/*
	Event handler that is called once a successful
	request has been made to submit an RSVP.
*/
function submitRsvpSuccessHandler(){
	var rsvpSuccess = window.document.getElementById("rsvp-modal-success");
	var paragraph = rsvpSuccess.getElementsByTagName("p")[0];
	paragraph.innerText = "you're all set!";

	modalModule.configure({
		modalContentID: "rsvp-modal-success"
	});

	modalModule.show();
}

function submitRsvpErrorHandler(message){
	var rsvpError = window.document.getElementById("rsvp-modal-error");
	var paragraph = rsvpError.getElementsByTagName("p")[0];
	paragraph.innerText = message;

	modalModule.configure({
		modalContentID: "rsvp-modal-error"
	});
	
	modalModule.show();
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
		div.setAttribute("data-guest-id", guestsWithoutReservation[i].id);
		header2.innerHTML = guestsWithoutReservation[i].givenName +
		" " + guestsWithoutReservation[i].surName;
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
	
	var headers = {};
	headers["Accept"] = "application/json";

	ajaxModule.get(
		"http://freer.ddns.net:8080/api/wedding/guests/"+ guestId + "/",
		headers,
		showRsvpDetail,
		function(){});
}

function showThatAllMatchingGuestsHaveReservation(){

	modalModule.configure({
		modalContentID: "rsvp-all-guests-have-reservation"
	});

	modalModule.show();

}