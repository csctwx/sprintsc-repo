package com.ericsson.sprint.msdp.selfcare.controllers.abstracts.payments;

import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.OrderHistory;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.PaymentHistory;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SearchCriteriaRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.TransactionHistoryResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.AbstractBaseController;

public abstract class AbstractPaymentsHistoryController extends AbstractBaseController {

	@RequestMapping(value = "/my-account-payments-transaction-history", method = {RequestMethod.GET, RequestMethod.POST})
	public String history(
			Model model,
			@RequestParam(value = "daysAgo", defaultValue = "7") String daysAgo,
			@RequestParam(value = "pageNumber", defaultValue = "1") String pageNumber,
			@RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId, 
			HttpSession session) {
		UserContextRequest request = createUserContextRequestWithMdn(subscriberId, session);
		
		SearchCriteriaRequest criteria = new SearchCriteriaRequest();
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, Integer.parseInt(daysAgo) * -1);
		criteria.setFromDate(calendar.getTime());
		
		criteria.setPageNumber(Integer.parseInt(pageNumber));
		
		TransactionHistoryResponse transactionHistory = accountService.getTransactionHistory(request, criteria);
		
		OrderHistory[] orders = transactionHistory.getTransactionHistory().getOrders();
		PaymentHistory[] payments = transactionHistory.getTransactionHistory().getPaymentHistoryPagination().getPayments();
		
		model.addAttribute("orders", orders);
		model.addAttribute("payments", payments);
		
		return resolvePath("myaccount/payments/payments-transaction-history");
	}


}
