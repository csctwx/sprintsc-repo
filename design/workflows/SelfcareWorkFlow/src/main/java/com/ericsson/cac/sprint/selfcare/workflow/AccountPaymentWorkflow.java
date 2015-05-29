package com.ericsson.cac.sprint.selfcare.workflow;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.CartPaymentResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.CartRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.FutureChargesResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.PaymentCCRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.PaymentInfoRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.PaymentRPVRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.PaymentResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.PaymentVoucherRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.RefillVoucherRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.RefillVoucherResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberAccountRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.TransactionHistoryResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;

public interface AccountPaymentWorkflow {

	public StatusMessageResponse cancelAutoPay(UserContextRequest request);

	public StatusMessageResponse deleteCard(UserContextRequest request);

	public StatusMessageResponse enrollAutopay(SubscriberAccountRequest request);

	public StatusMessageResponse updatePaymentOptions(PaymentInfoRequest request);

	public TransactionHistoryResponse getTransactionHistory(
			UserContextRequest request);

	public PaymentResponse makePayment(PaymentRPVRequest request);

	public PaymentResponse makePayment(PaymentCCRequest request);

	public PaymentResponse makePayment(PaymentVoucherRequest request);

	public RefillVoucherResponse refillWallet(RefillVoucherRequest request);

	public StatusMessageResponse cancelAdvancePayment(UserContextRequest request);

	public FutureChargesResponse getFutureCharges(UserContextRequest request);

	public StatusMessageResponse cancelFutureCharges(UserContextRequest request);
	
	public CartPaymentResponse makePayment(CartRequest request);
}
