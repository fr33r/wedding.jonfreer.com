package com.jonfreer.wedding.servicemodel;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a reservation made for the wedding.
 */
@XmlRootElement(name = "reservation")
public class Reservation implements Cloneable {

    private int id;
    private boolean isAttending;
    private java.util.Date submittedDateTime;

    /**
     * Default constructor for the Reservation class. Creates an empty Reservation object.
     */
    public Reservation() {
        this.id = 0;
        this.isAttending = false;
        this.submittedDateTime = null;
    }
    
    /**
     * Constructs a Reservation instance.
     *
     * @param id                The identifier of the reservation.
     * @param isAttending       The Boolean value indicating true when attending, false otherwise.
     * @param submittedDateTime The date and time that the reservation was submitted.
     */
    public Reservation(int id, boolean isAttending, java.util.Date submittedDateTime) {
        this.id = id;
        this.isAttending = isAttending;
        this.submittedDateTime = (java.util.Date) submittedDateTime.clone();
    }

    /**
     * Determines whether the calling Reservation instance is equal to the provided Object instance.
     *
     * @param obj The Reservation object (represented as Object) to be compared against.
     * @return true if the provided Object is of the Reservation class, and all property values match;
     * otherwise returns false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Reservation reservationObj = (Reservation) obj;
        if (
                this.id == reservationObj.id &&
                        this.isAttending == reservationObj.isAttending &&
                        this.submittedDateTime.equals(reservationObj.submittedDateTime)
                ) {
            return true;
        }
        return false;
    }

    /**
     * Creates a deep copy of the calling Reservation instance.
     *
     * @return The deep copy of the Reservation instance represented as an Object.
     */
    @Override
    public Object clone() {

        Reservation reservationObj = null;
        try {
            reservationObj = (Reservation) super.clone();
            reservationObj.submittedDateTime =
                    (java.util.Date) reservationObj.submittedDateTime.clone();
        } catch (CloneNotSupportedException e) { /* not possible. */ }

        return reservationObj;
    }

    /**
     * Retrieves the identifier for the reservation.
     *
     * @return The identifier for the reservation.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Alters the identifier for the reservation.
     *
     * @param id The desired identifier for the reservation.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the Boolean value indicating whether
     * the reservation owner is attending.
     *
     * @return true if attending; false otherwise.
     */
    public boolean getIsAttending() {
        return this.isAttending;
    }

    /**
     * Alters the Boolean values indicating whether the reservation
     * owner is attending.
     *
     * @param isAttending The desired Boolean value. Should be true if
     *                    attending; false otherwise.
     */
    public void setIsAttending(boolean isAttending) {
        this.isAttending = isAttending;
    }

    /**
     * Retrieves the date and time that the reservation was submitted.
     *
     * @return The date and time that the reservation was submitted.
     */
    public java.util.Date getSubmittedDateTime() {
        return this.submittedDateTime;
    }

    /**
     * Alters the date and time that the reservation was submitted.
     *
     * @param submittedDateTime The desired date and time that the reservation was submitted.
     */
    public void setSubmittedDateTime(java.util.Date submittedDateTime) {
        this.submittedDateTime = submittedDateTime;
    }
}

