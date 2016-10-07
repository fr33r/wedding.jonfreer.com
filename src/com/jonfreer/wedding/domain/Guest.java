package com.jonfreer.wedding.domain;

import com.jonfreer.wedding.domain.Reservation;

public class Guest{

  private int id;
  private String givenName;
  private String surName;
  private String description;
  private String inviteCode;
  private String dietaryRestrictions;
  private Reservation reservation;

  public Guest(){ }

  public int getId(){
    return this.id;
  }

  public void setId(int id){
    this.id = id;
  }

  public String getGivenName(){
    return this.givenName;
  }

  public void setGivenName(String givenName){
    this.givenName = givenName;
  }

  public String getSurName(){
    return this.surName;
  }

  public void setSurName(String surName){
    this.surName = surName;
  }

  public String getDescription(){
    return this.description;
  }

  public void setDescription(String description){
    this.description = description;
  }

  public String getInviteCode(){
    return this.inviteCode;
  }

  public void setInviteCode(String inviteCode){
    this.inviteCode = inviteCode;
  }

  public String getDietaryRestrictions(){
    return this.dietaryRestrictions;
  }

  public void setDietaryRestrictions(String dietaryRestrictions){
    this.dietaryRestrictions = dietaryRestrictions;
  }

  public Reservation getReservation(){
    return this.reservation;
  }

  public void setReservation(Reservation reservation){
    this.reservation = reservation;
  }
}
