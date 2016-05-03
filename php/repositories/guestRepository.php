<?php

  /*
    Performs all direct access logic against the data SplObjectStorage
    that contains information about the reservations.
  */
  class GuestRepository{

    public function __construct($mysqlConnection){
      $this->mysqlConnection = $mysqlConnection;
    }

    /*
      Retrieves a guest with the first and last name provided.
    */
    public function GetGuestByName($first_name, $last_name){

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
                  G.FIRST_NAME = '" . $first_name . "'" .
                  "AND G.LAST_NAME = '" . $last_name . "';";

    	$result = $connection->query($sql);

      $currentGuest = null;

      if($result->num_rows > 0){

        //even though i am using a while, i am strategically
        //hoping that there is only one guest returned.
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

      return $currentGuest;
    }

    /*
      Retrieves a guest with the id provided.
    */
    public function GetGuestById($id){

      $sql	=   "SELECT
                  G.GUEST_ID,
                  G.FIRST_NAME,
                  G.LAST_NAME,
                  G.GUEST_DESCRIPTION,
                  G.GUEST_DIETARY_RESTRICTIONS,
                  G.HAS_PLUS_ONE,
                  G.INVITE_CODE,
                  R.IS_ATTENDING,
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
                  G.GUEST_ID = " . $id . ";";

      $result = $connection->query($sql);

      $currentGuest = null;

      if($result->num_rows > 0){

        //even though i am using a while, i am strategically
        //hoping that there is only one guest returned.
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

      return $currentGuest;
    }

    /*
      Inserts a new guest.
    */
    public function InsertGuest($guest){

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

    }

    /*
      Updates an already existing guest.
    */
    public function UpdateGuest($guest){

      $sql	=   "CALL jonfreer_wedding.UPDATE_GUEST
                  (" .
                            $guest->guestId . "," .
                      "'" . $guest->firstName . "'," .
                      "'" . $guest->lastName . "'," .
                      "'" . $guest->description . "'," .
                      "'" . $guest->dietaryRestrictions . "'" .
                            $guest->hasPlusOne .
                  ");";

      $connection->query($sql);

    }

    /*
      Given an invite code, this function gets all of the guests
      that have been associated with the invite code.
    */
    public function GetGuestsWithMatchingInviteCode($inviteCode){

      //construct SQL query.
      $sql	= "	SELECT
                  G.GUEST_ID,
                  G.FIRST_NAME,
                  G.LAST_NAME,
                  G.GUEST_DESCRIPTION,
                  G.GUEST_DIETARY_RESTRICTIONS,
                  G.HAS_PLUS_ONE,
                  G.INVITE_CODE,
                  R.IS_ATTENDING,
                  A.ADDRESS_LINE1,
                  A.ADDRESS_LINE2,
                  A.STATE,
                  A.COUNTRY,
                  A.ZIP_CODE
                FROM
                  jonfreer_wedding.GUEST AS G
                  LEFT OUTER JOIN jonfreer_wedding.RESERVATION AS R
                    ON G.RESERVATION_ID = R.RESERVATION_ID
                  INNER JOIN jonfreer_wedding.ADDRESS AS A
                    ON A.ADDRESS_ID = G.ADDRESS_ID
                WHERE
                  G.INVITE_CODE = '" . $inviteCode . "'";

      //execute query.
    	$result = $this->mysqlConnection->query($sql);

    	$currentGuest = null;
    	$guestsWithMatchingInviteCode = array();

      //loop throught the results retrieved from the database.
    	if($result->num_rows > 0){

        echo "num of rows" . $result->num_rows;

        //even though i am using a while, i am strategically
        //hoping that there is only one guest returned.
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

          //add the current guest into the array.
    			$guestsWithMatchingInviteCode[] = $currentGuest;
    		}
    	}

      //return the array of guests with a matching invite code.
      return $guestsWithMatchingInviteCode;

    }
  }
?>
