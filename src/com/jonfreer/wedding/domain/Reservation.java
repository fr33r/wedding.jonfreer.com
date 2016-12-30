package com.jonfreer.wedding.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a reservation made for the wedding.
 */
@XmlRootElement(name = "reservation")
public class Reservation implements Cloneable {

    private Integer id;
    private Boolean isAttending;
    private java.util.Date submittedDateTime;

    /**
     * Default constructor for the Reservation class. Creates an empty Reservation object.
     */
    public Reservation() {
        this.id = null;
        this.isAttending = null;
        this.submittedDateTime = null;
    }

    /**
     * Constructs a Reservation instance.
     *
     * @param id                The identifier of the reservation.
     * @param isAttending       The Boolean value indicating true when attending, false otherwise.
     * @param submittedDateTime The date and time that the reservation was submitted.
     */
    public Reservation(Integer id, Boolean isAttending, java.util.Date submittedDateTime) {
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
        		(
	                (this.id == null && reservationObj.id == null) ||
	                (this.id.intValue() == reservationObj.id.intValue())
                ) &&
        		(
    				(this.isAttending == null && reservationObj.isAttending == null) ||
        			(this.isAttending.booleanValue() == reservationObj.isAttending.booleanValue())	
				) &&
        		(
    				(this.submittedDateTime == null && reservationObj.submittedDateTime == null) ||
        			(this.submittedDateTime.equals(reservationObj.submittedDateTime))	
				)
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
    public Integer getId() {
        return this.id;
    }

    /**
     * Alters the identifier for the reservation.
     *
     * @param id The desired identifier for the reservation.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retrieves the Boolean value indicating whether
     * the reservation owner is attending.
     *
     * @return true if attending; false otherwise.
     */
    public Boolean getIsAttending() {
        return this.isAttending;
    }

    /**
     * Alters the Boolean values indicating whether the reservation
     * owner is attending.
     *
     * @param isAttending The desired Boolean value. Should be true if
     *                    attending; false otherwise.
     */
    public void setIsAttending(Boolean isAttending) {
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
