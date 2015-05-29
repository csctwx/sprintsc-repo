package com.ericsson.cac.sprint.selfcare.workflow.impl;

import org.springframework.stereotype.Component;

import com.ericsson.cac.sprint.selfcare.workflow.AccountPaymentWorkflow;
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

@Component
public class AccountPaymentWorkflowImpl implements AccountPaymentWorkflow{

	@Override
	public StatusMessageResponse cancelAutoPay(UserContextRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusMessageResponse deleteCard(UserContextRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusMessageResponse enrollAutopay(SubscriberAccountRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusMessageResponse updatePaymentOptions(PaymentInfoRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionHistoryResponse getTransactionHistory(
			UserContextRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaymentResponse makePayment(PaymentRPVRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaymentResponse makePayment(PaymentCCRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaymentResponse makePayment(PaymentVoucherRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RefillVoucherResponse refillWallet(RefillVoucherRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusMessageResponse cancelAdvancePayment(UserContextRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FutureChargesResponse getFutureCharges(UserContextRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusMessageResponse cancelFutureCharges(UserContextRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CartPaymentResponse makePayment(CartRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
