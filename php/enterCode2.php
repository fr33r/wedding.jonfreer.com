<?php

  	require 'constants/constants.php';
	require 'domain/guest.php';
  	require 'domain/reservation.php';
  	require 'domain/address.php';
  	require 'serviceModel/response.php';
  	require 'repositories/guestRepository.php';

	//grab the code that the user entered.
	$codeEntered = $_POST["rsvp-code"];

	$connection = new mysqli(DATABASE_SERVER_NAME, DATABASE_USERNAME, DATABASE_PASSWORD, DATABASE_NAME);

	if($connection->connect_error){

		//create error response object.
		$error_response = new Response();
		$error_response->code 				= 1;
		$error_response->codeDescription 	= "DATABASE CONNECTION ERROR";
		$error_response->message 			= "there was an issue connecting to the database.";

		echo(json_encode($error_response, JSON_PRETTY_PRINT));
	}
	else
  {

		$guestRepository = new GuestRepository($connection);
	  	$guestsWithMatchingInviteCode = $guestRepository->GetGuestsWithMatchingInviteCode($codeEntered);

		//create the success response object.
		$success_response = new Response();
		$success_response->code 				= 0;
		$success_response->codeDescription 		= "SUCCESS";
		$success_response->message 				= "there were " . count($guestsWithMatchingInviteCode) . " guests found with code " . $codeEntered . ".";
		$success_response->data 				=	$guestsWithMatchingInviteCode;

		//close the database connection. maybe can do this earlier? need the $result object.
		$connection->close();

		header("Content-Type: application/json");

		//send it off.
		echo(json_encode($success_response, JSON_PRETTY_PRINT));

	}

?>
