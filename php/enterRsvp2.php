<?php

  require 'constants/constants.php';
  require 'domain/address.php';
  require 'domain/guest.php';
  require 'domain/reservation.php';
  require 'serviceModel/response.php';
  require 'repositories/reservationRepository.php';

	//grab the code that the user entered.
	$guestId = $_POST["guestId"];
	$guestDietaryRestrictions = $_POST["guestDietaryRestrictions"];
	$isAttending = $_POST["isAttending"];

	$connection = new mysqli(DATABASE_SERVER_NAME, DATABASE_USERNAME, DATABASE_PASSWORD, DATABASE_NAME);

	if($connection->connect_error){

		$error_response = new Response();
		$error_response->code 				= 1;
		$error_response->codeDescription 	= "DATABASE CONNECTION ERROR";
		$error_response->message 			= "there was an issue connecting to the database.";

		echo(json_encode($error_response, JSON_PRETTY_PRINT));
	}
	else
	{
		$reservationRepository = new ReservationRepository($connection);
	  	$reservationRepository->InsertReservationForGuest($guestId, $isAttending);

		$connection->close();

		$success_response = new Response();
		$success_response->code 				= 0;
		$success_response->codeDescription 		= "SUCCESS";
		$success_response->message 				= "we successfully received your rsvp.";

	  	header("Content-Type: application/json");

		echo(json_encode($success_response, JSON_PRETTY_PRINT));
	}
?>
