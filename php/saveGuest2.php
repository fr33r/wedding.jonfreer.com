<?php

  require 'constants/constants.php';
  require 'domain/guest.php';
  require 'domain/address.php';
  require 'domain/reservation.php';
  require 'serviceModel/response.php';
  require 'repositories/guestRepository.php';

  $json = file_get_contents('php://input');
  $guestReceived = json_decode($json);

	$connection = new mysqli(DATABASE_SERVER_NAME, DATABASE_USERNAME, DATABASE_PASSWORD, DATABASE_NAME);

	if($connection->connect_error){

		$error_response = new Response();
		$error_response->code 						= 1;
		$error_response->codeDescription 	= "DATABASE CONNECTION ERROR";
		$error_response->message 					= "there was an issue connecting to the database.";

		echo(json_encode($error_response, JSON_PRETTY_PRINT));

	}else{

    $guestRepository = new GuestRepository($connection);
    $reservationRepository = new ReservationRepository($connection);

    //check to see if the guest already exists...
    $guest = $guestRepository->GetGuestByName($guestReceived->firstName, $guestReceived->lastName);

    //if so...
    if($guest != null){

      //update their guest information.
      $guestRepository->UpdateGuest($guestReceived);

      //update their reservation information.
      $reservationRepository->UpdateReservation(
        $guest->reservation->reservationId, $guestReceived->reservation->isAttending);

    //if not...
    }else{

      //create a new guest.
      $guestRepository->InsertGuest($guest);

      //if a reservation information was provided...
      if($guest->reservation != null){

        //insert a reservation for the newly created guest.
        $reservationRepository->InsertReservation(
          $guest->guestId,
          $guest->dietaryRestrictions,
          $guest->plusOneFirstName,
          $guest->plusOneLastName,
          $guest->reservation->isAttending);
      }
    }
  }

  header("Content-Type: application/json");

	$connection->close();

	$success_response = new Response();
	$success_response->code 							= 0;
	$success_response->codeDescription 		= "SUCCESS";
	$success_response->message 						= "Saved guest successfully.";

	echo(json_encode($success_response, JSON_PRETTY_PRINT));

 ?>
