package com.ericsson.cac.sprint.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ericsson.cac.sprint.shop.workflow.ShopWorkflow;
import com.ericsson.cac.sprint.shop.workflow.datamodel.AssetRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CompareDeviceRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ComparePhoneRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ComparePhoneResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.EquipmentAvailabilityRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.EquipmentAvailabilityResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetFeatureRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetAccessoryResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetBannerResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetCompareDeviceResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetFeaturesResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetListAccessoriesResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetListPhonesResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetListPlansResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetPhoneDetailsRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetPhoneDetailsResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetPhoneGenieListRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetPhoneListRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetPhoneResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetPlanResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetShippingListRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ListAssetRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.PhoneGenieListResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ShippingListResponse;

@Controller
@RequestMapping(BaseController.SHOP_URI_PREFIX)
public class ShopWorkflowController implements BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ShopWorkflowController.class);
	
	@Autowired
	public ShopWorkflow shopWorkflow;
	
    @RequestMapping(value = "/getBanner", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody GetBannerResponse getBanner(@RequestParam("assetId") String assetId) {
    	LOGGER.debug("getBanner called with assetid = "+assetId);
    	AssetRequest request = new AssetRequest();
    	request.setAssetId(assetId);
    	GetBannerResponse getBannerResponse = shopWorkflow.getBanner(request);
    	LOGGER.debug("getBanner completed with status code  = "+getBannerResponse.getStatus());    	
	return getBannerResponse;
    }

    @RequestMapping(value = "/getListAccessories", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody GetListAccessoriesResponse getListAccessories(@RequestParam("listId") String listId) {
    	LOGGER.debug("getListAccessories called with listId = "+listId);
    	ListAssetRequest request = new ListAssetRequest();
    	request.setListId(listId);
    	GetListAccessoriesResponse getListAccessoriesResponse = shopWorkflow.getListAccessories(request);		
    	LOGGER.debug("getListAccessories completed with status code  = "+getListAccessoriesResponse.getStatus());
	return getListAccessoriesResponse;
    }

    @RequestMapping(value = "/getPlan", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody GetPlanResponse getPlan(@RequestParam("assetId") String assetId) {
    	LOGGER.debug("getPlan called with assetid = "+assetId);
    	AssetRequest request = new AssetRequest();
    	request.setAssetId(assetId);
    	GetPlanResponse getPlanResponse = shopWorkflow.getPlan(request);
    	LOGGER.debug("getPlan completed with status code  = "+getPlanResponse.getStatus());    	
	return getPlanResponse;
    }
    
    @RequestMapping(value = "/getAccessory", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody GetAccessoryResponse getAccessory(@RequestParam("assetId") String assetId) {
    	LOGGER.debug("getAccessory called with assetid = "+assetId);
    	AssetRequest request = new AssetRequest();
    	request.setAssetId(assetId);
		GetAccessoryResponse getAccessoryResponse = shopWorkflow.getAccessory(request);		
    	LOGGER.debug("getAccessory completed with status code  = "+getAccessoryResponse.getStatus());
	return getAccessoryResponse;
    }
    
    @RequestMapping(value = "/getPlans", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody GetListPlansResponse getPlans(@RequestParam("listId") String listId) {
    	LOGGER.debug("getPlans called with listId = "+listId);
    	ListAssetRequest request = new ListAssetRequest();
    	request.setListId(listId);
   	   	GetListPlansResponse getListPlansResponse = shopWorkflow.getPlans(request);
    	LOGGER.debug("getPlans completed with status code  = "+getListPlansResponse.getStatus());
    	return getListPlansResponse;
    }
    
    @RequestMapping(value = "/compare", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody GetCompareDeviceResponse compare(@RequestParam("externalId1") String externalId1,@RequestParam("externalId2") String externalId2,
    														@RequestParam("externalId3") String externalId3,@RequestParam("externalId4") String externalId4) {
    	LOGGER.debug("getPlans called with externalIds = "+externalId1 + ":: "+externalId2 + ":: "+externalId3+ ":: "+ externalId4);
    	CompareDeviceRequest request = new CompareDeviceRequest();
    	request.setExternalId1(externalId1);
    	request.setExternalId2(externalId2);
    	request.setExternalId3(externalId3);
    	request.setExternalId4(externalId4);
   
   	   	GetCompareDeviceResponse getCompareDeviceResponse = shopWorkflow.getCompareDeviceResponse(request);
    	LOGGER.debug("getPlans completed with status code  = "+getCompareDeviceResponse.getStatus());
    	return getCompareDeviceResponse;
    }

    @RequestMapping(value = "/getPhonesByBrandId", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody GetListPhonesResponse getPhonesByBrandId(@RequestParam("brandId") String brandId, 
    		@RequestParam(value="pageSize", required = false) Integer pageSize, 
    		@RequestParam(value="pageNumber", required = false, defaultValue = "1" ) Integer pageNumber) {
    	LOGGER.debug("getPhones called with brandId = "+brandId);
    	GetPhoneListRequest getPhoneListRequest = new GetPhoneListRequest();
    	getPhoneListRequest.setBrandId(brandId);
    	getPhoneListRequest.setPageSize(pageSize);
    	getPhoneListRequest.setPageNumber(pageNumber);
		GetListPhonesResponse getListPhonesResponse = shopWorkflow.getPhonesByBrandId(getPhoneListRequest );		
    	LOGGER.debug("getPhones completed with status code  = "+getListPhonesResponse.getStatus());
	return getListPhonesResponse;
    }
    
   /* @RequestMapping(value = "/getPhoneByExternalUrl", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody GetPhoneResponse getPhoneByExternalUrl(@RequestParam("url") String url,@RequestParam("brandId") String brandId) {
    	LOGGER.debug("getPhoneByExternalUrl called with url="+url+"and  brandId = "+brandId);
    	GetPhoneResponse getPhoneResponse = shopWorkflow.getPhoneByExternalUrl(url,brandId);		
    	LOGGER.debug("getPhones completed with status code  = "+getPhoneResponse.getStatus());
	return getPhoneResponse;
    }*/
    
    @RequestMapping(value = "/getPhoneByExternalUrl", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody GetPhoneDetailsResponse getPhoneByExternalUrl(@RequestParam("phoneId") String phoneId,@RequestParam("brandId") String brandId) {
    	LOGGER.debug("getPhoneByExternalUrl called with phoneId="+phoneId+"and  brandId = "+brandId);
    	
    	GetPhoneDetailsRequest getPhoneDetailsRequest = new GetPhoneDetailsRequest();
    	getPhoneDetailsRequest.setPhoneId(phoneId);
    	getPhoneDetailsRequest.setBrandId(brandId);
    	
    	GetPhoneDetailsResponse getPhoneDetailsResponse = shopWorkflow.getPhoneByExternalUrl(getPhoneDetailsRequest);		
    	LOGGER.debug("getPhones completed with status code  = "+getPhoneDetailsResponse.getStatus());
	return getPhoneDetailsResponse;
    }
    
    @RequestMapping(value = "/getFeatures", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody GetFeaturesResponse getFeatures(@RequestParam("phoneId") String url,@RequestParam("brandId") String brandId) {
    	LOGGER.debug("getFeatures called with url="+url +" brandId "+brandId);
    	GetFeatureRequest request=new GetFeatureRequest();
    	request.setPhoneId(url);
    	request.setBrandId(brandId);
    	GetFeaturesResponse getFeaturesResponse = shopWorkflow.getFeatures(request,false);		
    	//LOGGER.debug("getPhones completed with status code  = "+getPhoneResponse.getStatus());
	return getFeaturesResponse;
    }
    
    @RequestMapping(value = "/getShippingList", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody ShippingListResponse getShippingList(@RequestParam("brandId") String brandId) {
    	LOGGER.debug("getShippingList called with brandId = "+brandId);
    	
    	GetShippingListRequest getShippingListRequest = new GetShippingListRequest();
    	getShippingListRequest.setBrandId(brandId);
    	
    	ShippingListResponse shippingListResponse = shopWorkflow.getShippingList(getShippingListRequest);		
    	LOGGER.debug("getPhones completed with status code  = "+shippingListResponse.getStatus());
	return shippingListResponse;
    }
    
    @RequestMapping(value = "/getComparePhone", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody ComparePhoneResponse getComparePhone(@RequestParam("phones") String phones,@RequestParam("brandId") String brandId){
    	LOGGER.info("getComparePhone called with brandId = "+brandId);
    	LOGGER.info("getComparePhone called with phones = "+phones);
    	ComparePhoneRequest request = new ComparePhoneRequest();
    	request.setBrandId(brandId);
    	request.setPhones(phones.split(","));
    	ComparePhoneResponse response = shopWorkflow.getComparePhone(request);
    	LOGGER.info("getComparePhone completed  with status = "+response.getStatus());
    	return response;
    }
    
    @RequestMapping(value = "/getEquipmentAvailability", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody EquipmentAvailabilityResponse getEquipmentAvailability(@RequestParam("brandId") String brandId, @RequestParam("modelId") String modelId, @RequestParam("ispreorder") boolean ispreorder ) {
    	LOGGER.info("getEquipmentAvailability called with brandId = "+brandId);
    	EquipmentAvailabilityRequest availabilityRequest = new EquipmentAvailabilityRequest();
    	availabilityRequest.setBrandId(brandId.toUpperCase());
    	availabilityRequest.setIspreorder(ispreorder);
    	availabilityRequest.setModelId(modelId);
    	EquipmentAvailabilityResponse availabilityResponse = shopWorkflow.getEquipmentAvailability(availabilityRequest);
    	LOGGER.info("getEquipmentAvailability completed  with status = "+availabilityResponse.getStatus());
    	return availabilityResponse;

	}
    
    @RequestMapping(value = "/getPhoneGenieListService", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody PhoneGenieListResponse getPhoneGenieList(@RequestParam("brandId") String brandId) {
    	LOGGER.debug("getPhoneGenieListService called with  brandId "+brandId);
    	GetPhoneGenieListRequest request= new GetPhoneGenieListRequest();
    	request.setBrandId(brandId.toUpperCase());
    	PhoneGenieListResponse getPhoneGenieListResponse = shopWorkflow.getPhoneGenieList(request);		
    	//LOGGER.debug("getPhones completed with status code  = "+getPhoneResponse.getStatus());
	return getPhoneGenieListResponse ;
    }
    
}
