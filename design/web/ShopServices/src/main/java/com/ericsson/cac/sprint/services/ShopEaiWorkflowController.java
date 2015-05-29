package com.ericsson.cac.sprint.services;



import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ericsson.cac.sprint.shop.constants.ShopWorkFlowConstants;
import com.ericsson.cac.sprint.shop.workflow.ShopEaiWorkflow;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ValidatePromoCodeRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ValidatePromoCodeResponse;

@Controller
@RequestMapping(BaseController.SHOP_URI_PREFIX)
public class ShopEaiWorkflowController implements BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ShopEaiWorkflowController.class);
	
	@Autowired
	public ShopEaiWorkflow shopEaiWorkflow;
	
    @RequestMapping(value = "/validatePromoCode", method = RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody ValidatePromoCodeResponse validatePromoCode(@Valid @RequestBody ValidatePromoCodeRequest request,BindingResult validation) {
    	ValidatePromoCodeResponse validatePromoCodeResponse =null;
    	if(validation.hasErrors())
    	{
    		 validatePromoCodeResponse = new ValidatePromoCodeResponse();
    		 validatePromoCodeResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
    		 validatePromoCodeResponse.setDescription(validation.getFieldErrors().toString());
    		 return validatePromoCodeResponse;
    	}
    	LOGGER.debug("validatePromoCode called with promoCode = "+request.getPromoCode());
    	 validatePromoCodeResponse = shopEaiWorkflow.validatePromoCode(request);
    	LOGGER.debug("validatePromoCode completed with status code  = "+validatePromoCodeResponse.getStatus());    	
	return validatePromoCodeResponse;
    }
    
}
