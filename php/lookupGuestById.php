<?php

  require 'constants/constants.php';
  require 'domain/guest.php';
  require 'domain/address.php';
  require 'domain/reservation.php';
  require 'serviceModel/response.php';
  require 'repositories/guestRepository.php';

  //grab the values received from the query string.
  $guestId = $_GET["guestId"];

	$connection = new mysqli(DATABASE_SERVER_NAME, DATABASE_USERNAME, DATABASE_PASSWORD, DATABASE_NAME);

	if($connection->connect_error){

		$error_response = new Response();
		$error_response->code 						= 1;
		$error_response->codeDescription 	= "DATABASE CONNECTION ERROR";
		$error_response->message 					= "there was an issue connecting to the database.";

		echo(json_encode($error_response, JSON_PRETTY_PRINT));

	}else{

    $guestRepository = new GuestRepository($connection);
    $guest = $guestRepository->GetGuestById($guestId);

    if($guest == null){

    	$success_response = new Response();
    	$success_response->code 							= 0;
    	$success_response->codeDescription 		= "FAILURE";
    	$success_response->message 						= "No guests with id " . $guestId . " were found.";
      $success_response->data               = null;

    	echo(json_encode($success_response, JSON_PRETTY_PRINT));

    }else{

      $success_response = new Response();
      $success_response->code 							= 0;
      $success_response->codeDescription 		= "SUCCESS";
      $success_response->message 						= "Discovered a matching guest.";
      $success_response->data               = $guest;

      echo(json_encode($success_response, JSON_PRETTY_PRINT));

    }

    $connection->close();

  }

 ?>