package com.edis.momalert.model;

import java.io.Serializable;

import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Cascade;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Fitbit implements Serializable {


	@EmbeddedId
	private FitbitPK fitbitPK;
	
	private PCUser myuser;
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

	@ManyToOne()
	@Cascade ({CascadeType.SAVE_UPDATE})
	@JoinColumn(name = "email", insertable = false, updatable= false)
	public PCUser getPCUser() {
		return myuser;
	}

	
	public FitbitPK getFitbitPK(){
		return this.fitbitPK;
	}
	public void setFitbitPK(FitbitPK fitbitPK) {
		this.fitbitPK = fitbitPK;
	} 
}
