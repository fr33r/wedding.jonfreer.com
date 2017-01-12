package com.jonfreer.wedding.domain;

/**
 * Represents criteria that is used when searching through
 * the Guest resources.
 * @author jonfreer
 * @since 12/23/16
 */
public class GuestSearchCriteria implements Cloneable{

    private String givenName;
    private String surname;
    private String inviteCode;

    /**
     * Default constructor for the GuestSearchCriteria class.
     * Constructs an empty GuestSearchCriteria instance.
     */
    public GuestSearchCriteria(){
    	this.givenName = null;
    	this.surname = null;
    	this.inviteCode = null;
    }
    
    /**
     * Constructs an instance of the GuestSearchCriteria class.
     * @param givenName The given name (first name) to be searched for.
     * @param surname The surname (last name) to be searched for.
     * @param inviteCode The invite code to be searched for.
     */
    public GuestSearchCriteria(String givenName, String surname, String inviteCode){
        this.givenName = givenName;
        this.surname = surname;
        this.inviteCode = inviteCode;
    }

    /**
     * Retrieves the given name for the GuestSearchCriteria.
     * @return The given name for the GuestSearchCriteria.
     */
    public String getGivenName(){
        return this.givenName;
    }

    /**
     * Alters the given name (first name) of the search criteria to match
     * the given name provided.
     * @param givenName The desired given name for the search criteria.
     */
    public void setGivenName(String givenName){
        this.givenName = givenName;
    }

    /**
     * Retrieves the surname for the GuestSearchCriteria.
     * @return The surname for the GuestSearchCriteria.
     */
    public String getSurname(){
        return this.surname;
    }

    /**
     * Alters the surname (last name) of the search criteria to match
     * the surname provided.
     * @param surname The desired surname for the search criteria.
     */
    public void setSurname(String surname){
        this.surname = surname;
    }

    /**
     * Retrieves the invite code for the GuestSearchCriteria.
     * @return The invite code for the GuestSearchCriteria.
     */
    public String getInviteCode(){
        return this.inviteCode;
    }

    /**
     * Alters the invite code of the search criteria to match the
     * invite code provided.
     * @param inviteCode The desired invite code for the search criteria.
     */
    public void setInviteCode(String inviteCode){
        this.inviteCode = inviteCode;
    }

    /**
     * Determines if the calling instance of the GuestSearchCriteria
     * is equal to the provided GuestSearchCriteria instance.
     * @param obj The instance to be compared against the calling instance.
     * @return true if they are equal; false otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if(obj == null || this.getClass() != obj.getClass()){ return false; }
        GuestSearchCriteria guestSearchCriteria =
                (GuestSearchCriteria)obj;

        if (
                (
                        (this.givenName == null && guestSearchCriteria.givenName == null) ||
                                (this.givenName.equals(guestSearchCriteria.givenName))
                ) &&
                        (
                                (this.surname == null && guestSearchCriteria.surname == null) ||
                                        (this.surname.equals(guestSearchCriteria.surname))
                        ) &&
                        (
                                (this.inviteCode == null && guestSearchCriteria.inviteCode == null) ||
                                        (this.inviteCode.equals(guestSearchCriteria.inviteCode))
                        )
                ) {
            return true;
        }
        return false;
    }

    /**
     * Generates the hash code for the GuestSearchCriteria instance.
     * @return The hash code of the GuestSearchCriteria instance.
     */
    @Override
    public int hashCode(){
        final int prime = 17;
        int hashCode = 1;

        if(this.givenName != null){
            hashCode *= (prime + this.givenName.hashCode());
        }

        if(this.surname != null){
            hashCode *= (prime + this.surname.hashCode());
        }

        if(this.inviteCode != null){
            hashCode *= (prime + this.inviteCode.hashCode());
        }

        return hashCode;
    }

    /**
     * Creates a "deep" copy of the calling GuestSearchCriteria
     * instance. All immutable reference type members will remain as shallow copies.
     *
     * @return The "deep" copy of the calling GuestSearchCriteria
     * instance represented as an Object.
     */
    @Override
    public Object clone(){

        GuestSearchCriteria searchCriteria = null;

        try {
            searchCriteria =
                (GuestSearchCriteria) super.clone();
            return searchCriteria;
        }catch(CloneNotSupportedException cloneNotSupportedException){}

        return searchCriteria;
    }

    /**
     * Provides a string representation of the GuestSearchCriteria instance.
     * @return The string representation of the GuestSearchCriteria instance.
     */
    @Override
    public String toString(){
        return this.getClass().getName() + "\n" +
                "Given Name: " + this.givenName + "\n" +
                "Surname: " + this.surname + "\n" +
                "Invite Code: " + this.inviteCode + "\n";
    }
}
