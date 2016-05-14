function Guest(firstName, lastName, inviteCode, description, dietaryRestrictions, reservation){
  this.firstName = firstName;
  this.lastName = lastName;
  this.inviteCode = inviteCode;
  this.description = description;
  this.dietaryRestrictions = dietaryRestrictions;
  this.reservation = reservation;
}

function Reservation(isAttending){
  this.isAttending = isAttending;
}

/*
  Once focus is lost on the 'last name' field,
  check to see if the 'first-name' field is filled out
  and then perform an AJAX request.
*/

function overwriteFields(response){

  if(response.data !== undefined && response.data !== null){
    var firstNameInput = window.document.getElementsByName("guest-first-name")[0];
    var lastNameInput = window.document.getElementsByName("guest-last-name")[0];
    var inviteCodeInput = window.document.getElementsByName("guest-invite-code")[0];
    var description = window.document.getElementsByName("guest-description")[0];
    var dietaryRestrictions = window.document.getElementsByName("guest-dietary-restrictions")[0];
    var reservationStatus = window.document.getElementsByName("guest-reservation-status")[0];
    var reservationStatusOptions = reservationStatus.options;

    firstNameInput.value = response.data.first_name;
    lastNameInput.value = response.data.last_name;
    inviteCodeInput.value = response.data.invite_code;
    description.value = response.data.description;
    dietaryRestrictions.value = response.data.dietary_restrictions;

    if(response.data.reservation !== undefined && response.data.reservation !== null){
      if(response.data.reservation.is_attending){
        reservationStatus.options[2].selected = true;
      }else{
        reservationStatus.options[1].selected = true;
      }
    }else{
      reservationStatus.options[0].selected = true;
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
    reservation = new Reservation(false);
  }else if(reservationStatus.selectedIndex === 2){
    reservation = new Reservation(true);
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

function success(response){
  window.alert(response.message);
}

function error(response){
  window.alert(response.message);
}

function setupEventHandlers(){

  //grab the 'first-name' field.
  var firstNameInput = window.document.getElementsByName("guest-first-name")[0];

  //grab the 'last-name' field.
  var lastNameInput = window.document.getElementsByName("guest-last-name")[0];

  //set up event handlers.
  lastNameInput.addEventListener("blur", function blurEvent(e){
    if(firstNameInput.value !== "" && lastNameInput.value !== ""){
      ajaxModule.get(
        "php/lookupGuest2.php?firstName=" + firstNameInput.value + "&lastName=" + lastNameInput.value,
        overwriteFields,
        null
      );
    }
  });

  var saveButton = window.document.getElementById("guest-info-save-button");

  saveButton.addEventListener("click", function(e){
    e.preventDefault();
    var guest = readFields();
    ajaxModule.post(
      "php/saveGuest2.php",
      guest,
      success,
      error
    );
  });
}

var globalUtility = new Utility();

window.onload = function(){
	setupEventHandlers();

  var guestDescription = window.document.getElementsByName("guest-description")[0];
  guestDescription.value = 'Guest';
}
