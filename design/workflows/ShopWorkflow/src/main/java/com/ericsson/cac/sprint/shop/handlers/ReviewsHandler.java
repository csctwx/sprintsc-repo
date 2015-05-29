package com.ericsson.cac.sprint.shop.handlers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.ericsson.cac.sprint.shop.constants.ShopWorkFlowConstants;
import com.ericsson.cac.sprint.shop.workflow.datamodel.Phone;
import com.ericsson.cac.sprint.shop.workflow.datamodel.PhoneDetail;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ReviewStatistics;
import com.ericsson.cac.sprint.shop.workflow.impl.ShopWorkflowImpl;
import com.ericsson.cac.sprint.shop.workflow.util.ReviewStatisticsUtil;

@Component
public class ReviewsHandler {
	
	@Value("${shop.workflow.url.reviewUrl}")
	String reviewUrl;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ShopWorkflowImpl.class);
	
	public void setReviewStatistics(Phone[] phones) {
		StringBuilder phoneIdlist = new StringBuilder();
		int counter = 1;

		for (Phone phone : phones) {

			if (counter != phones.length) {
				phoneIdlist.append(phone.getSku());
				phoneIdlist.append(",");
			} else {
				phoneIdlist.append(phone.getSku());
			}
			counter++;
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("setReviewStatistics(Phone[]) - StringBuilder phoneIdlist="
					+ phoneIdlist);
		}

		Map<String, ReviewStatistics> reviewStatisticsMap = null;

		if (phoneIdlist != null) {
			reviewStatisticsMap = getReviewStatistics(phoneIdlist.toString());
		}

		if (reviewStatisticsMap != null) {
			for (int k = 0; k < phones.length; k++) {
				if (phones[k].getSku() != null) {
					phones[k].setReviewStatistics(reviewStatisticsMap
							.get(phones[k].getSku()));
				}
			}
		}
	}
	
	public void setPhoneDetailReviewStatistics(PhoneDetail[] phoneDetails) {
		StringBuilder phoneIdlist = new StringBuilder();
		int counter = 1;

		for (PhoneDetail phoneDetail : phoneDetails) {

			if (counter != phoneDetails.length) {
				phoneIdlist.append(phoneDetail.getSku());
				phoneIdlist.append(",");
			} else {
				phoneIdlist.append(phoneDetail.getSku());
			}
			counter++;
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("setReviewStatistics(PhoneDetail[]) - StringBuilder phoneIdlist="
					+ phoneIdlist);
		}

		Map<String, ReviewStatistics> reviewStatisticsMap = null;

		if (phoneIdlist != null) {
			reviewStatisticsMap = getReviewStatistics(phoneIdlist.toString());
		}

		if (reviewStatisticsMap != null) {
			for (int k = 0; k < phoneDetails.length; k++) {
				if (phoneDetails[k].getSku() != null) {
					phoneDetails[k].setReviewStatistics(reviewStatisticsMap
							.get(phoneDetails[k].getSku()));
				}
			}
		}
	}
	
	private String callReviewStatisticsURL(String phoneId) {
		LOGGER.debug("Requeted URL:" + reviewUrl+phoneId);
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		if(phoneId!=null && !phoneId.isEmpty())
		{
			try {
				//	System.setProperty("http.proxyHost", "www-proxy.exu.ericsson.se");
				//	System.setProperty("http.proxyPort", "8080");
				URL url = new URL(reviewUrl+phoneId);
				urlConn = url.openConnection();
				if (urlConn != null)
					urlConn.setReadTimeout(60 * 1000);
				if (urlConn != null && urlConn.getInputStream() != null) {
					in = new InputStreamReader(urlConn.getInputStream(),
							Charset.defaultCharset());
					BufferedReader bufferedReader = new BufferedReader(in);
					if (bufferedReader != null) {
						int cp;
						while ((cp = bufferedReader.read()) != -1) {
							sb.append((char) cp);
						}
						bufferedReader.close();
					}
					in.close();
				}

			} catch (Exception e) {
				throw new RuntimeException("Exception while calling URL:"+ reviewUrl, e);
			}
		}		 
		return sb.toString();
	}
	
	private Map<String,ReviewStatistics> getReviewStatistics(String phoneId)
	{


		ReviewStatistics reviewStatistics = null;
		Map<String,ReviewStatistics> reviewStatistics_map = null;

		try
		{

			String xml = callReviewStatisticsURL(phoneId);
			if(xml!=null && !xml.isEmpty())
			{
				DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(xml));
				Document doc = db.parse(is);
				NodeList nodes = doc.getElementsByTagName(ShopWorkFlowConstants.STATISTICS_TEXT);

				if(nodes.getLength()!=0)
				{
					reviewStatistics_map = new HashMap<String, ReviewStatistics>();
					
				}

				for (int i = 0; i < nodes.getLength(); i++) {
					
					Element element = (Element) nodes.item(i);
					reviewStatistics = new ReviewStatistics();
					
					NodeList productId = element.getElementsByTagName(ShopWorkFlowConstants.PHONE_ID_TEXT);
					Element productId_element = (Element) productId.item(0);
					

					NodeList totalReviewCount = element.getElementsByTagName(ShopWorkFlowConstants.TOTAL_REVIEW_COUNT_TEXT);
					Element totalReviewCount_element = (Element) totalReviewCount.item(0);
					reviewStatistics.setTotalReviewCount(ReviewStatisticsUtil.getCharacterDataFromElement(totalReviewCount_element));

					NodeList overallRatingRange = element.getElementsByTagName(ShopWorkFlowConstants.OVERALL_RATING_RANGE_TEXT);
					Element overallRatingRange_element = (Element) overallRatingRange.item(0);
					reviewStatistics.setOverallRatingRange(ReviewStatisticsUtil.getCharacterDataFromElement(overallRatingRange_element));

					NodeList averageOverallRating = element.getElementsByTagName(ShopWorkFlowConstants.AVERAGE_OVERALL_RATING_TEXT);
					Element averageOverallRating_element = (Element) averageOverallRating.item(0);
					reviewStatistics.setAverageOverallRating(ReviewStatisticsUtil.getCharacterDataFromElement(averageOverallRating_element));
					
					reviewStatistics_map.put(ReviewStatisticsUtil.getCharacterDataFromElement(productId_element), reviewStatistics);

				}
			}

		}
		catch (Exception e) {
			reviewStatistics_map = null;
		}

		return reviewStatistics_map;

	}
}
