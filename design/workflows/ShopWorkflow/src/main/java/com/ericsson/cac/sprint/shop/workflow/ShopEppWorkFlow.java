package com.ericsson.cac.sprint.shop.workflow;

import com.ericsson.cac.sprint.shop.workflow.datamodel.CompletePurchaseRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CompletePurchaseResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.QueryChannelPolicyRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.QueryChannelPolicyResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.QueryTotalOrderAmountResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ShippingAndBillingRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ShippingAndBillingResponse;



public interface ShopEppWorkFlow {
	/*CaptureShopPaymentResponse capturePayment(CaptureShopPaymentRequest request);
	AuthorizeShopPaymentResponse authorizePayment(AuthorizeShopPaymentRequest request);
	
	CancelAuthorizeShopResponse cancelAuthorize(CancelAuthorizeShopRequest request);
	GenerateShopTokenResponse generateToken(GenerateShopTokenRequest request);
	ValidateShopPaymentResponse validatePayment(ValidateShopPaymentRequest request);*/
	public CompletePurchaseResponse doCompletePurchase(CompletePurchaseRequest request);
	public ShippingAndBillingResponse doShippingBilling(ShippingAndBillingRequest request);
	public QueryChannelPolicyResponse getQueryChannelPolicy(String brandId);
	
	public QueryTotalOrderAmountResponse queryTotalOrderAmount();

}
