package com.jonfreer.wedding.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="reservation")
public class Reservation{

	private int id;
	private boolean isAttending;
	private java.util.Date submittedDateTime;

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

	public java.util.Date getSubmittedDateTime(){
		return this.submittedDateTime;
	}

	public void setSubmittedDateTime(java.util.Date submittedDateTime){
		this.submittedDateTime = submittedDateTime;
	}
}
