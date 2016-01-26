
function setUpRsvpLinkEventHandler(){
	var rsvpLink = window.document.getElementById("side-nav-link-rsvp");
	rsvpLink.addEventListener("click", function(e){
		e.preventDefault();
		modalModule.configure({
			modalContentID: "rsvp-modal-content-code-detail"
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

function setUpEventHandlers(){
	setUpRsvpLinkEventHandler();
	setUpSideNavEventHandlers();
}