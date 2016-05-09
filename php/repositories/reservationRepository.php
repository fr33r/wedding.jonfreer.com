<?php

  /*
    Performs all direct access logic against the data store
    that contains information about the reservations.
  */
  class ReservationRepository{

    public function __construct($mysqlConnection){
      $this->mysqlConnection = $mysqlConnection;
    }

    /*
      Retrieves a reservation for a specific guest.
    */
    public function GetReservationForGuest($guestId){

      $sql	=   "SELECT
                  R.RESERVATION_ID,
                  R.IS_ATTENDING,
                  R.DATETIME_SUBMITTED
                FROM
                  jonfreer_wedding.GUEST AS G
                  INNER JOIN jonfreer_wedding.RESERVATION AS R
                    ON G.RESERVATION_ID = R.RESERVATION_ID
                WHERE
                  G.GUEST_ID = " . $guestId . ";";

      $result = $this->mysqlConnection->query($sql);

      $reservation = null;

      if($result->num_rows > 0){

        while($row = $result->fetch_assoc()){

          $reservation = new Reservation();
          $reservation->reservation_id = $row["RESERVATION_ID"];
          $reservation->is_attending = $row["IS_ATTENDING"];
          $reservation->submitted_datetime = $row["DATETIME_SUBMITTED"];
        }
      }

      return $reservation;
    }

    /*
      Inserts a reservation for an existing guest.
    */
    public function InsertReservationForGuest($guestId, $isAttending){

      $isAttendingBit = 0;

      if($isAttending) { $isAttendingBit = 1; }

      $sql  =   "CALL jonfreer_wedding.INSERT_RESERVATION2
            (" .
              $guestId . "," .
              $isAttendingBit .
            ");";

      if(!$this->mysqlConnection->query($sql)){

        throw new Exception(
          "An issue occurred when attempting to insert reservation for guest ID: " .
          $guestId . ".");
      }

    }

    /*
      Updates an already existing reservation for a guest.
    */
    public function UpdateReservation($reservationId, $isAttending){

      $isAttendingBit = 0;

      if($isAttending) { $isAttendingBit = 1; }

      $sql	=   "CALL jonfreer_wedding.UPDATE_RESERVATION
                  (" .
                      $reservationId . "," .
                      $isAttendingBit .
                  ");";

      if(!$this->mysqlConnection->query($sql)){

        throw new Exception(
          "An issue occurred when attempting to update reservation with reservation ID: " .
          $reservationId . ".");
      }

    }

    /*
      Deletes an already existing reservation.
    */
    public function DeleteReservation($reservationId){

      $sql	=   "CALL jonfreer_wedding.DELETE_RESERVATION(" . $reservationId . ");";

      if(!$this->mysqlConnection->query($sql)){

        throw new Exception(
          "An issue occurred when attempting to delete reservation with reservation ID: " .
          $reservationId . ".");
      }
    }

  }
?>
