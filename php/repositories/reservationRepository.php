<?php

  /*
    Performs all direct access logic against the data SplObjectStorage
    that contains information about the reservations.
  */
  class ReservationRepository{

    public function __construct($mysqlConnection){
      $this->mysqlConnection = $mysqlConnection;
    }

    public function GetReservation(){

    }

    /*
      Inserts a reservation for an existing guest.
    */
    public function InsertReservation(
      $guestId, $guestDietaryRestrictions, $plusOneFirstName,
      $plusOneLastName, $isAttending){

      $sql	=   "CALL jonfreer_wedding.INSERT_RESERVATION
    				(" .
    					$guestId . "," .
    					"'" . $guestDietaryRestrictions . "'," .
              "'" . $plusOneFirstName . "'," .
              "'" . $plusOneLastName . "'" .
    					$isAttending .
    				");";

    	$result = $this->mysqlConnection->query($sql);

    }

    /*
      Updates an already existing reservation for a guest.
    */
    public function UpdateReservation($reservationId, $isAttending){

      $sql	=   "CALL jonfreer_wedding.UPDATE_RESERVATION
                  (" .
                      $reservationId . "," .
                      $isAttending .
                  ");";

      $this->mysqlConnection->query($sql);

    }

  }
?>
