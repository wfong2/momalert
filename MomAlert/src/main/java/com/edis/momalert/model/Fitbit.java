package com.edis.momalert.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Fitbit {
	@Embeddable
	public class FitbitPK implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -6609391343863898296L;
		private PCUser user;
		private String fitbitEmail;

		public FitbitPK( PCUser user, String fitbitEmail){
			this.setUser(user);
			this.setFitbitEmail(fitbitEmail);
		}

		public PCUser getUser() {
			return user;
		}

		public void setUser(PCUser user) {
			this.user = user;
		}

		public String getFitbitEmail() {
			return fitbitEmail;
		}

		public void setFitbitEmail(String fitbitEmail) {
			this.fitbitEmail = fitbitEmail;
		}
		
	}
	@EmbeddedId
	private FitbitPK fitbitPK;
	private String tempTokenReceived;
    private String tempTokenVerifier;
    
  
    public Fitbit(FitbitPK fitbitPK){
    	this.setFitbitPK(fitbitPK);
    }
    
    public String getTempTokenReceived() {
		return tempTokenReceived;
	}
	public void setTempTokenReceived(String tempTokenReceived) {
		this.tempTokenReceived = tempTokenReceived;
	}
	public String getTempTokenVerifier() {
		return tempTokenVerifier;
	}
	public void setTempTokenVerifier(String tempTokenVerifier) {
		this.tempTokenVerifier = tempTokenVerifier;
	}

	public FitbitPK getFitbitPK() {
		return fitbitPK;
	}

	public void setFitbitPK(FitbitPK fitbitPK) {
		this.fitbitPK = fitbitPK;
	} 
}
