package com.ericsson.cac.sprint.shop.workflow;

import com.ericsson.cac.sprint.shop.workflow.datamodel.CalculateTaxRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CalculateTaxResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CoverageAreaRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CoverageAreaResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.SubmitOrderRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.SubmitOrderResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ValidateAddressRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ValidatePromoCodeRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ValidatePromoCodeResponse;


public interface ShopEaiWorkflow {
	
	public ValidatePromoCodeResponse validatePromoCode(ValidatePromoCodeRequest request);
	com.ericsson.cac.sprint.shop.workflow.datamodel.ValidateAddressResponse validateAddress(ValidateAddressRequest request);
	
	CoverageAreaResponse checkCoverageArea(CoverageAreaRequest request);
	CalculateTaxResponse calculateTax(CalculateTaxRequest request);
	public SubmitOrderResponse submitOrder(SubmitOrderRequest request);
	
}
