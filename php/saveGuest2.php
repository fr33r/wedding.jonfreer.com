<?php

  require 'constants/constants.php';
  require 'domain/guest.php';
  require 'domain/address.php';
  require 'domain/reservation.php';
  require 'serviceModel/response.php';
  require 'repositories/guestRepository.php';
  require 'repositories/reservationRepository.php';

  $json = file_get_contents('php://input');
  $guestReceivedOther = json_decode($json);

  $guestReceived = new Guest();
  $guestReceived->guest_id = $guestReceivedOther->guestId;
  $guestReceived->first_name = $guestReceivedOther->firstName;
  $guestReceived->last_name = $guestReceivedOther->lastName;
  $guestReceived->description = $guestReceivedOther->description;
  $guestReceived->dietary_restrictions = $guestReceivedOther->dietaryRestrictions;
  $guestReceived->invite_code = $guestReceivedOther->inviteCode;

  if($guestReceivedOther->reservation != null){
    $guestReceived->reservation = new Reservation();
    $guestReceived->reservation->is_attending = $guestReceivedOther->reservation->isAttending;
  }

	$connection = new mysqli(DATABASE_SERVER_NAME, DATABASE_USERNAME, DATABASE_PASSWORD, DATABASE_NAME);

	if($connection->connect_error){

    header("Content-Type: application/json");

		$error_response = new Response();
		$error_response->code 						= 1;
		$error_response->codeDescription 	= "DATABASE CONNECTION ERROR";
		$error_response->message 					= "there was an issue connecting to the database.";

		echo(json_encode($error_response, JSON_PRETTY_PRINT));

	}else{

    $guestRepository = new GuestRepository($connection);
    $reservationRepository = new ReservationRepository($connection);

    //message to return to caller.
    $message = "";

    //check to see if the guest already exists...
    $guest = $guestRepository->GetGuestByName($guestReceived->first_name, $guestReceived->last_name);

    //if the guest already exists...
    if($guest != null){

      //update their guest information.
      $guestReceived->guest_id = $guest->guest_id;

      try{

        $guestRepository->UpdateGuest($guestReceived);

        $message = "successfully updated guest.";

        $reservation = $reservationRepository->GetReservationForGuest($guest->guest_id);

        //if there currently is a reservation for the guest...
        if($reservation != null){

          //and if the guest information recieved has reservation information...
          if($guestReceived->reservation != null){

            //update their reservation information.
            $reservationRepository->UpdateReservation(
              $reservation->reservation_id, $guestReceived->reservation->is_attending);

          }else{

            //delete their reservation.
            $reservationRepository->DeleteReservation($reservation->reservation_id);

          }

        }else{ //if there is no reservation information currently for the guest...

          //and if the guest information recieved has reservation information...
          if($guestReceived->reservation != null){

            //insert a reservation.
            $reservationRepository->InsertReservationForGuest(
              $guest->guest_id,
              $guestReceived->reservation->is_attending);
          }

        }

      }
      catch(Exception $exception){

        header("Content-Type: application/json");

        $success_response = new Response();
        $success_response->code               = 2;
        $success_response->codeDescription    = "DATABASE QUERY ERROR";
        $success_response->message            = $exception->getMessage();

        echo(json_encode($success_response, JSON_PRETTY_PRINT));

      }

    //if the guest does not exist yet...
    }else{

      //create a new guest.
      $guestRepository->InsertGuest($guestReceived);

      $message = "successfully created guest.";

      //if a reservation information was provided...
      if($guestReceived->reservation != null){

        $guest = $guestRepository->GetGuestByName($guestReceived->first_name, $guestReceived->last_name);

        //insert a reservation for the newly created guest.
        $reservationRepository->InsertReservationForGuest(
          $guest->guest_id,
          $guestReceived->reservation->is_attending);
      }

    }

    header("Content-Type: application/json");

  	$connection->close();

  	$success_response = new Response();
  	$success_response->code 							= 0;
  	$success_response->codeDescription 		= "SUCCESS";
  	$success_response->message 						= $message;

  	echo(json_encode($success_response, JSON_PRETTY_PRINT));

  }

 ?>
