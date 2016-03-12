
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

			//load up the page that shows all users that match that code.
			var contentDetail = window.document.getElementById("rsvp-modal-content-code-detail");
			var nameHeader = contentDetail.getElementsByTagName("h1")[0];
			nameHeader.innerText = matchingGuests[0].first_name + " " + matchingGuests[0].last_name;
			var descriptionHeader = contentDetail.getElementsByTagName("h3")[0];
			descriptionHeader.innerText = matchingGuests[0].guest_description;

			if(!matchingGuests[0].has_reservation){
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

function setUpEventHandlers(){
	setUpRsvpLinkEventHandler();
	setUpRegistryLinkEventHandler();
	setUpSideNavEventHandlers();
	setUpRsvpSubmitButtonHandler();
}