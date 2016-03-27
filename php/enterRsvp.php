<?php

	class Response{
		public $code 						= 0;
		public $codeDescription	= "";
		public $message					= "";
	}

	//grab the code that the user entered.
	$guestId = $_POST["guestId"];
	$guestDietaryRestrictions = $_POST["guestDietaryRestrictions"];
	$isAttending = $_POST["isAttending"];

	$serverName 	= "localhost";
	//$serverName 	= "jonfreer.com";
	$username			= "jonfreer";
	$password			= "__Goalie31__";
	$databaseName	= "jonfreer_wedding";

	$connection = new mysqli($serverName, $username, $password, $databaseName);

	if($connection->connect_error){

		$error_response = new Response();
		$error_response->code 						= 1;
		$error_response->codeDescription 	= "ERROR";
		$error_response->message 					= "there was an issue connecting to the database.";

		echo(json_encode($error_response, JSON_PRETTY_PRINT));
	}

	$sql	=   "CALL jonfreer_wedding.INSERT_RESERVATION
				(" .
					$guestId . "," .
					"'" . $guestDietaryRestrictions . "'," .
					$isAttending .
				");";

	//echo($sql);

	$result = $connection->query($sql);

	$connection->close();

	$success_response = new Response();
	$success_response->code 							= 0;
	$success_response->codeDescription 		= "SUCCESS";
	$success_response->message 						= "we successfully received your rsvp.";

	echo(json_encode($success_response, JSON_PRETTY_PRINT));

?>
