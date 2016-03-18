
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
		//e.preventDefault();
		modalModule.configure({
			modalContentID: "registry-modal-content"
		});
		//modalModule.show();
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
	setUpCanMakeItButtonHandler();
	setUpCannotMakeItButtonHandler();
}