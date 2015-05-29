package com.ericsson.cac.sprint.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ericsson.cac.sprint.shop.workflow.ShopEppWorkFlow;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CompletePurchaseRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CompletePurchaseResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.QueryChannelPolicyResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.QueryTotalOrderAmountResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ShippingAndBillingRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ShippingAndBillingResponse;


@Controller
@RequestMapping(BaseController.SHOP_URI_PREFIX)
public class ShopEppWorkflowController implements BaseController{
	
	@Autowired
	public ShopEppWorkFlow shopEppWorkFlow;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShopEppWorkflowController.class);
	
	@RequestMapping(value = "/doShippingBilling", method = RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody ShippingAndBillingResponse doShippingBilling(@RequestBody ShippingAndBillingRequest shippingAndBillingRequest) {
    	LOGGER.debug("doShippingBilling called with billingInfo = "+shippingAndBillingRequest.getShippingInfo().getAddress1());
    	ShippingAndBillingResponse shippingAndBillingResponse = shopEppWorkFlow.doShippingBilling(shippingAndBillingRequest);
    	LOGGER.debug("doShippingBilling completed with status code  = "+shippingAndBillingResponse.getStatus());    	
	return shippingAndBillingResponse;
    }
	
	@RequestMapping(value = "/doCompletePurchase", method = RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody CompletePurchaseResponse doCompletePurchase(@RequestBody CompletePurchaseRequest completePurchaseRequest) {
    	LOGGER.debug("Complete Purchase Response called with billingInfo = "+completePurchaseRequest.getShippingInfo().getAddress1());
    	CompletePurchaseResponse completePurchaseResponse = shopEppWorkFlow.doCompletePurchase(completePurchaseRequest);
    	LOGGER.debug("Complete Purchase Response completed with status code  = "+completePurchaseResponse.getStatus());    	
	return completePurchaseResponse;
    }
	
	@RequestMapping(value = "/getQueryChannelPolicy", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody QueryChannelPolicyResponse getQueryChannelPolicy(@RequestParam(value="brandId", required=false, defaultValue="SPP") String brandId) {
    	LOGGER.debug("QueryChannelPolicyRequest Called with brandId ::" + brandId);
    	QueryChannelPolicyResponse queryChannelPolicyResponse = shopEppWorkFlow.getQueryChannelPolicy(brandId);
    	LOGGER.debug("QueryChannelPolicyRequest completed with status code  = "+queryChannelPolicyResponse.getStatus());    	
	return queryChannelPolicyResponse;
    }
	
	@RequestMapping(value = "/queryTotalOrderAmount", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public @ResponseBody QueryTotalOrderAmountResponse queryTotalOrderAmount()
	{
		return shopEppWorkFlow.queryTotalOrderAmount();
	}

}
