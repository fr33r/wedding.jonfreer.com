<?php

  /*
    Represents a single wedding guest.
  */
  class Guest{
    public $guest_id;
    public $first_name;
    public $last_name;
    public $description;
    public $dietary_restrictions;
    public $has_plus_one;
    public $address;
    public $reservation;
  }

  /*
    Represents a single address.
  */
  class Address{
    public $line_1;
    public $line_2;
    public $state;
    public $country;
    public $zip_code;
  }

  /*
    Represents a reservation for a single guest.
  */
  class Reservation{
    public $is_attending;
  }

  /*
    Represents the response that is to be
    sent back to the client.
  */
  class Response{
    public $code 						= 0;
    public $codeDescription	= "";
    public $message					= "";
    public $data						= [];
  }

  //grab the values received from the query string.
  $guestFirstName = $_GET["firstName"];
  $guestLastName = $_GET["lastName"];

  //hit the database.
  $serverName 	= "jonfreer.com";
	$username			= "jonfreer";
	$password			= "__Goalie31__";
	$databaseName	= "jonfreer_wedding";

	$connection = new mysqli($serverName, $username, $password, $databaseName);

	if($connection->connect_error){

		$error_response = new Response();
		$error_response->code 						= 1;
		$error_response->codeDescription 	= "DATABASE CONNECTION ERROR";
		$error_response->message 					= "there was an issue connecting to the database.";

		echo(json_encode($error_response, JSON_PRETTY_PRINT));
	}

	$sql	=   "SELECT
              G.GUEST_ID,
              G.FIRST_NAME,
              G.LAST_NAME,
              G.GUEST_DESCRIPTION,
              G.GUEST_DIETARY_RESTRICTIONS,
              G.HAS_PLUS_ONE,
              G.INVITE_CODE,
              A.ADDRESS_LINE1,
              A.ADDRESS_LINE2,
              A.STATE,
              A.COUNTRY,
              A.ZIP_CODE
            FROM
              jonfreer_wedding.GUEST AS G
              JOIN jonfreer_wedding.ADDRESS AS A
                ON G.ADDRESS_ID = A.ADDRESS_ID
              LEFT JOIN jonfreer_wedding.RESERVATION AS R
                ON G.RESERVATION_ID = R.RESERVATION_ID
            WHERE
              G.FIRST_NAME = '" . $guestFirstName . "'" .
              "AND G.LAST_NAME = '" . $guestLastName . "';";

	//echo($sql);

	$result = $connection->query($sql);

  $currentGuest = null;

  if($result->num_rows > 0){
		while($row = $result->fetch_assoc()){

			$currentGuest = new Guest();
			$currentGuest->guest_id = $row["GUEST_ID"];
			$currentGuest->first_name = $row["FIRST_NAME"];
			$currentGuest->last_name = $row["LAST_NAME"];
			$currentGuest->description = $row["GUEST_DESCRIPTION"];
      $currentGuest->dietary_restrictions = $row["GUEST_DIETARY_RESTRICTIONS"];
			if($row["HAS_PLUS_ONE"] == 1){
				$currentGuest->has_plus_one = true;
			}
      $currentGuest->address = new Address();
      $currentGuest->address->line_1 = $row["ADDRESS_LINE1"];
      $currentGuest->address->line_2 = $row["ADDRESS_LINE2"];
      $currentGuest->address->state = $row["STATE"];
      $currentGuest->address->country = $row["COUNTRY"];
      $currentGuest->address->zip_code = $row["ZIP_CODE"];

			if($row["IS_ATTENDING"] == 1){
        $currentGuest->reservation = new Reservation();
				$currentGuest->reservation->is_attending = true;
			}

		}
	}

	$connection->close();

	$success_response = new Response();
	$success_response->code 							= 0;
	$success_response->codeDescription 		= "SUCCESS";
	$success_response->message 						= "Discovered a matching guest.";
  $success_response->data               = $currentGuest;

	echo(json_encode($success_response, JSON_PRETTY_PRINT));
 ?>
