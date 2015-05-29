package com.ericsson.cac.sprint.test.selfcare.workflow.getTextHistory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.selfcare.workflow.AccountServiceWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SearchCriteriaRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.TextHistoryResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContext;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class GetTextHistoryTest {
	
	@Autowired
	private AccountServiceWorkflow service;

	@Test
	public void testGetTextHistory_Success() {		
		
		UserContext userContext = new UserContext();
		SearchCriteriaRequest criteria = new SearchCriteriaRequest();
		userContext.setSubscriberId("80068822121");
		UserContextRequest userContextRequest = new UserContextRequest();
		userContextRequest.setUserContext(userContext);
		criteria.setPageNumber(1);
		
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = (Date)dateFormat.parse("2015-01-28");
			criteria.setFromDate(date);
			
			System.out.println("From Date: " +criteria.getFromDate());			
			System.out.println("Before invoking getTextHistory");
			TextHistoryResponse response = service.getTextHistory(userContextRequest,criteria);
			System.out.println("After invoking getTextHistory");
			
			System.out.println("Size:" + response.getTextHistory().length);
			System.out.println("From: " + response.getTextHistory()[0].getFrom());
			System.out.println("To: " + response.getTextHistory()[0].getTo());
			System.out.println("Date: " + response.getTextHistory()[0].getDate().toString());

			Assert.assertEquals("Thu Jan 01 00:00:00 IST 2015", response.getTextHistory()[2].getDate().toString());
			Assert.assertEquals("9999911111", response.getTextHistory()[1].getTo());
			
			Assert.assertEquals("9999911111", response.getTextHistory()[0].getFrom());
			Assert.assertEquals("Thu Jan 01 00:00:00 IST 2015", response.getTextHistory()[1].getDate());
			
			// success handling assert
			Assert.assertEquals(Integer.valueOf(0), response.getStatusMessageResponse().getStatusMessage().getStatus());
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}	
	
	/*@Test
	public void testGetTextHistory_Failure() {
		
		//System.setProperty("socksProxyHost", "localhost");
		//System.setProperty("socksProxyPort", "3128");
		UserContext userContext = new UserContext();
		SearchCriteriaRequest criteria = new SearchCriteriaRequest();
		userContext.setSubscriberId("");
		UserContextRequest userContextRequest = new UserContextRequest();
		userContextRequest.setUserContext(userContext);
		criteria.setPageNumber(1);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		criteria.setFromDate(dateFormat.getCalendar().getTime());
		
		try {	
			
			System.out.println("Before invoking getTextHistory");
			TextHistoryResponse response = service.getTextHistory(userContextRequest,criteria);
			System.out.println("After invoking getTextHistory");
			
			// error handling assert
			Assert.assertEquals(Integer.valueOf(2), response.getStatusMessageResponse().getStatusMessage().getStatus());
			Assert.assertEquals("NOK", response.getStatusMessageResponse().getStatusMessage().getDescription());
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}*/
}
