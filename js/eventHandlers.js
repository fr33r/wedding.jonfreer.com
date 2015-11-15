
function setUpMenuEventHandlers(){
	var rsvpLink = window.document.getElementById("rsvp");
	rsvpLink.addEventListener("click", function(){
		modalModule.show();
	});
}

function setUpEventHandlers(){
	setUpMenuEventHandlers();
}