/**
 * 
 */
package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author eashich
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Keywords {
	
	@XmlElement
	private String features;
	
	@XmlElement
	private String	plans;
	
	@XmlElement
	private String reviews;

	/**
	 * @return the features
	 */
	public String getFeatures() {
		return features;
	}

	/**
	 * @param features the features to set
	 */
	public void setFeatures(String features) {
		this.features = features;
	}

	/**
	 * @return the plans
	 */
	public String getPlans() {
		return plans;
	}

	/**
	 * @param plans the plans to set
	 */
	public void setPlans(String plans) {
		this.plans = plans;
	}

	/**
	 * @return the reviews
	 */
	public String getReviews() {
		return reviews;
	}

	/**
	 * @param reviews the reviews to set
	 */
	public void setReviews(String reviews) {
		this.reviews = reviews;
	}
}
