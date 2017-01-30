var saveButton = window.document.getElementById("guest-info-save-button");
var firstNameInput = window.document.getElementsByName("guest-first-name")[0];
var lastNameInput = window.document.getElementsByName("guest-last-name")[0];

//register all elements that will raise events.
eventModule.add("saveButton", saveButton);
eventModule.add("lastName", lastNameInput);

//register all listeners for events.
eventModule.attach("saveButton", "click", function(e){
	e.preventDefault();

	var headers = {};
	headers["Accept"] = "application/json";

	ajaxModule.get(
			"http://freer.ddns.net:8080/api/wedding/guests/?givenName=" +
			firstNameInput.value + "&surname=" + lastNameInput.value,
			headers,
			function createOrUpdate(guests){
				var headers = {};
				headers["Accept"] = "application/json";
				headers["Content-Type"] = "application/json";	

				if(guests === null || guests.length === 0){						
					ajaxModule.post(
							"http://freer.ddns.net:8080/api/wedding/guests/", 
							headers, readFields(), function(){}, error);
				}else{
					var guestReceived = guests[0];
					var guestFromInput = readFields();
					
					if(guestReceived.reservation !== null){
						
					}
					
					//overlay field values.
					guestReceived.givenName = guestFromInput.givenName;
					guestReceived.surName = guestFromInput.surName;
					guestReceived.description = guestFromInput.description;
					guestReceived.inviteCode = guestFromInput.inviteCode;
					guestReceived.dietaryRestrictions = guestFromInput.dietaryRestrictions;
					
					if(guestReceived.reservation !== null && guestFromInput.reservation !== null){
						guestReceived.reservation.isAttending = guestFromInput.reservation.isAttending;
					}else{
						guestReceived.reservation = guestFromInput.reservation;
					}
					
					ajaxModule.put(
							"http://freer.ddns.net:8080/api/wedding/guests/" + guestReceived.id + "/", 
							headers, guestReceived, function(){}, error);
				}
			},
			error
	);
});

eventModule.attach("lastName", "blur", function(e){
	if(firstNameInput.value !== "" && lastNameInput.value !== ""){
		var headers = {};
		headers["Accept"] = "application/json";

		ajaxModule.get(
				"http://freer.ddns.net:8080/api/wedding/guests/?givenName=" +
				firstNameInput.value + "&surname=" + lastNameInput.value,
				headers,
				overwriteFields,
				error
		);
	}
});

function overwriteFields(guests){

  if(guests !== undefined && guests !== null){

    if(guests.length > 0){
        var guest = guests[0];

        var firstNameInput = window.document.getElementsByName("guest-first-name")[0];
        var lastNameInput = window.document.getElementsByName("guest-last-name")[0];
        var inviteCodeInput = window.document.getElementsByName("guest-invite-code")[0];
        var description = window.document.getElementsByName("guest-description")[0];
        var dietaryRestrictions = window.document.getElementsByName("guest-dietary-restrictions")[0];
        var reservationStatus = window.document.getElementsByName("guest-reservation-status")[0];
        var reservationStatusOptions = reservationStatus.options;

        firstNameInput.value = guest.givenName;
        lastNameInput.value = guest.surName;
        inviteCodeInput.value = guest.inviteCode;
        description.value = guest.description;
        dietaryRestrictions.value = guest.dietaryRestrictions;

        if(guest.reservation !== undefined && guest.reservation !== null){
          if(guest.reservation.isAttending){
            reservationStatus.options[2].selected = true;
          }else{
            reservationStatus.options[1].selected = true;
          }
        }else{
          reservationStatus.options[0].selected = true;
        }
    }else{
        window.alert("no guests were found.");
    }
  }
}

function readFields(){
  var firstNameInput = window.document.getElementsByName("guest-first-name")[0];
  var lastNameInput = window.document.getElementsByName("guest-last-name")[0];
  var inviteCodeInput = window.document.getElementsByName("guest-invite-code")[0];
  var description = window.document.getElementsByName("guest-description")[0];
  var dietaryRestrictions = window.document.getElementsByName("guest-dietary-restrictions")[0];
  var reservationStatus = window.document.getElementsByName("guest-reservation-status")[0];

  var reservation = null;

  if(reservationStatus.selectedIndex === 1){
    reservation = new Reservation(null, false, null);
  }else if(reservationStatus.selectedIndex === 2){
    reservation = new Reservation(null, true, null);
  }

  var guest = new Guest(
    firstNameInput.value,
    lastNameInput.value,
    inviteCodeInput.value,
    description.value,
    dietaryRestrictions.value,
    reservation
  );

  return guest;
}

function error(response){
  window.alert(response);
}
