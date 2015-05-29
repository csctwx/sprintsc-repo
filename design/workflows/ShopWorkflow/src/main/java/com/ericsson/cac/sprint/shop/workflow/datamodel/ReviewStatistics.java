package com.ericsson.cac.sprint.shop.workflow.datamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class ReviewStatistics {

	@XmlElement(name="TotalReviewCount")
	private String totalReviewCount;
	@XmlElement(name="AverageOverallRating")
	private String averageOverallRating;
	@XmlElement(name="OverallRatingRange")
	private String overallRatingRange;
	
	public String getTotalReviewCount() {
		return totalReviewCount;
	}
	public void setTotalReviewCount(String totalReviewCount) {
		this.totalReviewCount = totalReviewCount;
	}
	public String getAverageOverallRating() {
		return averageOverallRating;
	}
	public void setAverageOverallRating(String averageOverallRating) {
		this.averageOverallRating = averageOverallRating;
	}
	public String getOverallRatingRange() {
		return overallRatingRange;
	}
	public void setOverallRatingRange(String overallRatingRange) {
		this.overallRatingRange = overallRatingRange;
	}
	
		
	
}
