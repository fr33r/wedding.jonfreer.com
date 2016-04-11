/*
  Once focus is lost on the 'last name' field,
  check to see if the 'first-name' field is filled out
  and then perform an AJAX request.
*/

function overwriteFields(response){

  if(response.data !== undefined && response.data !== null){
    var firstNameInput = window.document.getElementsByName("guest-first-name")[0];
    var lastNameInput = window.document.getElementsByName("guest-last-name")[0];
    var description = window.document.getElementsByName("guest-description")[0];
    var dietaryRestrictions = window.document.getElementsByName("guest-dietary-restrictions")[0];
    var addressLine1 = window.document.getElementsByName("guest-address-line-1")[0];
    var addressLine2 = window.document.getElementsByName("guest-address-line-2")[0];
    var addressState = window.document.getElementsByName("guest-address-state")[0];
    var addressCountry = window.document.getElementsByName("guest-address-country")[0];
    var addressZipCode = window.document.getElementsByName("guest-address-zip-code")[0];
    var hasPlusOne = window.document.getElementsByName("guest-has-plus-one")[0];
    var isAttending = window.document.getElementsByName("guest-is-attending")[0];

    firstNameInput.value = response.data.first_name;
    lastNameInput.value = response.data.last_name;
    description.value = response.data.description;
    dietaryRestrictions.value = response.data.dietary_restrictions;
    addressLine1.value = response.data.address.line_1;
    addressLine2.value = response.data.address.line_2;
    addressState.value = response.data.address.state;
    addressCountry.value = response.data.address.country;
    addressZipCode.value = response.data.address.zip_code;
    hasPlusOne.checked = response.data.has_plus_one;
    //isAttending.checked = response.data.reservation.is_attending;
  }

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
        "php/lookupGuest.php?firstName=" + firstNameInput.value + "&lastName=" + lastNameInput.value,
        overwriteFields,
        null
      );
    }
  });
}

var globalUtility = new Utility();

window.onload = function(){
	setupEventHandlers();
}
