package com.jonfreer.wedding.domain;

public class Reservation{
  private int id;
  private boolean isAttending;
  private Date submittedDateTime;

  public Reservation(){ }

  public int getId(){
    return this.id;
  }

  public void setId(int id){
    this.id = id;
  }

  public boolean getIsAttending(){
    return this.isAttending;
  }

  public void setIsAttending(boolean isAttending){
    this.isAttending = isAttending;
  }

  public Date getSubmittedDateTime(){
    return this.submittedDateTime;
  }

  public void setSubmittedDateTime(Date submittedDateTime){
    this.submittedDateTime = submittedDateTime;
  }
}
