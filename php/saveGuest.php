<?php

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

  $json = file_get_contents('php://input');
  echo($json);
  $guest = json_decode($json);

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

	}else{

    $sql	=   "SELECT
                 G.GUEST_ID,
                 R.RESERVATION_ID
               FROM
                 jonfreer_wedding.GUEST AS G
                 LEFT JOIN jonfreer_wedding.RESERVATION AS R
                  ON G.RESERVATION_ID = R.RESERVATION_ID
               WHERE
                 G.FIRST_NAME = '" . $guestFirstName . "'" .
                 "AND G.LAST_NAME = '" . $guestLastName . "';";

  	//echo($sql);

  	$result = $connection->query($sql);

    if($result->num_rows > 0){
      //doing an update...
      $row = $result->fetch_assoc();
      $guestId = $row["GUEST_ID"];
      $reservationId = $row["RESERVATION_ID"];

      $sql	=   "CALL jonfreer_wedding.UPDATE_GUEST
    				      (" .
				                    $guestId . "," .
                      "'" . $guest->firstName . "'," .
                      "'" . $guest->lastName . "'," .
                      "'" . $guest->description . "'," .
                      "'" . $guest->dietaryRestrictions . "'" .
                            $guest->hasPlusOne .
  				        ");";

      $connection->query($sql);

      $sql	=   "CALL jonfreer_wedding.UPDATE_RESERVATION
    				      (" .
				                    $reservationId . "," .
                            $guest->isAttending .
  				        ");";

      $connection->query($sql);

    }else{
      //doing an insert...
      $sql	=   "CALL jonfreer_wedding.INSERT_GUEST
    				      (" .
                      "'" . $guest->firstName . "'," .
                      "'" . $guest->lastName . "'," .
                      "'" . $guest->description . "'," .
                            $guest->hasPlusOne . "," .
                      "'" . $guest->address->line1 . "'," .
                      "'" . $guest->address->line2 . "'," .
                      "'" . $guest->address->state . "'," .
                      "'" . $guest->address->country . "'," .
                      "'" . $guest->address->zipCode .
  				        ");";

      $connection->query($sql);

      //if a reservation was provided.
      if($guest->reservation != null){

        $sql	=   "SELECT
                     G.GUEST_ID
                   FROM
                     jonfreer_wedding.GUEST AS G
                     LEFT JOIN jonfreer_wedding.RESERVATION AS R
                      ON G.RESERVATION_ID = R.RESERVATION_ID
                   WHERE
                     G.FIRST_NAME = '" . $guestFirstName . "'" .
                     "AND G.LAST_NAME = '" . $guestLastName . "';";

        $connection->query($sql);

        if($result->num_rows > 0){
          $row = $result->fetch_assoc();
          $guestId = $row["GUEST_ID"];

          $sql	=   "CALL jonfreer_wedding.INSERT_RESERVATION
        				      (" .
                                $guestId . "," .
  				                "'" . $guest->dietaryRestrictions . "'," .
                                $guest->isAttending .
      				        ");";

          $connection->query($sql);
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
  }

 ?>
