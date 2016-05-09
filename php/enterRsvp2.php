<?php

  require 'constants/constants.php';
  require 'domain/address.php';
  require 'domain/guest.php';
  require 'domain/reservation.php';
  require 'serviceModel/response.php';
  require 'repositories/reservationRepository.php';
  require 'repositories/guestRepository.php';

	//grab the code that the user entered.
	$guestId = $_POST["guestId"];
	$guestDietaryRestrictions = $_POST["guestDietaryRestrictions"];
	$isAttending = $_POST["isAttending"];

	$connection = new mysqli(DATABASE_SERVER_NAME, DATABASE_USERNAME, DATABASE_PASSWORD, DATABASE_NAME);

	if($connection->connect_error){

		$error_response = new Response();
		$error_response->code 				    = 1;
		$error_response->codeDescription 	= "DATABASE CONNECTION ERROR";
		$error_response->message 			    = "there was an issue connecting to the database.";

		echo(json_encode($error_response, JSON_PRETTY_PRINT));
	}
	else
	{
      try{

        //update reservation information.
  		  $reservationRepository = new ReservationRepository($connection);
  	  	$reservationRepository->InsertReservationForGuest($guestId, $isAttending);

        //retrieve guest information so update can be performed.
        $guestRepository = new GuestRepository($connection);
        $guest = $guestRepository->GetGuestById($guestId);

        if($guest != null){

          $guest->dietary_restrictions = $guestDietaryRestrictions;

          //update guest information.
          $guestRepository->UpdateGuest($guest);
    
        }

        $connection->close();

        $success_response = new Response();
        $success_response->code 				       = 0;
  	    $success_response->codeDescription 		 = "SUCCESS";
  	    $success_response->message 				     = "we successfully received your rsvp.";

  	  	header("Content-Type: application/json");

  	    echo(json_encode($success_response, JSON_PRETTY_PRINT));

      }catch(Exception $exception){

        header("Content-Type: application/json");

        $success_response = new Response();
        $success_response->code               = 2;
        $success_response->codeDescription    = "DATABASE QUERY ERROR";
        $success_response->message            = $exception->getMessage();

        echo(json_encode($success_response, JSON_PRETTY_PRINT));

      }
	}
?>
