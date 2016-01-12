
function setUpMenuEventHandlers(){
	var rsvpLink = window.document.getElementById("rsvp");
	rsvpLink.addEventListener("click", function(){
		modalModule.configure({
			modalContentID: "rsvp-modal-content-code-detail"
		});
		modalModule.show();
	});
}

function setUpEventHandlers(){
	setUpMenuEventHandlers();
}