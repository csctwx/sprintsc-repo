/**
 * Sprint Template
 */
package com.ericsson.cac.sprint.test.selfcare.workflow.gettransactionhistory;

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
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.TransactionHistoryResponse;
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
public class GetTransactionHistoryTest {

	@Autowired
	private AccountServiceWorkflow service;

	@Test
	public void testGetTransactionHistory_Success() {

		UserContext userContext = new UserContext();
		SearchCriteriaRequest criteria = new SearchCriteriaRequest();
		userContext.setSubscriberId("80068822121");
		UserContextRequest userContextRequest = new UserContextRequest();
		userContextRequest.setUserContext(userContext);
		criteria.setPageNumber(1);
		criteria.setNumberOfItems(50);

		try {

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = (Date) dateFormat.parse("2015-01-28");
			criteria.setFromDate(date);

			System.out.println("From Date: " + criteria.getFromDate());

			System.out.println("Before invoking getTransactionistory");
			TransactionHistoryResponse response = service.getTransactionHistory(
					userContextRequest, criteria);
			System.out.println("After invoking getTransactionistory");

			if(response.getTransactionHistory().getPaymentHistoryPagination() != null)
			{
				System.out.println("Total Count of Records: " + response.getTransactionHistory().getPaymentHistoryPagination().getTotalPaymentHistoryCount());
				
				for(int i=0;i<response.getTransactionHistory().getPaymentHistoryPagination().getPayments().length;i++)
				{
					System.out.println("Amount --> "+response.getTransactionHistory().getPaymentHistoryPagination().getPayments()[i].getAmount());
					System.out.println("Date --> "+response.getTransactionHistory().getPaymentHistoryPagination().getPayments()[i].getDate());
					System.out.println("Currency --> "+response.getTransactionHistory().getPaymentHistoryPagination().getPayments()[i].getCurrency());
					System.out.println("Description --> "+response.getTransactionHistory().getPaymentHistoryPagination().getPayments()[i].getDescription());
					System.out.println("Debit --> "+response.getTransactionHistory().getPaymentHistoryPagination().getPayments()[i].getDebit());
					
				}
			}

			Assert.assertEquals(2, response.getTransactionHistory().getPaymentHistoryPagination().getPayments().length);			

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
			System.out.println("Before invoking getTalkHistory");

			// error handling assert
			Assert.assertEquals(Integer.valueOf(2), response.getStatusMessageResponse().getStatusMessage().getStatus());
			Assert.assertEquals("NOK", response.getStatusMessageResponse().getStatusMessage().getDescription());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
