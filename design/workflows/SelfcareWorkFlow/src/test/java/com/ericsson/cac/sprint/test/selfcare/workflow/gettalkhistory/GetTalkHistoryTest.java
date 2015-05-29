/**
 * Sprint Template
 */
package com.ericsson.cac.sprint.test.selfcare.workflow.gettalkhistory;

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
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.TalkHistoryResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContext;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;

/**
 * GetTalkHistoryTest is used to test all the scenarios for the service layer
 * class
 * 
 * @author Ericsson
 * @version $Revision: 1.0 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class GetTalkHistoryTest {

	@Autowired
	private AccountServiceWorkflow service;

	@Test
	public void testGetTalkHistory_Success() {

		UserContext userContext = new UserContext();
		SearchCriteriaRequest criteria = new SearchCriteriaRequest();
		userContext.setSubscriberId("80068822121");
		UserContextRequest userContextRequest = new UserContextRequest();
		userContextRequest.setUserContext(userContext);
		criteria.setPageNumber(1);

		try {

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = (Date) dateFormat.parse("2015-01-28");
			criteria.setFromDate(date);

			System.out.println("From Date: " + criteria.getFromDate());

			System.out.println("Before invoking getTalkHistory");
			TalkHistoryResponse response = service.getTalkHistory(
					userContextRequest, criteria);
			System.out.println("After invoking getTalkHistory");

			System.out.println("Talk History list size:"
					+ response.getTalkHistory().length);
			System.out.println("Talk History from:"
					+ response.getTalkHistory()[0].getDate());

			Assert.assertEquals("Thu Jan 01 00:00:00 IST 2015",
					response.getTalkHistory()[0].getDate().toString());
			Assert.assertEquals("9999911111",
					response.getTalkHistory()[1].getTo());
			
			// success handling assert
			Assert.assertEquals(Integer.valueOf(0), response.getStatusMessageResponse().getStatusMessage().getStatus());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void testgetTalkHistory_Failure() {

		// System.setProperty("socksProxyHost", "localhost");
		// System.setProperty("socksProxyPort", "3128");
		UserContext userContext = new UserContext();
		SearchCriteriaRequest criteria = new SearchCriteriaRequest();
		userContext.setSubscriberId("");
		UserContextRequest userContextRequest = new UserContextRequest();
		userContextRequest.setUserContext(userContext);
		criteria.setPageNumber(1);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		criteria.setFromDate(dateFormat.getCalendar().getTime());

		try {

			System.out.println("Before invoking getTalkHistory");
			TalkHistoryResponse response = service.getTalkHistory(
					userContextRequest, criteria);
			System.out.println("After invoking getTalkHistory");
			
			// error handling assert
			Assert.assertEquals(Integer.valueOf(2), response.getStatusMessageResponse().getStatusMessage().getStatus());
			Assert.assertEquals("NOK", response.getStatusMessageResponse().getStatusMessage().getDescription());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
