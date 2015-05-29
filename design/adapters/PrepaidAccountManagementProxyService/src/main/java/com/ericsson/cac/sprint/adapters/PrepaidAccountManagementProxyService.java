package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.prepaidaccountmanagementservice.v1.prepaidaccountmanagementservice.PrepaidAccountManagementPortType;
import com.sprint.integration.eai.services.prepaidaccountmanagementservice.v1.prepaidaccountmanagementservice.PrepaidAccountManagementService;
import com.sprint.integration.eai.services.prepaidaccountmanagementservice.v1.prepaidaccountmanagementservice.SoapFault;
import com.sprint.integration.interfaces.manageadvancedpayment.v1.manageadvancedpayment.ManageAdvancedPaymentRequestType;
import com.sprint.integration.interfaces.manageadvancedpayment.v1.manageadvancedpayment.ManageAdvancedPaymentResponseType;
import com.sprint.integration.interfaces.rechargeaccountbycash.v1.rechargeaccountbycash.RechargeAccountByCashRequest;
import com.sprint.integration.interfaces.rechargeaccountbycash.v1.rechargeaccountbycash.RechargeAccountByCashResponse;
import com.sprint.integration.interfaces.rechargeaccountbyvoucher.v1.rechargeaccountbyvoucher.RechargeAccountByVoucherRequestType;
import com.sprint.integration.interfaces.rechargeaccountbyvoucher.v1.rechargeaccountbyvoucher.RechargeAccountByVoucherResponseType;
import com.sprint.integration.interfaces.restartplan.v1.restartplan.RestartPlanRequestType;
import com.sprint.integration.interfaces.restartplan.v1.restartplan.RestartPlanResponseType;






@Component
public class PrepaidAccountManagementProxyService extends PrepaidAccountManagementService {
    @Autowired
    private PrepaidAccountManagementPortType stub;
    
	public ManageAdvancedPaymentResponseType manageAdvancedPayment(ManageAdvancedPaymentRequestType  manageAdvancedPaymentRequest, Holder<WsMessageHeaderType> head) throws SoapFault{
		return stub.manageAdvancedPayment(manageAdvancedPaymentRequest, head);
	}
	
	public RechargeAccountByCashResponse rechargeAccountByCash(RechargeAccountByCashRequest rechargeAccountByCashRequest, Holder<WsMessageHeaderType> head) throws SoapFault{
		return stub.rechargeAccountByCash(rechargeAccountByCashRequest, head);
	}
	
	public RechargeAccountByVoucherResponseType rechargeAccountByVoucher(RechargeAccountByVoucherRequestType rechargeAccountByVoucherRequest, Holder<WsMessageHeaderType> head) throws SoapFault{
		return stub.rechargeAccountByVoucher(rechargeAccountByVoucherRequest, head);
	}
	
	public RestartPlanResponseType restartPlan(RestartPlanRequestType restartPlanRequest, Holder<WsMessageHeaderType> head) throws SoapFault{
		return stub.restartPlan(restartPlanRequest, head);
	}
		
}
