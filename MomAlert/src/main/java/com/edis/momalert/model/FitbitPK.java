package com.edis.momalert.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class FitbitPK implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6609391343863898296L;
	private String userEmail;
	private String fitbitEmail;

	public FitbitPK( String userEmail, String fitbitEmail){
		this.setUserMail(userEmail);
		this.setFitbitEmail(fitbitEmail);
	}

	public String getUserMail() {
		return this.userEmail;
	}

	public void setUserMail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getFitbitEmail() {
		return fitbitEmail;
	}

	public void setFitbitEmail(String fitbitEmail) {
		this.fitbitEmail = fitbitEmail;
	}
}
