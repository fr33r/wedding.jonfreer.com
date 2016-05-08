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
                  G.INVITE_CODE,
                  R.IS_ATTENDING
                FROM
                  jonfreer_wedding.GUEST AS G
                  LEFT JOIN jonfreer_wedding.RESERVATION AS R
                    ON G.RESERVATION_ID = R.RESERVATION_ID
                WHERE
                  G.FIRST_NAME = '" . $first_name . "'" .
                  "AND G.LAST_NAME = '" . $last_name . "';";

    	$result = $this->mysqlConnection->query($sql);

      $currentGuest = null;

      if($result->num_rows > 0){

        //even though i am using a while, i am strategically
        //hoping that there is only one guest returned.
    		while($row = $result->fetch_assoc()){

    			$currentGuest = new Guest();
    			$currentGuest->guest_id = $row["GUEST_ID"];
    			$currentGuest->first_name = $row["FIRST_NAME"];
    			$currentGuest->last_name = $row["LAST_NAME"];
          $currentGuest->invite_code = $row["INVITE_CODE"];
    			$currentGuest->description = $row["GUEST_DESCRIPTION"];
          $currentGuest->dietary_restrictions = $row["GUEST_DIETARY_RESTRICTIONS"];

    			if($row["IS_ATTENDING"] == 1){
            $currentGuest->reservation = new Reservation();
    				$currentGuest->reservation->is_attending = true;
    			}else if($row["IS_ATTENDING"] == 0){
            $currentGuest->reservation = new Reservation();
            $currentGuest->reservation->is_attending = false;
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
                  G.INVITE_CODE,
                  R.IS_ATTENDING
                FROM
                  jonfreer_wedding.GUEST AS G
                  LEFT JOIN jonfreer_wedding.RESERVATION AS R
                    ON G.RESERVATION_ID = R.RESERVATION_ID
                WHERE
                  G.GUEST_ID = " . $id . ";";

      $result = $this->mysqlConnection->query($sql);

      $currentGuest = null;

      if($result->num_rows > 0){

        //even though i am using a while, i am strategically
        //hoping that there is only one guest returned.
    		while($row = $result->fetch_assoc()){

    			$currentGuest = new Guest();
    			$currentGuest->guest_id = $row["GUEST_ID"];
    			$currentGuest->first_name = $row["FIRST_NAME"];
    			$currentGuest->last_name = $row["LAST_NAME"];
          $currentGuest->invite_code = $row["INVITE_CODE"];
    			$currentGuest->description = $row["GUEST_DESCRIPTION"];
          $currentGuest->dietary_restrictions = $row["GUEST_DIETARY_RESTRICTIONS"];

    			if($row["IS_ATTENDING"] == 1){
            $currentGuest->reservation = new Reservation();
            $currentGuest->reservation->is_attending = true;
          }else if($row["IS_ATTENDING"] == 0){
            $currentGuest->reservation = new Reservation();
            $currentGuest->reservation->is_attending = false;
          }

    		}
    	}

      return $currentGuest;
    }

    /*
      Inserts a new guest.
    */
    public function InsertGuest($guest){

      $sql	=   "CALL jonfreer_wedding.INSERT_GUEST2
    				      (" .
                      "'" . $guest->firstName         . "'," .
                      "'" . $guest->lastName          . "'," .
                      "'" . $guest->description       . "'," .
                      "'" . $guest->dietaryRestrictions       . "'," .
                      "'" . $guest->inviteCode        . "'" .
  				        ");";

      if(!$this->mysqlConnection->query($sql)){

        throw new Exception(
          "An issue occurred when attempting create new guest with name " .
          $guest->firstName . " " . $guest->lastName . ". " . $mysqlConnection->error);
      }

    }

    /*
      Updates an already existing guest.
    */
    public function UpdateGuest($guest){

      $sql	=   "CALL jonfreer_wedding.UPDATE_GUEST2
                  (" .
                            $guest->guest_id . "," .
                      "'" . $guest->firstName . "'," .
                      "'" . $guest->lastName . "'," .
                      "'" . $guest->description . "'," .
                      "'" . $guest->dietaryRestrictions . "'," .
                      "'" . $guest->inviteCode . "'" .
                  ");";

      if(!$this->mysqlConnection->query($sql)){

        throw new Exception(
          "An issue occurred when attempting to update " .
          $guest->firstName . " " . $guest->lastName . "'s information.");
      }

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
                  G.INVITE_CODE,
                  R.IS_ATTENDING
                FROM
                  jonfreer_wedding.GUEST AS G
                  LEFT OUTER JOIN jonfreer_wedding.RESERVATION AS R
                    ON G.RESERVATION_ID = R.RESERVATION_ID
                WHERE
                  G.INVITE_CODE = '" . $inviteCode . "'";

      //execute query.
    	$result = $this->mysqlConnection->query($sql);

    	$currentGuest = null;
    	$guestsWithMatchingInviteCode = array();

      //loop throught the results retrieved from the database.
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
