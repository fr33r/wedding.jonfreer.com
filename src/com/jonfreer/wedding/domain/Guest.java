package com.jonfreer.wedding.domain;

/**
 * Represents a guest of the wedding.
 */
public class Guest implements Cloneable {

	private Integer id;
	private String givenName;
	private String surName;
	private String description;
	private String inviteCode;
	private String dietaryRestrictions;
	private Reservation reservation;

	/**
	 * Default constructor for the Guest class. Creates an empty Guest object.
	 */
	public Guest() {
		this.id = null;
		this.givenName = null;
		this.surName = null;
		this.description = null;
		this.inviteCode = null;
		this.dietaryRestrictions = null;
		this.reservation = null;
	}

	/**
	 * Constructs a guest without a reservation.
	 *
	 * @param id                  The identifier for the guest.
	 * @param givenName           The given name (first name) of the guest.
	 * @param surName             The surname (family name or last name) of the guest.
	 * @param description         The relationship the guest has to either the groom or bride.
	 * @param inviteCode          The invite code that the guest is associated with. Needed in order to RSVP.
	 * @param dietaryRestrictions An explanation of what dietary restrictions the guest has.
	 */
	public Guest(Integer id, String givenName, String surName, String description,
		String inviteCode, String dietaryRestrictions) {

		this.id = id;
		this.givenName = givenName;
		this.surName = surName;
		this.description = description;
		this.inviteCode = inviteCode;
		this.dietaryRestrictions = dietaryRestrictions;
		this.reservation = null;
	}

	/**
	 * Constructs a guest with a reservation.
	 *
	 * @param id                  The identifier for the guest.
	 * @param givenName           The given name (first name) of the guest.
	 * @param surName             The surname (family name or last name) of the guest.
	 * @param description         The relationship the guest has to either the groom or bride.
	 * @param inviteCode          The invite code that the guest is associated with. Needed in order to RSVP.
	 * @param dietaryRestrictions An explanation of what dietary restrictions the guest has.
	 * @param reservation         Represents the reservation information for the guest.
	 */
	public Guest(Integer id, String givenName, String surName, String description,
		String inviteCode, String dietaryRestrictions, Reservation reservation) {

		this.id = id;
		this.givenName = givenName;
		this.surName = surName;
		this.description = description;
		this.inviteCode = inviteCode;
		this.dietaryRestrictions = dietaryRestrictions;
		this.reservation = (Reservation) reservation.clone();
	}

	/**
	 * Constructs a guest from an already existing guest. It can be said that this
	 * constructor creates a "copy" of the provided guest object.
	 *
	 * @param guest The guest in which to make a copy of.
	 */
	public Guest(Guest guest) {
		this.id = guest.id;
		this.givenName = guest.givenName;
		this.surName = guest.surName;
		this.description = guest.description;
		this.inviteCode = guest.inviteCode;
		this.dietaryRestrictions = guest.dietaryRestrictions;
		this.reservation =
			guest.reservation == null ? (Reservation) guest.reservation.clone() : null;
	}

	/**
	 * Determines whether the calling Guest instance is equal to the provided Object instance.
	 *
	 * @param obj The Guest object (represented as Object) to be compared against.
	 * @return true if the provided Object is of the Guest class, and all property values match;
	 * otherwise returns false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}

		Guest guestObj = (Guest) obj;
		if (
			(
				(this.id == null && guestObj.id == null) || 
				(this.id.intValue() == guestObj.id.intValue())
				) &&
			(	
				(this.givenName == null && guestObj.givenName == null) || 
				(this.givenName.equals(guestObj.givenName))
				) &&
			(
				(this.surName == null && guestObj.surName == null) || 
				(this.surName.equals(guestObj.surName))
				) &&
			(
				(this.description == null && guestObj.description == null) || 
				(this.description.equals(guestObj.description))
				) &&
			(
				(this.inviteCode == null && guestObj.inviteCode == null) || 
				(this.inviteCode.equals(guestObj.inviteCode))
				) &&
			(
				(this.dietaryRestrictions == null && guestObj.dietaryRestrictions == null) || 
				(this.dietaryRestrictions.equals(guestObj.dietaryRestrictions))
				) &&
			(
				(this.reservation == null && guestObj.reservation == null) || 
				(this.reservation.equals(guestObj.reservation))
				)
			) {
			return true;
		}
		return false;
	}

	/**
	 * Provides a string representation of the guest.
	 *
	 * @return The guest represented as a String.
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getName() + "\n");
		builder.append("id\t\t\t-->" + this.id.intValue() + "\n");
		builder.append("givenName\t\t-->" + this.givenName + "\n");
		builder.append("surName\t\t-->" + this.surName + "\n");
		builder.append("description\t\t-->" + this.description + "\n");
		builder.append("inviteCode\t\t-->" + this.inviteCode + "\n");
		builder.append("dietaryRestrictions\t-->" + this.dietaryRestrictions + "\n");

		if (this.reservation != null) {
			builder.append("reservation\t-->" + this.reservation.toString());
		}

		return builder.toString();
	}

	/**
	 * Creates a "deep" copy of the calling Guest instance. All immutable
	 * reference type members will remain as shallow copies.
	 *
	 * @return The "deep" copy of the calling Guest instance represented as an Object.
	 */
	@Override
	public Object clone() {

		Guest guestObj = null;

		try {
			guestObj = (Guest) super.clone();

			if (guestObj.reservation != null) {
				guestObj.reservation = (Reservation) guestObj.reservation.clone();
			}

		} catch (CloneNotSupportedException e) { /* not possible. */ }

		return guestObj;
	}

	/**
	 * Retrieves the identifier of the guest.
	 *
	 * @return The identifier of the guest.
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * Alters the identifier of the guest to instead have the value of the provided identifier.
	 *
	 * @param id The desired identifier of the guest.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Retrieves the given name (first name) of the guest.
	 *
	 * @return The given name (first name) of the guest.
	 */
	public String getGivenName() {
		return this.givenName;
	}

	/**
	 * Alters the given name (first name) of the guest to the name provided.
	 *
	 * @param givenName The desired given name (first name) of the guest.
	 */
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	/**
	 * Retrieves the surname (family name or last name) of the guest.
	 *
	 * @return The surname (family name or last name) of the guest.
	 */
	public String getSurName() {
		return this.surName;
	}

	/**
	 * Alters the surname (family name or last name) of the guest.
	 *
	 * @param surName The desired surname (family name or last name) of the guest.
	 */
	public void setSurName(String surName) {
		this.surName = surName;
	}

	/**
	 * Retrieves the description describing the relationship to either
	 * the groom or the bride for the guest.
	 *
	 * @return The description describing the relationship to the groom or
	 * the bride for the guest.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Alters the description describing the relationship to either the
	 * groom or the bride for the guest.
	 *
	 * @param description The desired description describing the relationship to
	 *                    either the groom or the bride for the guest.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Retrieves the invite code associated to the guest.
	 *
	 * @return The invite code associated to the guest.
	 */
	public String getInviteCode() {
		return this.inviteCode;
	}

	/**
	 * Alters the invite code associated to the guest.
	 *
	 * @param inviteCode The desired invite code to be associated to the guest.
	 */
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	/**
	 * Retrieves the dietary restrictions of the guest.
	 *
	 * @return The dietary restrictions of the guest.
	 */
	public String getDietaryRestrictions() {
		return this.dietaryRestrictions;
	}

	/**
	 * Alters the dietary restrictions of the guest.
	 *
	 * @param dietaryRestrictions The desired dietary restrictions of the guest.
	 */
	public void setDietaryRestrictions(String dietaryRestrictions) {
		this.dietaryRestrictions = dietaryRestrictions;
	}

	/**
	 * Retrieves the reservation information of the guest.
	 *
	 * @return com.jonfreer.wedding.domain.Reservation representing the
	 * reservation information for the guest.
	 */
	public Reservation getReservation() {
		return this.reservation;
	}

	/**
	 * Alters the reservation information for the guest.
	 *
	 * @param reservation com.jonfreer.wedding.domain.Reservation representing the
	 *                    desired reservation information for the guest.
	 */
	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
}
